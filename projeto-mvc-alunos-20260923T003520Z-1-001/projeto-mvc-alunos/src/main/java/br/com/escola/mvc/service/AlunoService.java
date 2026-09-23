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


    public Aluno buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));
    }

    public Aluno salvar(Aluno aluno) {
        if (aluno.getRg() != null) {
            aluno.setRg(aluno.getRg().trim());
        }
        validar(aluno);
        return repository.save(aluno);
    }


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

        if (aluno.getRg() == null || aluno.getRg().isBlank()) {
            throw new IllegalArgumentException("O RG é obrigatório.");
        }

        if (aluno.getEmail() == null || aluno.getEmail().isBlank()) {
            throw new IllegalArgumentException("O e-mail é obrigatório.");
        }

        if (aluno.getCurso() == null || aluno.getCurso().isBlank()) {
            throw new IllegalArgumentException("O curso é obrigatório.");
        }

        validarRgNaoDuplicado(aluno);
    }

    private void validarRgNaoDuplicado(Aluno aluno) {
        repository.findByRg(aluno.getRg())
                .filter(existente -> aluno.getId() == null
                        || !aluno.getId().equals(existente.getId()))
                .ifPresent(existente -> {
                    throw new IllegalArgumentException(
                            "Já existe um aluno cadastrado com o RG: " + aluno.getRg());
                });
    }
}