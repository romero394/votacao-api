package br.com.votacaoapi.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.votacaoapi.entity.PautaVotacao;

@Repository
public interface PautaVotacaoDao extends JpaRepository<PautaVotacao, Long>{

}
