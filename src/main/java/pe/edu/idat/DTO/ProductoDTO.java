package pe.edu.idat.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductoDTO {

    private String imagen_producto;
    private String nom_producto;
    private Double prec_venta;
    private String descripcion_producto;
}
