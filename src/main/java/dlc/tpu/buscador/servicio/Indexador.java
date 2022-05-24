package dlc.tpu.buscador.servicio;

import dlc.tpu.buscador.clases.*;
import dlc.tpu.buscador.repositorio.DocumentoXPalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

@Service
public class Indexador {

    public Indexador() {
    }

    private HashSet<String> stopWords = new HashSet<>();

    private int tamañoCarga = 400;

    @Autowired
    private DocumentoXPalabraRepository documentoXPalabraRepo;

    //Lista de posteos a cargar
    private ArrayList<DocumentoXPalabra> dxpACargar = new ArrayList<>();

    public void indexar() throws FileNotFoundException {


        //Recorre cada documento txt de la carpeta

        String palabra = "";
        File carpeta = new File("D:\\Users\\Ezequiel Torres\\Documents\\FACULTAD ARCHIVOS\\Cuarto Año\\DLC\\AA\\buscador\\DocumentosTP1");

        // vocabulario auxiliar
        HashMap<String, Contador> vocabularioAux;
        Scanner docScan;

        //Vocabulario vocabularioFinal = new Vocabulario();
        HashMap<String, Palabra> vocabularioFinal = new HashMap<>();

        int contArchivos = 0;

        for (File docFile : Objects.requireNonNull(carpeta.listFiles((File pathname) -> pathname.getName().endsWith(".txt")))) {

            contArchivos++;
            if (contArchivos >= 3){
                break;
            }
            vocabularioAux = new HashMap<>();
            //Documento documento = new Documento(docFile.getName(), docFile.getPath());
            //documentos.add(documento);

            docScan = new Scanner(docFile);

            //System.out.println("Comenzando lectuda del ducumento n°: "+ i + " " + docFile.getName());
            //i++;

            //Recorre cada palabra del documento
            while (docScan.hasNext()) {

                palabra = docScan.next();
                //int key = palabra.hashCode();

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


            // cuando termina de cargar todas las palabras en el vocabularioAux con el while las pasa al vocabulario final
            // y si ya existen le incrementa el contDoc en 1 y verifica si maxFrec es menor a la de este doc y cambia si corresponde

            for(Object po : vocabularioAux.keySet().toArray()){
                String p =(String) po;
                int cont = vocabularioAux.remove(p).getContador();

                // Aca se hacen los documentosXPalabra
                // poner la palabra junto con el doc y con contador que es la frecuencia en ese doc
                //
                DocumentoXPalabra documentoXPalabra = new DocumentoXPalabra(palabra, docFile.getName(), cont);
                //agregar a lista
                dxpACargar.add(documentoXPalabra);

                // Se agrega al vocabulario final
                if (vocabularioFinal.containsKey(p)){
                    Palabra palabraExistente = vocabularioFinal.get(p);
                    // si la contador en este doc es mayor cambia la frecMax
                    if (palabraExistente.getMaxFrecuenciaPalabra() < cont) palabraExistente.setMaxFrecuenciaPalabra(cont);
                    // incrementa el contador de docs
                    palabraExistente.incrementarCantDocumentos();
                }
                else{
                    Palabra nuevaPalabra = new Palabra(p, 1,cont);
                    vocabularioFinal.put(p, nuevaPalabra);
                }
            }

            if (dxpACargar.size() >= tamañoCarga){
                documentoXPalabraRepo.saveAll(dxpACargar);
            }
        }

        int contador = 0;
        for (Object po : vocabularioFinal.keySet().toArray()){
            contador++;
            String p =(String) po;
            System.out.println(p);
        }
        System.out.println(contador);

    }

    private void cargarStopWords() throws FileNotFoundException {
        File fileEnglish = new File("D:\\Users\\Ezequiel Torres\\Documents\\FACULTAD ARCHIVOS\\Cuarto Año\\DLC\\AA\\buscador\\StopsWords.txt");
        Scanner scStopWords = new Scanner(fileEnglish);

        //HashSet<String> stopWords = new HashSet<>();

        while (scStopWords.hasNext()) {

            stopWords.add(scStopWords.next());
        }

        //return stopWords;

    }

    private String limpiezaTotal(String palabra) throws FileNotFoundException {
        // hacer minusculas las palabras
        palabra = palabra.toLowerCase();

        //limpiar palabra
        palabra = palabra.replaceAll("[0123456789,;.:!´{}()\"<>*_#$%&@/=?¡-]", "");
        palabra = palabra.replace("[", "");
        palabra = palabra.replace("]", "");

        return palabra;

    }

}
