package dlc.tpu.buscador.repositorio;

import dlc.tpu.buscador.clases.Documento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentoRepository extends JpaRepository<Documento, String> {
}
