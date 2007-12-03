package algoritmo;

import java.lang.reflect.Constructor;

import model.AgenteVO;

public class GeneralTest {
	public static void main(String[] args) {
		try {
			AgenteVO ag = new AgenteVO("", Equipe1.class);
			Constructor constr;
			constr = ag.getClasse().getConstructor(Integer.TYPE);
			ProgramaAbstract s = (ProgramaAbstract) constr.newInstance(200);
			System.out.println(s.getNome());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
