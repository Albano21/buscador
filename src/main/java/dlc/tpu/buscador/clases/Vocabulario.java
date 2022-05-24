package dlc.tpu.buscador.clases;
import java.util.HashMap;

public class Vocabulario {
    private HashMap<String, Palabra> vocabulario;

    public Vocabulario() {
        this.vocabulario = new HashMap<>();
    }

    public boolean contains(String p){
        if (vocabulario.containsKey(p)){
            return true;
        }
        return false;
    }

    public Palabra get(String p){
        return vocabulario.get(p);
    }
}
