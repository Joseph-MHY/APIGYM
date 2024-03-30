package pe.edu.idat.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Productos")
@Getter
@Setter
@NoArgsConstructor
public class Productos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdProducto")
    private Integer idProducto;

    @Column(name = "NomProducto", length = 60)
    private String nomProducto;

    @Column(name = "DescripcionProducto", length = 200)
    private String descripcionProducto;

    @Column(name = "PrecCosto")
    private double precCosto;

    @Column(name = "PrecVenta")
    private double precVenta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IdCategoria", referencedColumnName = "IdCategoria")
    private CategoriasProducto categoriasProducto;

    @Column(name = "Stock")
    private int stock;

    @Column(name = "Imagen_producto")
    private String imagen_producto;

    public Productos(String nomProducto, String descProducto, double precCosto, double precVenta, int stock, String imagen_producto) {
        this.nomProducto = nomProducto;
        this.descripcionProducto = descProducto;
        this.precCosto = precCosto;
        this.precVenta = precVenta;
        this.stock = stock;
        this.imagen_producto = imagen_producto;
    }

    public Productos(String nomProducto, String descipcionProducto, double precCosto, double precVenta, CategoriasProducto categoriasProducto, int stock, String imagen_producto) {
        this.nomProducto = nomProducto;
        this.descripcionProducto = descipcionProducto;
        this.precCosto = precCosto;
        this.precVenta = precVenta;
        this.categoriasProducto = categoriasProducto;
        this.stock = stock;
        this.imagen_producto = imagen_producto;
    }
}
