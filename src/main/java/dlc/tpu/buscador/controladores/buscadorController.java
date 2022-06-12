package dlc.tpu.buscador.controladores;


import dlc.tpu.buscador.clases.Documento;
import dlc.tpu.buscador.servicio.Buscador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

@RestController
@RequestMapping("/buscador")
public class buscadorController {

    @Autowired
    private Buscador buscador;

    @GetMapping("/{consulta}")
    public Collection<Documento> buscar(@PathVariable String consulta){
        return buscador.buscar(consulta);
    }




}
