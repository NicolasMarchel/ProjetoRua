package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.dao.AvatarDao;
import br.com.tcc.projetorua.model.Avatar;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class CadastrarBeneficiarioForm implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		// Recupera os parâmetros da URL

		String primeiroNome = request.getParameter("primeiroNome");
		String ultimoNome = request.getParameter("ultimoNome");
		String cpf = request.getParameter("cpf");
		if (primeiroNome != null) {

			// Armazena os parâmetros como atributos na requisição
			request.setAttribute("primeiroNome", primeiroNome);
			request.setAttribute("ultimoNome", ultimoNome);
			request.setAttribute("cpf", cpf);

		}

		String status = (String) request.getSession().getAttribute("status");

		if (status != null) {

			request.getSession().removeAttribute("status");
			request.setAttribute("status", status);

		}
		
		FormatUtf8 formatUtf8 = new FormatUtf8();
	
		request.setAttribute("formatUtf8", formatUtf8);
		return "forward:formCadastrarBeneficiario.jsp";
	}

}
