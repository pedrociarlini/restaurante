package restauranteEasy.dao;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Named
@ApplicationScoped
public class Database {

	private static final String PU_NAME = "restauranteEasy";

	@Produces
	@ApplicationScoped
	public EntityManagerFactory createEntityManagerFactory() {
		try {
			return Persistence.createEntityManagerFactory(PU_NAME);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Erro em createEntityManagerFactory", ex);
		}
	}

	@Produces
	@RequestScoped
	public EntityManager createEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

	public void closeEm(@Disposes EntityManager em) {
		em.close();
	}

	/**
	 * Fecha o factory de EM. {@link EntityManagerFactory#close()}
	 * 
	 * @param emf
	 */
	public void closeEmf(@Disposes EntityManagerFactory emf) {
		emf.close();
	}

}
