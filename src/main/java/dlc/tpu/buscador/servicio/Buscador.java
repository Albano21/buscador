package dlc.tpu.buscador.servicio;

import dlc.tpu.buscador.clases.Documento;
import dlc.tpu.buscador.clases.DocumentoXPalabra;
import dlc.tpu.buscador.clases.Palabra;
import dlc.tpu.buscador.clases.PosteosXPalabra;
import dlc.tpu.buscador.repositorio.DocumentoRepository;
import dlc.tpu.buscador.repositorio.DocumentoXPalabraRepository;
import dlc.tpu.buscador.repositorio.PalabraRepository;
import dlc.tpu.buscador.soporte.Heap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.print.Doc;
import java.util.*;

public class Buscador {

    @Autowired
    private PalabraRepository palabraRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private DocumentoXPalabraRepository documentoXPalabraRepo;

    public Collection<Documento> Buscador(String consulta) {

        //-------------------------------ENTRADAS--------------------------------
        //El conjunto D con N documentos di.
        HashMap<Integer, Documento> documentos = new HashMap<>();
        for (Documento doc : documentoRepository.findAll()){
            documentos.put(doc.getId(), doc);
        }

        // Calcular N
        long N = documentos.size();

        // Vocabulario final
        HashMap<String, Palabra> vocabularioFinal = new HashMap<>();
        for(Palabra p : palabraRepository.findAll()) {
            vocabularioFinal.put(p.getPalabra(), p);
        }

        //La cantidad R de documentos que se desea recuperar. (lo harcodeo despues vemos)
        int R = 40;

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
            for(DocumentoXPalabra dxp : documentoXPalabraRepo.findAllByIdPalabra(palabra.getId())){
                posteosXPalabra.agregarPosteo(dxp);
            }
            //agrego el objeto posteosXPalabra a la lista de la consulta
            listasDePosteoConsulta.add(posteosXPalabra);
        }

        //-------------------------------PROCESO--------------------------------
        //creo la lista de documentos de la consulta, de tamaño R(ranking)
        // tienen que estar ordenados por ranking. cambiar arraylist por lo que vaya
        LinkedList<Documento> listaDocumentosConsulta = new LinkedList<>();

        // mientras haya palabras
        while(!listasDePosteoConsulta.isEmpty()){
            //saca del heap el posteo de la palabra que tiene menor cantidad de posteos
            PosteosXPalabra posteoDePalabra1 = listasDePosteoConsulta.get();
            // saca la palabra
            Palabra palabra1 = posteoDePalabra1.getPalabra();
            // En la lista Pk de tk, repetir R veces
            for (int i = 0; i < posteoDePalabra1.getTamañoLista(); i++) {
                DocumentoXPalabra dxp = posteoDePalabra1.getUnPosteo();
                int idDoc = dxp.getIdDoc();
                Documento docAAgregar = documentos.get(idDoc);
                if(!listaDocumentosConsulta.contains(docAAgregar)){
                    docAAgregar.setIr(0);
                    //aca se calcula el ir
                    int tf = dxp.getTf();
                    int nr = palabra1.getCantDocumentos();
                    double idf = Math.log(N/nr);
                    double ir = tf*idf;
                    docAAgregar.sumarIr(ir);
                    // agrego el documento
                    listaDocumentosConsulta.add(docAAgregar);
                }
                else{
                    //ver que pasa si el documento ya esta en la lista de documentos a mostrar
                }
            }
        }
        // ordeno la lista de documentos, se supone que se ordena por ir
        Collections.sort(listaDocumentosConsulta);

        // saco los primeros R documentos en una lista final
        LinkedList<Documento> listaDocumentosConsultaFinal = new LinkedList<>();
        for (int i = 0; i < R; i++) {
            Documento docR = listaDocumentosConsulta.getFirst();
            listaDocumentosConsultaFinal.add(docR);
        }

        //-------------------------------SALIDA--------------------------------
        return listaDocumentosConsultaFinal;
    }

}
