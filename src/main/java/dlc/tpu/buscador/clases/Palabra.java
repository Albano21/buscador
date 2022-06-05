package dlc.tpu.buscador.clases;
import javax.persistence.*;

@Entity
@Table(name = "palabra")

public class Palabra {
    @Id
    private int id;

    private String palabra;

    @Column(name = "cant_doc")
    private int cantDocumentos;

    @Column(name = "max_frec")
    private int maxFrecuenciaPalabra;

    public Palabra() {
    }

    public Palabra(int id, String palabra, int cantDocumentos, int maxFrecuenciaPalabra) {
        this.id = id;
        this.palabra = palabra;
        this.cantDocumentos = cantDocumentos;
        this.maxFrecuenciaPalabra = maxFrecuenciaPalabra;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
