package br.com.escola.mvc.service;

import br.com.escola.mvc.model.Matricula;
import br.com.escola.mvc.model.StatusMatricula;
import br.com.escola.mvc.repository.MatriculaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository repository;

    public Matricula salvar(Matricula matricula) {
        // Regra: Um aluno não poderá ter duas matrículas ativas no mesmo curso
        if (matricula.getId() == null &&
                repository.existsByAlunoAndCursoAndStatus(matricula.getAluno(), matricula.getCurso(), StatusMatricula.ATIVA)) {
            throw new IllegalArgumentException("Este aluno já possui uma matrícula ATIVA neste curso.");
        }
        return repository.save(matricula);
    }

    public List<Matricula> listarTodas() {
        return repository.findAll();
    }

    public Matricula buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada"));
    }

    public void alterarStatus(Long id, StatusMatricula novoStatus) {
        Matricula matricula = buscarPorId(id);
        matricula.setStatus(novoStatus);
        repository.save(matricula);
    }
}