package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class PautaVotacaoNotFoundException extends Exceptions {

	private static final long serialVersionUID = -2301744506376151177L;
	
	public PautaVotacaoNotFoundException() {
		super("", HttpStatus.NOT_FOUND);
	}

}
