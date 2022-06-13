package dlc.tpu.buscador.servicio;

import dlc.tpu.buscador.clases.*;
import dlc.tpu.buscador.repositorio.DocumentoRepository;
import dlc.tpu.buscador.repositorio.DocumentoXPalabraRepository;
import dlc.tpu.buscador.repositorio.PalabraRepository;
import dlc.tpu.buscador.soporte.Heap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.*;

@Service
@ApplicationScope
public class Buscador {

    @Autowired
    private PalabraRepository palabraRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private DocumentoXPalabraRepository documentoXPalabraRepo;

    private HashMap<Integer, Documento> documentos = new HashMap<>();

    private HashMap<String, Palabra> vocabularioFinal = new HashMap<>();

    public LinkedList<Documento> buscar(String consulta) {

        // hacer que el vocabulario final y el conjunto de documentos se carguen solo la primera vez que se activa el
        // buscador, hay que ponerlos como atributos de la clase y usarlos para busquedas dentro de la misma sesion

        //-------------------------------ENTRADAS--------------------------------
        //El conjunto D con N documentos di.
        if (documentos.isEmpty()){
            documentos = cargarDocumentos();
        }

        // Calcular N
        long N = documentos.size();

        // Vocabulario final
        //HashMap<String, Palabra> vocabularioFinal = cargarVocabularioFinal();
        if (vocabularioFinal.isEmpty()){
            vocabularioFinal = cargarVocabularioFinal();
        }

        //La cantidad R de documentos que se desea recuperar. (lo harcodeo despues vemos)
        int R = 40;

        //Llama al metodo para carga los posteos
        TreeSet<PosteosXPalabra> listasDePosteoConsulta = cargarPosteos(consulta);


        //-------------------------------PROCESO--------------------------------
        // creo la lista de documentos de la consulta, de tamaño R(ranking)
        // tienen que estar ordenados por ranking. cambiar arraylist por lo que vaya
        LinkedList<Documento> listaDocumentosConsulta = new LinkedList<>();

        // mientras haya palabras
        for(PosteosXPalabra posteoDePalabra1 : listasDePosteoConsulta.descendingSet()){

            //saca del heap el posteo de la palabra que tiene menor cantidad de posteos
            //PosteosXPalabra posteoDePalabra1 = listasDePosteoConsulta.last();
            //listasDePosteoConsulta.remove(posteoDePalabra1);

            // saca la palabra
            Palabra palabra1 = posteoDePalabra1.getPalabra();

            // En la lista Pk de tk, repetir R veces
            //for (int i = 0; i < R; i++)
            for (DocumentoXPalabra dxp : posteoDePalabra1.getPosteos().descendingSet()){
                //if (!posteoDePalabra1.esListaVacia()) {
                    // saca el primer posteo de la lista, es decir el que tiene mayor tf
                    //DocumentoXPalabra dxp = posteoDePalabra1.sacarPrimerPosteo();
                    // saca el id del documento del posteo
                    int idDoc = dxp.getIdDoc();

                    Documento docAAgregar = documentos.get(idDoc);

                    //aca se calcula el ir
                    int tf = dxp.getTf();
                    int nr = palabra1.getCantDocumentos();
                    double idf = Math.log(((double) N) / ((double)nr));
                    double ir = tf * idf;

                    if (!listaDocumentosConsulta.contains(docAAgregar)) {
                        docAAgregar.setIr(ir);
                        // agrego el documento
                        listaDocumentosConsulta.add(docAAgregar);
                    }
                    else {
                        //ver que pasa si el documento ya esta en la lista de documentos a mostrar
                        //creo que hay que subirle el tir, para que ascienda en ranking
                        docAAgregar.sumarIr(ir);
                    }

            }
        }
        // ordeno la lista de documentos, se supone que se ordena por ir
        Collections.sort(listaDocumentosConsulta);

        // saco los primeros R documentos en una lista final
        LinkedList<Documento> listaDocumentosConsultaFinal = new LinkedList<>();

        int contR = 0;
        for (int i = 0; i < listaDocumentosConsulta.size(); i++) {
            if (contR <= R) {
                Documento docR = listaDocumentosConsulta.removeLast();
                listaDocumentosConsultaFinal.add(docR);
            }
            else{
                break;
            }
            contR++;
        }

        //-------------------------------SALIDA--------------------------------
        return listaDocumentosConsultaFinal;
    }

