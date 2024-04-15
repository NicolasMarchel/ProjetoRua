package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;

import br.com.tcc.projetorua.security.FormatUtf8;

public class CadastrarPrestadorAjudaForm implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		// Recupera os parâmetros da URL
		String nomeInstituicao = request.getParameter("nomeInstituicao");
		String cnpj = request.getParameter("cnpj");
		String descricao = request.getParameter("descricao");
		String cep = request.getParameter("cep");
		String uf = request.getParameter("uf");
		String cidade = request.getParameter("cidade");
		String bairro = request.getParameter("bairro");
		String rua = request.getParameter("rua");
		String numero = request.getParameter("numero");

		if (nomeInstituicao != null) {

			// Armazena os parâmetros como atributos na requisição
			request.setAttribute("nomeInstituicao", nomeInstituicao);
			request.setAttribute("cnpj", cnpj);
			request.setAttribute("descricao", descricao);
			request.setAttribute("cep", cep);
			request.setAttribute("uf", uf);
			request.setAttribute("cidade", cidade);
			request.setAttribute("bairro", bairro);
			request.setAttribute("rua", rua);
			request.setAttribute("numero", numero);

		}

		String status = (String) request.getSession().getAttribute("status");

		if (status != null) {

			request.getSession().removeAttribute("status");
			request.setAttribute("status", status);

		}
		FormatUtf8 formatUtf8 = new FormatUtf8();
		request.setAttribute("formatUtf8", formatUtf8);
		return "forward:formCadastrarPrestadorAjuda.jsp";
	}
}
