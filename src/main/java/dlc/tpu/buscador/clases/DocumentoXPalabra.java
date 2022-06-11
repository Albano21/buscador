package dlc.tpu.buscador.clases;
import javax.persistence.*;

@Entity
@Table(name = "documento_x_palabra")
public class DocumentoXPalabra implements Comparable<DocumentoXPalabra>{

    @EmbeddedId
    private DocumentoXPalabraPk clave_primaria;

    @Basic
    private int tf;

   // @Transient
    //private DocumentoXPalabraController dxpController = new DocumentoXPalabraController();

    public DocumentoXPalabra() {}

    public DocumentoXPalabra(int idPalabra, int idDoc, int frecuencia) {
        this.tf = frecuencia;
        this.clave_primaria = new DocumentoXPalabraPk(idPalabra, idDoc);
    }

    public int getIdPalabra() {
        return clave_primaria.getIdPal();
    }

    public void setPalabra(int palabra) {this.clave_primaria.setIdPal(palabra);}

    public int getIdDoc() {
        return clave_primaria.getIdDoc();
    }

    public void setIdDoc(int nameDoc) {this.clave_primaria.setIdDoc(nameDoc);}

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    @Override
    public int compareTo(DocumentoXPalabra o) {
        return this.tf - o.tf;
    }
}


