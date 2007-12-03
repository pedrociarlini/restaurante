package algoritmo.agentes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import model.AgenteVO;

public final class AgentesManager {

	/**
	 * Guarda uma coleção de agentes possíveis de serem usados pela simulação.
	 * <br>
	 * Esse <code>Map</code> tem a segunda estrutura:<br>
	 * <pre>
	 * 	agentes = {
	 * 		["nomeAgente" -> ClasseDoAgente],
	 * 		["nomeAgente2" -> ClasseDoAgente2],
	 * 		["nomeAgente3" -> ClasseDoAgente3],
	 * 		...
	 * }
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	private static Map<String, AgenteVO> agentes = new HashMap<String, AgenteVO>();

	/**
	 * Registra o agente passado por parâmetro numa coleção de agentes 
	 * possíveis de serem usados pela simulação.
	 * <br>
	 * Esse <code>Map</code> de agentes tem a segunda estrutura:<br>
	 * <pre>
	 * 	agentes = {
	 * 		["nomeAgente" -> ClasseDoAgente],
	 * 		["nomeAgente2" -> ClasseDoAgente2],
	 * 		["nomeAgente3" -> ClasseDoAgente3],
	 * 		...
	 * }
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static synchronized void registerClass(AgenteVO agenteVO) {
		String storedName = agenteVO.getNome();
		if (agentes.containsKey(storedName)) {
			System.out.println("Classe com nome \"" + storedName
					+ "\" já está cadastrada.\n"
					+ "Tentando usar nome da classe...");
			storedName = agenteVO.getClasse().getSimpleName();
		}
		if (agentes.containsKey(storedName)) {
			System.out.println("Classe com nome \"" + storedName
					+ "\" já está cadastrada.");
		} else {
			agentes.put(storedName, agenteVO);
		}
	}

	public static String[] getClasses() {
		String[] result = new String[agentes.keySet().size()];
		result = agentes.keySet().toArray(result);
		return result;
	}

	@SuppressWarnings("unchecked")
	public static AgenteVO getClassByName(String nome) {
		return agentes.get(nome);
	}

	public static Set<String> getClassesNameSet() {
		return agentes.keySet();
	}
}