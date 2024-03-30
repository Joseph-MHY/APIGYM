package pe.edu.idat.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UsuarioConfiguracion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuarioConfiguracion;

    @OneToOne
    @JoinColumn(name = "correo")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "idconfig")
    private ConfiguracionEntrenamiento configuracionEntrenamiento;
}
