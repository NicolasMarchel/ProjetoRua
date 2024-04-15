package br.com.tcc.projetorua.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.acao.Acao;

//@WebServlet(urlPatterns="/controller")
public class ControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		String paramCall = request.getParameter("call");
		
		String nomeDaClasse = "br.com.tcc.projetorua.acao." + paramCall;
		
		String nome;
		try {
			Class classe = Class.forName(nomeDaClasse);
			Acao call = (Acao) classe.newInstance();
			nome = call.executa(request,response);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchAlgorithmException e) {
			throw new ServletException(e);
		}
		
		String[] tipoEndereco = nome.split(":");
		
		if(tipoEndereco[0].equals("forward")) {
			RequestDispatcher rd = request.getRequestDispatcher("WEB-INF/view/" + tipoEndereco[1]);
			rd.forward(request, response);
		} else {
			response.sendRedirect(tipoEndereco[1]);
		}
	}

}
