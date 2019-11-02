package br.edu.ifma.livraria.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Usuario {

    private final String nome;
    private final String matricula;
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public List<Emprestimo> getEmprestimos() {
        return Collections.unmodifiableList(emprestimos);
    }

    public void adicionaEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
    }

}