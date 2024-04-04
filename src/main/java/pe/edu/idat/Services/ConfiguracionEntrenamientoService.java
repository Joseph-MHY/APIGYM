package pe.edu.idat.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.idat.Models.ConfiguracionEntrenamiento;
import pe.edu.idat.Repositories.IConfigTrainingRepository;

@Slf4j
@Service
public class ConfiguracionEntrenamientoService {

    @Autowired
    private IConfigTrainingRepository iConfigTrainingRepository;

    public ConfiguracionEntrenamientoService() {
    }

    public ConfiguracionEntrenamiento guardarConfiguracion(ConfiguracionEntrenamiento configuracion) {
        return iConfigTrainingRepository.save(configuracion);
    }
}
