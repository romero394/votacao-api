package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class CpfInvalidoException extends Exceptions {

	private static final long serialVersionUID = 5818237683198022362L;
	
	public CpfInvalidoException() {
		super("", HttpStatus.BAD_REQUEST);
	}

}
