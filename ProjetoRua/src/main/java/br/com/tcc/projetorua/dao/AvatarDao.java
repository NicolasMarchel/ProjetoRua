package br.com.tcc.projetorua.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.tcc.projetorua.model.Avatar;
import br.com.tcc.projetorua.model.Beneficiario;

public class AvatarDao implements AutoCloseable {

	private EntityManager em;

	public AvatarDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Avatar avatar) {

		this.em.persist(avatar);

	}

	public Avatar atualizar(Avatar avatar) {

		return em.merge(avatar);

	}

	public void remover(Avatar avatar) {
		avatar = em.merge(avatar);
		em.remove(avatar);

	}
	public List<Avatar> buscarAvatares() {
		String JPQL = "SELECT a FROM Avatar a";

		List<Avatar> avatares = em.createQuery(JPQL, Avatar.class).getResultList();
		return avatares;
	}
	
public Avatar buscarAvatarPorId(Long id) {
		
		String JPQL = "SELECT a FROM Avatar a WHERE a.id = :id";

		Avatar avatar = em.createQuery(JPQL, Avatar.class).setParameter("id", id).getSingleResult();

		return avatar;
	}
	
	public void close() {
		em.close();
	}

}
