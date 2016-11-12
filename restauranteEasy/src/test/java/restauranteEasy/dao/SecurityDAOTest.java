package restauranteEasy.dao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.jglue.cdiunit.deltaspike.SupportDeltaspikeJpa;
import org.junit.Test;
import org.junit.runner.RunWith;

import restauranteEasy.entity.Usuario;

@RunWith(CdiRunner.class)
@AdditionalClasses({ Database.class, SecurityDAO.class })
@SupportDeltaspikeJpa
public class SecurityDAOTest {

	@Inject
	private SecurityDAO secDao;

	@Test
	@InRequestScope
	public void testListarUsuarios() {
		List<Usuario> result = secDao.listarUsuarios();
		assertNotNull(result);
		assertTrue(result.size() > 0);
	}

}
