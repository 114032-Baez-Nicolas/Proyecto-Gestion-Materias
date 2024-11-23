package ar.edu.utn.frc.tup.lciii.clients;

import ar.edu.utn.frc.tup.lciii.clients.dtos.AlumnosClientDTO;
import ar.edu.utn.frc.tup.lciii.clients.dtos.DocentesClientDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class MateriasClientRest {

    private final RestTemplate restTemplate;
    private final String url = "http://javaapi:8080";

    @CircuitBreaker(name = "alumnosService", fallbackMethod = "fallbackGetAlumnos")
    public List<AlumnosClientDTO> getAlumnos() {
        String endpoint = url + "/alumnos";
        AlumnosClientDTO[] response = restTemplate.getForObject(endpoint, AlumnosClientDTO[].class);
        return Arrays.asList(response);
    }

    @CircuitBreaker(name = "docentesService", fallbackMethod = "fallbackGetDocentes")
    public List<DocentesClientDTO> getDocentes() {
        String endpoint = url + "/docentes";
        DocentesClientDTO[] response = restTemplate.getForObject(endpoint, DocentesClientDTO[].class);
        return Arrays.asList(response);
    }

    // Métodos de fallback para manejar fallos
    private List<AlumnosClientDTO> fallbackGetAlumnos(Throwable throwable) {
        log.error("Error al obtener alumnos: {}", throwable.getMessage());
        return Collections.emptyList();
    }

    private List<DocentesClientDTO> fallbackGetDocentes(Throwable throwable) {
        log.error("Error al obtener docentes: {}", throwable.getMessage());
        return Collections.emptyList();
    }
}
