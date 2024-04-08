package pe.edu.idat.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import pe.edu.idat.Utils.TipoUsuario;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
public class Usuarios {

    @Id
    @Column(name = "correo", length = 50)
    private String correo;

    @Column(name = "nombre_usuario", length = 40, nullable = false)
    private String nombreUsuario;

    @Column(name = "apellido_usuario", length = 40, nullable = false)
    private String apellidoUsuario;

    @Column(name = "password", length = 12, nullable = false)
    private String password;

    @Column(name = "palabra_clave", length = 25, nullable = false)
    private String palabraClave;

    @Column(name = "dni", length = 9, nullable = false)
    private String dni;

    @Column(name = "num_celular", length = 10, nullable = false)
    private String numCelular;

    @Column(name = "altura", nullable = false)
    private Double altura;

    @Column(name = "peso", nullable = false)
    private Double peso;

    @Column(name = "fecha_registro", length = 15, nullable = false)
    private String fechaRegistro;

    @Column(name = "fecha_nacimiento", length = 15)
    private String fechaNacimiento;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private UsuarioRutina usuarioRutinas;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private UsuarioConfiguracion usuarioConfiguracions;

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private CarritoCompras carritoCompras;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", length = 15, nullable = false)
    private TipoUsuario tipoUsuario;

    public Usuarios(String correo, String nombreUsuario, String apellidoUsuario, String password, String palabraClave, String dni, String numCelular, Double altura, Double peso, String fechaRegistro, String fechaNacimiento, TipoUsuario tipoUsuario) {
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.password = password;
        this.palabraClave = palabraClave;
        this.dni = dni;
        this.numCelular = numCelular;
        this.altura = altura;
        this.peso = peso;
        this.fechaRegistro = fechaRegistro;
        this.fechaNacimiento = fechaNacimiento;
        this.tipoUsuario = tipoUsuario;
    }

    public Usuarios(String correo, String nombreUsuario, String apellidoUsuario, String password, String palabraClave, String dni, String numCelular, Double altura, Double peso, String fechaRegistro, String fechaNacimiento, UsuarioRutina usuarioRutinas, UsuarioConfiguracion usuarioConfiguracions, TipoUsuario tipoUsuario) {
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.password = password;
        this.palabraClave = palabraClave;
        this.dni = dni;
        this.numCelular = numCelular;
        this.altura = altura;
        this.peso = peso;
        this.fechaRegistro = fechaRegistro;
        this.fechaNacimiento = fechaNacimiento;
        this.usuarioRutinas = usuarioRutinas;
        this.usuarioConfiguracions = usuarioConfiguracions;
        this.tipoUsuario = tipoUsuario;
    }
}
