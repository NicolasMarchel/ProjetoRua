package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.dao.ServicoDeAssistenciaDao;
import br.com.tcc.projetorua.model.Localizacao;
import br.com.tcc.projetorua.model.Servico;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class ProcurarServicos implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		List<Servico> servicos = null;
		List<Servico> servicosFiltrados = new ArrayList<>();
		EntityManager em = JPAUtil.getEntityManager();

		String categoria = request.getParameter("categoria");
		String origem = request.getParameter("cep");
		String distanciaService = request.getParameter("distancia");

		Integer distanciaMaxima = null;

		try {
			distanciaMaxima = Integer.parseInt(distanciaService);
		} catch (NumberFormatException e) {

			distanciaMaxima = 100;
		}

		String destino = "";

		try (ServicoDeAssistenciaDao servicoDeAssistenciaDao = new ServicoDeAssistenciaDao(em)) {
			servicos = servicoDeAssistenciaDao.buscarServicosPorCategoria(categoria);
		} catch (NoResultException e) {
			servicos = null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<Localizacao> listaLocalizacao = new ArrayList<>();
		for (Servico servico : servicos) {

			destino = servico.getEndereco().getCep();

			Localizacao localizacao = Localizacao.obterLocalizacao(origem, destino, distanciaMaxima);
			

			if (localizacao != null) {
				localizacao.setServico( servico);
				servicosFiltrados.add(servico);
				listaLocalizacao.add(localizacao);
			}
		}
		
		
		Collections.sort(listaLocalizacao);
		List<Servico> servicosOrdenados = new ArrayList<>();
		for (Localizacao localizacao : listaLocalizacao) {
		    servicosOrdenados.add(localizacao.getServico());
		}
		servicosFiltrados = servicosOrdenados;
		
		FormatUtf8 formatUtf8 = new FormatUtf8();
		request.setAttribute("servicos", servicosFiltrados);
		request.setAttribute("listaLocalizacao", listaLocalizacao);
		request.setAttribute("formatUtf8", formatUtf8);

		return "forward:formProcurarServicos.jsp";
	}

}
