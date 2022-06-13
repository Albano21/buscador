package dlc.tpu.buscador.clases;
import javax.persistence.*;

@Entity
@Table(name = "documento_x_palabra")
public class DocumentoXPalabra implements Comparable<DocumentoXPalabra>{

    @EmbeddedId
    private DocumentoXPalabraPk clavePrimaria;

    @Basic
    private int tf;

   // @Transient
    //private DocumentoXPalabraController dxpController = new DocumentoXPalabraController();

    public DocumentoXPalabra() {}

    public DocumentoXPalabra(int idPalabra, int idDoc, int frecuencia) {
        this.tf = frecuencia;
        this.clavePrimaria = new DocumentoXPalabraPk(idPalabra, idDoc);
    }

    public int getIdPalabra() {
        return clavePrimaria.getIdPal();
    }

    public void setPalabra(int palabra) {this.clavePrimaria.setIdPal(palabra);}

    public int getIdDoc() {
        return clavePrimaria.getIdDoc();
    }

    public void setIdDoc(int nameDoc) {this.clavePrimaria.setIdDoc(nameDoc);}

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }

    @Override
    public int compareTo(DocumentoXPalabra o) {

        if(this.tf - o.tf > 0){
            return 1;
        }
        else if(this.tf - o.tf < 0){
            return -1;
        }
        else{
            return this.getIdDoc() - o.getIdDoc();
        }
    }
}


