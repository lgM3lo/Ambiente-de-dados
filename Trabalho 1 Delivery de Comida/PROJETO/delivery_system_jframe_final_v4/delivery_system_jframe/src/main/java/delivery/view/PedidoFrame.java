package delivery.view;

import delivery.dao.*;
import delivery.model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Tela de gerenciamento de Pedidos (CRUD completo).
 */
public class PedidoFrame extends JFrame {
    
    private PedidoDAO pedidoDAO;
    private ClienteDAO clienteDAO;
    private RestauranteDAO restauranteDAO;
    private StatusPedidoDAO statusPedidoDAO;
    
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idPedidoField;
    private JComboBox<ComboItem> clienteCombo, restauranteCombo, statusCombo;
    private JComboBox<String> statusEnumCombo;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private Integer selectedId = null;
    
    public PedidoFrame() {
        pedidoDAO = new PedidoDAO();
        clienteDAO = new ClienteDAO();
        restauranteDAO = new RestauranteDAO();
        statusPedidoDAO = new StatusPedidoDAO();
        
        setTitle("Gerenciamento de Pedidos");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        loadCombos();
        loadPedidos();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);
        
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Pedido"));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ID Pedido
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID Pedido:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        idPedidoField = new JTextField(20);
        panel.add(idPedidoField, gbc);
        
        // Cliente
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Cliente:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        clienteCombo = new JComboBox<>();
        panel.add(clienteCombo, gbc);
        
        // Restaurante
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Restaurante:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        restauranteCombo = new JComboBox<>();
        panel.add(restauranteCombo, gbc);
        
        // Status Enum
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Status (Enum):"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        statusEnumCombo = new JComboBox<>(new String[]{"em preparo", "a caminho", "entregue"});
        panel.add(statusEnumCombo, gbc);
        
