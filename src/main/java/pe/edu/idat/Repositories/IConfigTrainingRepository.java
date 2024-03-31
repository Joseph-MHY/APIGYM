package pe.edu.idat.Repositories;

import org.springframework.data.repository.CrudRepository;
import pe.edu.idat.Models.ConfiguracionEntrenamiento;

public interface IConfigTrainingRepository extends CrudRepository<ConfiguracionEntrenamiento, Integer> {
}
