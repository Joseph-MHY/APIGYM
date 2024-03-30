package pe.edu.idat.Models;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ConfiguracionEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idconfig")
    private Integer idconfig;

    @Column(name = "DiasEntrenamiento")
    private Integer diasEntrenamiento;

    @Column(name = "DiaInicioEntrenamiento", length = 15)
    private String diaInicioEntrenamiento;

    @OneToMany(mappedBy = "configuracionEntrenamiento")
    private List<UsuarioConfiguracion> usuarioConfiguracions;

    public ConfiguracionEntrenamiento(Integer diasEntrenamiento, String diaInicioEntrenamiento) {
        this.diasEntrenamiento = diasEntrenamiento;
        this.diaInicioEntrenamiento = diaInicioEntrenamiento;
    }
}
