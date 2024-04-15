package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.dao.BeneficiarioDao;
import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class ListarHistoriasForm implements Acao {
	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		List<Beneficiario> beneficiarios = null;

		EntityManager em = JPAUtil.getEntityManager();

		try (BeneficiarioDao beneficiarioDao = new BeneficiarioDao(em);) {

			beneficiarios = beneficiarioDao.buscarBeneficiarios();
		} catch (NoResultException e) {

			beneficiarios = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String categoria = request.getParameter("categoria");
		
		
		FormatUtf8 formatUtf8 = new FormatUtf8();
		request.setAttribute("beneficiarios", beneficiarios);
		request.setAttribute("formatUtf8", formatUtf8);
		
		
		
		return "forward:formListarHistorias.jsp";
	}
}
