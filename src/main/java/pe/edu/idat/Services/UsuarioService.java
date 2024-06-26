package pe.edu.idat.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.idat.DTO.*;
import pe.edu.idat.Models.*;
import pe.edu.idat.Repositories.*;
import pe.edu.idat.Utils.TipoUsuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UsuarioService {

    private IUsuarioRepository iUsuarioRepository;
    private IConfigTrainingRepository iConfigTrainingRepository;
    private IRutinaRepository iRutinaRepository;
    private IUsuarioConfigurationRepo iUsuarioConfigurationRepo;
    private IUsuarioRutinaRepository iUsuarioRutinaRepository;
    private ICarritoRepository iCarritoRepository;

    @Autowired
    public UsuarioService(pe.edu.idat.Repositories.IUsuarioRepository IUsuarioRepository,
                          IConfigTrainingRepository iConfigTrainingRepository,
                          IRutinaRepository iRutinaRepository,
                          IUsuarioConfigurationRepo iUsuarioConfigurationRepo,
                          IUsuarioRutinaRepository iUsuarioRutinaRepository,
                          ICarritoRepository iCarritoRepository) {
        this.iUsuarioRepository = IUsuarioRepository;
        this.iConfigTrainingRepository = iConfigTrainingRepository;
        this.iRutinaRepository = iRutinaRepository;
        this.iUsuarioConfigurationRepo = iUsuarioConfigurationRepo;
        this.iUsuarioRutinaRepository = iUsuarioRutinaRepository;
        this.iCarritoRepository = iCarritoRepository;
    }

    public UsuarioService() {
    }

    // Metodos para leer usuarios
    public Iterable<Usuarios> findAll() {
        return iUsuarioRepository.findAll();
    }

    public Optional<Usuarios> getUsuarioByEmail(String correo) {
        return iUsuarioRepository.findByCorreo(correo);
    }

    public CarritoDTO obtenerCarrito(Integer idCarrito) {
        Optional<CarritoCompras> optionalCarrito = iCarritoRepository.findById(idCarrito);
        CarritoCompras carrito = optionalCarrito.orElse(null);

        if (carrito != null) {
            return new CarritoDTO(carrito);
        } else {
            return null;
        }
    }

    public CarritoDTO obtenerCarritoporCorreo(String correo) {
        Usuarios usuario = getUsuarioByEmail(correo).orElse(null);
        CarritoCompras carrito = iCarritoRepository.findByUsuario(usuario);
        if (carrito != null) {
            return new CarritoDTO(
                    carrito.getIdProdCarrito(),
                    carrito.getFechaCreacion(),
                    carrito.getUsuario().getCorreo()
            );
        } else {
            return null; // O podrías lanzar una excepción indicando que no se encontró el carrito
        }
    }

    public List<UsuarioDTO> obtenerUsuarios() {
        Iterable<Usuarios> usuarios = iUsuarioRepository.findAll();
        List<UsuarioDTO> usuariosDTO = new ArrayList<>();
        for (Usuarios usuario : usuarios) {
            UsuarioDTO usuarioDTO = convertirAUsuarioDTO(usuario);
            usuariosDTO.add(usuarioDTO);
        }
        return usuariosDTO;
    }

    public UsuarioDTO obtenerUsuarioPorCorreo(String correo) {
        Optional<Usuarios> optionalUsuario = iUsuarioRepository.findByCorreo(correo);
        Usuarios usuario = optionalUsuario.orElse(null);
        if (usuario == null) {
            return null;
        }
        return convertirAUsuarioDTO(usuario);
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

    // Metodos para guardar usuario
    public ResponseEntity<Object> postUsuario(UsuarioDTO usuarioDTO) {
        // Validación del DTO y creación del usuario
        if (validateFields(usuarioDTO)) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensajeError", "Uno o más campos están vacíos");
            return ResponseEntity.badRequest().body(response);
        }

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

        // Obtener fecha actual en formato yyyy-MM-dd
        String fechaActualString = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
        usuario.setFechaRegistro(fechaActualString);

        usuario.setFechaNacimiento(usuarioDTO.getFechaNacimiento());

        // Automaticamente es Usuario
        usuario.setTipoUsuario(TipoUsuario.Usuario);
        /*
        // Obteniendo la configuracion y rutina del usuarioDTO según el id de estos
        Optional<ConfiguracionEntrenamiento> config = iConfigTrainingRepository.findById(usuarioDTO.getIdconfig());
        Optional<Rutina> rutine = iRutinaRepository.findById(usuarioDTO.getIdRutina());

        if (!config.isPresent() && !rutine.isPresent()) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensajeError", "Configuracion y Rutina no existentes");
            return ResponseEntity.badRequest().body(response);
        }

        ConfiguracionEntrenamiento configuracionEntrenamiento = new ConfiguracionEntrenamiento();
        if (config.isPresent()) {
            configuracionEntrenamiento.setIdconfig(config.get().getIdconfig());
            configuracionEntrenamiento.setDiasEntrenamiento(config.get().getDiasEntrenamiento());
            configuracionEntrenamiento.setDiaInicioEntrenamiento(config.get().getDiaInicioEntrenamiento());
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("mensajeError", "Configuracion no existente");
            return ResponseEntity.badRequest().body(response);
        }

        Rutina rutina = new Rutina();
        if (rutine.isPresent()) {
            rutina.setId(rutine.get().getId());
            rutina.setNombreRutina(rutine.get().getNombreRutina());
            rutina.setDescripcion(rutine.get().getDescripcion());
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("mensajeError", "Rutina no existente");
            return ResponseEntity.badRequest().body(response);
        }

         */

        //Se crea antes de querer crear un UsuarioConfiguracion
        try {
            iUsuarioRepository.save(usuario);
            /*
            UsuarioConfiguracion usuarioConfiguracion = new UsuarioConfiguracion();
            usuarioConfiguracion.setUsuario(usuario);
            usuarioConfiguracion.setConfiguracionEntrenamiento(configuracionEntrenamiento);

            UsuarioRutina usuarioRutina = new UsuarioRutina();
            usuarioRutina.setUsuario(usuario);
            usuarioRutina.setRutina(rutina);

            iUsuarioConfigurationRepo.save(usuarioConfiguracion);
            iUsuarioRutinaRepository.save(usuarioRutina);
             */

            CarritoCompras carrito = new CarritoCompras();
            carrito.setIdProdCarrito(null);
            carrito.setUsuario(usuario);
            carrito.setFechaCreacion(fechaActualString);
            iCarritoRepository.save(carrito);
            // Retornar respuesta exitosa
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario creado correctamente");
            response.put("Usuario", usuario);
            return ResponseEntity.ok().body(response);
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.badRequest().body(response);
        }
    }

    private boolean validateFields(UsuarioDTO usuarioDTO) {
        return usuarioDTO.getCorreo() == null || usuarioDTO.getCorreo().isEmpty() ||
                usuarioDTO.getApellidoUsuario() == null || usuarioDTO.getApellidoUsuario().isEmpty() ||
                usuarioDTO.getNombreUsuario() == null || usuarioDTO.getNombreUsuario().isEmpty() ||
                usuarioDTO.getPassword() == null || usuarioDTO.getPassword().isEmpty() ||
                usuarioDTO.getPalabraClave() == null || usuarioDTO.getPalabraClave().isEmpty() ||
                usuarioDTO.getNumCelular() == null || usuarioDTO.getNumCelular().isEmpty() ||
                usuarioDTO.getAltura() == null || usuarioDTO.getPeso() == null ||
                usuarioDTO.getDni() == null || usuarioDTO.getFechaNacimiento() == null;
    }


    public boolean loginUsuario(String correo, String password) {
        Optional<Usuarios> usuarioOptional = iUsuarioRepository.findById(correo);
        if (usuarioOptional.isPresent()) {
            Usuarios usuario = usuarioOptional.get();
            // Verificar si la contraseña coincide
            return usuario.getPassword().equals(password);
        }
        return false; // Si el usuario no existe
    }

    public Usuarios postearUsuario(Usuarios usuario){
        if (usuario != null ) {
            usuario.setTipoUsuario(TipoUsuario.Usuario);
            return iUsuarioRepository.save(usuario);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no válido: " + usuario.getTipoUsuario());
        }
    }

    public Usuarios actualizarPerfil(String correo, Usuarios usuarioModificado) {

        Optional<Usuarios> usuarioOptional = iUsuarioRepository.findById(correo);
        return usuarioOptional.map(usuario -> {
            // Actualizar los campos del usuario
            usuario.setNombreUsuario(usuarioModificado.getNombreUsuario());
            usuario.setApellidoUsuario(usuarioModificado.getApellidoUsuario());
            usuario.setDni(usuarioModificado.getDni());
            usuario.setNumCelular(usuarioModificado.getNumCelular());
            usuario.setAltura(usuarioModificado.getAltura());
            usuario.setPeso(usuarioModificado.getPeso());
            usuario.setFechaNacimiento(usuarioModificado.getFechaNacimiento());
            usuario.setPalabraClave(usuarioModificado.getPalabraClave());
            // Guardar los cambios en la base de datos
            return iUsuarioRepository.save(usuario);
        }).orElse(null);
    }

    public Usuarios actualizarpass(String correo, String password) {
        Optional<Usuarios> usuarioOptional = iUsuarioRepository.findById(correo);
        return usuarioOptional.map(usuario -> {
            // Actualizar la contraseña del usuario
            usuario.setPassword(password);
            // Guardar los cambios en la base de datos
            return iUsuarioRepository.save(usuario);
        }).orElse(null);
    }
}
