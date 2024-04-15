package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;

public class Home implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		Boolean sucessoCadastro = (Boolean) request.getSession().getAttribute("sucesso");

		if (sucessoCadastro != null && sucessoCadastro.booleanValue()) {

			request.getSession().removeAttribute("sucesso");
			request.setAttribute("sucesso", true);
			System.out.println("cadastro concluido");
		}

		return "forward:index.jsp";
	}

}
