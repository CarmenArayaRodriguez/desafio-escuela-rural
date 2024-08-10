package cl.praxis.EscuelaRural_JPA.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cl.praxis.EscuelaRural_JPA.model.Estudiante;
import cl.praxis.EscuelaRural_JPA.repository.EstudianteRepository;

import java.util.List;

@Service
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteService(EstudianteRepository estudianteRepository) {
        this.estudianteRepository = estudianteRepository;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> listarTodosLosEstudiantes() {
        return estudianteRepository.findAll();
    }

    @Transactional
    public void guardarEstudiante(Estudiante estudiante) {
        estudianteRepository.save(estudiante);
    }

    @Transactional
    public void actualizarEstudiante(Long id, Estudiante estudiante) {
        Estudiante estudianteExistente = estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + id));
        estudianteExistente.setNombre(estudiante.getNombre());
        estudianteExistente.setApellido(estudiante.getApellido());
        estudianteRepository.save(estudianteExistente);
    }

    @Transactional
    public void eliminarEstudiante(Long id) {
        estudianteRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Estudiante obtenerEstudiantePorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado con ID: " + id));
    }
}
