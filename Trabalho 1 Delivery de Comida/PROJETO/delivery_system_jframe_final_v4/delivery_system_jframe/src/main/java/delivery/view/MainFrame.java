package delivery.view;

import delivery.config.DatabaseConfig;
import delivery.dao.ConnectionFactory;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Tela principal do Sistema de Delivery.
 * Contém o menu para acessar as diferentes funcionalidades.
 */
public class MainFrame extends JFrame {
    
    public MainFrame() {
        setTitle("Sistema de Delivery - Gerenciamento");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Criar menu
        createMenuBar();
        
        // Painel principal com logo/informações
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 245));
        
        // Painel central com mensagem de boas-vindas
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 245, 245));
        
        JLabel welcomeLabel = new JLabel("Sistema de Delivery");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 32));
        welcomeLabel.setForeground(new Color(51, 51, 51));
        
        JLabel subtitleLabel = new JLabel("Selecione uma opção no menu acima");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(102, 102, 102));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        centerPanel.add(welcomeLabel, gbc);
        
        gbc.gridy = 1;
        centerPanel.add(subtitleLabel, gbc);
        
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        // Barra de status
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBackground(new Color(230, 230, 230));
        JLabel statusLabel = new JLabel("Pronto");
        statusBar.add(statusLabel);
        mainPanel.add(statusBar, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Testar conexão ao iniciar
        testConnection();
    }
    
    /**
     * Cria a barra de menu.
     */
    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        
        // Menu Arquivo
        JMenu fileMenu = new JMenu("Arquivo");
        JMenuItem configItem = new JMenuItem("Configurar Banco de Dados");
        configItem.addActionListener(e -> configureDatabase());
        JMenuItem exitItem = new JMenuItem("Sair");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(configItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        
        // Menu Cadastros
        JMenu cadastrosMenu = new JMenu("Cadastros");
        
        JMenuItem clientesItem = new JMenuItem("Clientes");
        clientesItem.addActionListener(e -> openClientesFrame());
        
        JMenuItem restaurantesItem = new JMenuItem("Restaurantes");
        restaurantesItem.addActionListener(e -> openRestaurantesFrame());
        
        JMenuItem statusPedidoItem = new JMenuItem("Status de Pedido");
        statusPedidoItem.addActionListener(e -> openStatusPedidoFrame());
        
        cadastrosMenu.add(clientesItem);
        cadastrosMenu.add(restaurantesItem);
        cadastrosMenu.add(statusPedidoItem);
        
        // Menu Pedidos
        JMenu pedidosMenu = new JMenu("Pedidos");
        
        JMenuItem novoPedidoItem = new JMenuItem("Novo Pedido");
        novoPedidoItem.addActionListener(e -> openPedidosFrame());
        
        JMenuItem consultarPedidosItem = new JMenuItem("Consultar Pedidos");
        consultarPedidosItem.addActionListener(e -> openPedidosFrame());
        
        pedidosMenu.add(novoPedidoItem);
        pedidosMenu.add(consultarPedidosItem);
        
        // Menu Ajuda
        JMenu helpMenu = new JMenu("Ajuda");
        JMenuItem aboutItem = new JMenuItem("Sobre");
        aboutItem.addActionListener(e -> showAbout());
        helpMenu.add(aboutItem);
        
        menuBar.add(fileMenu);
        menuBar.add(cadastrosMenu);
        menuBar.add(pedidosMenu);
        menuBar.add(helpMenu);
        
        setJMenuBar(menuBar);
    }
    
    /**
     * Testa a conexão com o banco de dados.
     */
    private void testConnection() {
        try (Connection conn = ConnectionFactory.getConnection()) {
            JOptionPane.showMessageDialog(
                this,
                "Conexão com o banco de dados estabelecida com sucesso!",
                "Conexão OK",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (SQLException e) {
            int result = JOptionPane.showConfirmDialog(
                this,
                "Erro ao conectar ao banco de dados:\n" + e.getMessage() + 
                "\n\nDeseja configurar as credenciais agora?",
                "Erro de Conexão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.ERROR_MESSAGE
            );
            
            if (result == JOptionPane.YES_OPTION) {
                configureDatabase();
            }
        }
    }
    
    /**
     * Abre o diálogo de configuração do banco de dados.
     */
    private void configureDatabase() {
        if (DatabaseConfig.showConfigDialog(this)) {
            JOptionPane.showMessageDialog(
                this,
                "Configurações salvas com sucesso!\nReinicie o sistema para aplicar as mudanças.",
                "Configuração",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
    
    /**
     * Abre a tela de gerenciamento de clientes.
     */
    private void openClientesFrame() {
        ClienteFrame clienteFrame = new ClienteFrame();
        clienteFrame.setVisible(true);
    }
    
    /**
     * Abre a tela de gerenciamento de restaurantes.
     */
    private void openRestaurantesFrame() {
        RestauranteFrame restauranteFrame = new RestauranteFrame();
        restauranteFrame.setVisible(true);
    }
    
    /**
     * Abre a tela de gerenciamento de status de pedido.
     */
    private void openStatusPedidoFrame() {
        StatusPedidoFrame statusFrame = new StatusPedidoFrame();
        statusFrame.setVisible(true);
    }
    
    /**
     * Abre a tela de gerenciamento de pedidos.
     */
    private void openPedidosFrame() {
        PedidoFrame pedidoFrame = new PedidoFrame();
        pedidoFrame.setVisible(true);
    }
    
    /**
     * Exibe informações sobre o sistema.
     */
    private void showAbout() {
        JOptionPane.showMessageDialog(
            this,
            "Sistema de Delivery - Versão 1.0\n\n" +
            "Sistema de gerenciamento de pedidos de delivery.\n" +
            "Desenvolvido com Java Swing e MySQL.\n\n" +
            "Arquitetura: Model-DAO-Service-Controller",
            "Sobre",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Método principal para iniciar o sistema.
     */
    public static void main(String[] args) {
        // Configurar Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Iniciar interface
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
