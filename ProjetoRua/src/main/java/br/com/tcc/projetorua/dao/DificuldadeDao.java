package br.com.tcc.projetorua.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.model.Dificuldade;

public class DificuldadeDao implements AutoCloseable {

	private EntityManager em;

	public void close() {
		em.close();
	}

	public DificuldadeDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Dificuldade dificuldade) {

		this.em.persist(dificuldade);

	}

	public Dificuldade atualizar(Dificuldade dificuldade) {

		return em.merge(dificuldade);

	}

	public void remover(Dificuldade dificuldade) {
		dificuldade = em.merge(dificuldade);
		em.remove(dificuldade);

	}
	
	
	public List<Dificuldade> buscarDificuldadesBeneficiario(Beneficiario beneficiario) {

		String JPQL = "SELECT d FROM Dificuldade d WHERE d.beneficiario.id = :id";

		List<Dificuldade> dificuldades = em.createQuery(JPQL, Dificuldade.class).setParameter("id", beneficiario.getId())
				.getResultList();

		return dificuldades;

	}

	public void RemoveCadastroAnterior(Long id) {
		
		String JPQL = "DELETE FROM Dificuldade d WHERE d.beneficiario.id = :id";
		
		em.createQuery(JPQL).setParameter("id", id).executeUpdate();
		
	}


}
