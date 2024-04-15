package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;

import br.com.tcc.projetorua.dao.EnderecoDao;
import br.com.tcc.projetorua.dao.RecorrenciaDao;
import br.com.tcc.projetorua.dao.ServicoDeAssistenciaDao;
import br.com.tcc.projetorua.model.Endereco;
import br.com.tcc.projetorua.model.Recorrencia;
import br.com.tcc.projetorua.model.Servico;
import br.com.tcc.projetorua.util.JPAUtil;

public class RemoverServico implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		String paramId = request.getParameter("id");
		Long id = Long.valueOf(paramId);
		Servico servico = null;
		Recorrencia recorrencia = null;
		Endereco endereco = null;

		EntityManager em = JPAUtil.getEntityManager();
		try (ServicoDeAssistenciaDao servicoDeAssistenciaDao = new ServicoDeAssistenciaDao(em);
				RecorrenciaDao recorrenciaDao = new RecorrenciaDao(em);
				EnderecoDao EnderecoDao = new EnderecoDao(em);) {

			em.getTransaction().begin();
			servico = servicoDeAssistenciaDao.buscarServicoPorId(id);
			recorrencia=servico.getRecorrencia();
			endereco=servico.getEndereco();
			servicoDeAssistenciaDao.remover(servico);
			recorrenciaDao.remover(recorrencia);
			EnderecoDao.remover(endereco);
			
			em.getTransaction().commit();
			em.close();

		} catch (NoResultException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:controller?call=ListarServicoForm";

	}

}
