package br.edu.ifma.livraria.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public final class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String autor;
    private String titulo;
    private boolean isEmprestado = false;
    private boolean isReservado = false;
    @OneToMany(mappedBy = "livro")
    private final List<Emprestimo> historico = new ArrayList<>();

    protected Livro() {

    }

    public Livro(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public Long getId() {
        return id;
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
        isEmprestado = true;
    }

    public List<Emprestimo> getHistorico() {
        return Collections.unmodifiableList(historico);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Livro other = (Livro) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}