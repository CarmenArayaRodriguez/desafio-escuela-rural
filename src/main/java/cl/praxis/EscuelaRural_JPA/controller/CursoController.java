package cl.praxis.EscuelaRural_JPA.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import cl.praxis.EscuelaRural_JPA.model.Curso;
import cl.praxis.EscuelaRural_JPA.service.CursoService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService cursoService;

    // Constructor para inyectar el servicio de cursos
    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // Método para listar todos los cursos
    @GetMapping
    public String listarCursos(Model modelo) {
        modelo.addAttribute("cursos", cursoService.listarTodosLosCursos());
        return "cursos";  // Asegúrate de que la vista 'index' exista bajo 'src/main/resources/templates/cursos/index.html'
    }

    // Método para mostrar el formulario de creación de un nuevo curso
    @GetMapping("/crear")
    public String mostrarFormularioDeNuevoCurso(Model modelo) {
        modelo.addAttribute("curso", new Curso());
        return "crear-curso";  // Asegúrate de que la vista 'crear' exista bajo 'src/main/resources/templates/cursos/crear.html'
    }

    // Método para guardar un curso nuevo
    @PostMapping
    public String guardarCurso(@ModelAttribute Curso curso) {
        cursoService.guardarCurso(curso);
        return "redirect:/cursos";
    }

    // Método para ver los detalles de un curso específico
    @GetMapping("/detalles/{id}")
    public String verDetallesDelCurso(@PathVariable Long id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        if (curso != null) {
            // Agrega el curso al modelo
            model.addAttribute("curso", curso);
            // Log para depuración
            System.out.println("Curso cargado con éxito: " + curso);
            System.out.println("Nivel del curso: " + curso.getNivel());
            // Retorna la vista con los detalles del curso
            return "detalles-curso";
        } else {
            // Si el curso es null, redirige a la lista de cursos
            System.out.println("Curso no encontrado para el ID: " + id);
            return "redirect:/cursos";
        }
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioDeEditarCurso(@PathVariable Long id, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        System.out.println(curso);
        if (curso == null) {
            return "redirect:/cursos";
        }
        model.addAttribute("curso", curso);
        return "editar-curso";
    }

    @PostMapping("/editar/{id}")
    public String actualizarCurso(@PathVariable Long id, @ModelAttribute("curso") Curso curso, RedirectAttributes redirectAttributes) {
        try {
            cursoService.actualizarCurso(id, curso);
            redirectAttributes.addFlashAttribute("mensaje", "Curso actualizado correctamente");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensaje", "Error al actualizar el curso");
        }
        return "redirect:/cursos";
    }
    // Método para eliminar un curso
    @PostMapping("/eliminar/{id}")
    public String eliminarCurso(@PathVariable Long id, RedirectAttributes redirectAttrs, Model model) {
        Curso curso = cursoService.obtenerCursoPorId(id);
        if (curso == null) {
            redirectAttrs.addFlashAttribute("error", "Curso no encontrado.");
            return "redirect:/cursos";
        }

        if (!curso.getEstudiantes().isEmpty()) {
            model.addAttribute("mensaje", "No se puede eliminar el curso porque tiene estudiantes inscritos.");
            return "error-eliminacion";
        }

        try {
            cursoService.eliminarCurso(id);
            redirectAttrs.addFlashAttribute("success", "Curso eliminado con éxito.");
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("error", "Error al eliminar el curso.");
        }

        return "redirect:/cursos";
    }

}
