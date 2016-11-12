package restauranteEasy.dao;

import static org.junit.Assert.*;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.deltaspike.SupportDeltaspikeJpa;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(CdiRunner.class)
@AdditionalClasses({ Database.class, SecurityDAO.class })
@SupportDeltaspikeJpa
public class PedidoDaoTest {
	
	

	@Test
	public void testInserirPedido() {

	}

	@Test
	public void testAlterarPedido() {
		fail("Not yet implemented");
	}

	@Test
	public void testObterPedido() {
		fail("Not yet implemented");
	}

}
