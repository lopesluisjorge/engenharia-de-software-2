package br.edu.ifma.livraria.servico;

import java.time.LocalDate;

import br.edu.ifma.livraria.exception.EmprestimoNaoRealizadoException;
import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Usuario;

public class EmprestimoService {

    public Emprestimo emprestaLivro(Usuario usuario, Livro livro) {
        if (livro.isReservado()) {
            throw new EmprestimoNaoRealizadoException("Livro Reservado");
        }
        Emprestimo emprestimo = new Emprestimo(usuario, livro, LocalDate.now());
        livro.setEmprestado(true);
        livro.adicionaEmprestimo(emprestimo);
        return emprestimo;
    }

}