package pe.edu.idat.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.DTO.*;
import pe.edu.idat.Models.*;
import pe.edu.idat.Repositories.ICarritoRepository;
import pe.edu.idat.Services.UsuarioService;
import pe.edu.idat.Utils.Constantes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/usuarios")
public class UsuariosController {

    private UsuarioService usuarioService;

    @Autowired
    public UsuariosController(UsuarioService usuarioService, ICarritoRepository iCarritoRepository) {
        this.usuarioService = usuarioService;
    }

    public UsuariosController() {
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> getUsuarios() {
        List<UsuarioDTO> usuariosDTO = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(usuariosDTO);
    }

    @GetMapping("/{correo}")
    public ResponseEntity<UsuarioDTO> getUsuario(@PathVariable String correo) {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorCorreo(correo);
        if (usuarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDTO);
    }

    @GetMapping("/carrito/{id}")
    public CarritoDTO getcarrito(@PathVariable Integer id){
        return usuarioService.obtenerCarrito(id);
    }

    @GetMapping("/carritoCorreo/{correo}")
    public CarritoDTO getcarritoxCorreo(@PathVariable String correo){
        return usuarioService.obtenerCarritoporCorreo(correo);
    }

    @PostMapping("/signup")
    public ResponseEntity<Object> postUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            if(usuarioDTO != null){
                Usuarios userExists = usuarioService.getUsuarioByEmail(usuarioDTO.getCorreo()).orElse(null);
                if(userExists == null){
                    usuarioService.postUsuario(usuarioDTO);
                    return ResponseEntity.accepted().body(Constantes.returnMessageAndObject(Constantes.CREATED_USER, usuarioDTO));
                } else {
                    return ResponseEntity.badRequest().body(Constantes.returnMessage(Constantes.ERROR_USER));
                }
            } else {
                return ResponseEntity.badRequest().body("Error al crear el usuario");
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
    public ResponseEntity<UsuarioDTO> getUserByEmail(@PathVariable("correo") String correo) {
        UsuarioDTO usuarioDTO = usuarioService.obtenerUsuarioPorCorreo(correo);
        if (usuarioDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuarioDTO);
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
