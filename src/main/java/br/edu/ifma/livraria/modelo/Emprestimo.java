package br.edu.ifma.livraria.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public final class Emprestimo {

    private Usuario usuario;
    private Livro livro;
    private LocalDate dataEmprestimo;
    private LocalDate dataPrevista;
    private LocalDate dataDevolucao;
    private BigDecimal valorPago;

    public static final int DIAS_PARA_DEVOLUCAO = 7;
    public static final BigDecimal VALOR_FIXO_PAGAMENTO = new BigDecimal("5.0");
    public static final BigDecimal MULTA_POR_DIA_DE_ATRASO_NO_PAGAMENTO = new BigDecimal("0.40");

    public Emprestimo(Usuario usuario, Livro livro, LocalDate dataEmprestimo) {
        this.setUsuario(usuario);
        this.setLivro(livro);
        this.setDataEmprestimo(dataEmprestimo);
        dataPrevista = dataEmprestimo.plusDays(DIAS_PARA_DEVOLUCAO);
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

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

}