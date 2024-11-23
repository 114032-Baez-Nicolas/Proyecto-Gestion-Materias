package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.common.InformeAcademicoDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.NotasEstadoDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.NotasRegistroDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.RegisterAlumnoDTO;
import ar.edu.utn.frc.tup.lciii.services.interfaces.IMateriasService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/gestion")
@RestController
@AllArgsConstructor
public class MateriasController {

    private final IMateriasService materiasService;

    //1) Registrar alumnos en materias
    @PostMapping
    public ResponseEntity<List<RegisterAlumnoDTO>> registrarMaterias() {
        List<RegisterAlumnoDTO> response = materiasService.registrarMaterias();
        return ResponseEntity.ok(response);
    }

    //2) Registrar nota del alumno
    @PutMapping
    public ResponseEntity<NotasEstadoDTO> registrarNota(@RequestBody NotasRegistroDTO notaRegistroDTO) {
        NotasEstadoDTO response = materiasService.registrarNota(notaRegistroDTO);
        return ResponseEntity.ok(response);
    }

    //3) Listar informe académico
    @GetMapping
    public ResponseEntity<List<InformeAcademicoDTO>> listarInformeAcademico(
            @RequestParam(required = false) String materia) {
        List<InformeAcademicoDTO> response = materiasService.listarInformeAcademico(materia);
        return ResponseEntity.ok(response);
    }
}