    private HashMap<Integer, Documento> cargarDocumentos(){
        HashMap<Integer, Documento> documentos = new HashMap<>();
        for (Documento doc : documentoRepository.findAll()){
            documentos.put(doc.getId(), doc);
        }
        return documentos;
    }

    private HashMap<String, Palabra> cargarVocabularioFinal(){
        HashMap<String, Palabra> vocabularioFinal = new HashMap<>();
        for(Palabra p : palabraRepository.findAll()) {
            vocabularioFinal.put(p.getPalabra(), p);
        }
        return vocabularioFinal;
    }

    private TreeSet<PosteosXPalabra> cargarPosteos(String consulta){
        // Posteo: La lista de posteo Pk de cada término tk
        // ordenado por la cantidad de posteos que tenga la lista(de menor a mayor)
        TreeSet<PosteosXPalabra> listasDePosteoConsulta = new TreeSet<>();

        // por cada palabra de la String consulta hago un objeto palabra y una lista de posteo para esa palabra
        for(String palabraCadena : consulta.split(" ")){
            if(vocabularioFinal.containsKey(palabraCadena)){
                Palabra palabraObjeto = vocabularioFinal.get(palabraCadena);

                //creo la lista de posteos para esta palabra
                PosteosXPalabra posteosXPalabra = new PosteosXPalabra(palabraObjeto);

                //por cada dxp de cada palabra la agrego a su lista
                //DocumentoXPalabraPk clave = new DocumentoXPalabraPk();
                //clave.setIdPal(palabraObjeto.getId());
                for(DocumentoXPalabra dxp : documentoXPalabraRepo.findAllByClavePrimaria_IdPal(palabraObjeto.getId())){
                    posteosXPalabra.agregarPosteo(dxp);
                }

                //agrego el objeto posteosXPalabra a la lista de la consulta
                listasDePosteoConsulta.add(posteosXPalabra);
            }
            else{
                //ver que pasa si no esta en el vocabulario
            }

        }

        return listasDePosteoConsulta;
    }

    public Collection<Documento> buscarTodosLosDocumentos(){

        if (vocabularioFinal.isEmpty()){
            vocabularioFinal = cargarVocabularioFinal();
        }

        if (documentos.isEmpty()){
            documentos = cargarDocumentos();
        }
        return documentos.values();
    }

    private void formaViejaDeCargarPosteos(String consulta){
        // hacer los posteos directamente desde la string consulta, saca la tablaConsulta

        // Tabla consulta: tiene las palabras de la consulta
        HashMap<String, Palabra> tablaConsulta = new HashMap<>();
        for(String palabra : consulta.split(" ")) {
            if(vocabularioFinal.containsKey(palabra)) {
                tablaConsulta.put(palabra, vocabularioFinal.get(palabra));
            }
            else{
                //ver que pasa si no esta en el vocabulario
            }
        }

        // Posteo: La lista de posteo Pk de cada término tk
        // ordenado por la cantidad de posteos que tenga la lista(de menor a mayor)
        Heap<PosteosXPalabra> listasDePosteoConsulta = new Heap<>(true);
        //por cada palabra de la consulta
        for(Palabra palabra : tablaConsulta.values()){
            //creo la lista de posteos
            PosteosXPalabra posteosXPalabra = new PosteosXPalabra(palabra);
            //por cada dxp de cada palabra la agrego a su lista
            DocumentoXPalabraPk clave = new DocumentoXPalabraPk();
            clave.setIdPal(palabra.getId());
            for(DocumentoXPalabra dxp : documentoXPalabraRepo.findAllByClavePrimaria(clave)){
                posteosXPalabra.agregarPosteo(dxp);
            }
            //agrego el objeto posteosXPalabra a la lista de la consulta
            listasDePosteoConsulta.add(posteosXPalabra);
        }
    }


}
