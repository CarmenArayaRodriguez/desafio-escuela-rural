package cl.praxis.EscuelaRural_JPA.dto;

import java.util.HashSet;
import java.util.Set;

public class CursoDTO {
    private Long id;
    private int nivel;
    private String letra;

    private Set<EstudianteDTO> estudiantes = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public Set<EstudianteDTO> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(Set<EstudianteDTO> estudiantes) {
        this.estudiantes = estudiantes;
    }
}
