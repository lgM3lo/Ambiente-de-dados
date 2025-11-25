package delivery.dao;

import delivery.model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do Data Access Object (DAO) para a entidade Cliente.
 * Responsável pelas operações CRUD (Create, Read, Update, Delete).
 */
public class ClienteDAO {

    // CREATE
    public void insert(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (nome, telefone, endereco) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());

            stmt.executeUpdate();

            // Recupera o ID gerado automaticamente
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setIdCliente(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao inserir cliente: " + e.getMessage());
            throw e;
        }
    }

    // READ (Find by ID)
    public Cliente findById(int id) throws SQLException {
        String sql = "SELECT id_cliente, nome, telefone, endereco FROM Cliente WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getInt("id_cliente"),
                            rs.getString("nome"),
                            rs.getString("telefone"),
                            rs.getString("endereco")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // READ (Find All)
    public List<Cliente> findAll() throws SQLException {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT id_cliente, nome, telefone, endereco FROM Cliente";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id_cliente"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("endereco")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os clientes: " + e.getMessage());
            throw e;
        }
        return clientes;
    }

    // UPDATE
    public void update(Cliente cliente) throws SQLException {
        String sql = "UPDATE Cliente SET nome = ?, telefone = ?, endereco = ? WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setString(3, cliente.getEndereco());
            stmt.setInt(4, cliente.getIdCliente());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE id_cliente = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar cliente: " + e.getMessage());
            throw e;
        }
    }
}
