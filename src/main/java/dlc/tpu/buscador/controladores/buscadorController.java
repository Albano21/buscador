package dlc.tpu.buscador.controladores;


import dlc.tpu.buscador.clases.Documento;
import dlc.tpu.buscador.servicio.Buscador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/descripcion/")
    public Collection<Documento> buscarConDescripcion(@RequestParam(required = false) String consulta){
        return buscador.buscarConDescripcion(consulta);
    }


/*
    @GetMapping("/{consulta}")
    public Collection<Documento> buscar(@PathVariable String consulta){
        return buscador.buscar(consulta);
    }
 */


    @GetMapping("/todos")
    public Collection<Documento> mostrarTodos(){
        return buscador.buscarTodosLosDocumentos();
    }

    @GetMapping("/todos/descripcion")
    public Collection<Documento> mostrarTodosConDescripcion(){
        return buscador.buscarTodosConDescripcion();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> buscarDocPorId(@PathVariable int id){
        if (buscador.buscarPorId(id) != null) {
            return ResponseEntity.ok(buscador.buscarPorId(id));
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }




}
