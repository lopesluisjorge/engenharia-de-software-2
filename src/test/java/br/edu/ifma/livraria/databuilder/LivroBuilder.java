package br.edu.ifma.livraria.databuilder;

import br.edu.ifma.livraria.modelo.Livro;

public class LivroBuilder {

    private Livro livro;

    private LivroBuilder() {
        livro = new Livro("Livro", "Autor");
    }

    public static LivroBuilder umLivro() {
        return new LivroBuilder();
    }

    public LivroBuilder comTitulo(String titulo) {
        livro = new Livro(titulo, livro.getAutor());
        return this;
    }

    public LivroBuilder doAutor(String autor) {
        livro = new Livro(livro.getTitulo(), autor);
        return this;
    }

    public Livro constroi() {
        return livro;
    }

}
