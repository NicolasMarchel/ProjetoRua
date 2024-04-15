package br.com.tcc.projetorua.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.tcc.projetorua.model.Categoria;

public class CategoriaDao implements AutoCloseable {
	private EntityManager em;

	public CategoriaDao(EntityManager em) {

		this.em = em;
	}

	public Categoria existeCategoria(String categoria) {

		String JPQL = "SELECT c FROM Categoria c WHERE c.nome = :categoria";

		Categoria consultaCategoria = (Categoria) em.createQuery(JPQL, Categoria.class)
				.setParameter("categoria", categoria).getSingleResult();

		return consultaCategoria;

	}

	public void cadastrar(Categoria categoria) {

		this.em.persist(categoria);

	}

	public Categoria atualizar(Categoria categoria) {

		return em.merge(categoria);

	}

	public void remover(Categoria categoria) {
		categoria = em.merge(categoria);
		em.remove(categoria);

	}

	public void close() {
		em.close();
	}

	public List<Categoria> buscarCategorias() {
		String JPQL = "SELECT c FROM Categoria c";

		List<Categoria> categorias = em.createQuery(JPQL, Categoria.class).getResultList();
		return categorias;
	}

}
