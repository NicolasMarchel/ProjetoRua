package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.tcc.projetorua.dao.AvatarDao;
import br.com.tcc.projetorua.dao.BeneficiarioDao;
import br.com.tcc.projetorua.dao.DificuldadeDao;
import br.com.tcc.projetorua.dao.EnderecoDao;
import br.com.tcc.projetorua.model.Avatar;
import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.model.Dificuldade;
import br.com.tcc.projetorua.model.Endereco;
import br.com.tcc.projetorua.util.JPAUtil;

public class CadastrarHistoria implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		EntityManager em = JPAUtil.getEntityManager();

		String descricao = "";
		String genero = "";
		String raca = "";
		String stringDataNascimento = "";
		String tempoNasRuas = "";
		Integer anosNaRua = null;
		LocalDate dataNascimento = null;

		String cep = "";
		String uf = "";
		String cidade = "";
		String bairro = "";

		String alimentacao = "";
		String hidratacao = "";
		String higiene = "";
		String preconceito = "";
		String violencia = "";
		String saude = "";
		String abrigo = "";
		String trabalho = "";

		String idAvatar = "";

		Integer escAlimentacao = 0;
		Integer escHidratacao = 0;
		Integer escHigiene = 0;
		Integer escPreconceito = 0;
		Integer escViolencia = 0;
		Integer escSaude = 0;
		Integer escAbrigo = 0;
		Integer escTrabalho = 0;
		Long intIdAvatar = null ;
		try {

			alimentacao = request.getParameter("Alimentacao");
			hidratacao = request.getParameter("Hidratacao");
			higiene = request.getParameter("Higiene");
			preconceito = request.getParameter("Preconceito");
			violencia = request.getParameter("Violencia");
			saude = request.getParameter("Saude");
			abrigo = request.getParameter("Abrigo");
			trabalho = request.getParameter("Trabalho");

			idAvatar = request.getParameter("idAvatar");

		} catch (Exception e) {
			alimentacao = "";
			hidratacao = "";
			higiene = "";
			preconceito = "";
			violencia = "";
			saude = "";
			abrigo = "";
			trabalho = "";
			idAvatar = "";
		}

		try {
			escAlimentacao = Integer.parseInt(alimentacao);
			escHidratacao = Integer.parseInt(hidratacao);
			escHigiene = Integer.parseInt(higiene);
			escPreconceito = Integer.parseInt(preconceito);
			escViolencia = Integer.parseInt(violencia);
			escSaude = Integer.parseInt(saude);
			escAbrigo = Integer.parseInt(abrigo);
			escTrabalho = Integer.parseInt(trabalho);
	
		} catch (NumberFormatException e) {
			escAlimentacao = 0;
			escHidratacao = 0;
			escHigiene = 0;
			escPreconceito = 0;
			escViolencia = 0;
			escSaude = 0;
			escAbrigo = 0;
			escTrabalho = 0;
		}

		try {
			
			intIdAvatar = Long.parseLong(idAvatar);
		} catch (NumberFormatException e) {
			intIdAvatar= null ;
		}
		
		
		
		DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		try {
			// coleta valores do formulario

			cep = request.getParameter("cep");
			uf = request.getParameter("uf");
			cidade = request.getParameter("cidade");
			bairro = request.getParameter("bairro");

			descricao = request.getParameter("descricao");
			genero = request.getParameter("genero");
			raca = request.getParameter("raca");
			stringDataNascimento = request.getParameter("dataNascimento");
			tempoNasRuas = request.getParameter("tempoNasRuas");

			anosNaRua = Integer.parseInt(tempoNasRuas);

			dataNascimento = LocalDate.parse(stringDataNascimento, formatterDate);
		} catch (Exception e) {
			stringDataNascimento = "";
			descricao = "";
			tempoNasRuas = "";
			raca = "";

			genero = "";
			anosNaRua = 0;
			dataNascimento = null;
			// return "redirect:controller?call=Home";
		}

		Avatar avatar=null;
		try (BeneficiarioDao beneficiarioDao = new BeneficiarioDao(em);
				DificuldadeDao dificuldadeDao = new DificuldadeDao(em);
				EnderecoDao enderecoDao = new EnderecoDao(em);
				AvatarDao avatarDao = new AvatarDao(em)) {

			HttpSession sessao = request.getSession();
			Beneficiario beneficiario = (Beneficiario) sessao.getAttribute("usuarioLogado");

			
			if (intIdAvatar != null && intIdAvatar != 0) {
			    avatar = (Avatar) avatarDao.buscarAvatarPorId(intIdAvatar);
			}
			Endereco endereco = new Endereco(cep, cidade, bairro, uf);
			Dificuldade dificuldadeAlimentacao = null;
			Dificuldade dificuldadeHidratacao = null;
			Dificuldade dificuldadeHigiene = null;
			Dificuldade dificuldadePreconceito = null;
			Dificuldade dificuldadeViolencia = null;
			Dificuldade dificuldadeSaude = null;
			Dificuldade dificuldadeAbrigo = null;
			Dificuldade dificuldadeTrabalho = null;

			em.getTransaction().begin();

			beneficiario = beneficiarioDao.atualizar(beneficiario);

			dificuldadeDao.RemoveCadastroAnterior(beneficiario.getId());

			if (escAlimentacao != 0) {
				dificuldadeAlimentacao = new Dificuldade("Alimentação", escAlimentacao);

				// dificuldadeDao.RemoveCadastroAnterior("Alimentação", beneficiario);

				dificuldadeDao.cadastrar(dificuldadeAlimentacao);
				dificuldadeAlimentacao.setBeneficiario(beneficiario);
			}

			if (escHidratacao != 0) {
				dificuldadeHidratacao = new Dificuldade("Hidratação", escHidratacao);
				// dificuldadeDao.RemoveCadastroAnterior("Hidratação", beneficiario);
				dificuldadeDao.cadastrar(dificuldadeHidratacao);
				dificuldadeHidratacao.setBeneficiario(beneficiario);
			}
			if (escHigiene != 0) {
				dificuldadeHigiene = new Dificuldade("Higiene", escHigiene);
				// dificuldadeDao.RemoveCadastroAnterior("Higiene", beneficiario);
				dificuldadeDao.cadastrar(dificuldadeHigiene);
				dificuldadeHigiene.setBeneficiario(beneficiario);
			}
			if (escPreconceito != 0) {
				dificuldadePreconceito = new Dificuldade("Preconceito", escPreconceito);
				// dificuldadeDao.RemoveCadastroAnterior("Preconceito", beneficiario);
				dificuldadeDao.cadastrar(dificuldadePreconceito);
				dificuldadePreconceito.setBeneficiario(beneficiario);
			}
			if (escViolencia != 0) {
				dificuldadeViolencia = new Dificuldade("Violência", escViolencia);
				// dificuldadeDao.RemoveCadastroAnterior("Violência", beneficiario);
				dificuldadeDao.cadastrar(dificuldadeViolencia);
				dificuldadeViolencia.setBeneficiario(beneficiario);
			}
			if (escSaude != 0) {
				dificuldadeSaude = new Dificuldade("Saúde", escSaude);
				// dificuldadeDao.RemoveCadastroAnterior("Saúde", beneficiario);
				dificuldadeDao.cadastrar(dificuldadeSaude);
				dificuldadeSaude.setBeneficiario(beneficiario);
			}
			if (escAbrigo != 0) {
				dificuldadeAbrigo = new Dificuldade("Abrigo", escAbrigo);
				// dificuldadeDao.RemoveCadastroAnterior("Abrigo", beneficiario);
				dificuldadeDao.cadastrar(dificuldadeAbrigo);
				dificuldadeAbrigo.setBeneficiario(beneficiario);
			}
			if (escTrabalho != 0) {
				dificuldadeTrabalho = new Dificuldade("Trabalho", escTrabalho);
				// dificuldadeDao.RemoveCadastroAnterior("Trabalho", beneficiario);
				dificuldadeDao.cadastrar(dificuldadeTrabalho);
				dificuldadeTrabalho.setBeneficiario(beneficiario);
			}

			if (!cep.isEmpty()) {
				enderecoDao.cadastrar(endereco);
				beneficiario.setEndereco(endereco);
			}

			beneficiario.setHistoria(descricao);
			beneficiario.setGenero(genero);
			beneficiario.setEtnia(raca);
			beneficiario.setTempoNasRuas(anosNaRua);
			beneficiario.setDataNascimento(dataNascimento);
			beneficiario.setDataPublicacao(LocalDate.now());

			
				
		
			
			if (intIdAvatar != null && intIdAvatar != 0) {
				beneficiario.setAvatar(avatar);
			}
			em.getTransaction().commit();
			em.close();

			sessao.setAttribute("usuarioLogado", beneficiario);

			return "redirect:controller?call=ListarHistoriaForm";

		}

		catch (Exception e) {
			return "redirect:controller?call=CadastrarHistoriaForm";
		}

	}
}
