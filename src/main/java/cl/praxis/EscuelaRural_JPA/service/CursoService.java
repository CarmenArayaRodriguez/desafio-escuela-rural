package cl.praxis.EscuelaRural_JPA.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.praxis.EscuelaRural_JPA.model.Curso;
import cl.praxis.EscuelaRural_JPA.repository.CursoRepository;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository cursoRepository;

    // Constructor que inyecta el repositorio
    public CursoService(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    // Método para listar todos los cursos, marcado como solo lectura
    @Transactional(readOnly = true)
    public List<Curso> listarTodosLosCursos() {
        return cursoRepository.findAll();
    }

    // Método para guardar un nuevo curso
    @Transactional
    public void guardarCurso(Curso curso) {
        cursoRepository.save(curso);
    }

    // Método para actualizar un curso existente
    @Transactional
    public void actualizarCurso(Long id, Curso cursoDatos) {
        Curso cursoExistente = cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + id));
        cursoExistente.setNivel(cursoDatos.getNivel());
        cursoExistente.setLetra(cursoDatos.getLetra());
        cursoRepository.save(cursoExistente);
    }

    // Método para eliminar un curso por ID
    @Transactional
    public void eliminarCurso(Long id) {
        cursoRepository.deleteById(id);
    }

    // Método para obtener un curso por ID
    @Transactional(readOnly = true)
    public Curso obtenerCursoPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + id));
    }
}
