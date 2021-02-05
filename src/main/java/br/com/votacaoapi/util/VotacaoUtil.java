package br.com.votacaoapi.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class VotacaoUtil {

	public static boolean nuloOuVazio(String campo) {
		return StringUtils.isBlank(campo);
	}
	
	public static boolean nuloOuVazio(Integer campo) {
		boolean retorno = false;
		if (campo == null || campo == 0) {
			retorno = true;
		}
		return retorno;
	}
	
	public static boolean nuloOuVazio(Object objeto) {
		if (objeto instanceof String) {
			return nuloOuVazio((String) objeto);
		}

		return objeto == null;
	}
	
	public static boolean isCpfValido(String cpf) {
		if (nuloOuVazio(cpf)) {
			return false;
		}

		String cpfLocal = removerCaracteresCPFCNPJ(cpf);

		if (cpfLocal.length() != 11 || isCpfSequencial(cpfLocal)) {
			return false;
		}

		// Valida 1o digito
		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpfLocal.charAt(i)) * (10 - i);
		}

		int resultado = 11 - (soma % 11);
		if (resultado == 10 || resultado == 11) {
			resultado = 0;
		}

		if (resultado != Character.getNumericValue(cpfLocal.charAt(9))) {
			return false;
		}

		// Valida 2o digito
		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpfLocal.charAt(i)) * (11 - i);
		}

		resultado = 11 - (soma % 11);
		if (resultado == 10 || resultado == 11) {
			resultado = 0;
		}

		if (resultado != Character.getNumericValue(cpfLocal.charAt(10))) {
			return false;
		}
		return true;
	}
	
	public static String removerCaracteresCPFCNPJ(String valor) {
		if (nuloOuVazio(valor)) {
			return null;
		}
		return valor.replaceAll("[.\\-/]", "");
	}
	
	private static boolean isCpfSequencial(String cpf) {
		return obterListaNumerosSequenciais(11).contains(cpf);
	}
	
	private static List<String> obterListaNumerosSequenciais(int tamanho) {
		ArrayList<String> lista = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			String numero = String.valueOf(i);
			lista.add(StringUtils.rightPad(numero, tamanho, numero));
		}
		return lista;
	}
}
