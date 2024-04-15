package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.tcc.projetorua.dao.EnderecoDao;
import br.com.tcc.projetorua.dao.PrestadorAjudaDao;
import br.com.tcc.projetorua.model.Endereco;
import br.com.tcc.projetorua.model.Prestador;
import br.com.tcc.projetorua.security.HashSenha;
import br.com.tcc.projetorua.util.JPAUtil;

public class CadastrarPrestadorAjuda implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		EntityManager em = JPAUtil.getEntityManager();

		try (PrestadorAjudaDao prestadorAjudaDao = new PrestadorAjudaDao(em);
				EnderecoDao enderecoDao = new EnderecoDao(em)) {

			String nome = request.getParameter("nomeInstituicao");

			String cnpj = request.getParameter("cnpj");
			String descricao = request.getParameter("descricao");

			String senha = request.getParameter("senha");
			String confirmaSenha = request.getParameter("confirmaSenha");

			String cep = request.getParameter("cep");
			String uf = request.getParameter("uf");
			String cidade = request.getParameter("cidade");
			String bairro = request.getParameter("bairro");
			String rua = request.getParameter("rua");
			String numero = request.getParameter("numero");

			Long LongCnpj = Long.parseLong(cnpj.trim());


			//CNPJ Existe
			if (prestadorAjudaDao.existeCnpj(LongCnpj)) {

				request.getSession().
				setAttribute
				("status", "cnpj");


				return "redirect:controller?call=CadastrarPrestadorAjudaForm&nomeInstituicao=" + URLEncoder.encode(nome, "UTF-8") +
					       "&cnpj=" + URLEncoder.encode(cnpj, "UTF-8") +
					       "&descricao=" + URLEncoder.encode(descricao, "UTF-8") +
					       "&cep=" + URLEncoder.encode(cep, "UTF-8") +
					       "&uf=" + URLEncoder.encode(uf, "UTF-8") +
					       "&cidade=" + URLEncoder.encode(cidade, "UTF-8") +
					       "&bairro=" + URLEncoder.encode(bairro, "UTF-8") +
					       "&rua=" + URLEncoder.encode(rua, "UTF-8") +
					       "&numero=" + URLEncoder.encode(numero, "UTF-8");
				
				
			} 
			//Senhas diferentes
			else if (!senha.equals(confirmaSenha)) {

				request.getSession().
				setAttribute
				("status", "diferente");
				return "redirect:controller?call=CadastrarPrestadorAjudaForm&nomeInstituicao=" + URLEncoder.encode(nome, "UTF-8") +
					       "&cnpj=" + URLEncoder.encode(cnpj, "UTF-8") +
					       "&descricao=" + URLEncoder.encode(descricao, "UTF-8") +
					       "&cep=" + URLEncoder.encode(cep, "UTF-8") +
					       "&uf=" + URLEncoder.encode(uf, "UTF-8") +
					       "&cidade=" + URLEncoder.encode(cidade, "UTF-8") +
					       "&bairro=" + URLEncoder.encode(bairro, "UTF-8") +
					       "&rua=" + URLEncoder.encode(rua, "UTF-8") +
					       "&numero=" + URLEncoder.encode(numero, "UTF-8");

			} 
			//senhas fracas
			else if (!(new HashSenha().ValidaSeguranca(senha))) {

				request.getSession().
				setAttribute
				("status", "senha");
				return "redirect:controller?call=CadastrarPrestadorAjudaForm&nomeInstituicao=" + URLEncoder.encode(nome, "UTF-8") +
					       "&cnpj=" + URLEncoder.encode(cnpj, "UTF-8") +
					       "&descricao=" + URLEncoder.encode(descricao, "UTF-8") +
					       "&cep=" + URLEncoder.encode(cep, "UTF-8") +
					       "&uf=" + URLEncoder.encode(uf, "UTF-8") +
					       "&cidade=" + URLEncoder.encode(cidade, "UTF-8") +
					       "&bairro=" + URLEncoder.encode(bairro, "UTF-8") +
					       "&rua=" + URLEncoder.encode(rua, "UTF-8") +
					       "&numero=" + URLEncoder.encode(numero, "UTF-8");

			} else {

				String hashSenha = new HashSenha().RetornaHashSenha(senha);

				Prestador prestadorAjuda = new Prestador(nome, descricao, hashSenha, LongCnpj);
				Endereco endereco = new Endereco(cep, cidade, bairro, uf, rua, numero);

				em.getTransaction().begin();
				enderecoDao.cadastrar(endereco);
				prestadorAjudaDao.cadastrar(prestadorAjuda);
				prestadorAjudaDao.atualizar(prestadorAjuda).setEndereco(endereco);
				em.getTransaction().commit();
				em.close();

				request.getSession().setAttribute("sucesso", true);
				return "redirect:controller?call=Home";
			}

		} // catch (Exception e) {

		// return "redirect:controller?call=Home";
		// }
	}
}