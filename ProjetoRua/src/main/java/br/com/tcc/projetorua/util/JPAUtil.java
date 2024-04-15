package br.com.tcc.projetorua.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil{

	private static final EntityManagerFactory factory = Persistence.createEntityManagerFactory("projetorua");

	public static EntityManager getEntityManager() {

		return factory.createEntityManager();

	}
	
	
 
}
