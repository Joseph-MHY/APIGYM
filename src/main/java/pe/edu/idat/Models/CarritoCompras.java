package pe.edu.idat.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CarritoCompras")
@Getter
@Setter
@NoArgsConstructor
public class CarritoCompras {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProdCarrito")
    private Integer idProdCarrito;

    @Column(name = "FechaCreacion", nullable = false, length = 15)
    private String fechaCreacion;

    @OneToOne
    @JoinColumn(name = "correo", referencedColumnName = "correo", insertable = false, updatable = false)
    private Usuarios usuario;

    public CarritoCompras(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CarritoCompras(Integer idProdCarrito, String fechaCreacion) {
        this.idProdCarrito = idProdCarrito;
        this.fechaCreacion = fechaCreacion;
    }

    public CarritoCompras(Integer idProdCarrito, String fechaCreacion, Usuarios usuario) {
        this.idProdCarrito = idProdCarrito;
        this.fechaCreacion = fechaCreacion;
        this.usuario = usuario;
    }
}

