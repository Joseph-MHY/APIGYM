package pe.edu.idat.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.idat.Models.UsuarioConfiguracion;

@Repository
public interface IUsuarioConfigRepository extends CrudRepository<UsuarioConfiguracion, Integer> {
}
