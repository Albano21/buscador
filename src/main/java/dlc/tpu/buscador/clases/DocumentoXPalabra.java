package dlc.tpu.buscador.clases;
import javax.persistence.*;

@Entity
@Table(name = "documento_x_palabra")
public class DocumentoXPalabra {

    @EmbeddedId
    private DocumentoXPalabraPk clave_primaria;

    @Basic
    private int tf;

   // @Transient
    //private DocumentoXPalabraController dxpController = new DocumentoXPalabraController();

    public DocumentoXPalabra() {}

    public DocumentoXPalabra(String palabra, String nameDoc, int frecuencia) {
        this.tf = frecuencia;
        this.clave_primaria = new DocumentoXPalabraPk(palabra, nameDoc);
    }

    public String getPalabra() {
        return clave_primaria.getPalabra();
    }

    public void setPalabra(String palabra) {this.clave_primaria.setPalabra(palabra);}

    public String getNameDoc() {
        return clave_primaria.getName_doc();
    }

    public void setNameDoc(String nameDoc) {this.clave_primaria.setName_doc(nameDoc);}

    public int getTf() {
        return tf;
    }

    public void setTf(int tf) {
        this.tf = tf;
    }
}


