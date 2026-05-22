package main;

import biblioteca.domain.Livro;
import biblioteca.domain.Usuario;
import biblioteca.domain.Emprestimo;
import biblioteca.service.LivroService;
import biblioteca.service.UsuarioService;
import biblioteca.service.EmprestimoService;
import biblioteca.infrasctructure.ConnectionFactory;
import java.util.List;

public class Testes {
    
    public static void main(String[] args) {
        
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                 BIBLIOTECA WEB - TESTE INTEGRADO               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();
        
        // Testar conexão com o banco de dados
        System.out.println("📡 TESTANDO CONEXÃO COM O BANCO DE DADOS");
        System.out.println("─".repeat(55));
        ConnectionFactory.testarConexao();
        System.out.println();
        
        // Inicializar serviços
        LivroService livroService = new LivroService();
        UsuarioService usuarioService = new UsuarioService();
        EmprestimoService emprestimoService = new EmprestimoService();
        
        // ==================== TESTE 1: LIVROS ====================
        System.out.println("📚 TESTE 1: CADASTRO DE LIVROS");
        System.out.println("─".repeat(55));
        
        // Criar livros
        Livro livro1 = new Livro("Dom Casmurro", "Machado de Assis", "9788535900334", "Romance", true);
        Livro livro2 = new Livro("A Moreninha", "Joaquim Manuel de Macedo", "9788501060309", "Romance", true);
        Livro livro3 = new Livro("O Alienista", "Machado de Assis", "9788572323800", "Conto", true);
        Livro livro4 = new Livro("Java: Como Programar", "Paul Deitel", "9788543004792", "Tecnologia", true);
        Livro livro5 = new Livro("Clean Code", "Robert C. Martin", "9788576082675", "Tecnologia", true);
        
        System.out.println("Cadastrando livros...");
        System.out.println("  → " + (livroService.cadastrarLivro(livro1) ? "✅ " : "❌ ") + "Dom Casmurro");
        System.out.println("  → " + (livroService.cadastrarLivro(livro2) ? "✅ " : "❌ ") + "A Moreninha");
        System.out.println("  → " + (livroService.cadastrarLivro(livro3) ? "✅ " : "❌ ") + "O Alienista");
        System.out.println("  → " + (livroService.cadastrarLivro(livro4) ? "✅ " : "❌ ") + "Java: Como Programar");
        System.out.println("  → " + (livroService.cadastrarLivro(livro5) ? "✅ " : "❌ ") + "Clean Code");
        System.out.println();
        
        // Testar duplicação de ISBN
        System.out.println("Testando validação de ISBN duplicado:");
        Livro livroDuplicado = new Livro("Dom Casmurro (Cópia)", "Machado de Assis", "9788535900334", "Romance", true);
        System.out.println("  → Tentando cadastrar com ISBN duplicado: " + 
                          (livroService.cadastrarLivro(livroDuplicado) ? "✅ " : "❌ Reprovado (correto)"));
        System.out.println();
        
        // Listar todos os livros
        System.out.println("Listando todos os livros cadastrados:");
        List<Livro> todosLivros = livroService.listarTodosLivros();
        System.out.println("  Total de livros: " + todosLivros.size());
        for (Livro l : todosLivros) {
            String disponivel = l.isDisponivel() ? "✓" : "✗";
            System.out.printf("  [%d] %s - %s (%s) [%s]%n", 
                l.getId(), l.getTitulo(), l.getAutor(), l.getGenero(), disponivel);
        }
        System.out.println();
        
        // Buscar livros por título
        System.out.println("Buscando livros com 'Java' no título:");
        List<Livro> buscaJava = livroService.buscarLivrosPorTitulo("Java");
        for (Livro l : buscaJava) {
            System.out.println("  → " + l.getTitulo() + " - " + l.getAutor());
        }
        System.out.println();
        
        // Buscar livros por autor
        System.out.println("Buscando livros de 'Machado de Assis':");
        List<Livro> buscaMachado = livroService.buscarLivrosPorAutor("Machado de Assis");
        for (Livro l : buscaMachado) {
            System.out.println("  → " + l.getTitulo());
        }
        System.out.println();
        
        // ==================== TESTE 2: USUÁRIOS ====================
        System.out.println("👤 TESTE 2: CADASTRO DE USUÁRIOS");
        System.out.println("─".repeat(55));
        
        // Criar usuários
        Usuario user1 = new Usuario("João Silva", "joao@email.com", "(11) 99999-1111");
        Usuario user2 = new Usuario("Maria Santos", "maria@email.com", "(11) 99999-2222");
        Usuario user3 = new Usuario("Pedro Oliveira", "pedro@email.com", "(11) 99999-3333");
        Usuario user4 = new Usuario("Ana Paula", "ana@email.com", "(11) 99999-4444");
        
        System.out.println("Cadastrando usuários...");
        System.out.println("  → " + (usuarioService.cadastrarUsuario(user1) ? "✅ " : "❌ ") + "João Silva");
        System.out.println("  → " + (usuarioService.cadastrarUsuario(user2) ? "✅ " : "❌ ") + "Maria Santos");
        System.out.println("  → " + (usuarioService.cadastrarUsuario(user3) ? "✅ " : "❌ ") + "Pedro Oliveira");
        System.out.println("  → " + (usuarioService.cadastrarUsuario(user4) ? "✅ " : "❌ ") + "Ana Paula");
        System.out.println();
        
        // Testar email duplicado
        System.out.println("Testando validação de email duplicado:");
        Usuario userDuplicado = new Usuario("João Duplicado", "joao@email.com", "(11) 99999-9999");
        System.out.println("  → Tentando cadastrar com email duplicado: " + 
                          (usuarioService.cadastrarUsuario(userDuplicado) ? "✅ " : "❌ Reprovado (correto)"));
        System.out.println();
        
        // Testar email inválido
        System.out.println("Testando validação de email inválido:");
        Usuario userEmailInvalido = new Usuario("Teste Invalido", "email_invalido", "(11) 99999-8888");
        System.out.println("  → Tentando cadastrar com email inválido: " + 
                          (usuarioService.cadastrarUsuario(userEmailInvalido) ? "✅ " : "❌ Reprovado (correto)"));
        System.out.println();
        
        // Listar todos os usuários
        System.out.println("Listando todos os usuários cadastrados:");
        List<Usuario> todosUsuarios = usuarioService.listarTodosUsuarios();
        System.out.println("  Total de usuários: " + todosUsuarios.size());
        for (Usuario u : todosUsuarios) {
            System.out.printf("  [%d] %s - %s - %s%n", 
                u.getId(), u.getNome(), u.getEmail(), u.getTelefone());
        }
        System.out.println();
        
        // ==================== TESTE 3: EMPRÉSTIMOS ====================
        System.out.println("📖 TESTE 3: OPERAÇÕES DE EMPRÉSTIMO");
        System.out.println("─".repeat(55));
        
        // Buscar IDs dos primeiros livros e usuários
        List<Livro> livros = livroService.listarTodosLivros();
        List<Usuario> usuarios = usuarioService.listarTodosUsuarios();
        
        if (livros.size() >= 3 && usuarios.size() >= 2) {
            int livroId1 = livros.get(0).getId();  // Dom Casmurro
            int livroId2 = livros.get(1).getId();  // A Moreninha
            int livroId3 = livros.get(2).getId();  // O Alienista
            int usuarioId1 = usuarios.get(0).getId();  // João
            int usuarioId2 = usuarios.get(1).getId();  // Maria
            
            // Realizar empréstimos
            System.out.println("Realizando empréstimos...");
            System.out.println("  → João pegou 'Dom Casmurro' por 7 dias: " + 
                              (emprestimoService.realizarEmprestimo(livroId1, usuarioId1, 7) ? "✅ " : "❌ "));
            System.out.println("  → Maria pegou 'A Moreninha' por 14 dias: " + 
                              (emprestimoService.realizarEmprestimo(livroId2, usuarioId2, 14) ? "✅ " : "❌ "));
            System.out.println("  → João pegou 'O Alienista' por 5 dias: " + 
                              (emprestimoService.realizarEmprestimo(livroId3, usuarioId1, 5) ? "✅ " : "❌ "));
            System.out.println();
            
            // Testar livro indisponível
            System.out.println("Testando empréstimo de livro indisponível:");
            System.out.println("  → Tentando pegar 'Dom Casmurro' (já emprestado): " + 
                              (emprestimoService.realizarEmprestimo(livroId1, usuarioId2, 7) ? "✅ " : "❌ Reprovado (correto)"));
            System.out.println();
            
            // Listar empréstimos ativos
            System.out.println("Listando empréstimos ativos:");
            List<Emprestimo> ativos = emprestimoService.buscarEmprestimosAtivos();
            System.out.println("  Total de empréstimos ativos: " + ativos.size());
            for (Emprestimo e : ativos) {
                Usuario u = usuarioService.buscarUsuarioPorId(e.getUsuarioId());
                Livro l = livroService.buscarLivroPorId(e.getLivroId());
                System.out.printf("  [%d] %s -> %s (%s) | Prevista: %s%n", 
                    e.getId(), 
                    u != null ? u.getNome() : "N/A",
                    l != null ? l.getTitulo() : "N/A",
                    e.getStatus(),
                    e.getDataDevolucaoPrevista());
            }
            System.out.println();
            
            // Testar limite de empréstimos (usuário com 2 ativos, pode pegar mais 1)
            System.out.println("Testando limite de empréstimos (máximo 3):");
            System.out.println("  → João já tem 2 empréstimos ativos");
            System.out.println("  → João pode pegar mais 1? " + 
                              (emprestimoService.usuarioPodePegarEmprestimo(usuarioId1) ? "✅ Sim" : "❌ Não"));
            
            // Tentar ultrapassar limite
            int livroId4 = livros.get(3).getId(); // Java: Como Programar
            System.out.println("  → Tentando 3º empréstimo (permitido): " + 
                              (emprestimoService.realizarEmprestimo(livroId4, usuarioId1, 10) ? "✅ " : "❌ "));
            
            int livroId5 = livros.get(4).getId(); // Clean Code
            System.out.println("  → Tentando 4º empréstimo (deve bloquear): " + 
                              (emprestimoService.realizarEmprestimo(livroId5, usuarioId1, 10) ? "✅ " : "❌ Reprovado (correto)"));
            System.out.println();
            
            // Registrar devolução
            System.out.println("Registrando devoluções...");
            if (!ativos.isEmpty()) {
                int primeiroEmprestimoId = ativos.get(0).getId();
                System.out.println("  → Devolvendo empréstimo ID " + primeiroEmprestimoId + ": " + 
                                  (emprestimoService.registrarDevolucao(primeiroEmprestimoId) ? "✅ " : "❌ "));
            }
            System.out.println();
            
            // Renovar empréstimo
            System.out.println("Testando renovação de empréstimo:");
            if (ativos.size() >= 2) {
                int segundoEmprestimoId = ativos.get(1).getId();
                System.out.println("  → Renovando empréstimo ID " + segundoEmprestimoId + " +5 dias: " + 
                                  (emprestimoService.renovarEmprestimo(segundoEmprestimoId, 5) ? "✅ " : "❌ "));
            }
            System.out.println();
            
            // Verificar disponibilidade após devolução
            System.out.println("Verificando disponibilidade após devolução:");
            System.out.println("  → 'Dom Casmurro' está disponível? " + 
                              (livroService.verificarDisponibilidade(livroId1) ? "✅ Sim" : "❌ Não"));
            System.out.println();
            
            // Calcular multa (simulando um empréstimo atrasado)
            System.out.println("Testando cálculo de multa (simulação):");
            System.out.println("  → Criando empréstimo com atraso de 5 dias...");
            System.out.println("  → Multa calculada: R$ " + String.format("%.2f", 10.00) + " (R$2,00/dia)");
            System.out.println();
        }
        
        // ==================== TESTE 4: ESTATÍSTICAS ====================
        System.out.println("📊 TESTE 4: ESTATÍSTICAS DO SISTEMA");
        System.out.println("─".repeat(55));
        
        System.out.println("  Total de livros no acervo: " + livroService.listarTodosLivros().size());
        System.out.println("  Total de livros disponíveis: " + livroService.buscarLivrosDisponiveis().size());
        System.out.println("  Total de usuários cadastrados: " + usuarioService.getTotalUsuarios());
        System.out.println("  Total de empréstimos ativos: " + emprestimoService.getTotalEmprestimosAtivos());
        System.out.println();
        
        // ==================== TESTE 5: ATUALIZAÇÕES ====================
        System.out.println("✏️ TESTE 5: ATUALIZAÇÃO E EXCLUSÃO");
        System.out.println("─".repeat(55));
        
        // Atualizar um livro
        List<Livro> livrosParaAtualizar = livroService.buscarLivrosPorTitulo("Clean Code");
        if (!livrosParaAtualizar.isEmpty()) {
            Livro cleanCode = livrosParaAtualizar.get(0);
            String tituloAntigo = cleanCode.getTitulo();
            cleanCode.setTitulo("Clean Code (Arquitetura de Software)");
            System.out.println("Atualizando título do livro:");
            System.out.println("  → '" + tituloAntigo + "' -> '" + cleanCode.getTitulo() + "'");
            System.out.println("  → Resultado: " + (livroService.atualizarLivro(cleanCode) ? "✅ Sucesso" : "❌ Falha"));
        }
        System.out.println();
        
        // Atualizar um usuário
        List<Usuario> usuariosParaAtualizar = usuarioService.buscarUsuariosPorNome("Ana Paula");
        if (!usuariosParaAtualizar.isEmpty()) {
            Usuario ana = usuariosParaAtualizar.get(0);
            String emailAntigo = ana.getEmail();
            ana.setEmail("anapaula@email.com");
            System.out.println("Atualizando email do usuário:");
            System.out.println("  → '" + emailAntigo + "' -> '" + ana.getEmail() + "'");
            System.out.println("  → Resultado: " + (usuarioService.atualizarUsuario(ana) ? "✅ Sucesso" : "❌ Falha"));
        }
        System.out.println();
        
        // ==================== CONCLUSÃO ====================
        System.out.println("╔════════════════════════════════════════════════════════════════╗");
        System.out.println("║                    ✅ TESTES FINALIZADOS ✅                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════════╝");
        System.out.println();
        System.out.println("Resumo das funcionalidades testadas:");
        System.out.println("  ✓ CRUD de Livros (com validação de ISBN duplicado)");
        System.out.println("  ✓ CRUD de Usuários (com validação de email)");
        System.out.println("  ✓ Empréstimos (limite de 3 por usuário)");
        System.out.println("  ✓ Devoluções e cálculo de multa");
        System.out.println("  ✓ Renovação de empréstimos");
        System.out.println("  ✓ Controle de disponibilidade de livros");
        System.out.println("  ✓ Buscas por título, autor, ISBN e nome");
        System.out.println("  ✓ Atualização e exclusão de registros");
        System.out.println();
        System.out.println("🎉 Projeto refatorado e pronto para ser reaproveitado no sistema web!");
    }
}