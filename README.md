# 📚 Sistema de Biblioteca - Versão Refatorada

Projeto refatorado do sistema desktop de biblioteca, preparado para ser reaproveitado em um sistema web.

## 🎯 Objetivo

Separar as responsabilidades do código original, aplicando o princípio **SRP (Single Responsibility Principle)** do SOLID, para que as regras de negócio possam ser reutilizadas independentemente da interface gráfica.

## 📁 Estrutura do Projeto

BibliotecaWeb/
├── biblioteca.domain/       # Entidades (Livro, Usuario, Emprestimo)
├── biblioteca.service/      # Regras de negócio (validações, cálculos)
├── biblioteca.repository/   # Acesso ao banco de dados (CRUD)
├── biblioteca.infrastructure/ # Conexão com o banco
└── main/                    # Testes integrados (Testes.java)

## 🛠️ Tecnologias

- Java 17+
- MySQL 8.0
- JDBC
- NetBeans IDE

## ⚙️ Funcionalidades

- Cadastro, consulta, atualização e exclusão de livros
- Cadastro, consulta, atualização e exclusão de usuários
- Controle de empréstimos (máximo 3 por usuário)
- Renovação de empréstimos
- Devolução com cálculo de multa (R$ 2,00 por dia de atraso)
- Validação de ISBN (ignora hífens)
- Validação de e-mail (formato e duplicidade)

## 🚀 Como executar

### 1. Clone o repositório

git clone https://github.com/Loadingzs/sistema-biblioteca-web.git

### 2. Configure o banco de dados

Crie um banco chamado sistema_biblioteca e execute o script abaixo:

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

### 3. Configure a conexão

Edite o arquivo biblioteca/infrastructure/ConnectionFactory.java com seus dados:

private static final String URL = "jdbc:mysql://localhost:3306/sistema_biblioteca";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";

### 4. Execute os testes

No NetBeans, execute a classe main/Testes.java.

Saída esperada:

✅ Conexão estabelecida com sucesso!
✅ Livros cadastrados: 5
✅ Usuários cadastrados: 4
✅ Empréstimos realizados
✅ Devolução registrada
✅ BUILD SUCCESSFUL

## 📊 O que foi refatorado

| Problema original | Solução aplicada |
|-------------------|------------------|
| Classes duplicadas (model e modelo) | Unificadas em domain |
| IDs como String em alguns lugares | Padronizados para int |
| Validação de ISBN com LIKE (falha) | Normalização (remove hífens) + busca exata |
| Regras de negócio nas telas Swing | Movidas para service |
| Status "DEVOLVIDO" incompatível | Alterado para "FINALIZADO" |

## 🔗 Links

- Versão refatorada (web-ready): https://github.com/Loadingzs/sistema-biblioteca-web
- Versão original (desktop): https://github.com/Loadingzs/sistema-biblioteca

## 📝 Licença

MIT License - Copyright (c) 2026 Maykon

---

Desenvolvido como parte do Projeto Integrador - SENAC
