package algoritmo;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementa as seguintes funcionalidades: <br>
 *  - Guadar um mapa relacionando cada soldado ao seu nome na matriz.
 *  <br>
 *  <pre>
 *  mapa = {
 *  	NOME1 -> <code>Programa</code>1,
 *  	NOME2 -> <code>Programa</code>2,
 *  	...
 *  }
 * </pre>
 *  <br />
 *  - Guardar a �ltima a��o tomada. <br />
 * <br />
 * <br />
 * Para tornar uma classe poss�vel participante da simula��o, deve-se
 * implementar um bloco de inicializa��o est�tico registrando-se ao gerenciador.<br />
 * O c�digo exemplo est� a seguir (pode ser copiado e colado);<br />
 * 
 */
public abstract class ProgramaAbstract implements Programa {

	static {
		try {
			Class.forName("algoritmo.Equipe1");
			Class.forName("algoritmo.Equipe2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Guarda um mapa cuja chave � o nome do soldado (inst�ncia do agente) e o
	 * valor � uma refer�ncia para o soldado.<br />
	 * <br />
	 * <code>agentes = { nome -> SOLDADO, nome2 -> SOLDADO2, ...}</code>
	 */
	private static Map<Integer, ProgramaAbstract> agentes = new HashMap<Integer, ProgramaAbstract>();

	private int ultimaAcao = -1;

	/**
	 * Nome do soldado.
	 */
	private int nome;

	private SensoresEquipe sensor = new SensoresEquipe();

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
	
	public SensoresEquipe getSensor() {
		return sensor;
	}

	public void setSensor(SensoresEquipe sensor2) {
		this.sensor = sensor2;
	}
	
	@Override
	public String toString() {
		return "Soldado (" + this.getClass().getSimpleName() + "): " + getNome();
	}
}
