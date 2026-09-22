package br.com.escola.mvc.controller;

import br.com.escola.mvc.model.Matricula;
import br.com.escola.mvc.model.StatusMatricula;
import br.com.escola.mvc.repository.AlunoRepository;
import br.com.escola.mvc.repository.CursoRepository;
import br.com.escola.mvc.service.MatriculaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("matriculas", matriculaService.listarTodas());
        model.addAttribute("statusMatricula", StatusMatricula.values());
        return "matriculas/lista";
    }

    @GetMapping("/nova")
    public String formulario(Model model) {
        model.addAttribute("matricula", new Matricula());
        model.addAttribute("alunos", alunoRepository.findAll());
        model.addAttribute("cursos", cursoRepository.findAll());
        return "matriculas/form";
    }

    @PostMapping
    public String salvar(@ModelAttribute Matricula matricula, Model model) {
        try {
            matriculaService.salvar(matricula);
            return "redirect:/matriculas";
        } catch (IllegalArgumentException e) {
            // Se cair na regra de negócio de matrícula duplicada, exibe o erro no form
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("alunos", alunoRepository.findAll());
            model.addAttribute("cursos", cursoRepository.findAll());
            return "matriculas/form";
        }
    }

    @PostMapping("/{id}/status")
    public String alterarStatus(@PathVariable Long id, @RequestParam StatusMatricula status) {
        matriculaService.alterarStatus(id, status);
        return "redirect:/matriculas";
    }
}