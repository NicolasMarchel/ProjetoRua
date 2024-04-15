package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.dao.BeneficiarioDao;
import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.security.HashSenha;
import br.com.tcc.projetorua.util.JPAUtil;

public class CadastrarBeneficiario implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		EntityManager em = JPAUtil.getEntityManager();

		try (BeneficiarioDao beneficiarioDao = new BeneficiarioDao(em)) {

			String nome = request.getParameter("primeiroNome");

			nome = nome + " " + request.getParameter("ultimoNome");

			String senha = request.getParameter("senha");
			String confirmaSenha = request.getParameter("confirmaSenha");

			String cpf = request.getParameter("cpf");

			String cpfNumerico = cpf.replaceAll("[^0-9]", "");

			Long longCpf = Long.parseLong(cpf.trim());
			String ultimoNome;
			// CPF Existe
			if (beneficiarioDao.existeCpf(longCpf)) {

				request.getSession().setAttribute("status", "cpf");

				nome = request.getParameter("primeiroNome");
				ultimoNome = request.getParameter("ultimoNome");

				return "redirect:controller?call=CadastrarBeneficiarioForm&primeiroNome="
						+ URLEncoder.encode(nome, "UTF-8") + "&ultimoNome=" + URLEncoder.encode(ultimoNome, "UTF-8")
						+ "&cpf=" + URLEncoder.encode(cpf, "UTF-8");

			}
			// Senhas diferentes
			else if (!senha.equals(confirmaSenha)) {

				request.getSession().setAttribute("status", "diferente");

				nome = request.getParameter("primeiroNome");
				ultimoNome = request.getParameter("ultimoNome");

				return "redirect:controller?call=CadastrarBeneficiarioForm&primeiroNome="
						+ URLEncoder.encode(nome, "UTF-8") + "&ultimoNome=" + URLEncoder.encode(ultimoNome, "UTF-8")
						+ "&cpf=" + URLEncoder.encode(cpf, "UTF-8");

			}
			// senhas fracas
			else if (!(new HashSenha().ValidaSeguranca(senha))) {

				request.getSession().setAttribute("status", "senha");
				nome = request.getParameter("primeiroNome");
				ultimoNome = request.getParameter("ultimoNome");

				return "redirect:controller?call=CadastrarBeneficiarioForm&primeiroNome="
						+ URLEncoder.encode(nome, "UTF-8") + "&ultimoNome=" + URLEncoder.encode(ultimoNome, "UTF-8")
						+ "&cpf=" + URLEncoder.encode(cpf, "UTF-8");

			} else {

				String hashSenha = new HashSenha().RetornaHashSenha(senha);

				Beneficiario beneficiario = new Beneficiario(nome, longCpf, hashSenha);

				em.getTransaction().begin();
				beneficiarioDao.cadastrar(beneficiario);
				em.getTransaction().commit();
				em.close();

				// javascript de confirmação de cadastro

				request.getSession().setAttribute("sucesso", true);
				return "redirect:controller?call=Home";
			}
		} catch (Exception e) {

			return "redirect:controller?call=Home";
		}

	}

}
