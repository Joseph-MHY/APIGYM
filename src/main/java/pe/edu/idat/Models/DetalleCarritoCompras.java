package pe.edu.idat.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "DetalleCarritoCompras")
public class DetalleCarritoCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdDetalle")
    private Integer idDetalle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProdCarrito", referencedColumnName = "IdProdCarrito")
    private CarritoCompras carritoCompras;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdProducto", referencedColumnName = "IdProducto")
    private Productos producto;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    public DetalleCarritoCompras(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public DetalleCarritoCompras(CarritoCompras carritoCompras, Productos producto, Integer cantidad) {
        this.carritoCompras = carritoCompras;
        this.producto = producto;
        this.cantidad = cantidad;
    }
}
