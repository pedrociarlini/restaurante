/**
 * 
 */

var restauranteApp = new Vue({
  el: '#restauranteApp',
  data: {
    mesaCorrente : 'mesa25',
    mesas : {
    	'mesa25' : {
    		nome : "Mesa 25",
    		apelido : "Marcelinho",
    		codigo : 'mesa25',
    		pedidos : [
    		    {descricao : 'Cerveja Skol', qtde : 2, status : 'aguardando'},
    		    {descricao : 'Aperitivozinho lá', qtde : 1, status : 'aguardando'},
    		    {descricao : 'Comida apimentada', qtde : 1, status : 'recebido'},
    		    {descricao : 'Cerveja gelada', qtde : 1, status : 'recebido'},
    		    {descricao : 'Arroz de camarão', qtde : 1, status : 'entregue'}
    		]
    	},
    	'mesa14' : {
    		nome : "Mesa 14 (cima)",
    		apelido : "Marcelinho",
    		codigo : 'mesa14'
    	},
    	'mesa12' : {
    		nome : "Mesa 12 (grande)",
    		apelido : "Marcelinho",
    		codigo : 'mesa12'
    	}

    }
  }
});

adicionarPedido = function(mesa, descricao, qtde) {
	var pedido = {'descricao' : descricao, 'qtde' : qtde, status : 'aguardando'};
	restauranteApp.mesas[mesa].pedidos.add(pedido);
}