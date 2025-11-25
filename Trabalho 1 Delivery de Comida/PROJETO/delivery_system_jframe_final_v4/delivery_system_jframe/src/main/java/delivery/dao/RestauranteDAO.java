package delivery.dao;

import delivery.model.Restaurante;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RestauranteDAO {

    // CREATE
    public void insert(Restaurante restaurante) throws SQLException {
        String sql = "INSERT INTO Restaurante (id_restaurante, nome, tipo_cozinha, telefone) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, restaurante.getIdRestaurante());
            stmt.setString(2, restaurante.getNome());
            stmt.setString(3, restaurante.getTipoCozinha());
            stmt.setString(4, restaurante.getTelefone());

            stmt.executeUpdate();

            // Não há getGeneratedKeys pois o ID é fornecido pelo usuário
            // Apenas para manter a consistência, mas o ID já está no objeto restaurante

        } catch (SQLException e) {
            System.err.println("Erro ao inserir restaurante: " + e.getMessage());
            throw e;
        }
    }

    // READ (Find by ID)
    public Restaurante findById(int id) throws SQLException {
        String sql = "SELECT id_restaurante, nome, tipo_cozinha, telefone FROM Restaurante WHERE id_restaurante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Restaurante(
                            rs.getInt("id_restaurante"),
                            rs.getString("nome"),
                            rs.getString("tipo_cozinha"),
                            rs.getString("telefone")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar restaurante por ID: " + e.getMessage());
            throw e;
        }
        return null;
    }

    // READ (Find All)
    public List<Restaurante> findAll() throws SQLException {
        List<Restaurante> restaurantes = new ArrayList<>();
        String sql = "SELECT id_restaurante, nome, tipo_cozinha, telefone FROM Restaurante";
        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Restaurante restaurante = new Restaurante(
                        rs.getInt("id_restaurante"),
                        rs.getString("nome"),
                        rs.getString("tipo_cozinha"),
                        rs.getString("telefone")
                );
                restaurantes.add(restaurante);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar todos os restaurantes: " + e.getMessage());
            throw e;
        }
        return restaurantes;
    }

    // UPDATE
    public void update(Restaurante restaurante) throws SQLException {
        String sql = "UPDATE Restaurante SET nome = ?, tipo_cozinha = ?, telefone = ? WHERE id_restaurante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, restaurante.getNome());
            stmt.setString(2, restaurante.getTipoCozinha());
            stmt.setString(3, restaurante.getTelefone());
            stmt.setInt(4, restaurante.getIdRestaurante());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar restaurante: " + e.getMessage());
            throw e;
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Restaurante WHERE id_restaurante = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao deletar restaurante: " + e.getMessage());
            throw e;
        }
    }
}
