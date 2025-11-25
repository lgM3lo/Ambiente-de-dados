package delivery.dao;

import delivery.model.StatusPedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusPedidoDAO {

    // CREATE
    public void insert(StatusPedido statusPedido) throws SQLException {
        String sql = "INSERT INTO StatusPedido (descricao) VALUES (?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, statusPedido.getDescricao());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    statusPedido.setIdStatus(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir status de pedido: " + e.getMessage());
            throw e;
        }
    }

    // READ (Find by ID)
    public StatusPedido findById(int id) throws SQLException {
        String sql = "SELECT id_status, descricao FROM StatusPedido WHERE id_status = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new StatusPedido(
                            rs.getInt("id_status"),
                            rs.getString("descricao")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar status de pedido por ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // READ (Find All)
    public List<StatusPedido> findAll() throws SQLException {
        List<StatusPedido> statusPedidos = new ArrayList<>();
        String sql = "SELECT id_status, descricao FROM StatusPedido";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                StatusPedido statusPedido = new StatusPedido(
                        rs.getInt("id_status"),
                        rs.getString("descricao")
                );
                statusPedidos.add(statusPedido);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os status de pedido: " + e.getMessage());
            throw e;
        }
        return statusPedidos;
    }

    // UPDATE
    public void update(StatusPedido statusPedido) throws SQLException {
        String sql = "UPDATE StatusPedido SET descricao = ? WHERE id_status = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statusPedido.getDescricao());
            stmt.setInt(2, statusPedido.getIdStatus());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar status de pedido: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM StatusPedido WHERE id_status = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar status de pedido: " + e.getMessage());
            throw e;
        }
    }
}
