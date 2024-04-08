package pe.edu.idat.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.idat.Models.CarritoCompras;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarritoDTO {

    private Integer idProdCarrito;
    private String fechaCreacion;
    private String correoUsuario;

    public CarritoDTO(CarritoCompras carrito) {
        this.idProdCarrito = carrito.getIdProdCarrito();
        this.fechaCreacion = carrito.getFechaCreacion();
        this.correoUsuario = carrito.getUsuario().getCorreo();
    }
}
