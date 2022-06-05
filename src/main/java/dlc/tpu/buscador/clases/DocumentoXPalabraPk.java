package dlc.tpu.buscador.clases;


import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class DocumentoXPalabraPk implements Serializable {
    private static final long serialVersionUID = -5299500110975967145L;
    private int idPal;
    private int idDoc;

    public DocumentoXPalabraPk() {
    }

    public DocumentoXPalabraPk(int idPal, int idDoc) {
        this.idPal = idPal;
        this.idDoc = idDoc;
    }

    public int getIdPal() {
        return idPal;
    }

    public void setIdPal(int idPal) {
        this.idPal = idPal;
    }

    public int getIdDoc() {
        return idDoc;
    }

    public void setIdDoc(int idDoc) {
        this.idDoc = idDoc;
    }
}