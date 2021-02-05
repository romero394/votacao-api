package br.com.votacaoapi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Table(name = "PAUTA_VOTACAO")
public class PautaVotacao implements Serializable {

	private static final long serialVersionUID = 7596951562145894517L;

//	@Id
//	@SequenceGenerator(name = "pauta_votacao_seq", sequenceName = "pauta_votacao_seq", allocationSize = 1)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pauta_votacao_seq")
	private Long id;

//	@Column(name = "NOME")
	private String nome;

}
