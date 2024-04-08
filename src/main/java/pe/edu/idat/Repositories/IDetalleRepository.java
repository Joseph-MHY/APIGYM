package pe.edu.idat.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pe.edu.idat.Models.DetalleCarritoCompras;

@Repository
public interface IDetalleRepository extends CrudRepository<DetalleCarritoCompras, Integer> {
}
