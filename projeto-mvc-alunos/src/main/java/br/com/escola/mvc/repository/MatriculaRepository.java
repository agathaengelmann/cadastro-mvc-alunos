package br.com.escola.mvc.repository;

import br.com.escola.mvc.model.Aluno;
import br.com.escola.mvc.model.Curso;
import br.com.escola.mvc.model.Matricula;
import br.com.escola.mvc.model.StatusMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    // Verifica se já existe uma matrícula com aquele aluno, curso e status específico
    boolean existsByAlunoAndCursoAndStatus(Aluno aluno, Curso curso, StatusMatricula status);
}