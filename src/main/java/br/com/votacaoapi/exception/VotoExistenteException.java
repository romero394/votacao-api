package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class VotoExistenteException extends Exceptions {

	private static final long serialVersionUID = -7152395310651864055L;
	
	public VotoExistenteException() {
		super("", HttpStatus.ALREADY_REPORTED);
	}

}
