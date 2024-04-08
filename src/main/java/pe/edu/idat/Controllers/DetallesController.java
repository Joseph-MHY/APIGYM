package pe.edu.idat.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.idat.DTO.DetalleCarritoDTO;
import pe.edu.idat.Services.DetalleCarritoService;
import pe.edu.idat.Utils.Constantes;

@RestController
@CrossOrigin
@RequestMapping("/api/detalle")
public class DetallesController {

    @Autowired
    private DetalleCarritoService detalleService;

    @PostMapping("/postDetail")
    public ResponseEntity<Object> postDetail(@RequestBody  DetalleCarritoDTO detalle){
            return detalleService.postDetalle(detalle);
    }
}
