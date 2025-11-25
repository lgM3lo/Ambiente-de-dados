package delivery.dao;

import delivery.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por estabelecer e fornecer a conexão com o banco de dados MySQL.
 * Requisito: Uso de JDBC para conexão com MySQL.
 * Atualizado para usar DatabaseConfig para credenciais configuráveis.
 */
public class ConnectionFactory {

    /**
     * Retorna uma nova conexão com o banco de dados.
     * @return Objeto Connection
     * @throws SQLException Se ocorrer um erro de conexão.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // O driver JDBC para MySQL é carregado automaticamente a partir da versão 8.0
            // Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(
                DatabaseConfig.getUrl(),
                DatabaseConfig.getUser(),
                DatabaseConfig.getPassword()
            );
        } catch (SQLException e) {
            // Tratamento de exceções em operações críticas (Requisito)
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Método principal para testar a conexão.
     */
    public static void main(String[] args) {
        /* try (Connection connection = getConnection()) {
            System.out.println("Conexão com o banco de dados estabelecida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Falha na conexão com o banco de dados.");
            e.printStackTrace();
        } */
    }
}