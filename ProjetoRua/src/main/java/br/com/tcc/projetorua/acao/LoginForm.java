package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;

public class LoginForm implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		

		boolean abrirLogin = true;

		request.setAttribute("fazerLogin", abrirLogin);
		return "forward:index.jsp";
	}
}
