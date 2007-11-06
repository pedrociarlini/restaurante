package algoritmo;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementa as seguintes funcionalidades:<br/> - Guadar um mapa relacionando
 * cada soldado ao seu nome na matriz.<br/> mapa = {NOME1 ->
 * <code>Programa</code>1, NOME2 -> <code>Programa</code>2,...} <br /> -
 * Guardar a última ação tomada.
 */
public abstract class ProgramaAbstract implements Programa {

	private static Map<Integer, ProgramaAbstract> agentes = new HashMap<Integer, ProgramaAbstract>();
	private int ultimaAcao;

	/**
	 * Nome do soldado.
	 */
	private int nome;

	/**
	 * Precisa ser chamado pelos seus filhos para poder funcionar.
	 * 
	 * @param nome
	 */
	public ProgramaAbstract(int nome) {
		this.nome = nome;
		agentes.put(nome, this);
	}

	/**
	 * Informa qual a ultima acao executada.
	 * 
	 * @param acao
	 */
	public void setUltimaAcao(int acao) {
		this.ultimaAcao = acao;
	}

	public int getUltimaAcao() {
		return ultimaAcao;
	}

	public int getNome() {
		return nome;
	}

	public static ProgramaAbstract getByNome(Integer nome) {
		return agentes.get(nome);
	}
}
