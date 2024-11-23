package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.JpaRepository.MateriaRepository;
import ar.edu.utn.frc.tup.lciii.clients.MateriasClientRest;
import ar.edu.utn.frc.tup.lciii.clients.dtos.AlumnosClientDTO;
import ar.edu.utn.frc.tup.lciii.clients.dtos.DocentesClientDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.*;
import ar.edu.utn.frc.tup.lciii.entities.MateriaEntity;
import ar.edu.utn.frc.tup.lciii.services.interfaces.IMateriasService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MateriasService implements IMateriasService {

    private final MateriasClientRest materiasClientRest;
    private final MateriaRepository materiaRepository;

    //1) Registrar alumnos en materias
    @Override
    public List<RegisterAlumnoDTO> registrarMaterias() {
        List<AlumnosClientDTO> alumnos = materiasClientRest.getAlumnos();
        List<DocentesClientDTO> docentes = materiasClientRest.getDocentes();

        return alumnos.stream().map(alumno -> {
            List<RegisterMateriaDTO> materias = docentes.stream()
                    .map(docente -> {
                        MateriaEntity materiaEntity = MateriaEntity.builder()
                                .legajo(alumno.getLegajo())
                                .materia(docente.getMateria())
                                .estado("Pendiente")
                                .calificacion(0)
                                .build();
                        materiaRepository.save(materiaEntity);

                        return RegisterMateriaDTO.builder()
                                .docente(docente.getNombre())
                                .materia(docente.getMateria())
                                .calificacion(0)
                                .estado("Pendiente")
                                .build();
                    }).collect(Collectors.toList());

            return RegisterAlumnoDTO.builder()
                    .legajo(alumno.getLegajo())
                    .nombre(alumno.getNombre())
                    .materias(materias)
                    .build();
        }).collect(Collectors.toList());
    }

    //2) Registrar nota del alumno
    @Override
    public NotasEstadoDTO registrarNota(NotasRegistroDTO notaRegistroDTO) {
        MateriaEntity materiaEntity = materiaRepository.findByLegajoAndMateria(
                notaRegistroDTO.getLegajo(), notaRegistroDTO.getMateria());

        if (materiaEntity == null) {
            throw new IllegalArgumentException("La materia no existe para el alumno especificado.");
        }

        String estado;
        if (notaRegistroDTO.getCalificacion() < 4) {
            estado = "Libre";
        } else if (notaRegistroDTO.getCalificacion() < 9) {
            estado = "Regular";
        } else {
            estado = "Promocional";
        }

        materiaEntity.setCalificacion(notaRegistroDTO.getCalificacion());
        materiaEntity.setEstado(estado);

        materiaRepository.save(materiaEntity);

        return NotasEstadoDTO.builder()
                .legajo(notaRegistroDTO.getLegajo())
                .materia(notaRegistroDTO.getMateria())
                .estado(estado)
                .build();
    }

    //3) Listar informe académico
    @Override
    public List<InformeAcademicoDTO> listarInformeAcademico(String materiaFiltro) {
        List<MateriaEntity> materias = (materiaFiltro == null)
                ? materiaRepository.findAll()
                : materiaRepository.findByMateriaIgnoreCase(materiaFiltro);

        Map<String, Map<String, Long>> estadoPorMateria = materias.stream()
                .collect(Collectors.groupingBy(
                        MateriaEntity::getMateria,
                        Collectors.groupingBy(MateriaEntity::getEstado, Collectors.counting())
                ));

        return estadoPorMateria.entrySet().stream().map(entry -> {
            String materia = entry.getKey();
            Map<String, Long> estados = entry.getValue();

            long totalAlumnos = estados.values().stream().mapToLong(Long::longValue).sum();

            double porcentajeLibre = (double) estados.getOrDefault("Libre", 0L) / totalAlumnos * 100;
            double porcentajeRegular = (double) estados.getOrDefault("Regular", 0L) / totalAlumnos * 100;
            double porcentajePromocional = (double) estados.getOrDefault("Promocional", 0L) / totalAlumnos * 100;

            String resultado = (porcentajeRegular + porcentajePromocional > 60) ? "EXITOSA" : "FRACASO";

            return InformeAcademicoDTO.builder()
                    .materia(materia)
                    .estado(EstadoDTO.builder()
                            .Libre(String.format("%.2f%%", porcentajeLibre))
                            .Regular(String.format("%.2f%%", porcentajeRegular))
                            .Promocional(String.format("%.2f%%", porcentajePromocional))
                            .build())
                    .resultado(resultado)
                    .build();
        }).collect(Collectors.toList());
    }
}
