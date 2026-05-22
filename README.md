markdown
# ⊕ BIBLIOTECA_WEB // FIELD STATION COMPILATION
`<Some truths are embedded in code>`

> node 01 :: project dossier [DECRYPTED]
> status :: operational [OK]
> signal.origin :: https://github.com/Loadingzs/sistema-biblioteca-web

---

## ▸ FIELD STATION // OVERVIEW

> processing manifest...
⊕ codename :: BIBLIOTECA_WEB
⊕ lineage :: refactored from desktop legacy [sistema-biblioteca]
⊕ purpose :: decoupled business logic layer for library management systems
⊕ target :: future web integration [REST API compatible]
⊕ status :: READY FOR DEPLOYMENT

text

Sistema de gerenciamento de biblioteca completamente refatorado, aplicando princípios **SOLID (SRP)** e separação estrita de responsabilidades. O código-fonte original, vinculado a interfaces Java Swing, foi decompilado e reorganizado em camadas isoladas: **Domain**, **Service**, **Repository** e **Infrastructure**.

A camada de regras de negócio está agora completamente desacoplada da persistência e da interface, permitindo reaproveitamento imediato em sistemas web, microsserviços ou APIs REST.

> status scan :: operational [OK]
> code smells eliminated :: 7 [DECRYPTED]
> duplicate classes merged :: 4 [OK]
> test coverage :: integration [Testes.java]

---

## ▸ SYSTEM FILE :: ARCHITECTURE & TECH STACK

> carrier nodes detected...

| Node | Component | Version |
|------|-----------|---------|
| ⊕ core.node | **Java** | JDK 17+ |
| ⊕ database.carrier | **MySQL** | 8.0+ |
| ⊕ persistence.bridge | **JDBC** | mysql-connector-j-9.5.0 |
| ⊕ legacy.interface | **Java Swing** (refactored out) | - |
| ⊕ version.control | **Git** + **GitHub** | - |

### ▸ LAYERED ARCHITECTURE [DECRYPTED]
BibliotecaWeb/
│
├── biblioteca.domain/ // data entities (getters/setters only)
│ ├── Livro.java
│ ├── Usuario.java
│ └── Emprestimo.java
│
├── biblioteca.service/ // business rules, validations, calculations
│ ├── LivroService.java
│ ├── UsuarioService.java
│ └── EmprestimoService.java
│
├── biblioteca.repository/ // CRUD operations, database persistence
│ ├── LivroRepository.java
│ ├── UsuarioRepository.java
│ └── EmprestimoRepository.java
│
├── biblioteca.infrastructure/ // connection factory, database config
│ └── ConnectionFactory.java
│
├── biblioteca.dto/ // data transfer objects (reserved for web)
│
└── main/
└── Testes.java // integration tests [BUILD SUCCESSFUL]

text

---

## ▸ CARRIER SIGNAL :: INSTALLATION & ENVIRONMENT

> initializing environment setup...

