# 📦 Sistema de Delivery de Comida

Este projeto consiste no modelo de banco de dados para um sistema de Delivery de comida, permitindo gerenciar clientes, restaurantes, pedidos e itens relacionados aos pedidos.

---

## 🧠 Objetivo

Modelar um banco de dados capaz de:

* Cadastrar clientes do sistema;
* Registrar restaurantes parceiros;
* Controlar pedidos realizados;
* Armazenar itens dentro de cada pedido;
* Acompanhar o status do pedido (em preparo, a caminho, entregue).

---

## 🗃 Estrutura do Banco de Dados

O banco é composto pelas seguintes tabelas:

### **Cliente**

Armazena informações dos clientes.

* `id_cliente` (PK)
* `nome`
* `telefone`
* `endereco`

### **Restaurante**

Guarda os dados dos restaurantes parceiros.

* `id_restaurante` (PK)
* `nome`
* `tipo_cozinha`
* `telefone`

### **StatusPedido**

Lista de possíveis status para os pedidos.

* `id_status` (PK)
* `descricao`

### **Pedido**

Representa um pedido feito por um cliente para um restaurante.

* `id_pedido` (PK)
* `id_cliente` (FK)
* `id_restaurante` (FK)
* `data_hora`
* `status` (ENUM)
* `id_status` (FK)

### **ItemPedido**

Itens pertencentes a um pedido.

* `id_item` (PK)
* `id_pedido` (FK)
* `descricao`
* `quantidade`
* `preco`

---

## ⚙ Funcionamento do Sistema

1. Um cliente e um restaurante são cadastrados no sistema.
2. O cliente realiza um pedido, que é vinculado ao restaurante escolhido.
3. O pedido inicia com um status inicial (ex.: *em preparo*).
4. Um ou mais itens são adicionados ao pedido.
5. Durante o processo, o status pode ser atualizado para:

   * em preparo
   * a caminho
   * entregue
6. Após entregue, o ciclo do pedido é encerrado.

---

## 🚀 Como Utilizar

1. Execute o script SQL para criar o banco de dados e tabelas.
2. Cadastre:

   * Clientes
   * Restaurantes
   * Status possíveis do pedido
3. Insira novos pedidos e respectivos itens.
4. Atualize o status do pedido conforme ele avança.
5. Consulte:

   * Pedidos por cliente
   * Pedidos por restaurante
   * Itens de cada pedido
   * Situação atual da entrega

---

## 🧾 Script SQL

O script para criação completa do banco de dados encontra-se no arquivo do projeto.

---

## 📌 Possíveis Melhorias

* Adição de tabela para entregadores;
* Histórico completo de atualização de status;
* Métodos de pagamento;
* Cálculo automático do valor total do pedido.

---

## 🏁 Conclusão

Este modelo fornece a estrutura essencial para um sistema de delivery simples, permitindo o cadastro de clientes, restaurantes, pedidos e acompanhamento de status e itens.

Sinta-se à vontade para ampliar conforme necessário!
