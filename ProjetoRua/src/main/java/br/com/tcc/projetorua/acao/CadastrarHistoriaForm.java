package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.dao.AvatarDao;
import br.com.tcc.projetorua.model.Avatar;
import br.com.tcc.projetorua.security.FormatUtf8;
import br.com.tcc.projetorua.util.JPAUtil;

public class CadastrarHistoriaForm implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		EntityManager em = JPAUtil.getEntityManager();

		try (AvatarDao avatarDao = new AvatarDao(em)) {

			List<Avatar> avatares = avatarDao.buscarAvatares();
			request.setAttribute("avatares", avatares);
			
			
		} 
		FormatUtf8 formatUtf8 = new FormatUtf8();
		request.setAttribute("formatUtf8", formatUtf8);
		return "forward:formCadastrarHistoria.jsp";
	}
}
