package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class SessaoInvalidadaException extends Exceptions{

	private static final long serialVersionUID = -2819521965008916437L;
	
	public SessaoInvalidadaException() {
		super("", HttpStatus.BAD_REQUEST);
	}

}
