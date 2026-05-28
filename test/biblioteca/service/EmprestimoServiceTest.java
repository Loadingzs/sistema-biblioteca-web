package biblioteca.service;

import biblioteca.domain.Emprestimo;
import java.time.LocalDate;
import org.junit.Test;
import static org.junit.Assert.*;

public class EmprestimoServiceTest {
    
    private EmprestimoService emprestimoService = new EmprestimoService();
    
    @Test
    public void testCalcularMulta_SemAtraso() {
        LocalDate dataPrevista = LocalDate.of(2026, 5, 20);
        LocalDate dataDevolucao = LocalDate.of(2026, 5, 20);
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataDevolucaoPrevista(dataPrevista);
        emprestimo.setDataDevolucaoReal(dataDevolucao);
        
        double multa = emprestimoService.calcularMulta(emprestimo);
        
        assertEquals(0.0, multa, 0.01);
        System.out.println("✅ Teste 1 passou: Sem atraso");
    }
    
    @Test
    public void testCalcularMulta_ComAtrasoDe3Dias() {
        LocalDate dataPrevista = LocalDate.of(2026, 5, 20);
        LocalDate dataDevolucao = LocalDate.of(2026, 5, 23);
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataDevolucaoPrevista(dataPrevista);
        emprestimo.setDataDevolucaoReal(dataDevolucao);
        
        double multa = emprestimoService.calcularMulta(emprestimo);
        
        assertEquals(6.0, multa, 0.01);
        System.out.println("✅ Teste 2 passou: Atraso 3 dias = R$6,00");
    }
    
    @Test
    public void testCalcularMulta_ComAtrasoDe10Dias() {
        LocalDate dataPrevista = LocalDate.of(2026, 5, 20);
        LocalDate dataDevolucao = LocalDate.of(2026, 5, 30);
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setDataDevolucaoPrevista(dataPrevista);
        emprestimo.setDataDevolucaoReal(dataDevolucao);
        
        double multa = emprestimoService.calcularMulta(emprestimo);
        
        assertEquals(20.0, multa, 0.01);
        System.out.println("✅ Teste 3 passou: Atraso 10 dias = R$20,00");
    }
}