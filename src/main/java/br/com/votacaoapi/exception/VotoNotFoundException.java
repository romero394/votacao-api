package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class VotoNotFoundException extends Exceptions {

	private static final long serialVersionUID = 7814153938553291034L;
	
	public VotoNotFoundException() {
		super("", HttpStatus.NOT_FOUND);
	}
}
