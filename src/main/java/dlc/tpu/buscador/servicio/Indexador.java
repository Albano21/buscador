package dlc.tpu.buscador.servicio;

import dlc.tpu.buscador.clases.*;
import dlc.tpu.buscador.repositorio.DocumentoRepository;
import dlc.tpu.buscador.repositorio.DocumentoXPalabraRepository;
import dlc.tpu.buscador.repositorio.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
@ApplicationScope
public class Indexador {

    public Indexador() {
    }

    private HashSet<String> stopWords = new HashSet<>();

    private int tamañoCarga = 20000;

    @Autowired
    private DocumentoXPalabraRepository documentoXPalabraRepo;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private PalabraRepository palabraRepository;






    public void indexar() throws FileNotFoundException {
        cargarStopWords();

        //Recorre cada documento txt de la carpeta

        String palabra = "";
        File carpeta = new File("DocumentosTP1");

        // vocabulario auxiliar
        HashMap<String, Contador> vocabularioAux;
        Scanner docScan;

        // Vocabulario final
        HashMap<String, Palabra> vocabularioFinal = new HashMap<>();

        //Conjunto de documentos a guardar al final del proceso
        HashMap<Integer, Documento> documentosAGuardar = new HashMap<>();

        //Conjunto de dxp a guardar
        ArrayList<DocumentoXPalabra> dxpACargar = new ArrayList<>();

        int contArchivos = 1;
        int contPalabra = 1;


        for (File docFile : Objects.requireNonNull(carpeta.listFiles((File pathname) -> pathname.getName().endsWith(".txt")))) {

            // crea el objeto documento y lo agrega al hashmap
            Documento documento = new Documento(contArchivos, docFile.getName(), docFile.getPath());
            documentosAGuardar.put(contArchivos, documento);
            //documentoRepository.save(documento);
            //documentos.add(documento);

            // incrementa el contador de archivos
            contArchivos++;

            // corte para hacer pocos documentos
            if (contArchivos > 12){
                break;
            }

            // vocabulario auxiliar usado para este doc, despues se pasa al vocabulario final
            vocabularioAux = new HashMap<>();


            docScan = new Scanner(docFile);

            //System.out.println("Comenzando lectuda del ducumento n°: "+ i + " " + docFile.getName());
            //i++;

            //Recorre cada palabra del documento
            while (docScan.hasNext()) {

                palabra = docScan.next();
                //int key = palabra.hashCode();
                //if (palabra == " ") continue;

                palabra = limpiezaTotal(palabra);

                // filtrar las stopwords
                if (stopWords.contains(palabra)) continue;

                //Pregunta si la palabra ya se encuentra y si lo esta aumenta su frecuencia y si no la agrega
                if (vocabularioAux.containsKey(palabra)) {
                    vocabularioAux.get(palabra).incrementar();
                } else {
                    vocabularioAux.put(palabra, new Contador());
                }
            }

            // cuando termina de cargar todas las palabras en el vocabularioAux con el while las pasa al vocabulario final
            // y si ya existen le incrementa el contDoc en 1 y verifica si maxFrec es menor a la de este doc y cambia si corresponde

            for(Object po : vocabularioAux.keySet().toArray()){
                String p =(String) po;
                int cont = vocabularioAux.remove(p).getContador();

                Palabra palabraAGuardar;

                // Se agrega al vocabulario final
                if (vocabularioFinal.containsKey(p)){
                    palabraAGuardar = vocabularioFinal.get(p);
                    // si la contador en este doc es mayor cambia la frecMax
                    if (palabraAGuardar.getMaxFrecuenciaPalabra() < cont) palabraAGuardar.setMaxFrecuenciaPalabra(cont);
                    // incrementa el contador de docs
                    palabraAGuardar.incrementarCantDocumentos();
                }
                else{
                    palabraAGuardar = new Palabra(contPalabra, p, 1,cont);
                    vocabularioFinal.put(p, palabraAGuardar);
                    contPalabra++;
                }

                // Aca se hacen los documentosXPalabra
                // poner la palabra junto con el doc y con contador que es la frecuencia en ese doc
                DocumentoXPalabra documentoXPalabra = new DocumentoXPalabra(palabraAGuardar.getId(), documento.getId(), cont);
                //agregar a lista
                dxpACargar.add(documentoXPalabra);

            }

            if (dxpACargar.size() >= tamañoCarga){
                documentoXPalabraRepo.saveAll(dxpACargar);
                dxpACargar.clear();
            }
        }

        // se asegura que no queden documentosXPalabra a cargar en la lista y si quedan los carga
        if (!dxpACargar.isEmpty()){
            documentoXPalabraRepo.saveAll(dxpACargar);
            dxpACargar.clear();
        }

        // manda a guardar todos los documentos juntos
        documentoRepository.saveAll(documentosAGuardar.values());

        // manda a guardar todas las palabras juntas
        palabraRepository.saveAll(vocabularioFinal.values());

        int contador = 0;
        for (Object po : vocabularioFinal.keySet().toArray()){
            contador++;
            String p =(String) po;
            System.out.println(p);
        }
        System.out.println(contador);

    }

