package ar.edu.utn.frc.tup.lciii.services.interfaces;

import ar.edu.utn.frc.tup.lciii.dtos.common.InformeAcademicoDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.NotasEstadoDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.NotasRegistroDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RegisterAlumnoDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IMateriasService {

    //1) Registrar alumnos en materias
    List<RegisterAlumnoDTO> registrarMaterias();

    //2) Registrar nota del alumno
    NotasEstadoDTO registrarNota(NotasRegistroDTO notaRegistroDTO);

    //3) Listar informe académico
    List<InformeAcademicoDTO> listarInformeAcademico(String materia);
}
