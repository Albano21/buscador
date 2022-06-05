package dlc.tpu.buscador.clases;


import javax.persistence.*;

@Entity
@Table(name = "documento")

public class Documento {
    @Id
    private int id;

    private String name;

    @Basic
    private String path;

    public Documento() {

    }

    public Documento(int id, String name, String path) {
        this.id = id;
        this.name = name;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

