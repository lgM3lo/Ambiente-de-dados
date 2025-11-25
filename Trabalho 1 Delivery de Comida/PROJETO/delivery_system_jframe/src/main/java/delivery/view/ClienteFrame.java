package delivery.view;

import delivery.dao.ClienteDAO;
import delivery.model.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Tela de gerenciamento de Clientes (CRUD completo).
 */
public class ClienteFrame extends JFrame {
    
    private ClienteDAO clienteDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField nomeField, telefoneField, enderecoField;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private Integer selectedId = null;
    
    public ClienteFrame() {
        clienteDAO = new ClienteDAO();
        
        setTitle("Gerenciamento de Clientes");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        loadClientes();
    }
    
    /**
     * Inicializa os componentes da interface.
     */
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Painel de formulário
        JPanel formPanel = createFormPanel();
        add(formPanel, BorderLayout.NORTH);
        
        // Painel de tabela
        JPanel tablePanel = createTablePanel();
        add(tablePanel, BorderLayout.CENTER);
        
        // Painel de botões
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Cria o painel de formulário.
     */
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Cliente"));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nomeField = new JTextField(30);
        panel.add(nomeField, gbc);
        
        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Telefone:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        telefoneField = new JTextField(30);
        panel.add(telefoneField, gbc);
        
        // Endereço
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Endereço:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        enderecoField = new JTextField(30);
        panel.add(enderecoField, gbc);
        
        return panel;
    }
    
    /**
     * Cria o painel da tabela.
     */
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Clientes"));
        
        String[] columns = {"ID", "Nome", "Telefone", "Endereço"};
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
                loadSelectedCliente();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Cria o painel de botões.
     */
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        btnNovo = new JButton("Novo");
        btnNovo.addActionListener(e -> limparCampos());
        
        btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarCliente());
        
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarCliente());
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirCliente());
        
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        panel.add(btnNovo);
        panel.add(btnSalvar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        
        return panel;
    }
    
    /**
     * Carrega todos os clientes na tabela.
     */
    private void loadClientes() {
        try {
            tableModel.setRowCount(0);
            List<Cliente> clientes = clienteDAO.findAll();
            
            for (Cliente cliente : clientes) {
                Object[] row = {
                    cliente.getIdCliente(),
                    cliente.getNome(),
                    cliente.getTelefone(),
                    cliente.getEndereco()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            showError("Erro ao carregar clientes: " + e.getMessage());
        }
    }
    
    /**
     * Carrega os dados do cliente selecionado nos campos.
     */
    private void loadSelectedCliente() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedId = (Integer) tableModel.getValueAt(selectedRow, 0);
            nomeField.setText((String) tableModel.getValueAt(selectedRow, 1));
            telefoneField.setText((String) tableModel.getValueAt(selectedRow, 2));
            enderecoField.setText((String) tableModel.getValueAt(selectedRow, 3));
        }
    }
    
    /**
     * Salva um novo cliente.
     */
    private void salvarCliente() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            Cliente cliente = new Cliente(
                nomeField.getText(),
                telefoneField.getText(),
                enderecoField.getText()
            );
            
            clienteDAO.insert(cliente);
            showSuccess("Cliente cadastrado com sucesso!");
            limparCampos();
            loadClientes();
        } catch (SQLException e) {
            showError("Erro ao salvar cliente: " + e.getMessage());
        }
    }
    
    /**
     * Atualiza um cliente existente.
     */
    private void atualizarCliente() {
        if (selectedId == null) {
            showWarning("Selecione um cliente para atualizar.");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        try {
            Cliente cliente = new Cliente(
                selectedId,
                nomeField.getText(),
                telefoneField.getText(),
                enderecoField.getText()
            );
            
            clienteDAO.update(cliente);
            showSuccess("Cliente atualizado com sucesso!");
            limparCampos();
            loadClientes();
        } catch (SQLException e) {
            showError("Erro ao atualizar cliente: " + e.getMessage());
        }
    }
    
    /**
     * Exclui um cliente.
     */
    private void excluirCliente() {
        if (selectedId == null) {
            showWarning("Selecione um cliente para excluir.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este cliente?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                clienteDAO.delete(selectedId);
                showSuccess("Cliente excluído com sucesso!");
                limparCampos();
                loadClientes();
            } catch (SQLException e) {
                showError("Erro ao excluir cliente: " + e.getMessage());
            }
        }
    }
    
    /**
     * Limpa os campos do formulário.
     */
    private void limparCampos() {
        selectedId = null;
        nomeField.setText("");
        telefoneField.setText("");
        enderecoField.setText("");
        table.clearSelection();
    }
    
    /**
     * Valida os campos do formulário.
     */
    private boolean validarCampos() {
        if (nomeField.getText().trim().isEmpty()) {
            showWarning("O campo Nome é obrigatório.");
            nomeField.requestFocus();
            return false;
        }
        
        if (telefoneField.getText().trim().isEmpty()) {
            showWarning("O campo Telefone é obrigatório.");
            telefoneField.requestFocus();
            return false;
        }
        
        if (enderecoField.getText().trim().isEmpty()) {
            showWarning("O campo Endereço é obrigatório.");
            enderecoField.requestFocus();
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
}
