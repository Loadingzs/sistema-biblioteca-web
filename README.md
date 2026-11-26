# 🛠️ Sistema de Biblioteca — Núcleo refatorado

Refatoração do sistema de biblioteca para separar **domínio, regras de negócio e persistência**. O objetivo é reutilizar a lógica independentemente da interface.

Apesar do nome do repositório, esta versão não inclui servidor HTTP nem páginas web. O ponto de entrada é uma demonstração de integração executada no console.

## Funcionalidades

- Cadastro, consulta, atualização e exclusão de livros e usuários.
- Busca de livros por título, autor e ISBN.
- Verificação de campos obrigatórios e duplicidade de ISBN, ignorando hífens e espaços na verificação de cadastro.
- Validação de formato e duplicidade de e-mail.
- Empréstimo com controle de disponibilidade e limite de três registros ativos ou atrasados por usuário.
- Renovação e registro de devolução.
- Cálculo de multa de **R$ 2,00 por dia de atraso** na devolução.

## Arquitetura

```text
src/
├── biblioteca/
│   ├── domain/           # Livro, Usuario e Emprestimo
│   ├── dto/              # Estruturas de transferência
│   ├── service/          # Validações e regras de negócio
│   ├── repository/       # Operações SQL via JDBC
│   └── infrasctructure/  # Conexão com o MySQL
└── main/Testes.java      # Demonstração de integração

test/biblioteca/service/EmprestimoServiceTest.java
```

O nome `infrasctructure` acima reproduz a grafia atual do pacote no código.

## Tecnologias e requisitos

- **Java 24**, conforme `javac.source` e `javac.target` em `nbproject/project.properties`.
- MySQL e MySQL Connector/J.
- NetBeans; Apache Ant para compilação por terminal.
- JUnit 4 e Hamcrest no classpath de testes para executar a suíte existente.

## Como executar

### 1. Clonar e abrir

```bash
git clone https://github.com/Loadingzs/sistema-biblioteca-web.git
cd sistema-biblioteca-web
```

Abra o projeto no NetBeans e selecione um JDK compatível com Java 24.

### 2. Preparar o banco

Em uma base de desenvolvimento nova, execute:

```sql
CREATE DATABASE IF NOT EXISTS sistema_biblioteca CHARACTER SET utf8mb4;
USE sistema_biblioteca;

CREATE TABLE livros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    isbn VARCHAR(50) UNIQUE,
    disponivel BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE emprestimos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    id_livro INT NOT NULL,
    id_usuario INT NOT NULL,
    data_emprestimo TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_devolucao_prevista DATE NOT NULL,
    data_devolucao_real DATE,
    status ENUM('ATIVO', 'FINALIZADO', 'ATRASADO') DEFAULT 'ATIVO',
    FOREIGN KEY (id_livro) REFERENCES livros(id),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);
```

### 3. Configurar o acesso

Em `src/biblioteca/infrasctructure/ConnectionFactory.java`, ajuste `URL`, `USER` e `PASSWORD`.

Resolva também a referência ao MySQL Connector/J nas propriedades do projeto: o caminho salvo em `nbproject/project.properties` depende da máquina de desenvolvimento original.

### 4. Executar a demonstração

Execute `main.Testes` pelo NetBeans. Essa classe **cria e altera registros no banco**, incluindo livros, usuários e empréstimos de exemplo. Utilize uma base de desenvolvimento; o resultado das execuções seguintes pode mudar por causa dos registros já existentes.

Para compilar com Ant, depois de resolver as dependências:

```bash
ant clean jar
```

## Testes

`test/biblioteca/service/EmprestimoServiceTest.java` contém três testes JUnit 4 para multa:

| Cenário | Valor esperado |
| --- | --- |
| Devolução no prazo | R$ 0,00 |
| Atraso de três dias | R$ 6,00 |
| Atraso de dez dias | R$ 20,00 |

Adicione JUnit 4 e Hamcrest às bibliotecas de testes no NetBeans e execute a classe de teste. As referências ainda não estão declaradas no classpath versionado. Esses testes de cálculo são diferentes da demonstração `main.Testes`, que acessa o MySQL.

`RegistrarDevolucaoTest.java` acrescenta três testes do fluxo de devolução: multa informada após
registrar a data real, devolução antecipada sem multa e falha de persistência sem anunciar sucesso
ou liberar o livro. Esses testes usam repositórios falsos em memória e não conectam ao MySQL.

## Relação com os outros projetos

| Etapa | Repositório |
| --- | --- |
| Interface desktop original | [sistema-biblioteca](https://github.com/Loadingzs/sistema-biblioteca) |
| Núcleo refatorado | Este repositório |
| API com Spring Boot | [biblioteca-api](https://github.com/Loadingzs/biblioteca-api) |

## Próximos passos

Padronizar dependências, ampliar os testes de validação, adicionar transações às operações de empréstimo e externalizar as credenciais de conexão.

## Licença

O README original declara licença MIT; o arquivo `LICENSE` ainda não está incluído no repositório.

## Autor

**Maykon Dias** · [GitHub](https://github.com/Loadingzs) · [LinkedIn](https://www.linkedin.com/in/maykondias/)
