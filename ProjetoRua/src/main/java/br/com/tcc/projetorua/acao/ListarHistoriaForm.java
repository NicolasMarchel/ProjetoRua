package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.tcc.projetorua.dao.DificuldadeDao;
import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.model.Dificuldade;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class ListarHistoriaForm implements Acao {
	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		FormatUtf8 formatUtf8 = new FormatUtf8();

		HttpSession sessao = request.getSession();
		Beneficiario beneficiario = (Beneficiario) sessao.getAttribute("usuarioLogado");

		List<Dificuldade> dificuldades=null;

		EntityManager em = JPAUtil.getEntityManager();

		try (DificuldadeDao dificuldadeDao = new DificuldadeDao(em);) {

			dificuldades = dificuldadeDao.buscarDificuldadesBeneficiario(beneficiario);
		} catch (NoResultException e) {

			dificuldades = null;

		} catch (Exception e) {
			e.printStackTrace();
		}

		request.setAttribute("formatUtf8", formatUtf8);
		request.setAttribute("dificuldades", dificuldades);
		return "forward:formListarHistoria.jsp";
	}

}
