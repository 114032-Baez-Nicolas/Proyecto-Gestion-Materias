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
public class DocentesClientDTO {

    @JsonProperty("matricula")
    private Long matricula;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("materia")
    private String materia;

}
