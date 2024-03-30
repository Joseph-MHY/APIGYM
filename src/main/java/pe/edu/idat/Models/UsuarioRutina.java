package pe.edu.idat.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class UsuarioRutina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuarioRutina;

    @OneToOne
    @JoinColumn(name = "correo")
    private Usuarios usuario;

    @ManyToOne
    @JoinColumn(name = "id")
    private Rutina rutina;
}
