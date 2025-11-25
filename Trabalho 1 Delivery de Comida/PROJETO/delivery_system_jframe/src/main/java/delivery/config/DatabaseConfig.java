package delivery.config;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;

/**
 * Classe para configuração das credenciais do banco de dados.
 * Permite configurar via interface gráfica e salvar em arquivo properties.
 */
public class DatabaseConfig {
    
    private static final String CONFIG_FILE = "database.properties";
    private static Properties properties = new Properties();
    
    static {
        loadConfig();
    }
    
    /**
     * Carrega as configurações do arquivo properties ou usa valores padrão.
     */
    private static void loadConfig() {
        File configFile = new File(CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
            } catch (IOException e) {
                System.err.println("Erro ao carregar configurações: " + e.getMessage());
                setDefaultConfig();
            }
        } else {
            setDefaultConfig();
        }
    }
    
    /**
     * Define configurações padrão.
     */
    private static void setDefaultConfig() {
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/Delivery");
        properties.setProperty("db.user", "root");
        properties.setProperty("db.password", "");
    }
    
    /**
     * Salva as configurações no arquivo properties.
     */
    private static void saveConfig() {
        try (FileOutputStream fos = new FileOutputStream(CONFIG_FILE)) {
            properties.store(fos, "Configurações do Banco de Dados - Sistema Delivery");
        } catch (IOException e) {
            System.err.println("Erro ao salvar configurações: " + e.getMessage());
        }
    }
    
    /**
     * Retorna a URL do banco de dados.
     */
    public static String getUrl() {
        return properties.getProperty("db.url");
    }
    
    /**
     * Retorna o usuário do banco de dados.
     */
    public static String getUser() {
        return properties.getProperty("db.user");
    }
    
    /**
     * Retorna a senha do banco de dados.
     */
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
    
    /**
     * Abre uma janela para configurar as credenciais do banco de dados.
     */
    public static boolean showConfigDialog(Component parent) {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        
        JTextField urlField = new JTextField(getUrl());
        JTextField userField = new JTextField(getUser());
        JPasswordField passwordField = new JPasswordField(getPassword());
        
        panel.add(new JLabel("URL do Banco:"));
        panel.add(urlField);
        panel.add(new JLabel("Usuário:"));
        panel.add(userField);
        panel.add(new JLabel("Senha:"));
        panel.add(passwordField);
        
        int result = JOptionPane.showConfirmDialog(
            parent,
            panel,
            "Configuração do Banco de Dados",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            properties.setProperty("db.url", urlField.getText());
            properties.setProperty("db.user", userField.getText());
            properties.setProperty("db.password", new String(passwordField.getPassword()));
            saveConfig();
            return true;
        }
        
        return false;
    }
}
