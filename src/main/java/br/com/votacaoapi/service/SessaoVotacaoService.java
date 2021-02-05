package br.com.votacaoapi.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacaoapi.dao.PautaVotacaoDao;
import br.com.votacaoapi.dao.SessaoVotacaoDao;
import br.com.votacaoapi.entity.PautaVotacao;
import br.com.votacaoapi.entity.SessaoVotacao;
import br.com.votacaoapi.exception.PautaVotacaoNotFoundException;
import br.com.votacaoapi.exception.SessaoVotacaoNotFoundException;
import br.com.votacaoapi.util.VotacaoUtil;

@Service
public class SessaoVotacaoService {

	private final SessaoVotacaoDao sessaoVotacaoDao;
	private final PautaVotacaoDao pautaVotacaoDao;
	
	@Autowired
	public SessaoVotacaoService(SessaoVotacaoDao sessaoVotacaoDao, PautaVotacaoDao pautaVotacaoDao) {
		this.sessaoVotacaoDao = sessaoVotacaoDao;
		this.pautaVotacaoDao = pautaVotacaoDao;
	}
	
	public List<SessaoVotacao> findAll() {
		return sessaoVotacaoDao.findAll();
	}
	
	public SessaoVotacao salvar(Long id, SessaoVotacao sessaoVotacao) {
		Optional<PautaVotacao> pautaVotacao = pautaVotacaoDao.findById(id);
		if(!pautaVotacao.isPresent()) {
			throw new PautaVotacaoNotFoundException();
		}
		sessaoVotacao.setPautaVotacao(pautaVotacao.get());
		
		return tratarSalvar(sessaoVotacao);
	}

	private SessaoVotacao tratarSalvar(SessaoVotacao sessaoVotacao) {
		if(VotacaoUtil.nuloOuVazio(sessaoVotacao.getData())) {
			sessaoVotacao.setData(LocalDateTime.now());
		}
		
		if(VotacaoUtil.nuloOuVazio(sessaoVotacao.getEncerramentoSessao())) {
			sessaoVotacao.setEncerramentoSessao(1L);
		}
		
		return sessaoVotacaoDao.save(sessaoVotacao);
	}
	
	public void excluir(Long id) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoDao.findById(id);
		if(!sessaoVotacao.isPresent()) {
			throw new SessaoVotacaoNotFoundException();
		}
		sessaoVotacaoDao.delete(sessaoVotacao.get());
	}
	
	public SessaoVotacao getById(Long id) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoDao.findById(id);
		if(!sessaoVotacao.isPresent()) {
			throw new SessaoVotacaoNotFoundException();
		}
		return sessaoVotacao.get();
	}
	
	public void excluirByPauta(Long id) {
		Optional<List<SessaoVotacao>> sessoes = sessaoVotacaoDao.findByPautaVotacaoId(id);
		sessoes.ifPresent(sessaoList -> sessaoList.forEach(sessaoVotacaoDao::delete));
	}
	
	public SessaoVotacao getByIdAndPautaVotacaoId(Long idSessaoVotacao, Long pautaVotacaoId) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoDao.findByIdAndPautaVotacaoId(idSessaoVotacao, pautaVotacaoId);
		if(!sessaoVotacao.isPresent()) {
			throw new SessaoVotacaoNotFoundException();
		}
		return sessaoVotacao.get();
	}
}
