package pe.edu.idat.Services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.idat.DTO.EjercicioDTO;
import pe.edu.idat.Repositories.IEjercicioRepository;
import pe.edu.idat.Repositories.IEjercicioRutinaRepository;
import pe.edu.idat.Repositories.IRutinaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EjercicioService {

    @Autowired
    private IEjercicioRutinaRepository iEjercicioRutinaRepository;

    public EjercicioService() {
    }

    public List<EjercicioDTO> getEjerciciosPorNivel(String nivel) {
        List<Object[]> ejercicios = iEjercicioRutinaRepository.getEjerciciosPorNivel(nivel);

        return ejercicios.stream()
                .map(obj -> new EjercicioDTO(
                        (String) obj[0],
                        (Integer) obj[1],
                        (String) obj[2]
                ))
                .collect(Collectors.toList());
    }
}
