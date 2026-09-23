package br.com.escola.mvc.service;

import br.com.escola.mvc.model.Matricula;
import br.com.escola.mvc.model.StatusMatricula;
import br.com.escola.mvc.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MatriculaService {

    private final MatriculaRepository repository;

    public MatriculaService(MatriculaRepository repository) {
        this.repository = repository;
    }

    public Matricula salvar(Matricula matricula) {
        if (matricula.getAluno() == null) {
            throw new IllegalArgumentException("Selecione um aluno.");
        }
        if (matricula.getCurso() == null) {
            throw new IllegalArgumentException("Selecione um curso.");
        }

        if (matricula.getId() == null && matricula.getDataMatricula() == null) {
            matricula.setDataMatricula(LocalDate.now());
        }

        if (matricula.getId() == null) {
            matricula.setStatus(StatusMatricula.ATIVA);
        }

        if (repository.existsByAlunoAndCursoAndStatus(
                matricula.getAluno(), matricula.getCurso(), StatusMatricula.ATIVA)) {
            throw new IllegalArgumentException("Este aluno já possui uma matrícula ATIVA neste curso.");
        }

        return repository.save(matricula);
    }

    public List<Matricula> listarTodas() {
        return repository.findAll();
    }

    public Matricula buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada: " + id));
    }

    public void alterarStatus(Long id, StatusMatricula novoStatus) {
        Matricula matricula = buscarPorId(id);

        if (novoStatus == StatusMatricula.ATIVA
                && matricula.getStatus() != StatusMatricula.ATIVA
                && repository.existsByAlunoAndCursoAndStatus(
                        matricula.getAluno(), matricula.getCurso(), StatusMatricula.ATIVA)) {
            throw new IllegalArgumentException("Este aluno já possui uma matrícula ATIVA neste curso.");
        }

        matricula.setStatus(novoStatus);
        repository.save(matricula);
    }
}