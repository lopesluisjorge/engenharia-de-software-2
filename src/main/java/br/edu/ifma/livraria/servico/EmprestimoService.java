package br.edu.ifma.livraria.servico;

import java.time.LocalDate;
import java.util.Objects;

import br.edu.ifma.livraria.exception.EmprestimoNaoRealizadoException;
import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Usuario;

public class EmprestimoService {

    public Emprestimo emprestaLivro(Usuario usuario, Livro livro) {
        if (livro.isEmprestado()) {
            throw new EmprestimoNaoRealizadoException("Livro já está emprestado.");
        }
        if (livro.isReservado()) {
            throw new EmprestimoNaoRealizadoException("Livro Reservado");
        }
        Long quantidadeDeEmprestimosNaoDevolvolvidos =
                usuario.getEmprestimos().stream()
                        .filter((emprestimo) -> Objects.isNull(emprestimo.getDataDevolucao()))
                        .count();
        if (quantidadeDeEmprestimosNaoDevolvolvidos == 2L) {
            throw new EmprestimoNaoRealizadoException("Máximo de 2 empréstimos simultaneos.");
        }

        Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDate.now());
        livro.adicionaEmprestimo(emprestimo);
        usuario.adicionaEmprestimo(emprestimo);

        return emprestimo;
    }

}