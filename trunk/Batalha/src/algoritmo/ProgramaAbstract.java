package algoritmo;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementa as seguintes funcionalidades:<br/> - Guadar um mapa relacionando
 * cada soldado ao seu nome na matriz.<br/> mapa = {NOME1 ->
 * <code>Programa</code>1, NOME2 -> <code>Programa</code>2,...} <br /> -
 * Guardar a última ação tomada.
 * <br /><br /><br />
 * Para tornar uma classe possível participante da simulação, deve-se 
 * implementar um bloco de inicialização estático registrando-se ao
 * gerenciador.<br />
 * O código exemplo está a seguir (pode ser copiado e colado);<br />
 * <pre>
 * static {
 *     
 * }
 * </pre>
 */
public abstract class ProgramaAbstract implements Programa {

	/**
	 * Guarda um mapa cuja chave é o nome do soldado (instância do agente) e o
	 * valor é uma referência para o soldado.<br /><br />
	 * <code>agentes = { nome -> SOLDADO, nome2 -> SOLDADO2, ...}</code>
	 */
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
