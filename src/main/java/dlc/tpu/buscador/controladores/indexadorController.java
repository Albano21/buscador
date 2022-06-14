package dlc.tpu.buscador.controladores;

import dlc.tpu.buscador.servicio.Indexador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/indexador")
public class indexadorController {

    @Autowired
    private Indexador indexador;

    // llama a la indexacion base
    @PutMapping("/")
    public ResponseEntity indexar(){
        try {
            indexador.indexar();
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/agregar/")
    public ResponseEntity agregarNuevoDoc(@RequestParam(required = false) String path){
        try {
            indexador.indexarPorPath(path);
            return ResponseEntity.ok().build();
        } catch (FileNotFoundException e) {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }
}
