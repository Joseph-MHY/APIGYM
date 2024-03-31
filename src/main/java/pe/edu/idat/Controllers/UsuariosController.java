package pe.edu.idat.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.DTO.*;
import pe.edu.idat.Models.*;
import pe.edu.idat.Repositories.IUsuarioConfigurationRepo;
import pe.edu.idat.Repositories.IUsuarioRutinaRepository;
import pe.edu.idat.Services.UsuarioService;
import pe.edu.idat.Utils.Constantes;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/usuarios")
public class UsuariosController {

    private UsuarioService usuarioService;
    private IUsuarioConfigurationRepo iUsuarioConfigurationRepo;
    private IUsuarioRutinaRepository iUsuarioRutinaRepository;

    @Autowired
    public UsuariosController(UsuarioService usuarioService, IUsuarioConfigurationRepo iUsuarioConfigurationRepo, IUsuarioRutinaRepository iUsuarioRutinaRepository) {
        this.usuarioService = usuarioService;
        this.iUsuarioConfigurationRepo = iUsuarioConfigurationRepo;
        this.iUsuarioRutinaRepository = iUsuarioRutinaRepository;
    }

    public UsuariosController() {
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuarios() {
        Iterable<Usuarios> usuarios = usuarioService.getUsuarios();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuarios usuario : usuarios) {
            UsuarioDTO usuarioDTO = convertirAUsuarioDTO(usuario);
            usuariosDTO.add(usuarioDTO);
        }
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{correo}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable String correo) {
        Optional<Usuarios> optionalUsuario = usuarioService.getUsuarioByEmail(correo);
        Usuarios usuario = optionalUsuario.orElse(null);
        System.out.println("OptionarUsuario: "+optionalUsuario.get().getUsuarioRutinas().getRutina().toString().toString());
        if (usuario == null) {
            return ResponseEntity.notFound().build();
        }
        UsuarioDTO usuarioDTO = convertirAUsuarioDTO(usuario);
        return ResponseEntity.ok(usuarioDTO);
    }


    private UsuarioDTO convertirAUsuarioDTO(Usuarios usuario) {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setCorreo(usuario.getCorreo());
        usuarioDTO.setNombreUsuario(usuario.getNombreUsuario());
        usuarioDTO.setApellidoUsuario(usuario.getApellidoUsuario());
        usuarioDTO.setPassword(usuario.getPassword());
        usuarioDTO.setPalabraClave(usuario.getPalabraClave());
        usuarioDTO.setDni(usuario.getDni());
        usuarioDTO.setNumCelular(usuario.getNumCelular());
        usuarioDTO.setAltura(usuario.getAltura());
        usuarioDTO.setPeso(usuario.getPeso());
        usuarioDTO.setFechaRegistro(usuario.getFechaRegistro());
        usuarioDTO.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioDTO.setTipoUsuario(usuario.getTipoUsuario());

        // Convertir configuración a DTO si existe
        UsuarioConfiguracionDTO configuracionDTO = new UsuarioConfiguracionDTO();
        UsuarioConfiguracion configuracion = usuario.getUsuarioConfiguracions();
        if (configuracion != null) {
            configuracionDTO.setIdUsuarioConfiguracion(configuracion.getIdUsuarioConfiguracion());
            configuracionDTO.setConfiguracionEntrenamiento(convertirAConfiguracionEntrenamientoDTO(configuracion.getConfiguracionEntrenamiento()));
        }

        // Convertir rutina a DTO si existe
        UsuarioRutinaDTO rutinaDTO = new UsuarioRutinaDTO();
        UsuarioRutina rutina = usuario.getUsuarioRutinas();
        if (rutina != null) {
            rutinaDTO.setIdUsuarioRutina(rutina.getIdUsuarioRutina());
            rutinaDTO.setRutina(convertirARutinaDTO(rutina.getRutina()));
        }

        // Configurar configuraciones y rutinas
        usuarioDTO.setUsuarioConfiguracions(configuracion != null ? configuracionDTO : new UsuarioConfiguracionDTO());
        usuarioDTO.setUsuarioRutina(rutina != null ? rutinaDTO : new UsuarioRutinaDTO());
        System.out.println("convertidor a configuracion: "+convertirAConfiguracionEntrenamiento(configuracionDTO.getConfiguracionEntrenamiento()));
        System.out.println("convertidor a rutina: "+convertirARutina(rutinaDTO.getRutina()));
        System.out.println(convertirARutina(rutinaDTO.getRutina()).getClass());
        System.out.println(usuarioDTO);
        return usuarioDTO;
    }

