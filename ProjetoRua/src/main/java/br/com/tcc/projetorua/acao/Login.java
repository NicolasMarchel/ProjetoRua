package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.xpath.XPathExpressionException;

import br.com.tcc.projetorua.dao.BeneficiarioDao;
import br.com.tcc.projetorua.dao.PrestadorAjudaDao;
import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.model.Prestador;
import br.com.tcc.projetorua.security.HashSenha;
import br.com.tcc.projetorua.util.JPAUtil;

public class Login implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		EntityManager em = JPAUtil.getEntityManager();

		Beneficiario beneficiario = null;
		Prestador prestadorAjuda = null;
		String hashSenha = "";
		String login = "";
		Long longlogin;
		String senha = "";

		try {
			login = request.getParameter("login");
			longlogin = Long.parseLong(login);
			senha = request.getParameter("senha");
			hashSenha = new HashSenha().RetornaHashSenha(senha);
		} catch (NumberFormatException e) {

			return "redirect:controller?call=LoginForm";
		} catch (Exception e) {

			return "redirect:controller?call=LoginForm";
		}

		try (BeneficiarioDao beneficiarioDao = new BeneficiarioDao(em)) {
			beneficiario = beneficiarioDao.existeUsuario(hashSenha, longlogin);
		} catch (NoResultException e) {

			beneficiario = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		em = JPAUtil.getEntityManager();
		try (PrestadorAjudaDao prestadorAjudaDao = new PrestadorAjudaDao(em)) {
			prestadorAjuda = prestadorAjudaDao.existeUsuario(hashSenha, longlogin);
		} catch (NoResultException e) {
			prestadorAjuda = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (beneficiario != null) {

			HttpSession sessao = request.getSession();
			sessao.setAttribute("usuarioLogado", beneficiario);

		
			return "redirect:controller?call=ListarHistoriaForm";

		}
		if (prestadorAjuda != null) {

			HttpSession sessao = request.getSession();
			sessao.setAttribute("usuarioLogado", prestadorAjuda);

			return "redirect:controller?call=ListarServicoForm";

		}
		return "redirect:controller?call=LoginForm";

	}

}
