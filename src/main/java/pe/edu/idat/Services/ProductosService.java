package pe.edu.idat.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.idat.DTO.ProductoDTO;
import pe.edu.idat.Repositories.IProductosRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductosService {

    @Autowired
    private IProductosRepository iProductosRepository;

    public ProductosService() {
    }

    public List<ProductoDTO> getProductosPorCategoria(String categoria){
        List<Object[]> productos = iProductosRepository.getProductosxCategoria(categoria);

        return productos.stream()
                .map(prod -> new ProductoDTO(
                        (String) prod[0],
                        (String) prod[1],
                        (Double) prod[2],
                        (String) prod[3]
                ))
                .collect(Collectors.toList());
    }
}
