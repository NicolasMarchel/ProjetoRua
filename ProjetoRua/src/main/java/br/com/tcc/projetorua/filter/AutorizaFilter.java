package br.com.tcc.projetorua.filter;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.model.Prestador;

//@WebFilter("/controller")
public class AutorizaFilter extends HttpFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String paramCall = null;

		String contentType = httpRequest.getContentType();
		if (contentType != null && contentType.startsWith("multipart/form-data")) {
			// Formularios do tipo multipart/form-data
			try {

				Collection<Part> parts = httpRequest.getParts();

				for (Part part : parts) {
					if ("call".equals(part.getName())) {
						paramCall = httpRequest.getParameter("call");
						break;
					}
				}

			} catch (Exception e) {
				paramCall = null;
			}
		} else {
			// Formularios padrao
			paramCall = httpRequest.getParameter("call");

		}
		System.out.println(paramCall);
		HttpSession sessao = httpRequest.getSession();
		boolean ehUmaAcaoProtegida = true;
		boolean usuarioNaoEstaLogado = (sessao.getAttribute("usuarioLogado") == null);
		Object usuarioLogado = sessao.getAttribute("usuarioLogado");

		if (usuarioNaoEstaLogado) {

			ehUmaAcaoProtegida = !(paramCall.equals("Home") || paramCall.equals("Login")
					|| paramCall.equals("LoginForm") || paramCall.equals("CadastrarBeneficiarioForm")
					|| paramCall.equals("CadastrarPrestadorAjudaForm") || paramCall.equals("CadastrarBeneficiario")
					|| paramCall.equals("CadastrarPrestadorAjuda") || paramCall.equals("Logout")|| paramCall.equals("ProcurarServicosForm")
					|| paramCall.equals("ProcurarServicos")|| paramCall.equals("CadastrarAvatarForm")|| paramCall.equals("CadastrarAvatar"));
			
			
			
			
		}
		if (usuarioLogado instanceof Prestador) {
			// sessao.getAttribute("usuarioLogado").getClass().getName()=="br.com.tcc.projetorua.model.PrestadorAjuda"
			ehUmaAcaoProtegida = !(paramCall.equals("Home") || paramCall.equals("Login")
					|| paramCall.equals("LoginForm") || paramCall.equals("CadastrarServico")
					|| paramCall.equals("CadastrarServicoForm") || paramCall.equals("Logout")
					|| paramCall.equals("CadastrarPrestadorAjudaForm") || paramCall.equals("CadastrarPrestadorAjuda")
					|| paramCall.equals("CadastrarBeneficiario") || paramCall.equals("CadastrarBeneficiarioForm") || 
					paramCall.equals("ListarServicoForm")|| 
					paramCall.equals("RemoverServico") || paramCall.equals("ProcurarServicosForm")	
					|| paramCall.equals("ProcurarServicos") || paramCall.equals("EditarServicoForm") 
					|| paramCall.equals("ListarHistoriasForm")|| paramCall.equals("ListarHistorias")
					|| paramCall.equals("VisualizarHistoriaForm"));
			
			
			
			
			
			
		}
		if (usuarioLogado instanceof Beneficiario) {
			// sessao.getAttribute("usuarioLogado").getClass().getName()=="br.com.tcc.projetorua.model.Beneficiario"
			ehUmaAcaoProtegida = !(paramCall.equals("Home") || paramCall.equals("Login")
					|| paramCall.equals("LoginForm") || paramCall.equals("CadastrarHistoriaForm")
					|| paramCall.equals("CadastrarHistoria") || paramCall.equals("Logout")
					|| paramCall.equals("CadastrarPrestadorAjudaForm") || paramCall.equals("CadastrarPrestadorAjuda")
					|| paramCall.equals("CadastrarBeneficiario") || paramCall.equals("CadastrarBeneficiarioForm") || paramCall.equals("ProcurarServicosForm")|| paramCall.equals("ProcurarServicos")
					|| paramCall.equals("ListarHistoriaForm"));
			
		}

		if (ehUmaAcaoProtegida) {
			httpResponse.sendRedirect("controller?call=LoginForm");
			return;
		}

		// if (ehUmaAcaoProtegida && usuarioNaoEstaLogado) {
		// httpResponse.sendRedirect("controller?call=LoginForm");
		// return;
		// }

		chain.doFilter(httpRequest, httpResponse);
	}

}
