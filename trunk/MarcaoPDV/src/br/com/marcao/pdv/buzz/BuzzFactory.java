package br.com.marcao.pdv.buzz;

import br.com.marcao.pdv.entity.MesaEntity;
import br.com.marcao.pdv.entity.UsuarioEntity;


public class BuzzFactory {
    public static final String BUZZ_MESA = "Mesa";

    public static String BUZZ_USUARIO = "Usuario";
    
    private static BuzzFactory instance = null;
    
    private BuzzFactory() {
        
    }
    
    /**
     * Implement��o do padr�o singleton.
     * @return
     */
    public static BuzzFactory getInstance() {
        if (instance == null) {
            instance = new BuzzFactory();
        }
        return instance;
    }
    
    /**
     * Cria uma inst�ncia de uma classe de neg�cios.
     * <br/>
     * Classe criada para abstrair o uso de frameworks como spring.
     * @param buzzName
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T retrieveBuzzInstance(String buzzName) {
        
        if (buzzName.equalsIgnoreCase(BUZZ_USUARIO)) {
            return (T) new UsuarioBuzz(UsuarioEntity.class);
        }
        if (buzzName.equalsIgnoreCase(BUZZ_MESA)) {
            return (T) new MesaBuzz(MesaEntity.class);
        }
        else {
            throw new RuntimeException("Classe de neg�cios n�o encontrada: " + buzzName);
        }
    }
}
