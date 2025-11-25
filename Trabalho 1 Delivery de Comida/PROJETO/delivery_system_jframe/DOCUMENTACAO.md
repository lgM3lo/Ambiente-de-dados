# Documentação do Backend Java - Sistema de Delivery de Comida

## 1. Funcionamento do Sistema

Este projeto implementa o backend para o Sistema de Delivery de Comida, conforme solicitado no Trabalho Final da disciplina de Ambiente de Dados. O sistema é estruturado em camadas (Model, DAO, Service, Controller) para seguir as boas práticas de organização de código.

### Camadas:
1.  **Model (delivery.model):** Contém as classes de entidade (`Cliente`, `Restaurante`, `Pedido`, `ItemPedido`, `StatusPedido`) que representam as tabelas do banco de dados.
2.  **DAO (delivery.dao):** Contém a `ConnectionFactory` para gerenciar a conexão JDBC e as classes Data Access Object (ex: `ClienteDAO`, `PedidoDAO`) que implementam as operações **CRUD** (Create, Read, Update, Delete) diretamente com o banco de dados.
3.  **Service (delivery.service):** Contém a lógica de negócio (`PedidoService`). Por exemplo, o método `criarPedidoCompleto` coordena a inserção de um `Pedido` e seus `ItemPedido`s, demonstrando a coordenação de múltiplos DAOs.
4.  **Controller (delivery.controller):** A classe `MainController` simula a interface de usuário ou a camada de API, demonstrando como as funcionalidades do `Service` e `DAO` são utilizadas.

### Requisitos Atendidos:
*   **Backend em Java:** Implementação completa em Java.
*   **CRUD:** Todas as classes DAO implementam as funcionalidades mínimas de CRUD.
*   **JDBC e MySQL:** Uso da `ConnectionFactory` com o driver JDBC para MySQL.
*   **Boas Práticas:** Código organizado nos padrões Model, DAO, Service, Controller.
*   **Tratamento de Exceções:** As operações DAO incluem blocos `try-catch` para tratamento de `SQLException`.

## 2. Guia Passo a Passo para Reprodução (Desenvolvedor)

Este guia assume que o desenvolvedor possui o **Java Development Kit (JDK) 17+** e o **Apache Maven** instalados.

### 2.1. Configuração do Banco de Dados

1.  **Instalar MySQL:** Certifique-se de que o servidor MySQL esteja instalado e em execução.
2.  **Executar Script SQL:** Use o script SQL fornecido pela dupla (`TrabalhoSQL.sql` ou a versão final acordada) para criar o banco de dados `Delivery` e todas as tabelas.
3.  **Credenciais:** Se suas credenciais de acesso ao MySQL não forem `root` e `password`, edite o arquivo `delivery/dao/ConnectionFactory.java` e altere as constantes `USER` e `PASSWORD`.

### 2.2. Configuração e Execução do Projeto Java

1.  **Navegar para o Diretório:**
    \`\`\`bash
    cd delivery_java_backend
    \`\`\`
2.  **Instalar Dependências:** O projeto usa Maven. O `pom.xml` já inclui a dependência do `mysql-connector-java`.
    \`\`\`bash
    mvn clean install
    \`\`\`
3.  **Executar o Sistema:** A classe principal para demonstração é `delivery.controller.MainController`.
    \`\`\`bash
    mvn exec:java -Dexec.mainClass="delivery.controller.MainController"
    \`\`\`

### 3. Orientações para Reprodução do Projeto

O projeto é totalmente replicável e auto-contido.

*   **Estrutura de Arquivos:** A estrutura de diretórios segue o padrão Maven, facilitando a importação em qualquer IDE (IntelliJ, Eclipse, VS Code).
*   **Dependências:** A única dependência externa é o driver JDBC do MySQL, gerenciado pelo Maven.
*   **Teste de Conexão:** O `MainController` inicia com um teste de conexão (`ConnectionFactory.main(null)`), garantindo que o problema seja diagnosticado imediatamente se o banco de dados não estiver acessível.
*   **Integridade Referencial:** As operações DAO foram escritas para respeitar a integridade referencial (ex: `PedidoDAO` usa `id_cliente`, `id_restaurante`, `id_status`).

**Observação:** Para o teste completo do CRUD, é necessário que as tabelas `Cliente` e `Restaurante` contenham registros com `id_cliente = 1` e `id_restaurante = 1`, conforme usado no `MainController` para fins de demonstração.

---
*Fim da Documentação.*
