package ar.edu.utn.frc.tup.lciii.services.impl;

import ar.edu.utn.frc.tup.lciii.JpaRepository.MateriaRepository;
import ar.edu.utn.frc.tup.lciii.clients.MateriasClientRest;
import ar.edu.utn.frc.tup.lciii.clients.dtos.AlumnosClientDTO;
import ar.edu.utn.frc.tup.lciii.clients.dtos.DocentesClientDTO;
import ar.edu.utn.frc.tup.lciii.dtos.common.*;
import ar.edu.utn.frc.tup.lciii.entities.MateriaEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MateriasServiceTest {

    @Mock
    private MateriasClientRest materiasClientRest;

    @Mock
    private MateriaRepository materiaRepository;

    @InjectMocks
    private MateriasService materiasService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //1) Tests de registrar Materias
    @Test
    void testRegistrarMaterias() {
        AlumnosClientDTO alumno1 = new AlumnosClientDTO("123", "Juan Perez");
        AlumnosClientDTO alumno2 = new AlumnosClientDTO("456", "Maria Gomez");
        List<AlumnosClientDTO> alumnos = Arrays.asList(alumno1, alumno2);

        DocentesClientDTO docente1 = new DocentesClientDTO(1L, "Prof. Lopez", "Matematica");
        DocentesClientDTO docente2 = new DocentesClientDTO(2L, "Prof. Garcia", "Historia");
        List<DocentesClientDTO> docentes = Arrays.asList(docente1, docente2);

        when(materiasClientRest.getAlumnos()).thenReturn(alumnos);
        when(materiasClientRest.getDocentes()).thenReturn(docentes);

        List<RegisterAlumnoDTO> result = materiasService.registrarMaterias();

        assertEquals(2, result.size());
        assertEquals(2, result.get(0).getMaterias().size());
        assertEquals(2, result.get(1).getMaterias().size());

        verify(materiaRepository, times(4)).save(any(MateriaEntity.class));
    }

    //2) Tests de registrar Nota
    @Test
    void testRegistrarNota() {
        NotasRegistroDTO notaRegistroDTO = new NotasRegistroDTO("123", "Matematica", 8);
        MateriaEntity materiaEntity = MateriaEntity.builder()
                .legajo("123")
                .materia("Matematica")
                .estado("Pendiente")
                .calificacion(0)
                .build();

        when(materiaRepository.findByLegajoAndMateria("123", "Matematica")).thenReturn(materiaEntity);

        NotasEstadoDTO result = materiasService.registrarNota(notaRegistroDTO);

        assertEquals("123", result.getLegajo());
        assertEquals("Matematica", result.getMateria());
        assertEquals("Regular", result.getEstado());

        verify(materiaRepository).save(materiaEntity);
    }

    //3) Tests de validar registrar Nota
    @Test
    void testRegistrarNotaMateriaNoExiste() {
        NotasRegistroDTO notaRegistroDTO = new NotasRegistroDTO("123", "Matematica", 8);

        when(materiaRepository.findByLegajoAndMateria("123", "Matematica")).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            materiasService.registrarNota(notaRegistroDTO);
        });
    }

    //4) Tests de listar Informe Academico
    @Test
    void testListarInformeAcademico() {
        MateriaEntity materia1 = MateriaEntity.builder()
                .legajo("123")
                .materia("Matematica")
                .estado("Regular")
                .calificacion(6)
                .build();
        MateriaEntity materia2 = MateriaEntity.builder()
                .legajo("456")
                .materia("Matematica")
                .estado("Promocional")
                .calificacion(10)
                .build();
        MateriaEntity materia3 = MateriaEntity.builder()
                .legajo("789")
                .materia("Matematica")
                .estado("Libre")
                .calificacion(2)
                .build();

        List<MateriaEntity> materias = Arrays.asList(materia1, materia2, materia3);

        when(materiaRepository.findAll()).thenReturn(materias);

        List<InformeAcademicoDTO> result = materiasService.listarInformeAcademico(null);

        assertEquals(1, result.size());
        InformeAcademicoDTO informe = result.get(0);
        assertEquals("Matematica", informe.getMateria());
        EstadoDTO estado = informe.getEstado();
        assertEquals("33.33%", estado.getLibre());
        assertEquals("33.33%", estado.getRegular());
        assertEquals("33.33%", estado.getPromocional());
        assertEquals("EXITOSA", informe.getResultado());

        verify(materiaRepository).findAll();
    }
}