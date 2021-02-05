package br.com.votacaoapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SessaoVotacao implements Serializable {

	private static final long serialVersionUID = -3917629018979810276L;
	
    private Long id;

	private LocalDateTime data;

    private Long encerramentoSessao;
    
    private PautaVotacao pautaVotacao;
    
    public SessaoVotacao pautaVotacao(PautaVotacao pautaVotacao) {
        this.pautaVotacao = pautaVotacao;
        return this;
    }
}