    //HAY QUE TRAER LA HASHTABLE DE PALABRAS DESDE LA BASE DE DATOS y hacer tabla dxps
    /*public void indexarNuevoDoc(File docFile) throws FileNotFoundException {

        // consigue el ultimo id y crea el objeto documento
        int id = documentoRepository.getIdTopByOrderByIdDesc()+1;
        Documento documento = new Documento(id, docFile.getName(), docFile.getPath());

        // vocabulario auxiliar
        HashMap<String, Contador> vocabularioAux = new HashMap<>();
        // lista de palabras nuevas a persistir y de palabras actualizar
        Collection<Palabra> palabrasAPersistir = new ArrayList<>();
        // consigue el ultimo id de palabras y le suma 1
        int contPalabra = palabraRepository.getIdTopByOrderByIdDesc();
        contPalabra++;

        // crea el scanner para el doc
        Scanner docScan = new Scanner(docFile);

        //recorre el doc leyendo de a una palabra
        String palabra;
        while (docScan.hasNext()){
            palabra = docScan.next();

            // limpia la palabra de caracteres
            palabra = limpiezaTotal(palabra);

            // filtrar las stopwords
            cargarStopWords();
            if (stopWords.contains(palabra)) continue;

            //Pregunta si la palabra ya se encuentra y si lo esta aumenta su frecuencia y si no la agrega
            if (vocabularioAux.containsKey(palabra)) {
                vocabularioAux.get(palabra).incrementar();
            } else {
                vocabularioAux.put(palabra, new Contador());
            }
        }

        // recorre el vocabulario auxiliar y persiste las palabras nuevas,
        //a las viejas verifica que el maxtf no sea mayor y actualiza
        for(Object po : vocabularioAux.keySet().toArray()){
            String p =(String) po;
            int cont = vocabularioAux.remove(p).getContador();

            Palabra palabraAGuardar;

            // Se agrega al vocabulario final
            if (vocabularioFinal.containsKey(p)){
                palabraAGuardar = vocabularioFinal.get(p);
                // si la contador en este doc es mayor cambia la frecMax
                if (palabraAGuardar.getMaxFrecuenciaPalabra() < cont) palabraAGuardar.setMaxFrecuenciaPalabra(cont);
                // incrementa el contador de docs
                palabraAGuardar.incrementarCantDocumentos();
                //guarda la palabra en la lista de palabras a persistir
                palabrasAPersistir.add(palabraAGuardar);
            }
            else{
                palabraAGuardar = new Palabra(contPalabra, p, 1,cont);
                vocabularioFinal.put(p, palabraAGuardar);
                palabrasAPersistir.add(palabraAGuardar);
                contPalabra++;
            }

            // Aca se hacen los documentosXPalabra
            // poner la palabra junto con el doc y con contador que es la frecuencia en ese doc
            DocumentoXPalabra documentoXPalabra = new DocumentoXPalabra(palabraAGuardar.getId(), documento.getId(), cont);
            //agregar a lista
            dxpACargar.add(documentoXPalabra);
        }


        // carga todos los dxp
        documentoXPalabraRepo.saveAll(dxpACargar);
        dxpACargar.clear();

        // persiste el documento
        documentoRepository.save(documento);

        // persiste las palabras nuevas y actualiza las que ya estan
        palabraRepository.saveAll(palabrasAPersistir);

    }

    */
    private void cargarStopWords() throws FileNotFoundException {
        File fileEnglish = new File("StopsWords.txt");
        Scanner scStopWords = new Scanner(fileEnglish);

        //HashSet<String> stopWords = new HashSet<>();

        while (scStopWords.hasNext()) {

            stopWords.add(scStopWords.next());
        }

    }

    private String limpiezaTotal(String palabra) throws FileNotFoundException {
        // hacer minusculas las palabras
        palabra = palabra.toLowerCase();

        //limpiar palabra
        palabra = palabra.replaceAll("[0123456789,;.:!´{}()\"<>*_#$%&@/=?¡-]", "");
        palabra = palabra.replace("[", "");
        palabra = palabra.replace("]", "");
        palabra = palabra.replace(" ", "");

        return palabra;

    }



}
