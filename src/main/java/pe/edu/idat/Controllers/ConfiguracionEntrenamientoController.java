package pe.edu.idat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.Models.ConfiguracionEntrenamiento;
import pe.edu.idat.Models.UsuarioConfiguracion;
import pe.edu.idat.Models.Usuarios;
import pe.edu.idat.Services.ConfiguracionEntrenamientoService;
import pe.edu.idat.Services.UsuarioService;
import pe.edu.idat.Repositories.IUsuarioConfigurationRepo;

import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/configuracion")
public class ConfiguracionEntrenamientoController {

    private final ConfiguracionEntrenamientoService configuracionEntrenamientoService;
    private final UsuarioService usuarioService;
    private final IUsuarioConfigurationRepo usuarioConfiguracionRepository;

    @Autowired
    public ConfiguracionEntrenamientoController(ConfiguracionEntrenamientoService configuracionEntrenamientoService,
                                                UsuarioService usuarioService,
                                                IUsuarioConfigurationRepo usuarioConfiguracionRepository) {
        this.configuracionEntrenamientoService = configuracionEntrenamientoService;
        this.usuarioService = usuarioService;
        this.usuarioConfiguracionRepository = usuarioConfiguracionRepository;
    }

    @PostMapping("/POST")
    public ResponseEntity<Object> postConfiguracionEntrenamiento(@RequestBody Map<String, Object> requestBody) {
        try {
            String correoUsuario = (String) requestBody.get("correoUsuario");
            Map<String, Object> configuracionData = (Map<String, Object>) requestBody.get("configuracionEntrenamiento");

            if (correoUsuario == null || configuracionData == null) {
                return ResponseEntity.badRequest().body("El correo del usuario o los datos de configuración de entrenamiento no pueden ser nulos.");
            }

            Optional<Usuarios> usuarioOptional = usuarioService.getUsuarioByEmail(correoUsuario);
            if (!usuarioOptional.isPresent()) {
                return ResponseEntity.badRequest().body("No se encontró ningún usuario con el correo proporcionado.");
            }

            Usuarios usuario = usuarioOptional.get();

            ConfiguracionEntrenamiento configuracionEntrenamiento = new ConfiguracionEntrenamiento();
            configuracionEntrenamiento.setDiasEntrenamiento((Integer) configuracionData.get("diasEntrenamiento"));
            configuracionEntrenamiento.setDiaInicioEntrenamiento((String) configuracionData.get("diaInicioEntrenamiento"));

            ConfiguracionEntrenamiento configuracionGuardada = configuracionEntrenamientoService.guardarConfiguracion(configuracionEntrenamiento);

            if (configuracionGuardada != null) {
                UsuarioConfiguracion usuarioConfiguracion = new UsuarioConfiguracion();
                usuarioConfiguracion.setUsuario(usuario);
                usuarioConfiguracion.setConfiguracionEntrenamiento(configuracionGuardada);
                usuarioConfiguracionRepository.save(usuarioConfiguracion);

                return ResponseEntity.accepted().body("Configuración de entrenamiento creada correctamente y asociada al usuario.");
            } else {
                return ResponseEntity.badRequest().body("Error al guardar la configuración de entrenamiento.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Error al procesar la solicitud: " + ex.getMessage());
        }
    }

}
