package br.com.votacaoapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.votacaoapi.dao.SessaoVotacaoDao;
import br.com.votacaoapi.dao.VotoDao;
import br.com.votacaoapi.dto.AtoVotacaoDto;
import br.com.votacaoapi.entity.PautaVotacao;
import br.com.votacaoapi.entity.Voto;
import br.com.votacaoapi.exception.AtoVotacaoNotFoundException;
import br.com.votacaoapi.exception.Exceptions;
import br.com.votacaoapi.exception.VotoNotFoundException;

@Service
public class AtoVotacaoService {

	private VotoDao votoDao;
	private SessaoVotacaoDao sessaoVotacaoDao;
	
	public AtoVotacaoService(VotoDao votoDao, SessaoVotacaoDao sessaoVotacaoDao) {
		this.votoDao = votoDao;
		this.sessaoVotacaoDao = sessaoVotacaoDao;
	}
	
	public List<Voto> getAll() {
		return votoDao.findAll();
	}
	
	public AtoVotacaoDto getResultado(Long id) {
		AtoVotacaoDto atoVotacao = montarAtoVotacao(id);
		return atoVotacao;
	}
	
	public AtoVotacaoDto montarAtoVotacao(Long id) {
		Optional<List<Voto>> list = votoDao.findByPautaVotacaoId(id);
		if(!list.isPresent() || list.get().isEmpty()) {
			throw new AtoVotacaoNotFoundException();
		}
		
		PautaVotacao pautaVotacao = list.get().iterator().next().getPautaVotacao();
		Long countSessoesVotacao = sessaoVotacaoDao.countByPautaVotacaoId(pautaVotacao.getId());
		Integer todosVotos = list.get().size();
		Integer votosSim = (int) list.get().stream().filter(voto -> Boolean.TRUE.equals(voto.getVotacao())).count();
		Integer votosNao = todosVotos - votosSim;
		
		return AtoVotacaoDto.builder().pautaVotacao(pautaVotacao).totalVotos(todosVotos).totalSessoes(countSessoesVotacao.intValue()).votosSim(votosSim).votosNao(votosNao).build();
	}

	public void excluir(Voto voto) {
		Optional<Voto> votoConsult = votoDao.findById(voto.getId());
		if(!votoConsult.isPresent()) {
			throw new VotoNotFoundException();
		}
		votoDao.delete(voto);
	}
	
	public List<Voto> getVotosByPautaVotacao(Long id) {
		Optional<List<Voto>> list = votoDao.findByPautaVotacaoId(id);
		if(!list.isPresent()) {
			throw new VotoNotFoundException();
		}
		return list.get();
	}
	
	public Voto salvar(Voto voto) {
		verificarVoto(voto);
		return votoDao.save(voto);
	}

	private void verificarVoto(Voto voto) {
		Optional<Voto> existeVoto = votoDao.findByCpf(voto.getCpf());
		
		if(existeVoto.isPresent() && (voto.isNew() || isNaoUnico(voto, existeVoto.get()))) {
			throw new Exceptions(null, null);
		}
	}

	private boolean isNaoUnico(Voto voto, Voto votoExiste) {
		return voto.alreadyExist() && !votoExiste.equals(voto);
	}
}
