package dlc.tpu.buscador.servicio;

import dlc.tpu.buscador.clases.Documento;
import dlc.tpu.buscador.clases.Palabra;
import dlc.tpu.buscador.repositorio.DocumentoRepository;
import dlc.tpu.buscador.repositorio.DocumentoXPalabraRepository;
import dlc.tpu.buscador.repositorio.PalabraRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class Buscador {

    @Autowired
    private PalabraRepository palabraRepository;
    @Autowired
    private DocumentoRepository documentoRepository;
    @Autowired
    private DocumentoXPalabraRepository documentoXPalabraRepo;

    public Collection<Documento> Buscador(String consulta) {

        // Calcular N
       long N = documentoRepository.count();

        // Vocabulario final
        HashMap<String, Palabra> vocabularioFinal = new HashMap<>();

        for(Palabra p : palabraRepository.findAll()) {
            vocabularioFinal.put(p.getPalabra(), p);
        }
        // Tabla consulta
        HashMap<String, Palabra> tablaConsulta = new HashMap<>();
        for(String palabra : consulta.split(" ")) {
            if(vocabularioFinal.containsKey(palabra)) {
                tablaConsulta.put(palabra, vocabularioFinal.get(palabra));
            }
            else{
                //ver que pasa si no esta en el vocabulario
            }
        }







        return null;
    }


}
