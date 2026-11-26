package biblioteca.service;

import biblioteca.domain.Emprestimo;
import biblioteca.repository.EmprestimoRepository;
import biblioteca.repository.LivroRepository;
import biblioteca.repository.UsuarioRepository;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class RegistrarDevolucaoTest {
    private final EmprestimosFake emprestimos = new EmprestimosFake();
    private final LivrosFake livros = new LivrosFake();
    private final EmprestimoService service = new EmprestimoService(
            emprestimos, livros, new UsuarioRepository());
    private PrintStream consoleOriginal;
    private PrintStream consoleTeste;
    private ByteArrayOutputStream saida;

    @Before
    public void capturarConsole() {
        consoleOriginal = System.out;
        saida = new ByteArrayOutputStream();
        consoleTeste = new PrintStream(saida, true, StandardCharsets.UTF_8);
        System.setOut(consoleTeste);
    }

    @After
    public void restaurarConsole() {
        System.setOut(consoleOriginal);
        consoleTeste.close();
    }

    @Test
    public void devolucaoAtrasadaInformaMultaComADataQueFoiPersistida() {
        prepararEmprestimo(-3);

        assertTrue(service.registrarDevolucao(42));

        assertNotNull(emprestimos.devolucaoPersistida);
        assertEquals("FINALIZADO", emprestimos.statusPersistido);
        long diasAtraso = ChronoUnit.DAYS.between(
                emprestimos.registro.getDataDevolucaoPrevista(), emprestimos.devolucaoPersistida);
        assertTrue(diasAtraso >= 3);
        String multaEsperada = String.format("%.2f", diasAtraso * 2.0);
        assertTrue("A devolução deve informar a multa calculada com a data registrada",
                texto().contains("Multa por atraso: R$ " + multaEsperada));
        assertEquals(7, livros.livroAtualizado);
        assertTrue(livros.disponivel);
    }

    @Test
    public void devolucaoAntesDoPrazoNaoInformaMulta() {
        prepararEmprestimo(5);

        assertTrue(service.registrarDevolucao(42));

        assertNotNull(emprestimos.devolucaoPersistida);
        assertEquals("FINALIZADO", emprestimos.statusPersistido);
        assertFalse(texto().contains("Multa por atraso"));
        assertEquals(7, livros.livroAtualizado);
        assertTrue(livros.disponivel);
    }

    @Test
    public void falhaAoPersistirNaoAnunciaSucessoNemDisponibilizaLivro() {
        prepararEmprestimo(-3);
        emprestimos.sucesso = false;

        assertFalse(service.registrarDevolucao(42));

        assertEquals(0, livros.livroAtualizado);
        assertFalse(texto().contains("Devolução registrada com sucesso"));
        assertFalse(texto().contains("Multa por atraso"));
    }

    private void prepararEmprestimo(int diasParaPrazo) {
        emprestimos.registro = new Emprestimo(42, 7, 9, LocalDate.of(2000, 1, 1),
                LocalDate.now().plusDays(diasParaPrazo), null, "ATIVO");
    }

    private String texto() {
        return saida.toString(StandardCharsets.UTF_8);
    }

    private static class EmprestimosFake extends EmprestimoRepository {
        Emprestimo registro;
        LocalDate devolucaoPersistida;
        String statusPersistido;
        boolean sucesso = true;

        @Override
        public Emprestimo buscarPorId(int id) {
            assertEquals(42, id);
            return registro;
        }

        @Override
        public boolean atualizar(Emprestimo emprestimo) {
            devolucaoPersistida = emprestimo.getDataDevolucaoReal();
            statusPersistido = emprestimo.getStatus();
            return sucesso;
        }
    }

    private static class LivrosFake extends LivroRepository {
        int livroAtualizado;
        boolean disponivel;

        @Override
        public boolean atualizarDisponibilidade(int idLivro, boolean disponivel) {
            livroAtualizado = idLivro;
            this.disponivel = disponivel;
            return true;
        }
    }
}
