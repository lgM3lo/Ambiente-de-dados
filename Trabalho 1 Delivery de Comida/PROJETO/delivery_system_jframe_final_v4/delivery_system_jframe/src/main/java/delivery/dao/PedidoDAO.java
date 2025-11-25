package delivery.dao;

import delivery.model.Pedido;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // CREATE
    public void insert(Pedido pedido) throws SQLException {
        // Adicionado 'status' na query de INSERT
        String sql = "INSERT INTO Pedido (id_pedido, id_cliente, id_restaurante, id_status, data_hora, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, pedido.getIdPedido()); // NOVO: Adicionado ID
            stmt.setInt(2, pedido.getIdCliente());
            stmt.setInt(3, pedido.getIdRestaurante());
            stmt.setInt(4, pedido.getIdStatus());
            // Converte LocalDateTime para Timestamp para o JDBC
            stmt.setTimestamp(5, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(6, pedido.getStatus()); // NOVO: Adicionado status

            stmt.executeUpdate();

            // Não há getGeneratedKeys pois o ID é fornecido pelo usuário
            // Apenas para manter a consistência, mas o ID já está no objeto pedido

        } catch (SQLException e) {
            System.err.println("Erro ao inserir pedido: " + e.getMessage());
            throw e;
        }
    }

    // READ (Find by ID)
    public Pedido findById(int id) throws SQLException {
        // Adicionado 'status' na query de SELECT
        String sql = "SELECT id_pedido, id_cliente, id_restaurante, id_status, data_hora, status FROM Pedido WHERE id_pedido = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Converte Timestamp para LocalDateTime
                    LocalDateTime dataHora = rs.getTimestamp("data_hora").toLocalDateTime();
                    return new Pedido(
                            rs.getInt("id_pedido"),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_restaurante"),
                            dataHora,
                            rs.getString("status"), // NOVO: Lendo status
                            rs.getInt("id_status")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar pedido por ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // READ (Find All)
    public List<Pedido> findAll() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        // Adicionado 'status' na query de SELECT
        String sql = "SELECT id_pedido, id_cliente, id_restaurante, id_status, data_hora, status FROM Pedido";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                LocalDateTime dataHora = rs.getTimestamp("data_hora").toLocalDateTime();
                Pedido pedido = new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_restaurante"),
                        dataHora,
                        rs.getString("status"), // NOVO: Lendo status
                        rs.getInt("id_status")
                );
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os pedidos: " + e.getMessage());
            throw e;
        }
        return pedidos;
    }

    // UPDATE
    public void update(Pedido pedido) throws SQLException {
        // Adicionado 'status' na query de UPDATE
        String sql = "UPDATE Pedido SET id_cliente = ?, id_restaurante = ?, id_status = ?, data_hora = ?, status = ? WHERE id_pedido = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, pedido.getIdCliente());
            stmt.setInt(2, pedido.getIdRestaurante());
            stmt.setInt(3, pedido.getIdStatus());
            stmt.setTimestamp(4, Timestamp.valueOf(pedido.getDataHora()));
            stmt.setString(5, pedido.getStatus()); // NOVO: Atualizando status
            stmt.setInt(6, pedido.getIdPedido());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar pedido: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Pedido WHERE id_pedido = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar pedido: " + e.getMessage());
            throw e;
        }
    }
}
