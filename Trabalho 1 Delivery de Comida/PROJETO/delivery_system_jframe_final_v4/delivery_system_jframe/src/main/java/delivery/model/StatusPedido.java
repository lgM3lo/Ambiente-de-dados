package delivery.model;

public class StatusPedido {
    private int idStatus;
    private String descricao;

    // Construtor completo
    public StatusPedido(int idStatus, String descricao) {
        this.idStatus = idStatus;
        this.descricao = descricao;
    }

    // Construtor para inserção (sem ID)
    public StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    // Getters e Setters
    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "StatusPedido{" +
                "idStatus=" + idStatus +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
