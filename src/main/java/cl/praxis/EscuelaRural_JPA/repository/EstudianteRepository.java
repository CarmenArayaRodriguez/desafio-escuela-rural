package cl.praxis.EscuelaRural_JPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.praxis.EscuelaRural_JPA.model.Estudiante;
import java.util.List;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    List<Estudiante> findByNombreContaining(String nombre);


    List<Estudiante> findByApellidoContaining(String apellido);


    List<Estudiante> findByCursoId(Long cursoId);
}
