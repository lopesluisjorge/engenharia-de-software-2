package br.edu.ifma.livraria.databuilder;

import java.time.LocalDate;

import br.edu.ifma.livraria.modelo.Emprestimo;
import br.edu.ifma.livraria.modelo.Livro;
import br.edu.ifma.livraria.modelo.Usuario;

public class EmprestimoBuilder {

    private Emprestimo emprestimo;
    private Livro livro;

    private EmprestimoBuilder(Livro livro) {
        this.livro = livro;
        emprestimo = new Emprestimo(UsuarioBuilder.umUsuario().constroi(), livro, LocalDate.now());
    }

    public static EmprestimoBuilder umEmprestimoDolivro(Livro livro) {
        return new EmprestimoBuilder(livro);
    }

    public EmprestimoBuilder paraUsuario(Usuario usuario) {
        emprestimo = new Emprestimo(usuario, livro, LocalDate.now());
        return this;
    }

    public EmprestimoBuilder aDiasAtras(int quantidadeDeDias) {
        if (quantidadeDeDias <= 0) {
            throw new IllegalArgumentException("Quantidade de dias inválida");
        }
        emprestimo = new Emprestimo(emprestimo.getUsuario(), livro, LocalDate.now().minusDays(quantidadeDeDias));
        return this;
    }

    public Emprestimo constroi() {
        livro.adicionaEmprestimo(emprestimo);
        return emprestimo;
    }

}
