package dlc.tpu.buscador.controladores;


import dlc.tpu.buscador.clases.Documento;
import dlc.tpu.buscador.servicio.Buscador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

@RestController
@RequestMapping("/buscador")
public class buscadorController {

    @Autowired
    private Buscador buscador;

    @GetMapping("/")
    public Collection<Documento> buscar(@RequestParam(required = false) String consulta){
        return buscador.buscar(consulta);
    }

    @GetMapping("/todos")
    public Collection<Documento> mostrarTodos(){
        return buscador.buscarTodosLosDocumentos();
    }




}
