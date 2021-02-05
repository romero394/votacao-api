package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class TempoExcedidoSessaoException extends Exceptions {
	private static final long serialVersionUID = -3891201610676582219L;

	public TempoExcedidoSessaoException() {
		super("", HttpStatus.REQUEST_TIMEOUT);
	}

}
