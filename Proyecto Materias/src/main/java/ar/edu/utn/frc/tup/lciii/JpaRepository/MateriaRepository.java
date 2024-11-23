package ar.edu.utn.frc.tup.lciii.JpaRepository;

import ar.edu.utn.frc.tup.lciii.entities.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MateriaRepository extends JpaRepository<MateriaEntity, Long> {
    MateriaEntity findByLegajoAndMateria(String legajo, String materia);

    @Query("SELECT m FROM MateriaEntity m WHERE LOWER(m.materia) = LOWER(:materia)")
    List<MateriaEntity> findByMateriaIgnoreCase(@Param("materia") String materia);
}
