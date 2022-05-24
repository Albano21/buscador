package dlc.tpu.buscador.repositorio;

import dlc.tpu.buscador.clases.DocumentoXPalabra;
import dlc.tpu.buscador.clases.DocumentoXPalabraPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoXPalabraRepository extends JpaRepository<DocumentoXPalabra, DocumentoXPalabraPk> {
}
