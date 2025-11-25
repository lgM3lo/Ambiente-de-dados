package delivery.view;

import delivery.dao.RestauranteDAO;
import delivery.model.Restaurante;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Tela de gerenciamento de Restaurantes (CRUD completo).
 */
public class RestauranteFrame extends JFrame {
    
    private RestauranteDAO restauranteDAO;
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, nomeField, tipoCozinhaField, telefoneField;
    private JButton btnNovo, btnSalvar, btnAtualizar, btnExcluir, btnLimpar;
    private Integer selectedId = null;
    
    public RestauranteFrame() {
        restauranteDAO = new RestauranteDAO();
        
        setTitle("Gerenciamento de Restaurantes");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        initComponents();
        loadRestaurantes();
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
        panel.setBorder(BorderFactory.createTitledBorder("Dados do Restaurante"));
        panel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("ID:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        idField = new JTextField(30);
        panel.add(idField, gbc);
        
        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        nomeField = new JTextField(30);
        panel.add(nomeField, gbc);
        
        // Tipo de Cozinha
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        panel.add(new JLabel("Tipo de Cozinha:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        tipoCozinhaField = new JTextField(30);
        panel.add(tipoCozinhaField, gbc);
        
        // Telefone
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0;
        panel.add(new JLabel("Telefone:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        telefoneField = new JTextField(30);
        panel.add(telefoneField, gbc);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Lista de Restaurantes"));
        
        String[] columns = {"ID", "Nome", "Tipo de Cozinha", "Telefone"};
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
                loadSelectedRestaurante();
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
        btnSalvar.addActionListener(e -> salvarRestaurante());
        
        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(e -> atualizarRestaurante());
        
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(e -> excluirRestaurante());
        
        btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        
        panel.add(btnNovo);
        panel.add(btnSalvar);
        panel.add(btnAtualizar);
        panel.add(btnExcluir);
        panel.add(btnLimpar);
        
        return panel;
    }
    
    private void loadRestaurantes() {
        try {
            tableModel.setRowCount(0);
            List<Restaurante> restaurantes = restauranteDAO.findAll();
            
            for (Restaurante restaurante : restaurantes) {
                Object[] row = {
                    restaurante.getIdRestaurante(),
                    restaurante.getNome(),
                    restaurante.getTipoCozinha(),
                    restaurante.getTelefone()
                };
                tableModel.addRow(row);
            }
        } catch (SQLException e) {
            showError("Erro ao carregar restaurantes: " + e.getMessage());
        }
    }
    
    private void loadSelectedRestaurante() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            selectedId = (Integer) tableModel.getValueAt(selectedRow, 0);
            idField.setText(String.valueOf(selectedId));
            idField.setEnabled(false);
            nomeField.setText((String) tableModel.getValueAt(selectedRow, 1));
            tipoCozinhaField.setText((String) tableModel.getValueAt(selectedRow, 2));
            telefoneField.setText((String) tableModel.getValueAt(selectedRow, 3));
        }
    }
    
    private void salvarRestaurante() {
        if (!validarCampos()) {
            return;
        }
        
        try {
            int id = Integer.parseInt(idField.getText());
            Restaurante restaurante = new Restaurante(
                id,
                nomeField.getText(),
                tipoCozinhaField.getText(),
                telefoneField.getText()
            );
            
            restauranteDAO.insert(restaurante);
            showSuccess("Restaurante cadastrado com sucesso!");
            limparCampos();
            loadRestaurantes();
        } catch (NumberFormatException e) {
            showError("ID deve ser um número válido.");
        } catch (SQLException e) {
            showError("Erro ao salvar restaurante: " + e.getMessage());
        }
    }
    
    private void atualizarRestaurante() {
        if (selectedId == null) {
            showWarning("Selecione um restaurante para atualizar.");
            return;
        }
        
        if (!validarCampos()) {
            return;
        }
        
        try {
            Restaurante restaurante = new Restaurante(
                selectedId,
                nomeField.getText(),
                tipoCozinhaField.getText(),
                telefoneField.getText()
            );
            
            restauranteDAO.update(restaurante);
            showSuccess("Restaurante atualizado com sucesso!");
            limparCampos();
            loadRestaurantes();
        } catch (SQLException e) {
            showError("Erro ao atualizar restaurante: " + e.getMessage());
        }
    }
    
    private void excluirRestaurante() {
        if (selectedId == null) {
            showWarning("Selecione um restaurante para excluir.");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(
            this,
            "Deseja realmente excluir este restaurante?",
            "Confirmação",
            JOptionPane.YES_NO_OPTION
        );
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                restauranteDAO.delete(selectedId);
                showSuccess("Restaurante excluído com sucesso!");
                limparCampos();
                loadRestaurantes();
            } catch (SQLException e) {
                showError("Erro ao excluir restaurante: " + e.getMessage());
            }
        }
    }
    
    private void limparCampos() {
        selectedId = null;
        idField.setText("");
        idField.setEnabled(true);
        nomeField.setText("");
        tipoCozinhaField.setText("");
        telefoneField.setText("");
        table.clearSelection();
    }
    
    private boolean validarCampos() {
        if (idField.getText().trim().isEmpty()) {
            showWarning("O campo ID é obrigatório.");
            idField.requestFocus();
            return false;
        }
        
        if (nomeField.getText().trim().isEmpty()) {
            showWarning("O campo Nome é obrigatório.");
            nomeField.requestFocus();
            return false;
        }
        
        if (tipoCozinhaField.getText().trim().isEmpty()) {
            showWarning("O campo Tipo de Cozinha é obrigatório.");
            tipoCozinhaField.requestFocus();
            return false;
        }
        
        if (telefoneField.getText().trim().isEmpty()) {
            showWarning("O campo Telefone é obrigatório.");
            telefoneField.requestFocus();
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
