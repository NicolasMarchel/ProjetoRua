package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import br.com.tcc.projetorua.dao.AvatarDao;
import br.com.tcc.projetorua.model.Avatar;
import br.com.tcc.projetorua.util.JPAUtil;

public class CadastrarAvatar implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		EntityManager em = JPAUtil.getEntityManager();

		Part imagemPart = request.getPart("foto");

		// Cria um array de bytes baseado no tamanho da imagem
		byte[] imagemBytes = new byte[(int) imagemPart.getSize()];

		// Obtem o InputStream e o armazena no array
		try (InputStream inputStream = imagemPart.getInputStream()) {
			inputStream.read(imagemBytes);
		} catch (Exception e) {
			System.out.println("Erro!");
		}

		try (AvatarDao avatarDao = new AvatarDao(em)) {

			HttpSession sessao = request.getSession();
			Avatar avatar = new Avatar();

			em.getTransaction().begin();

			if (imagemBytes.length > 0) {
				avatar.setFoto(imagemBytes);
			}

			avatarDao.cadastrar(avatar);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Erro!");
		}

		return "redirect:controller?call=Home";
	}
}
