package dlc.tpu.buscador.repositorio;

import dlc.tpu.buscador.clases.DocumentoXPalabra;
import dlc.tpu.buscador.clases.DocumentoXPalabraPk;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface DocumentoXPalabraRepository extends JpaRepository<DocumentoXPalabra, DocumentoXPalabraPk> {


    List<DocumentoXPalabra> findAllByClavePrimaria(DocumentoXPalabraPk clave);

    List<DocumentoXPalabra> findAllByClavePrimaria_IdPal(int idPal);
}
