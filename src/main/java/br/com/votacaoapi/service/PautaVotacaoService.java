package br.com.votacaoapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.votacaoapi.dao.PautaVotacaoDao;
import br.com.votacaoapi.entity.PautaVotacao;
import br.com.votacaoapi.exception.PautaVotacaoNotFoundException;

@Service
public class PautaVotacaoService {

	private PautaVotacaoDao pautaVotacaoDao;
	private SessaoVotacaoService sessaoVotacaoService;
	private VotoService votoService;
	
	public PautaVotacaoService(PautaVotacaoDao pautaVotacaoDao, SessaoVotacaoService sessaoVotacaoService,
			VotoService votoService) {
		this.pautaVotacaoDao = pautaVotacaoDao;
		this.sessaoVotacaoService = sessaoVotacaoService;
		this.votoService = votoService;
	}
	
	public PautaVotacao salvar(PautaVotacao pautaVotacao) {
		return pautaVotacaoDao.save(pautaVotacao);
	}
	
	public List<PautaVotacao> getAll() {
		return pautaVotacaoDao.findAll();
	}
	
	public PautaVotacao getById(Long id) {
		Optional<PautaVotacao> pauta = pautaVotacaoDao.findById(id);
		if(!pauta.isPresent()) {
			throw new PautaVotacaoNotFoundException();
		}
		return pauta.get();
	}
	
	public void excluir(Long id) {
		Optional<PautaVotacao> pauta = pautaVotacaoDao.findById(id);
		if(!pauta.isPresent()) {
			throw new PautaVotacaoNotFoundException();
		}
		pautaVotacaoDao.delete(pauta.get());
		sessaoVotacaoService.excluirByPauta(id);
		votoService.excluirByPautaVotacao(id);
	}
	
}
