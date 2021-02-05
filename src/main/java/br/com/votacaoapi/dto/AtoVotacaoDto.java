package br.com.votacaoapi.dto;

import java.io.Serializable;

import br.com.votacaoapi.entity.PautaVotacao;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AtoVotacaoDto implements Serializable {
	
	private static final long serialVersionUID = 4543235642163021939L;
	
	private PautaVotacao pautaVotacao;
	private Integer votosSim;
	private Integer votosNao;
	private Integer totalSessoes;
	private Integer totalVotos;

}
