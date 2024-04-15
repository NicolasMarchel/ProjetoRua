package br.com.tcc.projetorua.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashSenha {

	public String RetornaHashSenha(String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException {

		MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
		byte[] messageDigestSenha = algorithm.digest(senha.getBytes("UTF-8"));

		StringBuilder hexStringSenha = new StringBuilder();

		for (byte b : messageDigestSenha) {
			hexStringSenha.append(String.format("%02X", 0xFF & b));
		}

		return hexStringSenha.toString();

	}

	public boolean ValidaSeguranca(String senha) {
		// Verifica se a senha tem pelo menos 8 caracteres
		if (senha.length() < 8) {
			return false;
		}

		// Verifica se a senha contém pelo menos uma letra maiúscula
		if (!senha.matches(".*[A-Z].*")) {
			return false;
		}

		// Verifica se a senha contém pelo menos uma letra minúscula
		if (!senha.matches(".*[a-z].*")) {
			return false;
		}

		// Verifica se a senha contém pelo menos um número
		if (!senha.matches(".*\\d.*")) {
			return false;
		}

		// Verifica se a senha contém pelo menos um caractere especial
		if (!senha.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
			return false;
		}

		// Se a senha atender a todos os critérios, retorna true
		return true;
	}

}
