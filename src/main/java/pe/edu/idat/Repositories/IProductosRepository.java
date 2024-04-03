package pe.edu.idat.Repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.idat.Models.Productos;

import java.util.List;

@Repository
public interface IProductosRepository extends CrudRepository<Productos, Integer> {

    @Query(nativeQuery = true, value = "CALL ObtenerProductosPorCategoria(:categoria)")
    List<Object[]> getProductosxCategoria(@Param("categoria") String categoria);
}
