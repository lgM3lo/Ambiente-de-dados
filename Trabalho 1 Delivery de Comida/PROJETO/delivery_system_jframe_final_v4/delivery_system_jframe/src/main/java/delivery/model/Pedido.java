package delivery.model;

import java.time.LocalDateTime;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private int idRestaurante;
    private int idStatus;
    private LocalDateTime dataHora;
    private String status; // Adicionado para o ENUM do SQL

    // Construtor completo
    public Pedido(int idPedido, int idCliente, int idRestaurante, LocalDateTime dataHora, String status, int idStatus) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.dataHora = dataHora;
        this.status = status;
        this.idStatus = idStatus;
    }

    // Construtor para inserção (sem ID e dataHora, que será gerada pelo banco)
    public Pedido(int idCliente, int idRestaurante, String status, int idStatus) {
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.dataHora = LocalDateTime.now(); // Valor padrão para o objeto
        this.status = status;
        this.idStatus = idStatus;
    }

    // Getters e Setters
    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdRestaurante() {
        return idRestaurante;
    }

    public void setIdRestaurante(int idRestaurante) {
        this.idRestaurante = idRestaurante;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
    // NOVO: Getter para o campo 'status' (ENUM)
    public String getStatus() {
        return status;
    }

    // NOVO: Setter para o campo 'status' (ENUM)
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", idCliente=" + idCliente +
                ", idRestaurante=" + idRestaurante +
                ", dataHora=" + dataHora +
                ", status='" + status + '\'' +
                ", idStatus=" + idStatus +
                '}';
    }
}
