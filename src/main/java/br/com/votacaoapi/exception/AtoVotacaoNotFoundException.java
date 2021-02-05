package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class AtoVotacaoNotFoundException extends Exceptions {

	private static final long serialVersionUID = 5956262654448493330L;
	
	public AtoVotacaoNotFoundException() {
		super("", HttpStatus.NOT_FOUND);
	}

}
