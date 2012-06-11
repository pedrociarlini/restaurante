package br.com.marcao.pdv.buzz;


public class BuzzFactory {
    public static final String BUZZ_MESA = "Mesa";

    public static String BUZZ_USUARIO = "Usuario";
    
    private static BuzzFactory instance = null;
    
    private BuzzFactory() {
        
    }
    
    /**
     * Implementção do padrão singleton.
     * @return
     */
    public static BuzzFactory getInstance() {
        if (instance == null) {
            instance = new BuzzFactory();
        }
        return instance;
    }
    
    /**
     * Cria uma instância de uma classe de negócios.
     * <br/>
     * Classe criada para abstrair o uso de frameworks como spring.
     * @param buzzName
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T retrieveBuzzInstance(String buzzName) {
        
        if (buzzName.equalsIgnoreCase(BUZZ_USUARIO)) {
            return (T) new UsuarioBuzz();
        }
        if (buzzName.equalsIgnoreCase(BUZZ_MESA)) {
            return (T) new MesaBuzz();
        }
        else {
            throw new RuntimeException("Classe de negócios não encontrada: " + buzzName);
        }
    }
}
