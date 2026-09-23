package br.com.escola.mvc.controller;

import br.com.escola.mvc.model.Matricula;
import br.com.escola.mvc.model.StatusMatricula;
import br.com.escola.mvc.service.AlunoService;
import br.com.escola.mvc.service.CursoService;
import br.com.escola.mvc.service.MatriculaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final AlunoService alunoService;
    private final CursoService cursoService;

    public MatriculaController(MatriculaService matriculaService,
                               AlunoService alunoService,
                               CursoService cursoService) {
        this.matriculaService = matriculaService;
        this.alunoService = alunoService;
        this.cursoService = cursoService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("matriculas", matriculaService.listarTodas());
        model.addAttribute("statusMatricula", StatusMatricula.values());
        return "matriculas/lista";
    }

    @GetMapping("/nova")
    public String formulario(Model model) {
        model.addAttribute("matricula", new Matricula());
        model.addAttribute("alunos", alunoService.listarTodos());
        model.addAttribute("cursos", cursoService.listarTodos());
        return "matriculas/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Matricula matricula, Model model) {
        try {
            matriculaService.salvar(matricula);
            return "redirect:/matriculas";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("matricula", matricula);
            model.addAttribute("alunos", alunoService.listarTodos());
            model.addAttribute("cursos", cursoService.listarTodos());
            return "matriculas/form";
        }
    }

    @PostMapping("/{id}/status")
    public String alterarStatus(@PathVariable Long id, @RequestParam StatusMatricula status, Model model) {
        try {
            matriculaService.alterarStatus(id, status);
        } catch (IllegalArgumentException e) {
            model.addAttribute("erroStatus", e.getMessage());
        }
        model.addAttribute("matriculas", matriculaService.listarTodas());
        model.addAttribute("statusMatricula", StatusMatricula.values());
        return "matriculas/lista";
    }
}