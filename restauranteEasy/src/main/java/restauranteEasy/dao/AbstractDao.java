package restauranteEasy.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import restauranteEasy.entity.MainEntity;

public class AbstractDao {

	@Inject
	protected EntityManager em;

	public <T extends MainEntity> T inserir(T e, Class<T> t) {

		em.getTransaction().begin();

		em.persist(e);

		em.getTransaction().commit();

		T result = em.find(t, e.getId());
		return result;
	}

}
