package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.dao.EnderecoDao;
import br.com.tcc.projetorua.dao.RecorrenciaDao;
import br.com.tcc.projetorua.dao.ServicoDeAssistenciaDao;
import br.com.tcc.projetorua.model.Endereco;
import br.com.tcc.projetorua.model.Recorrencia;
import br.com.tcc.projetorua.model.Servico;
import br.com.tcc.projetorua.util.JPAUtil;

public class EditarServicoForm implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		//String paramId = request.getParameter("id");
		//Long id = Long.valueOf(paramId);
		//Servico servico = null;
		//Recorrencia recorrencia = null;
		//Endereco endereco = null;
		
		

		//request.setAttribute("listaLocalizacao", listaLocalizacao);
		//request.setAttribute("formatUtf8", formatUtf8);
		

		return "forward:formEditarServico.jsp";

	}

}
