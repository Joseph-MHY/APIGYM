package pe.edu.idat.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.idat.DTO.CarritoDTO;
import pe.edu.idat.DTO.DetalleCarritoDTO;
import pe.edu.idat.DTO.ProductosDTO2;
import pe.edu.idat.Models.*;
import pe.edu.idat.Repositories.ICategoriaProductoRepository;
import pe.edu.idat.Repositories.IDetalleRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class DetalleCarritoService {

    private IDetalleRepository iDetalleRepository;
    private UsuarioService usuarioService;
    private ProductosService productosService;
    private ICategoriaProductoRepository iCategoriaProductoRepository;

    @Autowired
    public DetalleCarritoService(IDetalleRepository iDetalleRepository, UsuarioService usuarioService, ProductosService productosService, ICategoriaProductoRepository iCategoriaProductoRepository) {
        this.iDetalleRepository = iDetalleRepository;
        this.usuarioService = usuarioService;
        this.productosService = productosService;
        this.iCategoriaProductoRepository = iCategoriaProductoRepository;
    }

    public DetalleCarritoService() {
    }

    public ResponseEntity<Object> postDetalle(DetalleCarritoDTO detalleDTO) {
        if(detalleDTO == null ||
                detalleDTO.getCantidad() == null ||
                detalleDTO.getCorreo() == null ||
                detalleDTO.getNomProducto() == null
        ) {
            return ResponseEntity.badRequest().body("Uno o más campos están vacíos");
        }

        Usuarios usuario = usuarioService.getUsuarioByEmail(detalleDTO.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        CarritoDTO carrito = usuarioService.obtenerCarritoporCorreo(usuario.getCorreo());

        ProductosDTO2 producto = productosService.getProductoporNombre(detalleDTO.getNomProducto());

        CarritoCompras carritoCompras = new CarritoCompras();
        carritoCompras.setIdProdCarrito(carrito.getIdProdCarrito());
        carritoCompras.setUsuario(usuario);
        carritoCompras.setFechaCreacion(carrito.getFechaCreacion());

        CategoriasProducto categoria = iCategoriaProductoRepository.findById(producto.getIdCategoria())
                .orElseThrow(() -> new RuntimeException("Categoría de producto no encontrada"));

        Productos prod = new Productos();
        prod.setIdProducto(producto.getIdProducto());
        prod.setNomProducto(producto.getNomProducto());
        prod.setDescripcionProducto(producto.getDescripcionProducto());
        prod.setPrecCosto(producto.getPrecCosto());
        prod.setPrecVenta(producto.getPrecVenta());
        prod.setCategoriasProducto(categoria);
        prod.setStock(producto.getStock());
        prod.setImagen_producto(producto.getImagen_producto());

        DetalleCarritoCompras detalle = new DetalleCarritoCompras();
        detalle.setCantidad(detalleDTO.getCantidad());
        detalle.setCarritoCompras(carritoCompras);
        detalle.setProducto(prod);

        try {
            iDetalleRepository.save(detalle);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Detalle creado correctamente");
            response.put("Detalle", detalle.toString());
            return ResponseEntity.ok().body(response);
        } catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el detalle: " + ex.getMessage());
        }
    }

}
