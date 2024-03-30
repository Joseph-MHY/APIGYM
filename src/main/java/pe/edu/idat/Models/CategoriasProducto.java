package pe.edu.idat.Models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "CategoriasProd")
@Data
public class CategoriasProducto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdCategoria")
    private Integer IdCategoria;

    @Column(name = "NomCategoria", nullable = false, length = 60)
    private String nomCategoria;
}