    private ConfiguracionEntrenamientoDTO convertirAConfiguracionEntrenamientoDTO(ConfiguracionEntrenamiento configuracion) {
        ConfiguracionEntrenamientoDTO configuracionDTO = new ConfiguracionEntrenamientoDTO();
        configuracionDTO.setIdconfig(configuracion.getIdconfig());
        configuracionDTO.setDiasEntrenamiento(configuracion.getDiasEntrenamiento());
        configuracionDTO.setDiaInicioEntrenamiento(configuracion.getDiaInicioEntrenamiento());
        return configuracionDTO;
    }

    private RutinaDTO convertirARutinaDTO(Rutina rutina) {
        RutinaDTO rutinaDTO = new RutinaDTO();
        rutinaDTO.setId(rutina.getId());
        rutinaDTO.setNombreRutina(rutina.getNombreRutina());
        rutinaDTO.setDescripcion(rutina.getDescripcion());
        return rutinaDTO;
    }

    private ConfiguracionEntrenamiento convertirAConfiguracionEntrenamiento(ConfiguracionEntrenamientoDTO configuraciondto) {
        ConfiguracionEntrenamiento configuracion = new ConfiguracionEntrenamiento();
        configuracion.setIdconfig(configuraciondto.getIdconfig());
        configuracion.setDiasEntrenamiento(configuraciondto.getDiasEntrenamiento());
        configuracion.setDiaInicioEntrenamiento(configuraciondto.getDiaInicioEntrenamiento());
        return configuracion;
    }

    private Rutina convertirARutina(RutinaDTO rutinaDTO){
        Rutina rutina = new Rutina();
        rutina.setId(rutinaDTO.getId());
        rutina.setNombreRutina(rutinaDTO.getNombreRutina());
        rutina.setDescripcion(rutinaDTO.getDescripcion());
        return rutina;
    }

