package br.com.votacaoapi.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votacaoapi.entity.SessaoVotacao;

@Repository
public interface SessaoVotacaoDao extends JpaRepository<SessaoVotacao, Long>{

	Optional<SessaoVotacao> findByIdAndPautaVotacaoId(Long id, Long pautaId);
	
	Long countByPautaVotacaoId(Long id);
	
	Optional<List<SessaoVotacao>> findByPautaVotacaoId(Long id);
}
