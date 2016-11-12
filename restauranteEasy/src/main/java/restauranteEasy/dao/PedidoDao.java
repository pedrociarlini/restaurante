package restauranteEasy.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import org.bson.types.ObjectId;

import restauranteEasy.entity.Pedido;
import restauranteEasy.entity.StatusPedido;

public class PedidoDao extends AbstractDao {

	public Pedido inserirPedido(Pedido pedido) {
		return inserir(pedido, Pedido.class);
	}

	public void alterarPedido(Pedido pedido) {
		em.merge(pedido);
		em.flush();
	}

	/**
	 * Retorna o pedido pelo _id dele.
	 * 
	 * @param objectId
	 * @return
	 */
	public Pedido obterPedido(String objectId) {
		return em.find(Pedido.class, new ObjectId(objectId));
	}

	public List<Pedido> consultarPedidos(List<StatusPedido> status) {
		List<Pedido> result = new ArrayList<Pedido>();
		TypedQuery<Pedido> query = em.createQuery("select p from Pedido p where p.status in (:status)", Pedido.class);
		query.setParameter("status", status);
		result = query.getResultList();
		return result;
	}

}
