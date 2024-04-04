package pe.edu.idat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.DTO.EjercicioDTO;
import pe.edu.idat.Services.EjercicioService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/ejercicios")
public class EjercicioController {

    private EjercicioService ejercicioService;

    @Autowired
    public EjercicioController(EjercicioService ejercicioService) {
        this.ejercicioService = ejercicioService;
    }

    @GetMapping("/{nivel}") // Básico, Medio, Avanzado
    public List<EjercicioDTO> getEjerciciosPorNivel(@PathVariable String nivel) {
        return ejercicioService.getEjerciciosPorNivel(nivel);
    }
}
