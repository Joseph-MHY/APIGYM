package pe.edu.idat.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import pe.edu.idat.Models.ConfiguracionEntrenamiento;
import pe.edu.idat.Models.Usuarios;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioConfiguracionDTO {

    private Integer idUsuarioConfiguracion;

    private Integer configuracionEntrenamiento;
}
