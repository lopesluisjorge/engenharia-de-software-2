package br.edu.ifma.livraria.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Livro {

    private final String autor;
    private final String titulo;
    private boolean isEmprestado = false;
    private boolean isReservado = false;
    private final List<Emprestimo> historico = new ArrayList<>();

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isEmprestado() {
        return isEmprestado;
    }

    public void emprestar() {
        this.isEmprestado = true;
    }

    public void devolver() {
        this.isEmprestado = false;
    }

    public boolean isReservado() {
        return isReservado;
    }

    public void reservar() {
        this.isReservado = true;
    }

    public void adicionaEmprestimo(Emprestimo emprestimo) {
        historico.add(emprestimo);
        emprestar();
    }

    public List<Emprestimo> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

}