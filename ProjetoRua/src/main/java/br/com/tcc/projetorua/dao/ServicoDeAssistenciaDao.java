package br.com.tcc.projetorua.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.tcc.projetorua.model.Prestador;
import br.com.tcc.projetorua.model.Servico;

public class ServicoDeAssistenciaDao implements AutoCloseable {
	private EntityManager em;

	public ServicoDeAssistenciaDao(EntityManager em) {

		this.em = em;
	}

	public void cadastrar(Servico servicoDeAssistencia) {

		this.em.persist(servicoDeAssistencia);

	}

	public Servico atualizar(Servico servicoDeAssistencia) {

		return em.merge(servicoDeAssistencia);

	}

	public void remover(Servico servicoDeAssistencia) {
		servicoDeAssistencia = em.merge(servicoDeAssistencia);
		em.remove(servicoDeAssistencia);

	}

	// public void removerPorId(Long id) {

	// Pega id da recorrencia
	// String JPQLIDREC = "SELECT s.recorrencia_id FROM Servico s WHERE s.id = :id";
	// Long recorrencia_id = em.createQuery(JPQLIDREC,
	// Long.class).setParameter("id", id).getSingleResult();

	// Pega id do endereço
	// String JPQLIDEND = "SELECT s.endereco_id FROM Servico s WHERE s.id = :id";
	// Long endereco_id = em.createQuery(JPQLIDEND, Long.class).setParameter("id",
	// id).getSingleResult();

	// Delete do Serviço
	// String JPQLS = "DELETE s FROM Servico s WHERE s.id=:id;";

	// em.createQuery(JPQLS).setParameter("id", id).executeUpdate();

	// Delete da Recorrencia do Serviço
	// String JPQLR = "DELETE r FROM Recorrencia r WHERE r.id=:recorrencia_id;";

	// em.createQuery(JPQLR).setParameter("recorrencia_id",
	// recorrencia_id).executeUpdate();

	// Delete do Endereco do Serviço
	// String JPQLE = "DELETE e FROM Endereco e WHERE e.id=:endereco_id;";

	// em.createQuery(JPQLR).setParameter("endereco_id",
	// endereco_id).executeUpdate();

	// }

	public void close() {
		em.close();
	}

	public List<Servico> buscarServicosPrestador(Prestador prestador) {

		String JPQL = "SELECT s FROM Servico s WHERE s.prestador.id = :id";

		List<Servico> servicos = em.createQuery(JPQL, Servico.class).setParameter("id", prestador.getId())
				.getResultList();

		return servicos;

	}

	public List<Servico> buscarServicos() {

		String JPQL = "SELECT s FROM Servico s";

		List<Servico> servicos = em.createQuery(JPQL, Servico.class).getResultList();

		return servicos;

	}

	public List<Servico> buscarServicosPorCategoria(String categoria) {

		String JPQL = "SELECT s FROM Servico s WHERE s.categoria.nome = :categoria";

		List<Servico> servicos = em.createQuery(JPQL, Servico.class).setParameter("categoria", categoria)
				.getResultList();

		return servicos;

	}

	public Servico buscarServicoPorId(Long id) {

		String JPQL = "SELECT s FROM Servico s WHERE s.id = :id";

		Servico servico = em.createQuery(JPQL, Servico.class).setParameter("id", id).getSingleResult();

		return servico;

	}

}
