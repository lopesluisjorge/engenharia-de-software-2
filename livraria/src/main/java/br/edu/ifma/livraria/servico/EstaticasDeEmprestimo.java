package br.edu.ifma.livraria.servico;

import java.time.LocalDate;
import java.util.List;

import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.repository.EmprestimoRepository;

public class EstaticasDeEmprestimo {

    private final EmprestimoRepository emprestimos;

    public EstaticasDeEmprestimo(EmprestimoRepository emprestimos) {
        this.emprestimos = emprestimos;
    }
    
    public List<Livro> porPeridodo(LocalDate inicio, LocalDate fim) {
        return emprestimos.livrosPorPeriodo(inicio, fim);
    }

}
