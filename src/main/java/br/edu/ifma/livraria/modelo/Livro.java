package br.edu.ifma.livraria.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Livro {

    private String autor;
    private String titulo;
    private boolean isEmprestado;
    private boolean isReservado;
    private final List<Emprestimo> historico = new ArrayList<>();

    public Livro(String titulo, String autor) {
        this.setTitulo(titulo);
        this.setAutor(autor);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isEmprestado() {
        return isEmprestado;
    }

    public void setEmprestado(boolean isEmprestado) {
        this.isEmprestado = isEmprestado;
    }

    public boolean isReservado() {
        return isReservado;
    }

    public void setReservado(boolean isReservado) {
        this.isReservado = isReservado;
    }

    public void adicionaEmprestimo(Emprestimo emprestimo) {
        historico.add(emprestimo);
    }

    public List<Emprestimo> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

}