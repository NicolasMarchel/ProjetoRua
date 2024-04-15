package br.com.tcc.projetorua.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.tcc.projetorua.model.Beneficiario;
import br.com.tcc.projetorua.model.Categoria;
import br.com.tcc.projetorua.model.Servico;

public class BeneficiarioDao implements AutoCloseable {

	private EntityManager em;

	public BeneficiarioDao(EntityManager em) {
		this.em = em;
	}

	public void cadastrar(Beneficiario beneficiario) {

		this.em.persist(beneficiario);

	}

	public Beneficiario atualizar(Beneficiario beneficiario) {

		return em.merge(beneficiario);

	}

	
	public List<Beneficiario> buscarBeneficiarios() {
		String JPQL = "SELECT b FROM Beneficiario b";

		List<Beneficiario> beneficiario = em.createQuery(JPQL, Beneficiario.class).getResultList();
		return beneficiario;
	}
	
	
	
	public void remover(Beneficiario beneficiario) {
		beneficiario = em.merge(beneficiario);
		em.remove(beneficiario);

	}

	public Beneficiario existeUsuario(String hashSenha, Long cpf) {

		String JPQL = "SELECT b FROM Beneficiario b WHERE b.cpf = :cpf AND b.hashSenha = :hashSenha";

		Beneficiario beneficiario;
		
			beneficiario = (Beneficiario) em.createQuery(JPQL, Beneficiario.class).setParameter("cpf", cpf)
					.setParameter("hashSenha", hashSenha).getSingleResult();

			return beneficiario;

		

	}

	public Boolean existeCpf(Long cpf) {

		String JPQL = "SELECT COUNT(b) FROM Beneficiario b WHERE b.cpf = :cpf";

		Long count = em.createQuery(JPQL, Long.class).setParameter("cpf", cpf).getSingleResult();

		return count == 1;

	}

	public void close() {
		em.close();
	}

	public Beneficiario buscarBeneficiarioPorId(Long id) {
		
		String JPQL = "SELECT b FROM Beneficiario b WHERE b.id = :id";

		Beneficiario beneficiario = em.createQuery(JPQL, Beneficiario.class).setParameter("id", id).getSingleResult();

		return beneficiario;
	}
}
