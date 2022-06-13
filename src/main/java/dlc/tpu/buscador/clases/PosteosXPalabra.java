package dlc.tpu.buscador.clases;

import com.sun.source.tree.Tree;
import dlc.tpu.buscador.soporte.Heap;

import java.util.TreeSet;

public class PosteosXPalabra implements Comparable<PosteosXPalabra> {

    private Palabra palabra;

    //necesito que esta coleccion este ordenada por el tf
    // es un heap descendente de mayor a menor posteo
    private TreeSet<DocumentoXPalabra> posteos;

    public PosteosXPalabra(Palabra palabra) {
        this.palabra = palabra;
        this.posteos = new TreeSet<DocumentoXPalabra>();
    }

    public Palabra getPalabra() {
        return palabra;
    }

    public DocumentoXPalabra sacarPrimerPosteo(){
        return posteos.first();
    }

    public TreeSet<DocumentoXPalabra> getPosteos() {
        return posteos;
    }

    public int getTamañoLista(){
        return posteos.size();
    }

    public boolean esListaVacia(){
        if (posteos.size() == 0){
            return true;
        }
        else{
            return false;
        }
    }

    //compara segun el tamaño de la lista
    @Override
    public int compareTo(PosteosXPalabra o) {
        if (this.palabra.equals(o.palabra)) {
            return 0;
        } else if (o.getTamañoLista() - this.getTamañoLista() > 0) {
            return 1;
        }else if (o.getTamañoLista() - this.getTamañoLista() < 0){
            return -1;
        }
        else{
            return -1;
        }
    }

    public void agregarPosteo(DocumentoXPalabra documentoXPalabra){
        posteos.add(documentoXPalabra);
    }
}
