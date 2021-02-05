package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class CpfInaptoException extends Exceptions {

	private static final long serialVersionUID = -4072147354840965153L;
	
	public CpfInaptoException() {
		super("", HttpStatus.UNAUTHORIZED);
	}

}
