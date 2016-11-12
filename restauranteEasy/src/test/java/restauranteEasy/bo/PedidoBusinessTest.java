package restauranteEasy.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.inject.Inject;

import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.jglue.cdiunit.InRequestScope;
import org.jglue.cdiunit.deltaspike.SupportDeltaspikeJpa;
import org.junit.Test;
import org.junit.runner.RunWith;

import restauranteEasy.dao.Database;
import restauranteEasy.dao.SecurityDAO;
import restauranteEasy.entity.Mesa;
import restauranteEasy.entity.Pedido;
import restauranteEasy.entity.StatusPedido;

@RunWith(CdiRunner.class)
@AdditionalClasses({ Database.class, SecurityDAO.class })
@SupportDeltaspikeJpa
public class PedidoBusinessTest {

	@Inject
	private PedidoBusiness pedBusiness;
	private static final String MESA_NOME_1 = "mesa1";

	@Test
	@InRequestScope
	public void testRealizarPedido() {
		Mesa mesa = pedBusiness.obterMesaPorNome(MESA_NOME_1);
		List<Pedido> pedsIniciados = pedBusiness.consultarPedidosPorStatus(StatusPedido.INICIADO);
		Pedido pedido = new Pedido();
		pedido.setDescricaoPedido("NOVO PEDIDO PARA TESTE UNITÁRIO");
		pedido.setMesa(mesa);
		pedBusiness.realizarPedido(pedido);
		List<Pedido> novosPedsIniciados = pedBusiness.consultarPedidosPorStatus(StatusPedido.INICIADO);

		assertTrue("Era pra ter inserido", pedsIniciados.size() < novosPedsIniciados.size());
	}

	@Test
	@InRequestScope
	public void testPedidoRecebido() {
		fail("Not yet implemented");
	}

	@Test
	@InRequestScope
	public void testPedidoAtendido() {
		fail("Not yet implemented");
	}

	@Test
	@InRequestScope
	public void testObterPedido() {
		fail("Not yet implemented");
	}

	@Test
	@InRequestScope
	public void testInserirMesa() {
		Mesa mesa = new Mesa(MESA_NOME_1);
		Mesa result = pedBusiness.inserirMesa(mesa);
		assertNotNull(result);
		assertEquals("Era pra ser igual.", MESA_NOME_1, result.getNome());
	}
}
