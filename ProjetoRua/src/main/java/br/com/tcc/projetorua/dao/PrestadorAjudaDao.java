package br.com.tcc.projetorua.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import br.com.tcc.projetorua.model.Prestador;

public class PrestadorAjudaDao implements AutoCloseable {
	private EntityManager em;

	public PrestadorAjudaDao(EntityManager em) {

		this.em = em;
	}

	public void cadastrar(Prestador prestadorAjuda) {

		this.em.persist(prestadorAjuda);

	}

	public Prestador atualizar(Prestador prestadorAjuda) {

		return em.merge(prestadorAjuda);

	}

	public void remover(Prestador prestadorAjuda) {
		prestadorAjuda = em.merge(prestadorAjuda);
		em.remove(prestadorAjuda);

	}

	public Prestador existeUsuario(String hashSenha, Long cnpj) {

		String JPQL = "SELECT p FROM Prestador p WHERE p.cnpj = :cnpj AND p.hashSenha = :hashSenha";

		Prestador prestadorAjuda;
		try {
			prestadorAjuda = (Prestador) em.createQuery(JPQL, Prestador.class).setParameter("cnpj", cnpj)
					.setParameter("hashSenha", hashSenha).getSingleResult();

			return prestadorAjuda;

		} catch (NoResultException e) {

			return null;
		}

	}

	public boolean existeCnpj(Long cnpj) {

		String JPQL = "SELECT COUNT(p) FROM Prestador p WHERE p.cnpj = :cnpj";

		Long count = em.createQuery(JPQL, Long.class).setParameter("cnpj", cnpj).getSingleResult();

		return count == 1;

	}

	public void close() {
		em.close();
	}

}
