package pe.edu.idat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.DTO.ProductoDTO;
import pe.edu.idat.Services.ProductosService;

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
}
