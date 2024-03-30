package pe.edu.idat.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.idat.Models.UsuarioRutina;

@Repository
public interface IUsuarioRutinaRepository extends CrudRepository<UsuarioRutina, Integer> {
}
