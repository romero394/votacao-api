package br.com.votacaoapi.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "SESSAO_VOTACAO")
public class SessaoVotacao implements Serializable {

	private static final long serialVersionUID = -3917629018979810276L;
	
	@Id
    @SequenceGenerator(name = "voto_seq", sequenceName = "voto_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "voto_seq")
    private Long id;

	@Column(name = "DATA")
	private LocalDateTime data;

	@Column(name = "ENCERRAMENTO_SESSAO")
    private Long encerramentoSessao;
    
    @ManyToOne(fetch = FetchType.EAGER)
    private PautaVotacao pautaVotacao;
    
    public SessaoVotacao pautaVotacao(PautaVotacao pautaVotacao) {
        this.pautaVotacao = pautaVotacao;
        return this;
    }
}
