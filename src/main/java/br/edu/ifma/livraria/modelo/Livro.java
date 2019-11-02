package br.edu.ifma.livraria.modelo;

public class Livro {

    private String autor;
    private String titulo;
    private boolean isEmprestado;
    private boolean isReservado;

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

}