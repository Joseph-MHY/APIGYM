package pe.edu.idat.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.idat.Models.CarritoCompras;
import pe.edu.idat.Models.Usuarios;

@Repository
public interface ICarritoRepository extends CrudRepository<CarritoCompras, Integer> {
    CarritoCompras findByUsuario(Usuarios usuario);
}
