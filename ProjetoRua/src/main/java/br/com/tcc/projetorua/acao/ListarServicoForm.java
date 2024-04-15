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
import javax.xml.xpath.XPathExpressionException;

import br.com.tcc.projetorua.dao.ServicoDeAssistenciaDao;
import br.com.tcc.projetorua.model.Prestador;
import br.com.tcc.projetorua.model.Servico;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class ListarServicoForm implements Acao {
	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		HttpSession sessao = request.getSession();
		Prestador usuarioLogado = (Prestador) sessao.getAttribute("usuarioLogado");
		List<Servico> servicos = null;
		
		

		EntityManager em = JPAUtil.getEntityManager();

		try (ServicoDeAssistenciaDao servicoDeAssistenciaDao = new ServicoDeAssistenciaDao(em);) {

			servicos = servicoDeAssistenciaDao.buscarServicosPrestador(usuarioLogado);
		} catch (NoResultException e) {

			servicos = null;

		} catch (Exception e) {
			e.printStackTrace();
		}
		// em = JPAUtil.getEntityManager();
		// try (RecorrenciaDao recorrenciaDao = new RecorrenciaDao(em);) {
		// recorrencias = recorrenciaDao.buscarRecorrencias();
		// } catch (NoResultException e) {

		// recorrencias = null;

		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// em = JPAUtil.getEntityManager();
		// try (CategoriaDao categoriaDao = new CategoriaDao(em);) {

		// categorias = categoriaDao.buscarCategorias();
		// } catch (NoResultException e) {

		// categorias = null;

		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		// em.close();
		request.setAttribute("servicos", servicos);
		
		FormatUtf8 formatUtf8=new FormatUtf8();
		
		request.setAttribute("formatUtf8", formatUtf8);
		// request.setAttribute("recorrencias", recorrencias);
		// request.setAttribute("categorias", categorias);

		return "forward:formListarServico.jsp";
	}

}
