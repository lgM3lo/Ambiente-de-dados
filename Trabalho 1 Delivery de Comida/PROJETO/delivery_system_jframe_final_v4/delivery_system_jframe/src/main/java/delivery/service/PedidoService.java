package delivery.service;

import delivery.dao.PedidoDAO;
import delivery.dao.ItemPedidoDAO;
import delivery.model.Pedido;
import delivery.model.ItemPedido;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Camada de Serviço (Service) para a entidade Pedido.
 * Responsável por implementar a lógica de negócio e coordenar as operações de múltiplos DAOs.
 */
public class PedidoService {

    private final PedidoDAO pedidoDAO;
    private final ItemPedidoDAO itemPedidoDAO;

    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.itemPedidoDAO = new ItemPedidoDAO();
    }

    /**
     * Cria um novo pedido e seus itens.
     * Em um cenário real, esta operação usaria transações para garantir atomicidade.
     */
    public void criarPedidoCompleto(Pedido pedido, List<ItemPedido> itens) throws SQLException {
        // 1. Insere o Pedido principal
        pedidoDAO.insert(pedido);
        
        // 2. Insere os Itens do Pedido, usando o ID gerado do Pedido
        int idPedido = pedido.getIdPedido();
        for (ItemPedido item : itens) {
            item.setIdPedido(idPedido);
            itemPedidoDAO.insert(item);
        }
        System.out.println("Pedido " + idPedido + " criado com sucesso, incluindo " + itens.size() + " itens.");
    }

    // Métodos de delegação simples para CRUD (para demonstração)

    public Pedido buscarPedidoPorId(int id) throws SQLException {
        return pedidoDAO.findById(id);
    }

    public List<Pedido> buscarTodosPedidos() throws SQLException {
        return pedidoDAO.findAll();
    }

    public void atualizarPedido(Pedido pedido) throws SQLException {
        pedidoDAO.update(pedido);
    }

    public void deletarPedido(int id) throws SQLException {
        // Em um cenário real, a exclusão de itens relacionados viria antes (Integridade Referencial)
        // Para simplificar a demonstração, assumimos que a exclusão em cascata está configurada no DB ou é tratada separadamente.
        pedidoDAO.delete(id);
    }
    
    public List<ItemPedido> buscarItensPorPedido(int idPedido) throws SQLException {
        // Este método seria implementado no ItemPedidoDAO para buscar todos os itens de um pedido específico.
        // Como não implementamos o método específico no DAO, vamos simular uma busca geral e filtrar (não ideal, mas funcional para demonstração)
        // Em um projeto real, ItemPedidoDAO teria um método findByPedidoId(int idPedido).
        return itemPedidoDAO.findAll().stream()
                .filter(item -> item.getIdPedido() == idPedido)
                .collect(Collectors.toList());
    }
}
