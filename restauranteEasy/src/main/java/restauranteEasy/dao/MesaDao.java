package restauranteEasy.dao;

import restauranteEasy.entity.Mesa;

public class MesaDao extends AbstractDao {

	/**
	 * Obtém mesa pelo nome.
	 * 
	 * @param nome
	 * @return
	 */
	public Mesa obterMesa(String nome) {
		Mesa result = em.createQuery("select m from Mesa m where m.nome = :nome", Mesa.class)
				.setParameter("nome", nome).getSingleResult();
		
		return result;
	}

}
