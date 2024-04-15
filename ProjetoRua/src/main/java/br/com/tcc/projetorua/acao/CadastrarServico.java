package br.com.tcc.projetorua.acao;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.tcc.projetorua.dao.CategoriaDao;
import br.com.tcc.projetorua.dao.EnderecoDao;
import br.com.tcc.projetorua.dao.RecorrenciaDao;
import br.com.tcc.projetorua.dao.ServicoDeAssistenciaDao;
import br.com.tcc.projetorua.model.Categoria;
import br.com.tcc.projetorua.model.Endereco;
import br.com.tcc.projetorua.model.Prestador;
import br.com.tcc.projetorua.model.Recorrencia;
import br.com.tcc.projetorua.model.Servico;
import br.com.tcc.projetorua.util.JPAUtil;

public class CadastrarServico implements Acao {

	public String executa(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NoSuchAlgorithmException {

		EntityManager em = JPAUtil.getEntityManager();
		String horaInicioStr = "";
		String horaFinalStr = "";
		String dataInicialStr = "";
		String dataFinalStr = "";
		String categoria = "";
		Categoria categoriaSelecionada = null;
		String descricao = "";
		String cep = "";
		String uf = "";
		String cidade = "";
		String bairro = "";
		String rua = "";
		String numero = "";
		String frequencia = "";

		String segunda = "";
		String terca = "";
		String quarta = "";
		String quinta = "";
		String sexta = "";
		String sabado = "";
		String domingo = "";

		try {

			
			segunda = request.getParameter("segunda");
			
			terca = request.getParameter("terca");
			quarta = request.getParameter("quarta");
			quinta = request.getParameter("quinta");
			sexta = request.getParameter("sexta");
			sabado = request.getParameter("sabado");
			domingo = request.getParameter("domingo");
			
			System.out.println("AQUI"+segunda);
			System.out.println("AQUI"+terca);
			System.out.println("AQUI"+quarta);
			System.out.println("AQUI"+quinta);
			System.out.println("AQUI"+sexta);
			System.out.println("AQUI"+sabado);
			System.out.println("AQUI"+domingo);

		} catch (NumberFormatException e) {
			
			segunda = "";
			terca = "";
			quarta = "";
			quinta = "";
			sexta = "";
			sabado = "";
			domingo = "";
		} catch (Exception e) {
			
		}

		boolean bSegunda = false;

		boolean bTerca = false;
		boolean bQuarta = false;
		boolean bQuinta = false;
		boolean bSexta = false;
		boolean bSabado = false;
		boolean bDomingo = false;
		try {
			
			if ("on".equalsIgnoreCase(segunda)) {
				bSegunda = true;
			}
			if ("on".equalsIgnoreCase(terca)) {
				bTerca = true;
			}
			if ("on".equalsIgnoreCase(quarta)) {
				bQuarta = true;
			}
			if ("on".equalsIgnoreCase(quinta)) {
				bQuinta = true;
			}
			if ("on".equalsIgnoreCase(sexta)) {
				bSexta = true;
			}
			if ("on".equalsIgnoreCase(sabado)) {
				bSabado = true;
			}
			if ("on".equalsIgnoreCase(domingo)) {
				bDomingo = true;
			}
			

		} catch (NumberFormatException e) {

		
			bSegunda = false;

			bTerca = false;
			bQuarta = false;
			bQuinta = false;
			bSexta = false;
			bSabado = false;
			bDomingo = false;
		} catch (Exception e) {
		}

		HttpSession sessao = request.getSession();
		Prestador usuarioLogado = (Prestador) sessao.getAttribute("usuarioLogado");

		try {
			// coleta valores do formulario
			descricao = request.getParameter("descricao");
			cep = request.getParameter("cep");
			uf = request.getParameter("uf");
			cidade = request.getParameter("cidade");
			bairro = request.getParameter("bairro");
			rua = request.getParameter("rua");
			numero = request.getParameter("numero");
			categoria = request.getParameter("categoria");
			frequencia = request.getParameter("frequencia");
			horaInicioStr = request.getParameter("horaInicio");
			horaFinalStr = request.getParameter("horaFinal");

			dataInicialStr = request.getParameter("dataInicial");
			dataFinalStr = request.getParameter("dataFinal");
		} catch (Exception e) {
		}

		LocalTime horaInicio = null;
		LocalTime horaFinal = null;
		LocalDate dataInicial = null;
		LocalDate dataFinal = null;
		try {
			// conversoes
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
			horaInicio = LocalTime.parse(horaInicioStr, formatter);
			horaFinal = LocalTime.parse(horaFinalStr, formatter);

			DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			dataInicial = LocalDate.parse(dataInicialStr, formatterDate);
			dataFinal = LocalDate.parse(dataFinalStr, formatterDate);
		} catch (Exception e) {
		}

		// Pega a instancia da categoria se ela já existir

		try (CategoriaDao categoriaDao = new CategoriaDao(em);) {

			categoriaSelecionada = categoriaDao.existeCategoria(categoria);

		} catch (NoResultException e) {

			categoriaSelecionada = null;

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		em = JPAUtil.getEntityManager();

		// Cria a categoria caso ela não exista
		try (CategoriaDao categoriaDao = new CategoriaDao(em);) {

			if (categoriaSelecionada == null) {

				categoriaSelecionada = new Categoria(categoria);
				em.getTransaction().begin();
				categoriaDao.cadastrar(categoriaSelecionada);
				em.getTransaction().commit();
				em.close();

			}

		}

		catch (Exception e) {
			e.printStackTrace();
		}

		em = JPAUtil.getEntityManager();
		try (ServicoDeAssistenciaDao servicoDeAssistenciaDao = new ServicoDeAssistenciaDao(em);
				CategoriaDao categoriaDao = new CategoriaDao(em);
				RecorrenciaDao recorrenciaDao = new RecorrenciaDao(em);
				EnderecoDao enderecoDao = new EnderecoDao(em)) {

			Recorrencia recorrencia = new Recorrencia(dataInicial, dataFinal, frequencia, horaInicio, horaFinal);
			recorrencia.setSegunda(bSegunda);
			recorrencia.setTerca(bTerca);
			recorrencia.setQuarta(bQuarta);
			recorrencia.setQuinta(bQuinta);
			recorrencia.setSexta(bSexta);
			recorrencia.setSabado(bSabado);
			recorrencia.setDomingo(bDomingo);

			Endereco endereco = new Endereco(cep, cidade, bairro, uf, rua, numero);
			Servico servico = new Servico(descricao, endereco, categoriaSelecionada, recorrencia);

			em.getTransaction().begin();
			recorrenciaDao.cadastrar(recorrencia);
			enderecoDao.cadastrar(endereco);
			servicoDeAssistenciaDao.cadastrar(servico);
			servicoDeAssistenciaDao.atualizar(servico).setPrestador(usuarioLogado);
			em.getTransaction().commit();
			em.close();

			// return "redirect:controller?call=ListarServicoForm";

		} catch (Exception e) {

			return "redirect:controller?call=CadastrarServicoForm";
		}

		return "redirect:controller?call=ListarServicoForm";

	}

}
