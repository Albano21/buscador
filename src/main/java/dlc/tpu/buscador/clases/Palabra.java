package dlc.tpu.buscador.clases;
import javax.persistence.*;

@Entity
@Table(name = "palabra")

public class Palabra {
    @Id
    private String palabra;

    @Column(name = "cant_doc")
    private int cantDocumentos;

    @Column(name = "max_frec")
    private int maxFrecuenciaPalabra;

   // @Transient
   // private PalabraController palabController = new PalabraController();
    public Palabra() {
    }

    public Palabra(String palabra, int cantDocumentos, int maxFrecuenciaPalabra) {
        this.palabra = palabra;
        this.cantDocumentos = cantDocumentos;
        this.maxFrecuenciaPalabra = maxFrecuenciaPalabra;
    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public int getCantDocumentos() {
        return cantDocumentos;
    }

    public void setCantDocumentos(int cantDocumentos) {
        this.cantDocumentos = cantDocumentos;
    }

    public void incrementarCantDocumentos(){
        this.cantDocumentos++;
    }

    public int getMaxFrecuenciaPalabra() {
        return maxFrecuenciaPalabra;
    }

    public void setMaxFrecuenciaPalabra(int maxFrecuenciaPalabra) {
        this.maxFrecuenciaPalabra = maxFrecuenciaPalabra;
    }

}
