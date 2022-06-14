package dlc.tpu.buscador.repositorio;

import dlc.tpu.buscador.clases.Palabra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface PalabraRepository extends JpaRepository<Palabra, Integer> {


    Integer getIdTopByOrderByIdDesc();

    Palabra getDistinctTopByOrderByIdDesc();

}