        // Status Pedido
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 0;
        panel.add(new JLabel("Status Pedido:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        statusCombo = new JComboBox<>();
        panel.add(statusCombo, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Pedidos"));
        
        String[] columns = {"ID", "Cliente", "Restaurante", "Data/Hora", "Status", "ID Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelectedPedido();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparCampos());
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarPedido());
        
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarPedido());
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirPedido());
        
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        panel.add(btnNovo);
        panel.add(btnSalvar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        
        return panel;
    }
    
    private void loadCombos() {
        try {
            // Carregar clientes
            clienteCombo.removeAllItems();
            List<Cliente> clientes = clienteDAO.findAll();
            for (Cliente cliente : clientes) {
                clienteCombo.addItem(new ComboItem(cliente.getIdCliente(), cliente.getNome()));
            }
            
            // Carregar restaurantes
            restauranteCombo.removeAllItems();
            List<Restaurante> restaurantes = restauranteDAO.findAll();
            for (Restaurante restaurante : restaurantes) {
                restauranteCombo.addItem(new ComboItem(restaurante.getIdRestaurante(), restaurante.getNome()));
            }
            
            // Carregar status
            statusCombo.removeAllItems();
            List<StatusPedido> statusList = statusPedidoDAO.findAll();
            for (StatusPedido status : statusList) {
                statusCombo.addItem(new ComboItem(status.getIdStatus(), status.getDescricao()));
            }
            
        } catch (SQLException e) {
            showError("Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    private void loadPedidos() {
        try {
            tableModel.setRowCount(0);
            List<Pedido> pedidos = pedidoDAO.findAll();
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            
            for (Pedido pedido : pedidos) {
                Object[] row = {
                    pedido.getIdPedido(),
                    pedido.getIdCliente(),
                    pedido.getIdRestaurante(),
                    pedido.getDataHora().format(formatter),
                    pedido.getStatus(),
                    pedido.getIdStatus()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            showError("Erro ao carregar pedidos: " + e.getMessage());
        }
    }
    
    private void loadSelectedPedido() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedId = (Integer) tableModel.getValueAt(selectedRow, 0);
            idPedidoField.setText(String.valueOf(selectedId));
            idPedidoField.setEnabled(false);
            
            int idCliente = (Integer) tableModel.getValueAt(selectedRow, 1);
            int idRestaurante = (Integer) tableModel.getValueAt(selectedRow, 2);
            String status = (String) tableModel.getValueAt(selectedRow, 4);
            Integer idStatus = (Integer) tableModel.getValueAt(selectedRow, 5);
            
            // Selecionar nos combos
            for (int i = 0; i < clienteCombo.getItemCount(); i++) {
                if (((ComboItem) clienteCombo.getItemAt(i)).getId() == idCliente) {
                    clienteCombo.setSelectedIndex(i);
                    break;
                }
            }
            
            for (int i = 0; i < restauranteCombo.getItemCount(); i++) {
                if (((ComboItem) restauranteCombo.getItemAt(i)).getId() == idRestaurante) {
                    restauranteCombo.setSelectedIndex(i);
                    break;
                }
            }
            
            statusEnumCombo.setSelectedItem(status);
            
            if (idStatus != null) {
                for (int i = 0; i < statusCombo.getItemCount(); i++) {
                    if (((ComboItem) statusCombo.getItemAt(i)).getId() == idStatus) {
                        statusCombo.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
    }
    
    private void salvarPedido() {
        try {
            int idPedido = Integer.parseInt(idPedidoField.getText());
            
            if (!validarCampos()) {
                return;
            }
            ComboItem clienteItem = (ComboItem) clienteCombo.getSelectedItem();
            ComboItem restauranteItem = (ComboItem) restauranteCombo.getSelectedItem();
            String statusEnum = (String) statusEnumCombo.getSelectedItem();
            ComboItem statusItem = (ComboItem) statusCombo.getSelectedItem();
            
            // O construtor já foi corrigido para aceitar o ID
            Pedido pedido = new Pedido(
                idPedido,
                clienteItem.getId(),
                restauranteItem.getId(),
                LocalDateTime.now(),
                statusEnum,
                statusItem.getId()
            );
            
            pedidoDAO.insert(pedido);
            showSuccess("Pedido cadastrado com sucesso!");
            limparCampos();
            loadPedidos();
        } catch (NumberFormatException e) {
            showError("ID do pedido deve ser um número válido.");
        } catch (SQLException e) {
            showError("Erro ao salvar pedido: " + e.getMessage());
        }
    }
    
    private void atualizarPedido() {
        if (selectedId == null) {
            showWarning("Selecione um pedido para atualizar.");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        try {
            ComboItem clienteItem = (ComboItem) clienteCombo.getSelectedItem();
            ComboItem restauranteItem = (ComboItem) restauranteCombo.getSelectedItem();
            String statusEnum = (String) statusEnumCombo.getSelectedItem();
            ComboItem statusItem = (ComboItem) statusCombo.getSelectedItem();
            
            Pedido pedido = new Pedido(
                selectedId,
                clienteItem.getId(),
                restauranteItem.getId(),
                LocalDateTime.now(),
                statusEnum,
                statusItem.getId()
            );
            
            pedidoDAO.update(pedido);
            showSuccess("Pedido atualizado com sucesso!");
            limparCampos();
            loadPedidos();
        } catch (SQLException e) {
            showError("Erro ao atualizar pedido: " + e.getMessage());
        }
    }
    
    private void excluirPedido() {
        if (selectedId == null) {
            showWarning("Selecione um pedido para excluir.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este pedido?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                pedidoDAO.delete(selectedId);
                showSuccess("Pedido excluído com sucesso!");
                limparCampos();
                loadPedidos();
            } catch (SQLException e) {
                showError("Erro ao excluir pedido: " + e.getMessage());
            }
        }
    }
    
    private void limparCampos() {
        selectedId = null;
        idPedidoField.setText("");
        idPedidoField.setEnabled(true);
        clienteCombo.setSelectedIndex(0);
        restauranteCombo.setSelectedIndex(0);
        statusEnumCombo.setSelectedIndex(0);
        statusCombo.setSelectedIndex(0);
        table.clearSelection();
    }
    
    private boolean validarCampos() {
        if (idPedidoField.getText().trim().isEmpty()) {
            showWarning("O campo ID Pedido é obrigatório.");
            idPedidoField.requestFocus();
            return false;
        }
        
        if (clienteCombo.getSelectedItem() == null) {
            showWarning("Selecione um cliente.");
            return false;
        }
        
        if (restauranteCombo.getSelectedItem() == null) {
            showWarning("Selecione um restaurante.");
            return false;
        }
        
        if (statusCombo.getSelectedItem() == null) {
            showWarning("Selecione um status.");
            return false;
        }
        
        return true;
    }
    
    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Erro", JOptionPane.ERROR_MESSAGE);
    }
    
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Atenção", JOptionPane.WARNING_MESSAGE);
    }
    
    /**
     * Classe auxiliar para itens de ComboBox.
     */
    private static class ComboItem {
        private int id;
        private String descricao;
        
        public ComboItem(int id, String descricao) {
            this.id = id;
            this.descricao = descricao;
        }
        
        public int getId() {
            return id;
        }
        
        @Override
        public String toString() {
            return id + " - " + descricao;
        }
    }
}
