package dlc.tpu.buscador.repositorio;

import dlc.tpu.buscador.clases.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PalabraRepository extends JpaRepository<Palabra, String> {


}
