package br.com.escola.mvc;

import br.com.escola.mvc.model.Aluno;
import br.com.escola.mvc.repository.AlunoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjetoMvcAlunosApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetoMvcAlunosApplication.class, args);
    }

    @Bean
    CommandLineRunner carregarDadosIniciais(AlunoRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(new Aluno(null,  "365985365","Ana Souza","ana@email.com", "Informática para Internet"));
                repository.save(new Aluno(null,"560431983","Carlos Lima","carlos@email.com", "Informática para Internet"));
                repository.save(new Aluno(null,  "298562356","Mariana Silva", "mariana@email.com", "Desenvolvimento de Sistemas"));
            }
        };
    }
}