package pe.edu.idat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.DTO.ProductoDTO;
import pe.edu.idat.DTO.ProductosDTO2;
import pe.edu.idat.Models.Productos;
import pe.edu.idat.Services.ProductosService;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/productos")
public class ProductosController {

    private ProductosService productosService;

    @Autowired
    public ProductosController(ProductosService productosService) {
        this.productosService = productosService;
    }

    @GetMapping("/{categoria}")
    public List<ProductoDTO> getProductosPorCategoria(@PathVariable String categoria){
        return productosService.getProductosPorCategoria(categoria);
    }

    @GetMapping
    public Iterable<ProductosDTO2> getProductos(){
        return productosService.getProductos();
    }

    @GetMapping("/nombre")
    public ResponseEntity<ProductosDTO2> getProductoPorNombre(@RequestParam("nombre") String nombre) {
        // Decodificamos el nombre para manejar los espacios
        String nombreDecodificado = URLDecoder.decode(nombre, StandardCharsets.UTF_8);

        ProductosDTO2 productoDTO = productosService.getProductoporNombre(nombreDecodificado);
        if (productoDTO != null) {
            return new ResponseEntity<>(productoDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
