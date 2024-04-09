package pe.edu.idat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.DTO.ConfiguracionEntrenamientoDTO;
import pe.edu.idat.Models.ConfiguracionEntrenamiento;
import pe.edu.idat.Models.UsuarioConfiguracion;
import pe.edu.idat.Models.Usuarios;
import pe.edu.idat.Services.ConfiguracionEntrenamientoService;
import pe.edu.idat.Services.UsuarioService;
import pe.edu.idat.Repositories.IUsuarioConfigurationRepo;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/configuracion")
public class ConfiguracionEntrenamientoController {

    private final ConfiguracionEntrenamientoService configuracionEntrenamientoService;

    @Autowired
    public ConfiguracionEntrenamientoController(ConfiguracionEntrenamientoService configuracionEntrenamientoService) {
        this.configuracionEntrenamientoService = configuracionEntrenamientoService;
    }

    @PostMapping("/configuracion")
    public ResponseEntity<Object> crearConfiguracionEntrenamiento(@RequestBody ConfiguracionEntrenamientoDTO configuracionDTO) {
        try {
            // Crear una nueva instancia de ConfiguracionEntrenamiento a partir de los datos recibidos en el DTO
            ConfiguracionEntrenamiento nuevaConfiguracion = new ConfiguracionEntrenamiento();
            nuevaConfiguracion.setDiasEntrenamiento(configuracionDTO.getDiasEntrenamiento());
            nuevaConfiguracion.setDiaInicioEntrenamiento(configuracionDTO.getDiaInicioEntrenamiento());

            // Guardar la nueva configuración de entrenamiento utilizando el servicio
            configuracionEntrenamientoService.guardarConfiguracion(nuevaConfiguracion);

            // Retornar una respuesta exitosa con el mensaje de éxito
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Se ha insertado la configuración correctamente");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            // Manejar cualquier error y retornar una respuesta de error
            return ResponseEntity.badRequest().body("Error al procesar la solicitud: " + ex.getMessage());
        }
    }

}
