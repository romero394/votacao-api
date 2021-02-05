package br.com.votacaoapi.dao;

import org.springframework.stereotype.Repository;

import br.com.votacaoapi.entity.Voto;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface VotoDao extends JpaRepository<Voto, Long>{

	Optional<Voto> findByCpf(String cpf);
	
	Optional<List<Voto>> findByPautaVotacaoId(Long id);

    Optional<Voto> findByCpfAndPautaVotacaoId(String cpf, Long id);
}
