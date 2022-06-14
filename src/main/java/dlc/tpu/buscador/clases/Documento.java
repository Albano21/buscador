package dlc.tpu.buscador.clases;


import javax.persistence.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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

    @Transient
    private String descripcion;

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

    public void setIr(double ir) {
        this.ir = ir;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void sumarIr(double ir){
        this.ir += ir;
    }

    @Override
    public int compareTo(Documento o) {

        if ((this.ir - o.ir > 0)) {
            return 1;
        } else if ((this.ir - o.ir < 0)) {
            return -1;
        } else {
            return this.id - o.id;
        }


/*
        int respuesta = 0;
        // ver si esto esta bien
        if (this.ir - o.ir > 0){
            respuesta =  1;
        }
        else{
            respuesta = -1;
        }
        return respuesta;

 */




    }

    //Metodo que carga la descripcion del doc
    public void cargarDescripcion() {
        Scanner scDoc = null;
        try {
            scDoc = new Scanner(new File(path));
            while(scDoc.hasNextLine()){
                String linea = scDoc.nextLine();
                if (linea.isEmpty()){
                    break;
                }
                descripcion += linea + "\n";
            }
        } catch (FileNotFoundException e) {
            descripcion = "Sin descripcion.";
        }


    }
}

