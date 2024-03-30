package pe.edu.idat.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Ejercicio")
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdEjercicio")
    private Integer idEjercicio;

    @Column(name = "NomEjercicio", nullable = false, length = 50)
    private String nombreEjercicio;

    @Column(name = "DescEjercicio")
    private String descripcionEjercicio;

    @Column(name = "NomImagen")
    private String nombreImagen;

    public Ejercicio(String nombreEjercicio, String descripcionEjercicio, String nombreImagen) {
        this.nombreEjercicio = nombreEjercicio;
        this.descripcionEjercicio = descripcionEjercicio;
        this.nombreImagen = nombreImagen;
    }
}
