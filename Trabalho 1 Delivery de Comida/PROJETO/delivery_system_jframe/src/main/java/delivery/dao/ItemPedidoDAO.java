package delivery.dao;

import delivery.model.ItemPedido;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    // CREATE
    public void insert(ItemPedido itemPedido) throws SQLException {
        String sql = "INSERT INTO ItemPedido (id_pedido, descricao, quantidade, preco) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, itemPedido.getIdPedido());
            stmt.setString(2, itemPedido.getDescricao());
            stmt.setInt(3, itemPedido.getQuantidade());
            stmt.setBigDecimal(4, itemPedido.getPreco());

            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    itemPedido.setIdItem(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir item de pedido: " + e.getMessage());
            throw e;
        }
    }

    // READ (Find by ID)
    public ItemPedido findById(int id) throws SQLException {
        String sql = "SELECT id_item, id_pedido, descricao, quantidade, preco FROM ItemPedido WHERE id_item = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ItemPedido(
                            rs.getInt("id_item"),
                            rs.getInt("id_pedido"),
                            rs.getString("descricao"),
                            rs.getInt("quantidade"),
                            rs.getBigDecimal("preco")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar item de pedido por ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // READ (Find All)
    public List<ItemPedido> findAll() throws SQLException {
        List<ItemPedido> itensPedido = new ArrayList<>();
        String sql = "SELECT id_item, id_pedido, descricao, quantidade, preco FROM ItemPedido";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                ItemPedido itemPedido = new ItemPedido(
                        rs.getInt("id_item"),
                        rs.getInt("id_pedido"),
                        rs.getString("descricao"),
                        rs.getInt("quantidade"),
                        rs.getBigDecimal("preco")
                );
                itensPedido.add(itemPedido);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os itens de pedido: " + e.getMessage());
            throw e;
        }
        return itensPedido;
    }

    // UPDATE
    public void update(ItemPedido itemPedido) throws SQLException {
        String sql = "UPDATE ItemPedido SET id_pedido = ?, descricao = ?, quantidade = ?, preco = ? WHERE id_item = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, itemPedido.getIdPedido());
            stmt.setString(2, itemPedido.getDescricao());
            stmt.setInt(3, itemPedido.getQuantidade());
            stmt.setBigDecimal(4, itemPedido.getPreco());
            stmt.setInt(5, itemPedido.getIdItem());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar item de pedido: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM ItemPedido WHERE id_item = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar item de pedido: " + e.getMessage());
            throw e;
        }
    }
}
