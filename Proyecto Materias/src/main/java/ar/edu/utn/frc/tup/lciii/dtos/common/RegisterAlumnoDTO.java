package ar.edu.utn.frc.tup.lciii.dtos.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterAlumnoDTO {

    @JsonProperty("legajo")
    private String legajo;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("materia")
    private List<RegisterMateriaDTO> materias;

}
