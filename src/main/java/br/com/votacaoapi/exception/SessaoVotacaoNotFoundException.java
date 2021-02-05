package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class SessaoVotacaoNotFoundException extends Exceptions {

	private static final long serialVersionUID = -3411601107424778501L;
	
	public SessaoVotacaoNotFoundException() {
		super("", HttpStatus.NOT_FOUND);
	}

}
