package dlc.tpu.buscador.clases;


import javax.persistence.*;

@Entity
@Table(name = "documento")

public class Documento {
    @Id
    private String name;

    @Basic
    private String path;

  //  @Transient
   // private DocumentoController docController = new DocumentoController();

    public Documento() {

    }

    public Documento(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

