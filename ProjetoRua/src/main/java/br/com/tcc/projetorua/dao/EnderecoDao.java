package br.com.tcc.projetorua.dao;

import javax.persistence.EntityManager;

import br.com.tcc.projetorua.model.Endereco;

public class EnderecoDao implements AutoCloseable {
	private EntityManager em;

	public EnderecoDao(EntityManager em) {
		
		this.em = em;
	}
	
	public void cadastrar(Endereco endereco) {

		this.em.persist(endereco);

	}
	
	public Endereco atualizar(Endereco endereco) {

		return em.merge(endereco);

	}

	
	public void remover(Endereco endereco) {
		endereco = em.merge(endereco);
		 em.remove(endereco);

	}
	public void close() {
        em.close();
    }
	
}
