package br.edu.ifma.livraria.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public final class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(unique = true)
    private String matricula;
    private String email;
    @OneToMany(mappedBy = "usuario")
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    protected Usuario() {

    }

    @Deprecated
    public Usuario(String nome, String matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }
    
    public Usuario(String nome, String matricula, String email) {
        this(nome, matricula);
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }
    
    public String getEmail() {
        return email;
    }

    public List<Emprestimo> getEmprestimos() {
        return Collections.unmodifiableList(emprestimos);
    }

    public void adicionaEmprestimo(Emprestimo emprestimo) {
        emprestimos.add(emprestimo);
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
        Usuario other = (Usuario) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}