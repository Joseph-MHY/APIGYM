package pe.edu.idat.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.idat.Models.Rutina;

@Repository
public interface IRutinaRepository extends CrudRepository<Rutina, Integer> {
}
