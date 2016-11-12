package restauranteEasy.bo;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import restauranteEasy.dao.MesaDao;
import restauranteEasy.dao.PedidoDao;
import restauranteEasy.entity.Mesa;
import restauranteEasy.entity.Pedido;
import restauranteEasy.entity.StatusPedido;

public class PedidoBusiness {

	@Inject
	private PedidoDao pedidoDao;

	@Inject
	private MesaDao mesaDao;

	public Pedido realizarPedido(Pedido pedido) {
		return pedidoDao.inserirPedido(pedido);
	}

	public void pedidoRecebido(String objectId) {
		Pedido pedido = pedidoDao.obterPedido(objectId);
		pedido.setUltimaAlteracao(Calendar.getInstance().getTime());
		pedido.setStatusPedido(StatusPedido.RECEBIDO);
		pedidoDao.alterarPedido(pedido);
	}

	public void pedidoAtendido(String objectId) {
		Pedido pedido = pedidoDao.obterPedido(objectId);
		pedido.setUltimaAlteracao(Calendar.getInstance().getTime());
		pedido.setStatusPedido(StatusPedido.ATENDIDO);

		pedidoDao.alterarPedido(pedido);
	}

	public Pedido obterPedido(String objectId) {
		return pedidoDao.obterPedido(objectId);
	}

	public List<Pedido> consultarPedidosPorStatus(StatusPedido status) {
		return pedidoDao.consultarPedidos(Arrays.asList(status));
	}

	public Mesa obterMesaPorNome(String nome) {
		return mesaDao.obterMesa(nome);
	}

	public Mesa inserirMesa(Mesa mesa) {
		return mesaDao.inserir(mesa, Mesa.class);
	}

}
