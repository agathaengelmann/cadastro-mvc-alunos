package br.com.escola.mvc.controller;

import br.com.escola.mvc.model.Curso;
import br.com.escola.mvc.service.CursoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("cursos", service.listarTodos());
        return "cursos/lista";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("curso", new Curso());
        return "cursos/formulario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("curso", service.buscarPorId(id));
        return "cursos/formulario";
    }

    @PostMapping("/salvar")
    public String salvar(Curso curso, Model model) {
        try {
            service.salvar(curso);
            return "redirect:/cursos";
        } catch (IllegalArgumentException e) {
            model.addAttribute("curso", curso);
            model.addAttribute("erro", e.getMessage());
            return "cursos/formulario";
        }
    }

    @PostMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.excluir(id);
        return "redirect:/cursos";
    }
}