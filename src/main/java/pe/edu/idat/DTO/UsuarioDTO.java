package pe.edu.idat.DTO;

import lombok.*;
import pe.edu.idat.Utils.TipoUsuario;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioDTO {

    private String correo;
    private String nombreUsuario;
    private String apellidoUsuario;
    private String password;
    private String palabraClave;
    private String dni;
    private String numCelular;
    private Double altura;
    private Double peso;
    private String fechaRegistro;
    private String fechaNacimiento;
    private TipoUsuario tipoUsuario;
    private UsuarioConfiguracionDTO usuarioConfiguracions;
    private UsuarioRutinaDTO usuarioRutina;
}
