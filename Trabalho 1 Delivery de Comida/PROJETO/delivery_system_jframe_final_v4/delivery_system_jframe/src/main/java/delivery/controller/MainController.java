package delivery.controller;

import delivery.dao.ConnectionFactory;
import delivery.dao.StatusPedidoDAO;
import delivery.model.*;
import delivery.service.PedidoService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Classe principal que simula a camada de Controller/Interface.
 * Demonstra a funcionalidade CRUD e o uso das camadas Model, DAO e Service.
 */
public class MainController {

    public static void main(String[] args) {
        // 1. Testar a Conexão
        try {
            ConnectionFactory.main(null);
        } catch (Exception e) {
            System.err.println("\n--- ERRO CRÍTICO: Não foi possível conectar ao banco de dados. ---");
            System.err.println("Verifique se o MySQL está rodando e se as credenciais em ConnectionFactory.java estão corretas.");
            System.err.println("O restante do código não funcionará sem a conexão.");
            return;
        }

        // 2. Inicializar DAOs e Services
        PedidoService pedidoService = new PedidoService();
        StatusPedidoDAO statusPedidoDAO = new StatusPedidoDAO();

        try {
            // 3. Inserir dados iniciais (Restaurante e Cliente) para teste
            // Para simplificar, vamos usar IDs fixos para Cliente e Restaurante,
            // assumindo que eles já foram inseridos no DB.
            // Em um projeto completo, usaríamos ClienteDAO e RestauranteDAO.
            int idClienteTeste = 1;
            int idRestauranteTeste = 1;
            
            // Inserir status padrão se não existirem (para garantir que o ID 1, 2, 3 existam)
            if (statusPedidoDAO.findAll().isEmpty()) {
                statusPedidoDAO.insert(new StatusPedido("em preparo"));
                statusPedidoDAO.insert(new StatusPedido("a caminho"));
                statusPedidoDAO.insert(new StatusPedido("entregue"));
            }
            
            // ID do status "em preparo" (assumindo ID 1)
            int idStatusEmPreparo = 1; 

            // 4. CREATE: Criar um novo Pedido
            System.out.println("\n--- 4. CREATE: Criando Novo Pedido ---");
            // O novo construtor requer o status ENUM (String)
            String statusEnumTeste = "em preparo";
            Pedido novoPedido = new Pedido(idClienteTeste, idRestauranteTeste, statusEnumTeste, idStatusEmPreparo);
            
            List<ItemPedido> itens = Arrays.asList(
                new ItemPedido(0, "Pizza Margherita", 1, new BigDecimal("45.00")),
                new ItemPedido(0, "Coca-Cola 2L", 1, new BigDecimal("10.50"))
            );
            
            pedidoService.criarPedidoCompleto(novoPedido, itens);
            System.out.println("Pedido criado: " + novoPedido);

            // 5. READ: Buscar o Pedido recém-criado
            System.out.println("\n--- 5. READ: Buscando Pedido por ID ---");
            Pedido pedidoBuscado = pedidoService.buscarPedidoPorId(novoPedido.getIdPedido());
            System.out.println("Pedido encontrado: " + pedidoBuscado);
            
            List<ItemPedido> itensBuscados = pedidoService.buscarItensPorPedido(novoPedido.getIdPedido());
            System.out.println("Itens do Pedido:");
            itensBuscados.forEach(System.out::println);

	            // 6. UPDATE: Atualizar o status do Pedido para "a caminho" (assumindo ID 2)
	            System.out.println("\n--- 6. UPDATE: Atualizando Status do Pedido ---");
	            int idStatusACaminho = 2;
	            String statusEnumACaminho = "a caminho";
	            pedidoBuscado.setIdStatus(idStatusACaminho);
	            pedidoBuscado.setStatus(statusEnumACaminho);
            pedidoService.atualizarPedido(pedidoBuscado);
            
            Pedido pedidoAtualizado = pedidoService.buscarPedidoPorId(novoPedido.getIdPedido());
            System.out.println("Pedido atualizado: " + pedidoAtualizado);

            // 7. READ ALL: Listar todos os Pedidos
            System.out.println("\n--- 7. READ ALL: Listando Todos os Pedidos ---");
            List<Pedido> todosPedidos = pedidoService.buscarTodosPedidos();
            todosPedidos.forEach(p -> System.out.println("ID: " + p.getIdPedido() + ", Status: " + p.getIdStatus()));

            // 8. DELETE: Deletar o Pedido (CUIDADO: Requer que ItemPedido seja deletado primeiro ou que haja CASCADE)
            // Para fins de demonstração, vamos pular o DELETE para evitar problemas de integridade referencial
            // se o CASCADE não estiver configurado no DB.
            // System.out.println("\n--- 8. DELETE: Deletando Pedido ---");
            // pedidoService.deletarPedido(novoPedido.getIdPedido());
            // System.out.println("Pedido deletado.");

        } catch (SQLException e) {
            System.err.println("\n--- ERRO DE SQL DURANTE A EXECUÇÃO DO CRUD ---");
            e.printStackTrace();
        }
    }
}
