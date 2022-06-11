package dlc.tpu.buscador.clases;


import javax.persistence.*;

@Entity
@Table(name = "documento")

public class Documento implements Comparable<Documento>{
    @Id
    private int id;

    private String name;

    @Basic
    private String path;

    @Transient
    private double ir;

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

    public double getIr() {
        return ir;
    }

    public void setIr(float ir) {
        this.ir = ir;
    }

    public void sumarIr(double ir){
        this.ir += ir;
    }

    @Override
    public int compareTo(Documento o) {
        // ver si esto esta bien
        int respuesta = 0;
        if (this.ir - o.ir > 0){
            respuesta = 1;
        }
        else{
            respuesta = 1;
        }
        return respuesta;
    }
}

