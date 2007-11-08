package algoritmo.agentes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public final class AgentesManager {

	@SuppressWarnings("unchecked")
	private static Map<String, Class> agentes = new HashMap<String, Class>();

	@SuppressWarnings("unchecked")
	public static synchronized void registerClass(String nome, Class clazz) {
		String storedName = nome;
		if (agentes.containsKey(storedName)) {
			System.out.println("Classe com nome \"" + storedName
					+ "\" já está cadastrada.\n"
					+ "Tentando uisar nome da classe...");
			storedName = clazz.getSimpleName();
		}
		if (agentes.containsKey(storedName)) {
			System.out.println("Classe com nome \"" + storedName
					+ "\" já está cadastrada.");
		} else {
			agentes.put(storedName, clazz);
		}
	}

	public static String[] getClasses() {
		String[] result = new String[agentes.keySet().size()];
		result = agentes.keySet().toArray(result);
		return result;
	}

	public static Class getClassByName(String nome) {
		return agentes.get(nome);
	}

	public static Set<String> getClassesNameSet() {
		return agentes.keySet();
	}
}
