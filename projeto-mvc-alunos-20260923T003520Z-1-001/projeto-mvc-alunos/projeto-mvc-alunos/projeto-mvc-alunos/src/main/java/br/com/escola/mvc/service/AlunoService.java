package br.com.escola.mvc.service;

import br.com.escola.mvc.model.Aluno;
import br.com.escola.mvc.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public List<Aluno> listarTodos() {
        return repository.findAll();
    }

    // Apenas UMA versão de buscarPorId
    public Aluno buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));
    }

    public Aluno salvar(Aluno aluno) {
        validar(aluno);
        return repository.save(aluno);
    }

    // Ajustado para usar 'repository' (e String caso seu model use String)
    public Aluno buscarPorRg(String rg) {
        return repository.findByRg(rg)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com o RG: " + rg));
    }

    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Aluno não encontrado: " + id);
        }
        repository.deleteById(id);
    }

    private void validar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }

        if (aluno.getEmail() == null || aluno.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }

        if (aluno.getCurso() == null || aluno.getCurso().isBlank()) {
            throw new IllegalArgumentException("O curso é obrigatório.");
        }
    }
}