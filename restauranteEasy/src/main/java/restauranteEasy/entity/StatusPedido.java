package restauranteEasy.entity;

public enum StatusPedido {
	INICIADO("INICIADO"), RECEBIDO("RECEBIDO"), ATENDIDO("ATENDIDO");

	private String nomeStatus;

	StatusPedido(String status) {
		nomeStatus = status;
	}

	public String getNomeStatus() {
		return nomeStatus;
	}
}
