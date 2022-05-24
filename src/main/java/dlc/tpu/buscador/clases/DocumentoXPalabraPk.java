package dlc.tpu.buscador.clases;


import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DocumentoXPalabraPk implements Serializable {
    private static final long serialVersionUID = -5299500110975967145L;
    private String palabra;
    private String name_doc;

    public DocumentoXPalabraPk(String palabra, String nameDoc) {
        this.palabra = palabra;
        this.name_doc = nameDoc;
    }

    public DocumentoXPalabraPk() {

    }

    public String getPalabra() {
        return palabra;
    }

    public String getName_doc() {
        return name_doc;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public void setName_doc(String name_doc) {
        this.name_doc = name_doc;
    }
}
