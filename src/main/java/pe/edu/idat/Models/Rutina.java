package pe.edu.idat.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Rutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nombre_rutina", nullable = false, length = 60)
    private String nombreRutina;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "rutina")
    private List<UsuarioRutina> usuarioRutinas;

    public Rutina(String nombreRutina, String descripcion) {
        this.nombreRutina = nombreRutina;
        this.descripcion = descripcion;
    }
}

