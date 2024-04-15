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
import br.com.tcc.projetorua.dao.DificuldadeDao;
import br.com.tcc.projetorua.dao.EnderecoDao;
import br.com.tcc.projetorua.dao.RecorrenciaDao;
import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.model.Dificuldade;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class VisualizarHistoriaForm implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		String paramId = request.getParameter("id");
		Long id = Long.valueOf(paramId);
		Beneficiario beneficiario = null;
		FormatUtf8 formatUtf8 = new FormatUtf8();
		
		//recupera a instancia do beneficiario
		
		
		EntityManager em = JPAUtil.getEntityManager();
		try (BeneficiarioDao beneficiarioDao = new BeneficiarioDao(em)) {

			em.getTransaction().begin();
			beneficiario = beneficiarioDao.buscarBeneficiarioPorId(id);	
			em.getTransaction().commit();
			em.close();

		} catch (NoResultException e) {
			beneficiario = null;
		} catch (Exception e) {
			beneficiario = null;
			e.printStackTrace();
		}
		
		
		//Pega as vulnerabilidade dos beneficiario
		List<Dificuldade> dificuldades=null;

		 em = JPAUtil.getEntityManager();

		try (DificuldadeDao dificuldadeDao = new DificuldadeDao(em);) {

			dificuldades = dificuldadeDao.buscarDificuldadesBeneficiario(beneficiario);
		} catch (NoResultException e) {

			dificuldades = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		request.setAttribute("dificuldades", dificuldades);
		request.setAttribute("formatUtf8", formatUtf8);
		request.setAttribute("beneficiario", beneficiario);
		return "forward:formVisualizarHistoria.jsp";

	}

}
