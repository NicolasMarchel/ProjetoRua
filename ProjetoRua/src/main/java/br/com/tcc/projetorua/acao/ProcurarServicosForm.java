package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.xpath.XPathExpressionException;

import br.com.tcc.projetorua.dao.ServicoDeAssistenciaDao;
import br.com.tcc.projetorua.model.Localizacao;
import br.com.tcc.projetorua.model.Servico;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class ProcurarServicosForm implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		List<Servico> servicos = null;

		EntityManager em = JPAUtil.getEntityManager();

		try (ServicoDeAssistenciaDao servicoDeAssistenciaDao = new ServicoDeAssistenciaDao(em);) {

			servicos = servicoDeAssistenciaDao.buscarServicos();
		} catch (NoResultException e) {

			servicos = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		FormatUtf8 formatUtf8 = new FormatUtf8();
		request.setAttribute("servicos", servicos);

		List<Localizacao> listaLocalizacao = null;
		request.setAttribute("listaLocalizacao", listaLocalizacao);
		request.setAttribute("formatUtf8", formatUtf8);

		return "forward:formProcurarServicos.jsp";
	}

}
