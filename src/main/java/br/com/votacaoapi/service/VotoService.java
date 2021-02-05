package br.com.votacaoapi.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.votacaoapi.dao.VotoDao;
import br.com.votacaoapi.dto.AtoVotacaoDto;
import br.com.votacaoapi.dto.CPFAptoDto;
import br.com.votacaoapi.entity.PautaVotacao;
import br.com.votacaoapi.entity.SessaoVotacao;
import br.com.votacaoapi.entity.Voto;
import br.com.votacaoapi.exception.CpfInaptoException;
import br.com.votacaoapi.exception.CpfInvalidoException;
import br.com.votacaoapi.exception.SessaoInvalidadaException;
import br.com.votacaoapi.exception.TempoExcedidoSessaoException;
import br.com.votacaoapi.exception.VotoExistenteException;
import br.com.votacaoapi.exception.VotoNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Service
public class VotoService {

	private static final String CPF_UNABLE_TO_VOTE = "UNABLE_TO_VOTE";
	
	@Value("${app.integracao.cpf.url}")
	private String urlCpfValidator = "";
	
	private VotoDao votoDao;
	private SessaoVotacaoService sessaoVotacaoService;
	private RestTemplate restTemplate;
	private AtoVotacaoService atoVotacaoService;
	
	@Autowired
	public VotoService(VotoDao votoDao, SessaoVotacaoService sessaoVotacaoService, RestTemplate restTemplate, AtoVotacaoService atoVotacaoService) {
		this.votoDao = votoDao;
		this.sessaoVotacaoService = sessaoVotacaoService;
		this.restTemplate = restTemplate;
		this.atoVotacaoService = atoVotacaoService;
	}
	
	public Voto getById(Long id) {
		Optional<Voto> consulta = votoDao.findById(id);
		
		if(!consulta.isPresent()) {
			throw new VotoNotFoundException();
		}
		return consulta.get();
	}
	
	public List<Voto> getAll() {
		return votoDao.findAll();
	}
	
	public void excluir(Long id) {
		Optional<Voto> voto = votoDao.findById(id);
		if(!voto.isPresent()) {
			throw new VotoNotFoundException();
		}
		votoDao.delete(voto.get());
	}
	
	public void excluirByPautaVotacao(Long id) {
		Optional<List<Voto>> list = votoDao.findByPautaVotacaoId(id);
		list.ifPresent(voto -> voto.forEach(votoDao::delete));
	}
	
	private void enviarMensagem(PautaVotacao pautaVotacao) {
		AtoVotacaoDto atoVotacaoDto = atoVotacaoService.montarAtoVotacao(pautaVotacao.getId());
	}
	
	public void votosExistentes(Voto voto) {
		Optional<Voto> votoConsult = votoDao.findByCpfAndPautaVotacaoId(voto.getCpf(), voto.getPautaVotacao().getId());
		
		if(votoConsult.isPresent()) {
			throw new VotoExistenteException();
		}
	}
	
	public List<Voto> getVotosByPautaVotacao(Long id) {
		Optional<List<Voto>> list = votoDao.findByPautaVotacaoId(id);
		
		if(!list.isPresent()) {
			throw new VotoNotFoundException();
		}
		return list.get();
	}
	
	public Voto salvar(Long idPautaVotacao, Long idSessaoVotacao, Voto voto) {
		SessaoVotacao sessao = sessaoVotacaoService.getByIdAndPautaVotacaoId(idSessaoVotacao, idPautaVotacao);
		if(!sessao.equals(sessao.getPautaVotacao().getId())) {
			throw new SessaoInvalidadaException();
		}
		voto.setPautaVotacao(sessao.getPautaVotacao());
		return tratarSalvar(sessao, voto);
	}

	private Voto tratarSalvar(SessaoVotacao sessao, Voto voto) {
		verificarVoto(sessao, voto);
		
		return votoDao.save(voto);
	}

	private void verificarVoto(SessaoVotacao sessao, Voto voto) {
		LocalDateTime encerramentoSessao = sessao.getData().plusMinutes(sessao.getEncerramentoSessao());
		if(LocalDateTime.now().isAfter(encerramentoSessao)) {
			throw new TempoExcedidoSessaoException();
		}
		
		verificarCpfAptoVoto(voto);
	}

	private void verificarCpfAptoVoto(Voto voto) {
		ResponseEntity<CPFAptoDto> cpfApto = restIntegracao(voto);
		if(HttpStatus.OK.equals(cpfApto.getStatusCode())) {
			if(CPF_UNABLE_TO_VOTE.equalsIgnoreCase(cpfApto.getBody().getStatus())) {
				throw new CpfInaptoException();
			} else {
				throw new CpfInvalidoException();
			}
		}
	}

	private ResponseEntity<CPFAptoDto> restIntegracao(Voto voto) {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		return  restTemplate.exchange(urlCpfValidator.concat("/").concat(voto.getCpf()), HttpMethod.GET, entity,
				CPFAptoDto.class);
	}
}
