package cl.praxis.EscuelaRural_JPA.controller;

import cl.praxis.EscuelaRural_JPA.service.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import cl.praxis.EscuelaRural_JPA.model.Estudiante;
import cl.praxis.EscuelaRural_JPA.service.EstudianteService;
import cl.praxis.EscuelaRural_JPA.model.Curso;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.util.List;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;
    private final CursoService cursoService;

    public EstudianteController(EstudianteService estudianteService, CursoService cursoService) {
        this.estudianteService = estudianteService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listarEstudiantes(Model modelo) {
        modelo.addAttribute("estudiantes", estudianteService.listarTodosLosEstudiantes());
        return "detalles-curso";
    }

    @GetMapping("/crear")
    public String mostrarFormularioDeNuevoEstudiante(Model modelo) {
        modelo.addAttribute("estudiante", new Estudiante());
        modelo.addAttribute("cursos", cursoService.listarTodosLosCursos());
        return "crear-estudiante";
    }

    @PostMapping("/guardar")
    public String guardarEstudiante(@ModelAttribute("estudiante") Estudiante estudiante, @RequestParam("cursoId") Long cursoId) {
        Curso curso = cursoService.obtenerCursoPorId(cursoId);
        if (curso != null) {
            estudiante.setCurso(curso);
            estudianteService.guardarEstudiante(estudiante);
            return "redirect:/cursos/detalles/" + curso.getId();
        } else {
            // Manejar el caso donde el curso no existe o no es válido
            return "redirect:/estudiantes/crear"; // Redirige de nuevo al formulario si hay un error
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditarEstudiante(@PathVariable Long id, Model modelo) {
        Estudiante estudiante = estudianteService.obtenerEstudiantePorId(id);
        List<Curso> cursos = cursoService.listarTodosLosCursos();
        if (estudiante == null) {
            return "redirect:/detalles-curso";
        }
        modelo.addAttribute("estudiante", estudiante);
        modelo.addAttribute("cursos", cursos);
        return "editar-estudiante";
    }

@PostMapping("/editar/{id}")
public String actualizarEstudiante(@PathVariable Long id, @RequestParam("cursoId") Long cursoId, @ModelAttribute("estudiante") Estudiante estudiante, RedirectAttributes redirectAttributes) {
    try {
        Estudiante estudianteExistente = estudianteService.obtenerEstudiantePorId(id);
        if (estudianteExistente != null) {
            Curso curso = cursoService.obtenerCursoPorId(cursoId);  // Obtener el curso por el ID
            estudianteExistente.setNombre(estudiante.getNombre());
            estudianteExistente.setApellido(estudiante.getApellido());
            estudianteExistente.setCurso(curso);  // Actualizar el curso del estudiante
            estudianteService.guardarEstudiante(estudianteExistente);
            redirectAttributes.addFlashAttribute("mensaje", "Estudiante actualizado correctamente");
        } else {
            redirectAttributes.addFlashAttribute("mensaje", "Estudiante no encontrado");
        }
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el estudiante");
    }
    return "redirect:/estudiantes/confirmacion-edicion";
}

    // Método para mostrar la página de confirmación
    @GetMapping("/confirmacion-edicion")
    public String mostrarConfirmacion() {
        return "confirmacion-edicion";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEstudiante(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        Estudiante estudiante = null;
        try {
            estudiante = estudianteService.obtenerEstudiantePorId(id);
            if (estudiante != null) {
                estudianteService.eliminarEstudiante(id);
                redirectAttrs.addFlashAttribute("mensaje", "Estudiante eliminado correctamente.");
            } else {
                redirectAttrs.addFlashAttribute("mensaje", "Estudiante no encontrado.");
            }
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("mensaje", "Error al eliminar el estudiante.");
        }

        // Verifica que estudiante no sea null antes de intentar acceder a su curso
        if (estudiante != null && estudiante.getCurso() != null) {
            return "redirect:/cursos/detalles/" + estudiante.getCurso().getId();
        } else {
            return "redirect:/cursos";
        }
    }


}