    @PostMapping("/usuarioSolo")
    public ResponseEntity<Object> crearUsuarioSolo(@RequestBody UsuarioDTO usuarioDTO) {
        // Validación del DTO y creación del usuario
        Usuarios usuario = new Usuarios();
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setApellidoUsuario(usuarioDTO.getApellidoUsuario());
        usuario.setNombreUsuario(usuarioDTO.getNombreUsuario());
        usuario.setPassword(usuarioDTO.getPassword());
        usuario.setPalabraClave(usuarioDTO.getPalabraClave());
        usuario.setNumCelular(usuarioDTO.getNumCelular());
        usuario.setAltura(usuarioDTO.getAltura());
        usuario.setPeso(usuarioDTO.getPeso());
        usuario.setDni(usuarioDTO.getDni());
        usuario.setFechaRegistro(usuarioDTO.getFechaRegistro());
        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());
        usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());

        ConfiguracionEntrenamiento configuracionEntrenamiento = new ConfiguracionEntrenamiento();
        configuracionEntrenamiento.setIdconfig(usuarioDTO.getUsuarioConfiguracions().getConfiguracionEntrenamiento().getIdconfig());
        configuracionEntrenamiento.setDiasEntrenamiento(usuarioDTO.getUsuarioConfiguracions().getConfiguracionEntrenamiento().getDiasEntrenamiento());
        configuracionEntrenamiento.setDiaInicioEntrenamiento(usuarioDTO.getUsuarioConfiguracions().getConfiguracionEntrenamiento().getDiaInicioEntrenamiento());

        Rutina rutina = new Rutina();
        rutina.setId(usuarioDTO.getUsuarioRutina().getRutina().getId());
        rutina.setNombreRutina(usuarioDTO.getUsuarioRutina().getRutina().getNombreRutina());
        rutina.setDescripcion(usuarioDTO.getUsuarioRutina().getRutina().getDescripcion());

        usuarioService.postUsuario(usuario);
        UsuarioConfiguracion usuarioConfiguracion = new UsuarioConfiguracion();
        usuarioConfiguracion.setUsuario(usuario);
        usuarioConfiguracion.setConfiguracionEntrenamiento(configuracionEntrenamiento);

        UsuarioRutina usuarioRutina = new UsuarioRutina();
        usuarioRutina.setUsuario(usuario);
        usuarioRutina.setRutina(rutina);

        iUsuarioConfigurationRepo.save(usuarioConfiguracion);
        iUsuarioRutinaRepository.save(usuarioRutina);
        // Retornar respuesta exitosa
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "Usuario creado correctamente");
        response.put("Usuario", usuario);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody Map<String, String> requestBody) {
        try {
            String correo = requestBody.get("correo");
            String password = requestBody.get("password");

            if (correo == null || password == null) {
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "El correo y la contraseña no pueden ser nulos."));
            }

            boolean loginExitoso = usuarioService.loginUsuario(correo, password);
            if (loginExitoso) {
                return ResponseEntity.ok().body(Collections.singletonMap("mensaje", "Credenciales correctas"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("mensaje", "Credenciales inválidas"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Error al procesar la solicitud de inicio de sesión: " + ex.getMessage()));
        }
    }

    @GetMapping("/email/{correo}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable("correo") String correo) {
        try {
            Optional<Usuarios> usuarioOptional = usuarioService.getUsuarioByEmail(correo);
            if (usuarioOptional.isPresent()) {
                Usuarios usuario = usuarioOptional.get();
                return ResponseEntity.ok().body(usuario);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().body("Error al obtener el usuario por correo: " + ex.getMessage());
        }
    }

    @PutMapping("/{correo}")
    public ResponseEntity<Object> actualizarPerfil(@PathVariable String correo, @RequestBody Usuarios usuarioModificado) {
        Usuarios usuarioActualizado = usuarioService.actualizarPerfil(correo, usuarioModificado);
        if (usuarioActualizado != null) {
            // Construye el mensaje JSON de éxito
            Map<String, String> response = new HashMap<>();
            response.put("mensajePerfil", "Perfil actualizado correctamente para el correo: " + correo);
            // Devuelve el mensaje JSON con el estado HTTP aceptado (202)
            return ResponseEntity.accepted().body(response);
        } else {
            // Construye el mensaje JSON de error
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("mensajePerfil", "No se pudo actualizar el perfil para el correo: " + correo);
            // Devuelve el mensaje de error en formato JSON con el estado HTTP no encontrado (404)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @PutMapping("/actualizarpassword")
    public ResponseEntity<Object> actualizarContra(@RequestBody Map<String, String> requestBody) {
        String correo = requestBody.get("correo");
        String password = requestBody.get("password");
        String palabraClave = requestBody.get("palabraClave");

        if (correo == null || password == null || palabraClave == null) {
            return ResponseEntity.badRequest().body("El correo, la contraseña y la palabra clave no pueden ser nulos.");
        }

        // Llamar al servicio para obtener el usuario por correo
        Optional<Usuarios> usuarioOptional = usuarioService.getUsuarioByEmail(correo);
        if (usuarioOptional.isPresent()) {
            Usuarios usuario = usuarioOptional.get();

            // Verificar si la palabra clave coincide con la registrada para el usuario
            if (palabraClave.equals(usuario.getPalabraClave())) {
                // Actualizar la contraseña
                usuario.setPassword(password);

                // Guardar los cambios en la base de datos
                Usuarios usuarioActualizado = usuarioService.actualizarpass(correo, password);

                if (usuarioActualizado != null) {
                    // Construir el mensaje JSON de éxito
                    Map<String, String> response = new HashMap<>();
                    response.put("mensaje", "Contraseña actualizada correctamente");
                    // Devolver el mensaje JSON con el estado HTTP aceptado (202)
                    return ResponseEntity.accepted().body(response);
                } else {
                    // Construir el mensaje JSON de error
                    Map<String, String> errorResponse = Collections.singletonMap("mensaje", "No se pudo actualizar la contraseña");
                    // Devolver el mensaje de error en formato JSON con el estado HTTP interno del servidor (500)
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
                }
            } else {
                // La palabra clave no coincide con la registrada para el usuario
                return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "La palabra clave no coincide con la registrada para el usuario."));
            }
        } else {
            // No se encontró ningún usuario con el correo dado
            return ResponseEntity.badRequest().body(Collections.singletonMap("mensaje", "Correo no registrado"));
        }
    }

}
