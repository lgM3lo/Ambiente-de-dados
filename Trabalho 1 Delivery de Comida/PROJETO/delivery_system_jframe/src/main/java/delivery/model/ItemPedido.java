package delivery.model;

import java.math.BigDecimal;

public class ItemPedido {
    private int idItem;
    private int idPedido;
    private String descricao;
    private int quantidade;
    private BigDecimal preco;

    // Construtor completo
    public ItemPedido(int idItem, int idPedido, String descricao, int quantidade, BigDecimal preco) {
        this.idItem = idItem;
        this.idPedido = idPedido;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Construtor para inserção (sem ID)
    public ItemPedido(int idPedido, String descricao, int quantidade, BigDecimal preco) {
        this.idPedido = idPedido;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    // Getters e Setters
    public int getIdItem() {
        return idItem;
    }

    public void setIdItem(int idItem) {
        this.idItem = idItem;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    @Override
    public String toString() {
        return "ItemPedido{" +
                "idItem=" + idItem +
                ", idPedido=" + idPedido +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }
}
