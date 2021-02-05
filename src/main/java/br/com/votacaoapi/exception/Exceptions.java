package br.com.votacaoapi.exception;

import org.springframework.http.HttpStatus;

public class Exceptions extends RuntimeException {

	private static final long serialVersionUID = -6361910240134987330L;
	
	private String codigoException;
	private HttpStatus statusException;
	
	public String getCodigoException() {
		return codigoException;
	}
	public void setCodigoException(String codigoException) {
		this.codigoException = codigoException;
	}
	public HttpStatus getStatusException() {
		return statusException;
	}
	public void setStatusException(HttpStatus statusException) {
		this.statusException = statusException;
	}
	public Exceptions(String codigoException, HttpStatus statusException) {
		this.codigoException = codigoException;
		this.statusException = statusException;
	}
}
