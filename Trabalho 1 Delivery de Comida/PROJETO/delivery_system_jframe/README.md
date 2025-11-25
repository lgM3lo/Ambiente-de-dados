_# Documentação do Sistema de Delivery com Interface Gráfica (JFrame)_

## 1. Visão Geral do Projeto

Este projeto integra um backend Java, estruturado em camadas (Model, DAO, Service, Controller), com um banco de dados MySQL e uma interface gráfica de usuário (GUI) desenvolvida com **Java Swing (JFrame)**. A aplicação fornece uma interface completa para realizar operações **CRUD (Create, Read, Update, Delete)** em todas as entidades do sistema: Clientes, Restaurantes, Status de Pedido e Pedidos.

### Arquitetura e Tecnologias:

- **Backend:** Java 11
- **Banco de Dados:** MySQL
- **Interface Gráfica:** Java Swing (JFrame)
- **Gerenciador de Dependências:** Apache Maven
- **Conexão BD:** JDBC (com `mysql-connector-java`)
- **Estrutura:** Model-View-Controller (MVC) adaptado, com camadas DAO e Service.

### Novas Funcionalidades:

- **Interface Gráfica (Pacote `delivery.view`):**
  - `MainFrame`: Tela principal com menu de navegação.
  - `ClienteFrame`: Janela para CRUD de Clientes.
  - `RestauranteFrame`: Janela para CRUD de Restaurantes.
  - `StatusPedidoFrame`: Janela para CRUD de Status de Pedido.
  - `PedidoFrame`: Janela para CRUD de Pedidos, com ComboBoxes para seleção de chaves estrangeiras.
- **Configuração de Banco de Dados (Pacote `delivery.config`):**
  - `DatabaseConfig`: Permite que o usuário configure as credenciais do banco de dados (URL, usuário, senha) através de uma janela gráfica. As configurações são salvas no arquivo `database.properties`.

---

## 2. Guia de Instalação e Execução

Siga os passos abaixo para configurar e executar o sistema em seu ambiente de desenvolvimento.

### Pré-requisitos:

- **Java Development Kit (JDK) 11** ou superior.
- **Apache Maven**.
- **Servidor MySQL** instalado e em execução.

### Passo 1: Configuração do Banco de Dados

1.  **Crie o Banco de Dados:** Execute o script `TrabalhoSQL1.sql` (incluído no projeto) em seu cliente MySQL. Este script criará o banco de dados `Delivery` e todas as tabelas necessárias.

    ```sql
    -- Exemplo de como executar o script via linha de comando:
    mysql -u seu_usuario -p < TrabalhoSQL1.sql
    ```

2.  **Insira Dados Iniciais (Recomendado):** Para testar a funcionalidade de criação de pedidos, é recomendável que as tabelas `Cliente`, `Restaurante` e `StatusPedido` já contenham alguns registros. Você pode inseri-los manualmente ou usar as telas de cadastro da aplicação.

### Passo 2: Compilação e Execução do Projeto

1.  **Navegue até o Diretório do Projeto:**

    ```bash
    cd delivery_system_jframe
    ```

2.  **Compile e Instale as Dependências:** Use o Maven para compilar o projeto e baixar a dependência do conector MySQL.

    ```bash
    mvn clean install
    ```

3.  **Execute a Aplicação:** A classe principal que inicia a interface gráfica é `delivery.view.MainFrame`.

    ```bash
    mvn exec:java -Dexec.mainClass="delivery.view.MainFrame"
    ```

### Passo 3: Configuração da Conexão (Primeira Execução)

1.  Ao iniciar a aplicação pela primeira vez, ela tentará se conectar ao banco de dados com as credenciais padrão.
2.  Se a conexão falhar, uma caixa de diálogo de erro aparecerá, perguntando se você deseja **configurar as credenciais**. Clique em **"Sim"**.
3.  Na janela "Configuração do Banco de Dados", insira a **URL, usuário e senha** corretos para o seu servidor MySQL e clique em **"OK"**.
4.  As credenciais serão salvas no arquivo `database.properties`. **Reinicie a aplicação** para que as novas configurações sejam aplicadas.

---

## 3. Estrutura do Projeto

```
.delivery_system_jframe/
├── src/main/java/delivery/
│   ├── config/             # (NOVO) Configuração de BD
│   │   └── DatabaseConfig.java
│   ├── controller/
│   │   └── MainController.java # (Original, para referência)
│   ├── dao/                # Operações de acesso a dados
│   │   ├── ClienteDAO.java
│   │   ├── ConnectionFactory.java # (Atualizado)
│   │   └── ...
│   ├── model/              # Classes de entidade
│   │   ├── Cliente.java
│   │   └── ...
│   ├── service/            # Lógica de negócio
│   │   └── PedidoService.java
│   └── view/               # (NOVO) Interface Gráfica (JFrame)
│       ├── ClienteFrame.java
│       ├── MainFrame.java
│       ├── PedidoFrame.java
│       ├── RestauranteFrame.java
│       └── StatusPedidoFrame.java
├── pom.xml                 # Configurações do Maven
├── TrabalhoSQL1.sql        # Script de criação do banco de dados
└── README.md               # Esta documentação
```

**Observação:** O `MainController` original foi mantido para referência, mas a execução principal agora é feita pela `MainFrame`.

---
*Fim da Documentação.*
