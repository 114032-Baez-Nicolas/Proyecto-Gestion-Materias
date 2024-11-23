package ar.edu.utn.frc.tup.lciii.clients.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlumnosClientDTO {

    @JsonProperty("legajo")
    private String legajo;

    @JsonProperty("nombre")
    private String nombre;

}
