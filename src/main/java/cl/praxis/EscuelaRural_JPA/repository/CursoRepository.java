package cl.praxis.EscuelaRural_JPA.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import cl.praxis.EscuelaRural_JPA.model.Curso;
import java.util.List;

public interface CursoRepository extends JpaRepository<Curso, Long> {
    List<Curso> findByNivel(int nivel);
    List<Curso> findByLetra(String letra);
}
