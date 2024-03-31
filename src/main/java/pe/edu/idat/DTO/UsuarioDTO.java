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

    //se usan para indicar la configuracion y Rutina del usuario en el metodo POST
    private Integer idconfig;
    private Integer idRutina;

    private UsuarioConfiguracionDTO usuarioConfiguracions;
    private UsuarioRutinaDTO usuarioRutina;
}
