package delivery.view;

import delivery.dao.StatusPedidoDAO;
import delivery.model.StatusPedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Tela de gerenciamento de Status de Pedido (CRUD completo).
 */
public class StatusPedidoFrame extends JFrame {
    
    private StatusPedidoDAO statusPedidoDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField descricaoField;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private Integer selectedId = null;
    
    public StatusPedidoFrame() {
        statusPedidoDAO = new StatusPedidoDAO();
        
        setTitle("Gerenciamento de Status de Pedido");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        loadStatusPedidos();
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
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Status"));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Descrição:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        descricaoField = new JTextField(30);
        panel.add(descricaoField, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Status"));
        
        String[] columns = {"ID", "Descrição"};
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
                loadSelectedStatus();
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
        btnSalvar.addActionListener(e -> salvarStatus());
        
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarStatus());
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirStatus());
        
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        panel.add(btnNovo);
        panel.add(btnSalvar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        
        return panel;
    }
    
    private void loadStatusPedidos() {
        try {
            tableModel.setRowCount(0);
            List<StatusPedido> statusList = statusPedidoDAO.findAll();
            
            for (StatusPedido status : statusList) {
                Object[] row = {
                    status.getIdStatus(),
                    status.getDescricao()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            showError("Erro ao carregar status: " + e.getMessage());
        }
    }
    
    private void loadSelectedStatus() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedId = (Integer) tableModel.getValueAt(selectedRow, 0);
            descricaoField.setText((String) tableModel.getValueAt(selectedRow, 1));
        }
    }
    
    private void salvarStatus() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            StatusPedido status = new StatusPedido(descricaoField.getText());
            statusPedidoDAO.insert(status);
            showSuccess("Status cadastrado com sucesso!");
            limparCampos();
            loadStatusPedidos();
        } catch (SQLException e) {
            showError("Erro ao salvar status: " + e.getMessage());
        }
    }
    
    private void atualizarStatus() {
        if (selectedId == null) {
            showWarning("Selecione um status para atualizar.");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        try {
            StatusPedido status = new StatusPedido(selectedId, descricaoField.getText());
            statusPedidoDAO.update(status);
            showSuccess("Status atualizado com sucesso!");
            limparCampos();
            loadStatusPedidos();
        } catch (SQLException e) {
            showError("Erro ao atualizar status: " + e.getMessage());
        }
    }
    
    private void excluirStatus() {
        if (selectedId == null) {
            showWarning("Selecione um status para excluir.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este status?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                statusPedidoDAO.delete(selectedId);
                showSuccess("Status excluído com sucesso!");
                limparCampos();
                loadStatusPedidos();
            } catch (SQLException e) {
                showError("Erro ao excluir status: " + e.getMessage());
            }
        }
    }
    
    private void limparCampos() {
        selectedId = null;
        descricaoField.setText("");
        table.clearSelection();
    }
    
    private boolean validarCampos() {
        if (descricaoField.getText().trim().isEmpty()) {
            showWarning("O campo Descrição é obrigatório.");
            descricaoField.requestFocus();
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
