package restauranteEasy.dao;

import java.util.List;

import javax.inject.Named;

import restauranteEasy.entity.Usuario;

@Named
public class SecurityDAO extends AbstractDao {

	public List<Usuario> listarUsuarios() {
		List<Usuario> result;
		result = em.createQuery("select u from Usuario u", Usuario.class).getResultList();
		return result;
	}
}
