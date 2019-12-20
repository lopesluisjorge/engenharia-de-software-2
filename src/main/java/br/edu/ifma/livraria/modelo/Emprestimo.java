package br.edu.ifma.livraria.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

@Entity
public final class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario usuario;
    @ManyToOne
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private BigDecimal valorTotal;
    @OneToMany(mappedBy = "emprestimo")
    private Set<Pagamento> pagamentos = new LinkedHashSet<>();

    public static final int DIAS_PARA_DEVOLUCAO = 7;
    public static final BigDecimal VALOR_FIXO_PAGAMENTO = new BigDecimal("5.0");
    public static final BigDecimal MULTA_POR_DIA_DE_ATRASO = new BigDecimal("0.40");

    protected Emprestimo() {

    }

    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo) {
        this.setUsuario(usuario);
        this.setLivro(livro);
        this.setDataEmprestimo(dataEmprestimo);
        dataPrevista = dataEmprestimo.plusDays(DIAS_PARA_DEVOLUCAO);
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    private void setUsuario(Usuario usuario) {
        if (Objects.isNull(usuario)) {
            throw new IllegalArgumentException("Usuário Inválido");
        }

        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    private void setLivro(Livro livro) {
        if (Objects.isNull(livro)) {
            throw new IllegalArgumentException("Livro Inválido");
        }

        this.livro = livro;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    private void setDataEmprestimo(LocalDate dataEmprestimo) {
        if (Objects.isNull(dataEmprestimo)) {
            throw new IllegalArgumentException("Data de Empréstimo Inválida");
        }

        this.dataEmprestimo = dataEmprestimo;
    }

    public LocalDate getDataPrevista() {
        return dataPrevista;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(LocalDate dataDevolucao) {
        if (dataEmprestimo.isAfter(dataDevolucao)) {
            throw new IllegalArgumentException("Data de devolução deve ser posterior à data de empréstimo");
        }

        this.dataDevolucao = dataDevolucao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void adicionaPagamento(Pagamento pagamento) {
        pagamentos.add(pagamento);
    }

    public Set<Pagamento> getPagamentos() {
        return Collections.unmodifiableSet(pagamentos);
    }

    @Transient
    public BigDecimal getValorPago() {
        return pagamentos.stream()
                .map(Pagamento::getValor)
                .reduce((identity, accumulator) -> identity.add(accumulator))
                .get();
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
        Emprestimo other = (Emprestimo) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}