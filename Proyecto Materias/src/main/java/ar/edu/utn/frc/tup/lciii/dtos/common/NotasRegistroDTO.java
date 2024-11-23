package ar.edu.utn.frc.tup.lciii.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotasRegistroDTO {

    @JsonProperty("legajo")
    private String legajo;

    @JsonProperty("materia")
    private String materia;

    @JsonProperty("calificacion")
    private int calificacion;

}
