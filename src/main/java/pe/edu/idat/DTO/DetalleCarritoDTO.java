package pe.edu.idat.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCarritoDTO {

    private Integer cantidad;
    private String correo;
    private String nomProducto;
}
