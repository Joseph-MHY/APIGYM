package pe.edu.idat.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.idat.Models.EjercicioRutina;

import java.util.List;

@Repository
public interface IEjercicioRutinaRepository extends CrudRepository<EjercicioRutina, Integer> {

    @Query(nativeQuery = true, value = "CALL GetEjerciciosPorNivel(:nivel)")
    List<Object[]> getEjerciciosPorNivel(@Param("nivel") String nivel);
}
