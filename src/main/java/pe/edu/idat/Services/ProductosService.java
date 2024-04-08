package pe.edu.idat.Services;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.idat.DTO.ProductoDTO;
import pe.edu.idat.DTO.ProductosDTO2;
import pe.edu.idat.Models.Productos;
import pe.edu.idat.Repositories.IProductosRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
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

    public Iterable<ProductosDTO2> getProductos(){
        Iterable<Productos> productos = iProductosRepository.findAll();
        List<ProductosDTO2> productoDTOs = new ArrayList<>();
        for (Productos producto : productos) {
            productoDTOs.add(new ProductosDTO2(producto));
        }
        return productoDTOs;
    }

    public ProductosDTO2 getProductoporNombre(String nombre) {
        Productos producto = iProductosRepository.findByNomProducto(nombre);
        if (producto != null) {
            return new ProductosDTO2(producto);
        } else {
            // Manejar el caso en que no se encuentre ningún producto con el nombre dado, por ejemplo, lanzar una excepción o devolver un valor predeterminado
            return null;
        }
    }
}
