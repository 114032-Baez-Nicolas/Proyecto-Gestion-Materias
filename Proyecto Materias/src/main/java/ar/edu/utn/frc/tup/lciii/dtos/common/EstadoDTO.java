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
public class EstadoDTO {

    @JsonProperty("Libre")
    private String Libre;

    @JsonProperty("Regular")
    private String Regular;

    @JsonProperty("Promocional")
    private String Promocional;

}
