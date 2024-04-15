package br.com.tcc.projetorua.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.tcc.projetorua.model.Recorrencia;

public class RecorrenciaDao implements AutoCloseable {
	private EntityManager em;

	public RecorrenciaDao(EntityManager em) {

		this.em = em;
	}

	public void cadastrar(Recorrencia recorrencia) {

		this.em.persist(recorrencia);

	}

	public Recorrencia atualizar(Recorrencia recorrencia) {

		return em.merge(recorrencia);

	}

	public void remover(Recorrencia recorrencia) {
		recorrencia = em.merge(recorrencia);
		em.remove(recorrencia);

	}

	public void close() {
		em.close();
	}

	public List<Recorrencia> buscarRecorrencias() {
		String JPQL = "SELECT r FROM Recorrencia r";

		List<Recorrencia> recorrencias = em.createQuery(JPQL, Recorrencia.class).getResultList();
		return recorrencias;
	}

}