```bash
# ▸ CLONE REPOSITORY
git clone https://github.com/Loadingzs/sistema-biblioteca-web.git
cd sistema-biblioteca-web
▸ PREREQUISITES VERIFICATION
text
⊕ checking JDK version      :: [REQUIRED 17+]
⊕ checking MySQL service    :: [REQUIRED 8.0+]
⊕ checking driver library   :: mysql-connector-j-9.5.0.jar [FOUND]
▸ DATABASE CONFIGURATION
Execute the following SQL schema:

sql
CREATE DATABASE sistema_biblioteca CHARACTER SET utf8mb4;
USE sistema_biblioteca;

-- TABELA: livros
CREATE TABLE livros (
    id INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(200) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    isbn VARCHAR(50) UNIQUE,
    disponivel BOOLEAN DEFAULT TRUE,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA: usuarios
CREATE TABLE usuarios (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- TABELA: emprestimos
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
▸ CONNECTION PARAMETERS
Edit biblioteca/infrastructure/ConnectionFactory.java:

java
private static final String URL = "jdbc:mysql://localhost:3306/sistema_biblioteca";
private static final String USER = "root";
private static final String PASSWORD = "your_password";
▸ NODE OPERATIONS :: USAGE & COMMANDS
verifying deployment status :: execution parameters

▸ COMPILE & RUN (NetBeans)
text
1. Open project in NetBeans IDE
2. Add library: lib/mysql-connector-j-9.5.0.jar
3. Run → main/Testes.java
4. Verify BUILD SUCCESSFUL
▸ COMPILE & RUN (Terminal)
bash
# Compile all sources
javac -d bin -cp "lib/*" src/biblioteca/**/*.java src/main/*.java

# Run integration tests
java -cp "bin;lib/*" main.Testes
▸ EXPECTED OUTPUT [OK]
text
╔════════════════════════════════════════════════════════════════╗
║                 BIBLIOTECA WEB - TESTE INTEGRADO               ║
╚════════════════════════════════════════════════════════════════╝

✅ Conexão com banco estabelecida com sucesso!
✅ Livros cadastrados: 5
✅ Usuários cadastrados: 4
✅ Empréstimos realizados (limite 3 por usuário)
✅ Devolução registrada com sucesso
✅ Renovação de empréstimo funcionando
✅ BUILD SUCCESSFUL (total time: 1 second)
▸ BUSINESS RULES [DECRYPTED]
core logic extracted from legacy Swing views...

Rule	Implementation	Status
⊕ ISBN validation	Normalization (ignores hyphens/spaces) via normalizarIsbn()	[OK]
⊕ Email uniqueness	usuarioRepository.emailExiste()	[OK]
⊕ Email format validation	Regex pattern ^[A-Za-z0-9+_.-]+@(.+)$	[OK]
⊕ Max active loans	3 per user (usuarioPodePegarEmprestimo())	[OK]
⊕ Late fee calculation	R$ 2.00/day via calcularMulta()	[OK]
⊕ Loan renewal	+5 days max extension	[OK]
⊕ Availability control	atualizarDisponibilidade() on loan/return	[OK]
▸ REFACTORING DOSSIER // SOLID PRINCIPLES
applying single responsibility principle [SRP]...

Class	Original Responsibility	Refactored Responsibility
Livro.java	Mixed data + validation	Only data (getters/setters)
Usuario.java	Mixed data + validation	Only data (getters/setters)
Emprestimo.java	Mixed data + validation	Only data (getters/setters)
LivroService.java	Does not exist	Business rules, validations
UsuarioService.java	Does not exist	Business rules, validations
EmprestimoService.java	Does not exist	Business rules, validations
LivroRepository.java	DAO with validation	Pure CRUD + persistence
UsuarioRepository.java	DAO with validation	Pure CRUD + persistence
EmprestimoRepository.java	Does not exist	Pure CRUD + persistence
▸ CODE SMELLS ELIMINATED
text
✕ Duplicate classes (model + modelo)      → merged into domain/
✕ Inconsistent ID types (String vs int)   → standardized to int
✕ ISBN validation with SQL LIKE           → normalized exact match
✕ Business logic inside Swing views       → extracted to Service layer
✕ Hardcoded status "DEVOLVIDO"            → corrected to "FINALIZADO"
✕ Missing null validations                → added defensive checks
✕ Missing toString() methods              → added for debugging
▸ ARTIFACTS // CONTRIBUTION DOSSIER
handshake protocol for external nodes:

text
▸ fork signal          :: clone repository
▸ branch creation      :: git checkout -b patch/fix-name
▸ commit payload       :: git commit -m "fix: description"
▸ transmit data        :: git push origin patch/fix-name
▸ pull request         :: open PR to main branch
▸ COMMIT CONVENTION
Tag	Purpose	Example
feat:	New feature	feat: add book search by author
fix:	Bug fix	fix: ISBN validation ignoring hyphens
refactor:	Code restructuring	refactor: extract business logic to service
docs:	Documentation	docs: update README with installation steps
test:	Testing	test: add integration tests for loans
▸ REPOSITORY MAP
original vs refactored...

text
┌─────────────────────────────────────────────────────────────────┐
│                    LEGACY DESKTOP (original)                    │
├─────────────────────────────────────────────────────────────────┤
│  sistema-biblioteca/                                            │
│  ├── biblioteca.model/     (Livro, Usuario, Emprestimo)        │
│  ├── modelo/               (duplicate: Livro, Usuario) ⚠️       │
│  ├── biblioteca.view/      (Swing interfaces)                  │
│  ├── dao/                  (LivrosDAO, UsuariosDAO)            │
│  └── conexao/              (ConnectionFactory)                 │
└─────────────────────────────────────────────────────────────────┘
                              │
                              ▼ REFACTORED
┌─────────────────────────────────────────────────────────────────┐
│                    WEB-READY (refactored)                       │
├─────────────────────────────────────────────────────────────────┤
│  BibliotecaWeb/                                                 │
│  ├── biblioteca.domain/     (merged + standardized) ✅          │
│  ├── biblioteca.service/    (extracted from views) ✅           │
│  ├── biblioteca.repository/ (renamed + pure CRUD) ✅            │
│  ├── biblioteca.infrastructure/ (renamed) ✅                    │
│  └── main/Testes.java       (integration tests) ✅              │
└─────────────────────────────────────────────────────────────────┘
▸ ENCRYPTED LOGS // LICENSE
text
⊕ Effective          :: 2026
⊕ Author             :: Maykon (Loadingzs)
⊕ Organization       :: SENAC / Projeto Integrador
⊕ Distribution       :: MIT License
⊕ Repository         :: https://github.com/Loadingzs/sistema-biblioteca-web
⊕ Original Desktop   :: https://github.com/Loadingzs/sistema-biblioteca
license
MIT License

Copyright (c) 2026 Maykon

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
▸ SIGNAL TERMINATED
text
> session closed [OK]
> data transmitted :: 78 objects
> code smells eliminated :: 7
> repository online :: https://github.com/Loadingzs/sistema-biblioteca-web
> status :: READY FOR WEB INTEGRATION
</end_of_transmission>

text

---

## Como adicionar o README no GitHub:

### Opção 1: Direto pelo site do GitHub

1. Acesse: https://github.com/Loadingzs/sistema-biblioteca-web
2. Clique em **"Add file"** → **"Create new file"**
3. Nome do arquivo: `README.md`
4. Copie e cole todo o conteúdo acima
5. Role para baixo e clique em **"Commit new file"**

### Opção 2: Pelo NetBeans

1. No NetBeans, clique com o botão direito no projeto `BibliotecaWeb`
2. **New** → **Other...** → **Other** → **Markdown File**
3. Nome: `README`
4. Copie e cole o conteúdo
5. Salve o arquivo
6. Depois faça commit e push:

```bash
git add README.md
git commit -m "docs: add immersive README with terminal aesthetic"
git push origin main
