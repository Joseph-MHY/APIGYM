package pe.edu.idat.DTO;

import lombok.*;
import pe.edu.idat.Models.Productos;

@Getter
@Setter
@NoArgsConstructor
public class ProductosDTO2 {
    private Integer idProducto;
    private String nomProducto;
    private String descripcionProducto;
    private double precCosto;
    private double precVenta;
    private int stock;
    private String imagen_producto;
    private Integer idCategoria; // Agregar el ID de la categoría para mostrar en el DTO

    public ProductosDTO2(Productos producto) {
        this.idProducto = producto.getIdProducto();
        this.nomProducto = producto.getNomProducto();
        this.descripcionProducto = producto.getDescripcionProducto();
        this.precCosto = producto.getPrecCosto();
        this.precVenta = producto.getPrecVenta();
        this.stock = producto.getStock();
        this.imagen_producto = producto.getImagen_producto();
        this.idCategoria = producto.getCategoriasProducto().getIdCategoria(); // Obtener el ID de la categoría
    }
}
