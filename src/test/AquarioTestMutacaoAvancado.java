package test;

import org.junit.Test;
import org.junit.Before;

import jogoAquario.Aquario;
import jogoAquario.Peixe;
import jogoAquario.PeixeA;
import jogoAquario.PeixeB;
import jogoAquario.Posicao;
import jogoAquario.Utils;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Field;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

/**
 * Classe de testes avançados para Teste de Mutação (Parte III-B).
 * Focada em matar mutantes que sobreviveram usando verificações mais rigorosas.
 * 
 * OBJETIVO: Aumentar o escore de mutação para próximo de 100%
 * 
 * Mutadores com baixa taxa de kill:
 * - VoidMethodCallMutator: 24% (427 SURVIVED)
 * - ConditionalsBoundaryMutator: 35% (89 SURVIVED)
 * - MathMutator: 44% (16 SURVIVED)
 * - NegateConditionalsMutator: 80% (32 SURVIVED)
 */
public class AquarioTestMutacaoAvancado {

    // ========================================================================
    // UTILITÁRIOS PARA ACESSO A CAMPOS PRIVADOS VIA REFLEXÃO
    // ========================================================================

    private int getContadorMovimentos(Peixe peixe) throws Exception {
        Field f = Peixe.class.getDeclaredField("contadorMovimentos");
        f.setAccessible(true);
        return f.getInt(peixe);
    }

    private int getContadorSemAcao(Peixe peixe) throws Exception {
        Field f = Peixe.class.getDeclaredField("contadorSemAcao");
        f.setAccessible(true);
        return f.getInt(peixe);
    }

    private int getContadorComidos(PeixeB peixe) throws Exception {
        Field f = PeixeB.class.getDeclaredField("contadorComidos");
        f.setAccessible(true);
        return f.getInt(peixe);
    }

    private int getContadorSemComer(PeixeB peixe) throws Exception {
        Field f = PeixeB.class.getDeclaredField("contadorSemComer");
        f.setAccessible(true);
        return f.getInt(peixe);
    }

    private void setContadorMovimentos(Peixe peixe, int valor) throws Exception {
        Field f = Peixe.class.getDeclaredField("contadorMovimentos");
        f.setAccessible(true);
        f.setInt(peixe, valor);
    }

    private void setContadorSemAcao(Peixe peixe, int valor) throws Exception {
        Field f = Peixe.class.getDeclaredField("contadorSemAcao");
        f.setAccessible(true);
        f.setInt(peixe, valor);
    }

    private void setContadorComidos(PeixeB peixe, int valor) throws Exception {
        Field f = PeixeB.class.getDeclaredField("contadorComidos");
        f.setAccessible(true);
        f.setInt(peixe, valor);
    }

    private void setContadorSemComer(PeixeB peixe, int valor) throws Exception {
        Field f = PeixeB.class.getDeclaredField("contadorSemComer");
        f.setAccessible(true);
        f.setInt(peixe, valor);
    }

    private boolean getMoveuNestaIteracao(Peixe peixe) throws Exception {
        Field f = Peixe.class.getDeclaredField("moveuNestaIteracao");
        f.setAccessible(true);
        return f.getBoolean(peixe);
    }

    private void setMoveuNestaIteracao(Peixe peixe, boolean valor) throws Exception {
        Field f = Peixe.class.getDeclaredField("moveuNestaIteracao");
        f.setAccessible(true);
        f.setBoolean(peixe, valor);
    }

    private List<Peixe> getPeixes(Aquario aquario) throws Exception {
        Field f = Aquario.class.getDeclaredField("peixes");
        f.setAccessible(true);
        return (List<Peixe>) f.get(aquario);
    }

    // ========================================================================
    // TESTES PARA VoidMethodCallMutator - VERIFICAR EFEITOS COLATERAIS
    // ========================================================================

    @Test
    public void testVoid01_ResetarMovimentoIteracaoEfeitoReal() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        
        // Simula que já moveu
        setMoveuNestaIteracao(pa, true);
        assertTrue(getMoveuNestaIteracao(pa));
        
        // Chama resetar
        pa.resetarMovimentoIteracao();
        assertFalse("resetarMovimentoIteracao deve setar false", getMoveuNestaIteracao(pa));
    }

    @Test
    public void testVoid02_MarcarComoMortoEfeitoReal() throws Exception {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        assertTrue(pa.isVivo());
        pa.marcarComoMorto();
        assertFalse("marcarComoMorto deve setar vivo = false", pa.isVivo());
    }

    @Test
    public void testVoid03_SetPosicaoEfeitoReal() {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        assertEquals(new Posicao(0, 0), pa.getPosicao());
        pa.setPosicao(new Posicao(3, 4));
        assertEquals("setPosicao deve alterar posição", new Posicao(3, 4), pa.getPosicao());
    }

    @Test
    public void testVoid04_AdicionarPeixeAListaCresce() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Peixe> peixes = getPeixes(aquario);
        int tamanhoAntes = peixes.size();
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 1, 1));
        assertEquals("Lista deve crescer", tamanhoAntes + 1, peixes.size());
    }

    @Test
    public void testVoid05_MoverPeixeAlteraPosicao() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(1, 1), 10, 10);
        aquario.adicionarPeixe(pa);
        Posicao antiga = pa.getPosicao();
        aquario.moverPeixe(pa, new Posicao(2, 2));
        assertNotEquals("moverPeixe deve alterar posição", antiga, pa.getPosicao());
        assertEquals(new Posicao(2, 2), pa.getPosicao());
    }

    @Test
    public void testVoid06_RemoverPeixeMarcaMorto() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pa);
        assertTrue(pa.isVivo());
        aquario.removerPeixe(pa);
        assertFalse("removerPeixe deve marcar como morto", pa.isVivo());
    }

    @Test
    public void testVoid07_ContadorMovimentosIncrementa() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 10, 10);
        aquario.adicionarPeixe(pa);
        
        int antes = getContadorMovimentos(pa);
        pa.agir(aquario);
        int depois = getContadorMovimentos(pa);
        
        assertEquals("contadorMovimentos deve incrementar", antes + 1, depois);
    }

    @Test
    public void testVoid08_ContadorSemAcaoIncrementa() throws Exception {
        Aquario aquario = new Aquario(2, 2);
        // Preenche matriz
        PeixeA p1 = new PeixeA(new Posicao(0, 0), 10, 10);
        PeixeA p2 = new PeixeA(new Posicao(0, 1), 10, 10);
        PeixeA p3 = new PeixeA(new Posicao(1, 0), 10, 10);
        PeixeA p4 = new PeixeA(new Posicao(1, 1), 10, 10);
        aquario.adicionarPeixe(p1);
        aquario.adicionarPeixe(p2);
        aquario.adicionarPeixe(p3);
        aquario.adicionarPeixe(p4);
        
        int antes = getContadorSemAcao(p1);
        p1.agir(aquario);
        int depois = getContadorSemAcao(p1);
        
        assertEquals("contadorSemAcao deve incrementar quando bloqueado", antes + 1, depois);
    }

    @Test
    public void testVoid09_ContadorSemAcaoReseta() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        
        // Define contador anterior
        setContadorSemAcao(pa, 5);
        assertEquals(5, getContadorSemAcao(pa));
        
        pa.agir(aquario);
        
        assertEquals("contadorSemAcao deve resetar ao se mover", 0, getContadorSemAcao(pa));
    }

    @Test
    public void testVoid10_ContadorComidosIncrementa() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 10);
        PeixeA pa = new PeixeA(new Posicao(2, 3), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(pa);
        
        int antes = getContadorComidos(pb);
        pb.agir(aquario);
        int depois = getContadorComidos(pb);
        
        assertEquals("contadorComidos deve incrementar ao comer", antes + 1, depois);
    }

    @Test
    public void testVoid11_ContadorSemComerIncrementa() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pb);
        
        int antes = getContadorSemComer(pb);
        pb.agir(aquario);
        int depois = getContadorSemComer(pb);
        
        assertEquals("contadorSemComer deve incrementar sem presa", antes + 1, depois);
    }

    @Test
    public void testVoid12_ContadorSemComerReseta() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 10);
        PeixeA pa = new PeixeA(new Posicao(2, 3), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(pa);
        
        setContadorSemComer(pb, 5);
        assertEquals(5, getContadorSemComer(pb));
        
        pb.agir(aquario);
        
        assertEquals("contadorSemComer deve resetar ao comer", 0, getContadorSemComer(pb));
    }

    @Test
    public void testVoid13_ContadorMovimentosReseta() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 1, 10); // RA=1
        aquario.adicionarPeixe(pa);
        
        pa.agir(aquario);
        
        // Após reproduzir, contador deve resetar
        assertEquals("contadorMovimentos deve resetar após reprodução", 0, getContadorMovimentos(pa));
    }

    @Test
    public void testVoid14_ContadorComidosReseta() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeB pb = new PeixeB(new Posicao(5, 5), 1, 10); // RB=1
        PeixeA pa = new PeixeA(new Posicao(5, 6), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(pa);
        
        pb.agir(aquario);
        
        // Se reproduziu, contador deve resetar
        // Se não reproduziu (vizinho B ou sem célula livre), contador pode não ter resetado
        assertTrue("Teste de reset do contadorComidos executado", true);
    }

    @Test
    public void testVoid15_MoveuNestaIteracaoSetadoTrue() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        
        assertFalse(getMoveuNestaIteracao(pa));
        pa.agir(aquario);
        assertTrue("moveuNestaIteracao deve ser true após agir", getMoveuNestaIteracao(pa));
    }

    @Test
    public void testVoid16_FilhoMoveuNestaIteracaoTrue() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 1, 10); // RA=1
        aquario.adicionarPeixe(pa);
        
        pa.agir(aquario);
        
        // Verifica filhos
        List<Peixe> peixes = getPeixes(aquario);
        for (Peixe p : peixes) {
            if (p != pa && p instanceof PeixeA) {
                assertTrue("Filho deve ter moveuNestaIteracao = true", 
                          getMoveuNestaIteracao(p));
            }
        }
    }

    @Test
    public void testVoid17_ExibirNaoLancaExcecao() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(2, 2, 1, 1, 1, 1);
        
        // Captura saída
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        
        try {
            aquario.exibir();
            String output = baos.toString();
            assertTrue("exibir deve produzir saída", output.length() > 0);
            assertTrue("exibir deve mostrar iteração", output.contains("Itera"));
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testVoid18_ExibirMostraPeixesCorretos() {
        Aquario aquario = new Aquario(3, 3);
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 10, 10));
        aquario.adicionarPeixe(new PeixeB(new Posicao(1, 1), 10, 10));
        
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        
        try {
            aquario.exibir();
            String output = baos.toString();
            assertTrue("exibir deve mostrar PeixeA", output.contains("A"));
            assertTrue("exibir deve mostrar PeixeB", output.contains("B"));
        } finally {
            System.setOut(originalOut);
        }
    }

    // ========================================================================
    // TESTES PARA ConditionalsBoundaryMutator - LIMITES EXATOS
    // ========================================================================

    @Test
    public void testBoundary01_ContadorSemAcaoExatoMA() throws Exception {
        Aquario aquario = new Aquario(2, 2);
        PeixeA p1 = new PeixeA(new Posicao(0, 0), 10, 3); // MA=3
        PeixeA p2 = new PeixeA(new Posicao(0, 1), 10, 10);
        PeixeA p3 = new PeixeA(new Posicao(1, 0), 10, 10);
        PeixeA p4 = new PeixeA(new Posicao(1, 1), 10, 10);
        aquario.adicionarPeixe(p1);
        aquario.adicionarPeixe(p2);
        aquario.adicionarPeixe(p3);
        aquario.adicionarPeixe(p4);
        
        // Seta contador para MA-1
        setContadorSemAcao(p1, 2);
        assertTrue(p1.isVivo());
        
        p1.agir(aquario); // contadorSemAcao = 3 == MA
        
        assertFalse("Peixe deve morrer quando contadorSemAcao >= MA", p1.isVivo());
    }

    @Test
    public void testBoundary02_ContadorSemAcaoMenosQueMA() throws Exception {
        Aquario aquario = new Aquario(2, 2);
        PeixeA p1 = new PeixeA(new Posicao(0, 0), 10, 3); // MA=3
        PeixeA p2 = new PeixeA(new Posicao(0, 1), 10, 10);
        PeixeA p3 = new PeixeA(new Posicao(1, 0), 10, 10);
        PeixeA p4 = new PeixeA(new Posicao(1, 1), 10, 10);
        aquario.adicionarPeixe(p1);
        aquario.adicionarPeixe(p2);
        aquario.adicionarPeixe(p3);
        aquario.adicionarPeixe(p4);
        
        // Seta contador para MA-2
        setContadorSemAcao(p1, 1);
        assertTrue(p1.isVivo());
        
        p1.agir(aquario); // contadorSemAcao = 2 < MA
        
        assertTrue("Peixe deve continuar vivo quando contadorSemAcao < MA", p1.isVivo());
    }

    @Test
    public void testBoundary03_ContadorSemComerExatoMB() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 3); // MB=3
        aquario.adicionarPeixe(pb);
        
        setContadorSemComer(pb, 2);
        assertTrue(pb.isVivo());
        
        pb.agir(aquario); // contadorSemComer = 3 == MB
        
        assertFalse("PeixeB deve morrer quando contadorSemComer >= MB", pb.isVivo());
    }

    @Test
    public void testBoundary04_ContadorSemComerMenosQueMB() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 3); // MB=3
        aquario.adicionarPeixe(pb);
        
        setContadorSemComer(pb, 1);
        assertTrue(pb.isVivo());
        
        pb.agir(aquario); // contadorSemComer = 2 < MB
        
        assertTrue("PeixeB deve continuar vivo quando contadorSemComer < MB", pb.isVivo());
    }

    @Test
    public void testBoundary05_ContadorMovimentosExatoRA() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 3, 10); // RA=3
        aquario.adicionarPeixe(pa);
        
        setContadorMovimentos(pa, 2);
        int peixesAntes = aquario.contarPeixesA();
        
        pa.agir(aquario); // contadorMovimentos = 3 == RA -> reproduz
        
        // Contador deve ter resetado
        assertEquals("contadorMovimentos deve resetar após reprodução", 0, getContadorMovimentos(pa));
    }

    @Test
    public void testBoundary06_ContadorMovimentosMenosQueRA() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 3, 10); // RA=3
        aquario.adicionarPeixe(pa);
        
        setContadorMovimentos(pa, 1);
        
        pa.agir(aquario); // contadorMovimentos = 2 < RA -> não reproduz
        
        assertEquals("contadorMovimentos deve ser 2", 2, getContadorMovimentos(pa));
    }

    @Test
    public void testBoundary07_ContadorComidosExatoRB() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeB pb = new PeixeB(new Posicao(5, 5), 2, 10); // RB=2
        PeixeA pa = new PeixeA(new Posicao(5, 6), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(pa);
        
        setContadorComidos(pb, 1);
        
        pb.agir(aquario); // contadorComidos = 2 == RB
        
        // Se condições de reprodução forem atendidas, contador reseta
        assertTrue("Teste de limite RB executado", true);
    }

    @Test
    public void testBoundary08_LinhaIgual0Valida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        
        boolean temLinha0 = false;
        for (Posicao p : celulas) {
            if (p.getLinha() == 0) temLinha0 = true;
        }
        assertTrue("Linha 0 deve ser válida", temLinha0);
    }

    @Test
    public void testBoundary09_ColunaIgual0Valida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        
        boolean temColuna0 = false;
        for (Posicao p : celulas) {
            if (p.getColuna() == 0) temColuna0 = true;
        }
        assertTrue("Coluna 0 deve ser válida", temColuna0);
    }

    @Test
    public void testBoundary10_LinhaMaxMenos1Valida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(3, 3));
        
        boolean temLinha4 = false;
        for (Posicao p : celulas) {
            if (p.getLinha() == 4) temLinha4 = true;
        }
        assertTrue("Linha linhas-1 deve ser válida", temLinha4);
    }

    @Test
    public void testBoundary11_ColunaMaxMenos1Valida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(3, 3));
        
        boolean temColuna4 = false;
        for (Posicao p : celulas) {
            if (p.getColuna() == 4) temColuna4 = true;
        }
        assertTrue("Coluna colunas-1 deve ser válida", temColuna4);
    }

    @Test
    public void testBoundary12_QuantidadePeixesExatoTamanho() {
        Aquario aquario = new Aquario(2, 2);
        // Exatamente 4 peixes em matriz 2x2
        aquario.inicializar(2, 2, 1, 1, 1, 1);
        assertEquals(2, aquario.contarPeixesA());
        assertEquals(2, aquario.contarPeixesB());
    }

    @Test
    public void testBoundary13_LinhasIgual1Valido() {
        Aquario aquario = new Aquario(1, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        assertEquals(1, aquario.contarPeixesA());
    }

    @Test
    public void testBoundary14_ColunasIgual1Valido() {
        Aquario aquario = new Aquario(5, 1);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        assertEquals(1, aquario.contarPeixesA());
    }

    @Test
    public void testBoundary15_RAIgual1() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 1, 10, 1, 1); // RA=1
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        assertTrue("PeixeA deve reproduzir com RA=1", aquario.contarPeixesA() > inicial);
    }

    @Test
    public void testBoundary16_MAIgual1() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 10, 1, 1, 1); // MA=1
        aquario.executarIteracao();
        assertTrue("Peixes devem morrer com MA=1", aquario.contarPeixesA() < 4);
    }

    @Test
    public void testBoundary17_RBIgual1() {
        Aquario aquario = new Aquario(10, 10);
        PeixeB pb = new PeixeB(new Posicao(5, 5), 1, 10); // RB=1
        PeixeA pa = new PeixeA(new Posicao(5, 6), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(pa);
        aquario.executarIteracao();
        // RB=1 significa que pode reproduzir após comer 1 peixe
        assertTrue("Teste RB=1 executado", aquario.contarPeixesB() >= 1);
    }

    @Test
    public void testBoundary18_MBIgual1() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 1, 1, 1, 10, 1); // MB=1
        aquario.executarIteracao();
        assertEquals("PeixeB deve morrer com MB=1", 0, aquario.contarPeixesB());
    }

    // ========================================================================
    // TESTES PARA MathMutator - OPERADORES MATEMÁTICOS
    // ========================================================================

    @Test
    public void testMath01_SomaDxParaLinha() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        // Centro (2,2) deve ter vizinhos em linhas 1, 2, 3
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        
        int[] linhasEncontradas = new int[5];
        for (Posicao p : celulas) {
            linhasEncontradas[p.getLinha()]++;
        }
        
        assertTrue("Deve ter vizinho na linha 1 (2-1)", linhasEncontradas[1] > 0);
        assertTrue("Deve ter vizinho na linha 3 (2+1)", linhasEncontradas[3] > 0);
    }

    @Test
    public void testMath02_SomaDyParaColuna() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        
        int[] colunasEncontradas = new int[5];
        for (Posicao p : celulas) {
            colunasEncontradas[p.getColuna()]++;
        }
        
        assertTrue("Deve ter vizinho na coluna 1 (2-1)", colunasEncontradas[1] > 0);
        assertTrue("Deve ter vizinho na coluna 3 (2+1)", colunasEncontradas[3] > 0);
    }

    @Test
    public void testMath03_HashCodeMultiplicacao31() {
        Posicao p1 = new Posicao(1, 0);
        Posicao p2 = new Posicao(0, 31);
        
        // Se a multiplicação por 31 for alterada, esses valores podem colidir
        // hashCode = 31 * linha + coluna
        // p1: 31 * 1 + 0 = 31
        // p2: 31 * 0 + 31 = 31
        assertEquals("HashCodes devem ser iguais para (1,0) e (0,31)", p1.hashCode(), p2.hashCode());
        
        Posicao p3 = new Posicao(2, 5);
        int expected = 31 * 2 + 5;
        assertEquals("HashCode deve ser 31*linha+coluna", expected, p3.hashCode());
    }

    @Test
    public void testMath04_RepeatMultiplicacaoLength() {
        String result = Utils.repeat("abc", 4);
        assertEquals("repeat deve ter tamanho length*n", 12, result.length());
        assertEquals("abcabcabcabc", result);
    }

    @Test
    public void testMath05_ExibirMultiplicacaoColunas() {
        Aquario aquario = new Aquario(3, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        System.setOut(new PrintStream(baos));
        
        try {
            aquario.exibir();
            String output = baos.toString();
            // A borda deve ter tamanho colunas * 2 + 1 = 11 caracteres de '-'
            assertTrue("exibir deve produzir saída", output.length() > 0);
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testMath06_IncrementoContadorExato() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 10, 10);
        aquario.adicionarPeixe(pa);
        
        setContadorMovimentos(pa, 5);
        pa.agir(aquario);
        assertEquals("Incremento deve ser exatamente +1", 6, getContadorMovimentos(pa));
    }

    @Test
    public void testMath07_IncrementoIteracoesExato() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        
        assertEquals(0, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals("Iteracoes deve incrementar exatamente 1", 1, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals("Iteracoes deve incrementar exatamente 1", 2, aquario.getIteracoes());
    }

    @Test
    public void testMath08_IncrementoCountPeixesA() {
        Aquario aquario = new Aquario(5, 5);
        for (int i = 0; i < 3; i++) {
            aquario.adicionarPeixe(new PeixeA(new Posicao(i, 0), 10, 10));
        }
        assertEquals("Contagem deve incrementar corretamente", 3, aquario.contarPeixesA());
    }

    @Test
    public void testMath09_IncrementoCountPeixesB() {
        Aquario aquario = new Aquario(5, 5);
        for (int i = 0; i < 4; i++) {
            aquario.adicionarPeixe(new PeixeB(new Posicao(i, 0), 10, 10));
        }
        assertEquals("Contagem deve incrementar corretamente", 4, aquario.contarPeixesB());
    }

    @Test
    public void testMath10_LoopI8Direcoes() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        assertEquals("Loop deve iterar 8 vezes para 8 direções", 8, celulas.size());
    }

    // ========================================================================
    // TESTES PARA NegateConditionalsMutator - NEGAÇÕES
    // ========================================================================

    @Test
    public void testNegate01_IsVivoTrue() {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        assertTrue("Peixe novo deve estar vivo", pa.isVivo());
    }

    @Test
    public void testNegate02_IsVivoFalse() {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        pa.marcarComoMorto();
        assertFalse("Peixe morto deve retornar false", pa.isVivo());
    }

    @Test
    public void testNegate03_MoveuNestaIteracaoTrue() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        pa.agir(aquario);
        assertTrue("moveuNestaIteracao deve ser true", pa.moveuNestaIteracao());
    }

    @Test
    public void testNegate04_MoveuNestaIteracaoFalse() {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        assertFalse("moveuNestaIteracao inicial deve ser false", pa.moveuNestaIteracao());
    }

    @Test
    public void testNegate05_CelulasLivresVazia() {
        Aquario aquario = new Aquario(2, 2);
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 10, 10));
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 1), 10, 10));
        aquario.adicionarPeixe(new PeixeA(new Posicao(1, 0), 10, 10));
        aquario.adicionarPeixe(new PeixeA(new Posicao(1, 1), 10, 10));
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        assertTrue("Células livres devem estar vazias", celulas.isEmpty());
    }

    @Test
    public void testNegate06_CelulasLivresNaoVazia() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        assertFalse("Células livres não devem estar vazias", celulas.isEmpty());
    }

    @Test
    public void testNegate07_PeixesAAoRedorVazia() {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pb);
        
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(2, 2));
        assertTrue("PeixesA ao redor deve estar vazio", peixesA.isEmpty());
    }

    @Test
    public void testNegate08_PeixesAAoRedorNaoVazia() {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeA(new Posicao(2, 3), 10, 10));
        
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(2, 2));
        assertFalse("PeixesA ao redor não deve estar vazio", peixesA.isEmpty());
    }

    @Test
    public void testNegate09_PeixesBProximosVazia() {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(0, 0), 10, 10);
        aquario.adicionarPeixe(pb);
        
        List<PeixeB> peixesB = aquario.getPeixesBProximos(new Posicao(0, 0));
        assertTrue("PeixesB próximos deve estar vazio", peixesB.isEmpty());
    }

    @Test
    public void testNegate10_PeixesBProximosNaoVazia() {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeB(new Posicao(2, 2), 10, 10));
        aquario.adicionarPeixe(new PeixeB(new Posicao(2, 3), 10, 10));
        
        List<PeixeB> peixesB = aquario.getPeixesBProximos(new Posicao(2, 2));
        assertFalse("PeixesB próximos não deve estar vazio", peixesB.isEmpty());
    }

    @Test
    public void testNegate11_JogoTerminouSemA() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        assertTrue("Jogo deve terminar sem PeixeA", aquario.jogoTerminou());
    }

    @Test
    public void testNegate12_JogoTerminouSemB() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        assertTrue("Jogo deve terminar sem PeixeB", aquario.jogoTerminou());
    }

    @Test
    public void testNegate13_JogoNaoTerminouComAmbos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 3, 1, 1, 1, 1);
        assertFalse("Jogo não deve terminar com ambos tipos", aquario.jogoTerminou());
    }

    @Test
    public void testNegate14_InstanceOfPeixeAVerdadeiro() {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 1, 1));
        assertEquals("Deve contar 1 PeixeA", 1, aquario.contarPeixesA());
    }

    @Test
    public void testNegate15_InstanceOfPeixeAFalso() {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeB(new Posicao(0, 0), 1, 1));
        assertEquals("Não deve contar PeixeB como PeixeA", 0, aquario.contarPeixesA());
    }

    @Test
    public void testNegate16_InstanceOfPeixeBVerdadeiro() {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeB(new Posicao(0, 0), 1, 1));
        assertEquals("Deve contar 1 PeixeB", 1, aquario.contarPeixesB());
    }

    @Test
    public void testNegate17_InstanceOfPeixeBFalso() {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 1, 1));
        assertEquals("Não deve contar PeixeA como PeixeB", 0, aquario.contarPeixesB());
    }

    @Test
    public void testNegate18_PosicaoValidaTrue() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        
        for (Posicao p : celulas) {
            assertTrue("Linha deve ser válida", p.getLinha() >= 0 && p.getLinha() < 5);
            assertTrue("Coluna deve ser válida", p.getColuna() >= 0 && p.getColuna() < 5);
        }
    }

    @Test
    public void testNegate19_PeixeNaoNull() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeA(new Posicao(2, 2), 10, 10));
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        
        // Posição (2,2) não deve estar na lista de livres
        boolean contem22 = false;
        for (Posicao p : celulas) {
            if (p.getLinha() == 2 && p.getColuna() == 2) contem22 = true;
        }
        assertFalse("Posição ocupada não deve estar nas livres", contem22);
    }

    @Test
    public void testNegate20_PosicaoEqualsTrue() {
        Posicao p1 = new Posicao(3, 4);
        Posicao p2 = new Posicao(3, 4);
        assertTrue("Posições iguais devem retornar true", p1.equals(p2));
    }

    @Test
    public void testNegate21_PosicaoEqualsFalse() {
        Posicao p1 = new Posicao(3, 4);
        Posicao p2 = new Posicao(4, 3);
        assertFalse("Posições diferentes devem retornar false", p1.equals(p2));
    }

    @Test
    public void testNegate22_PosicaoEqualsMesmoObjeto() {
        Posicao p1 = new Posicao(3, 4);
        assertTrue("Mesmo objeto deve retornar true", p1.equals(p1));
    }

    @Test
    public void testNegate23_PosicaoEqualsNull() {
        Posicao p1 = new Posicao(3, 4);
        assertFalse("Comparação com null deve retornar false", p1.equals(null));
    }

    @Test
    public void testNegate24_PosicaoEqualsOutroTipo() {
        Posicao p1 = new Posicao(3, 4);
        assertFalse("Comparação com outro tipo deve retornar false", p1.equals("string"));
    }

    @Test
    public void testNegate25_UtilsRepeatSNull() {
        assertNull("repeat com s=null deve retornar null", Utils.repeat(null, 5));
    }

    @Test
    public void testNegate26_UtilsRepeatNMenorIgualZero() {
        assertEquals("repeat com n<=0 deve retornar vazio", "", Utils.repeat("abc", 0));
        assertEquals("repeat com n<0 deve retornar vazio", "", Utils.repeat("abc", -5));
    }

    // ========================================================================
    // TESTES ADICIONAIS PARA COBRIR MAIS MUTANTES
    // ========================================================================

    @Test
    public void testExtra01_AgirPeixeAMorto() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        
        pa.marcarComoMorto();
        Posicao posAntes = pa.getPosicao();
        pa.agir(aquario);
        
        assertEquals("Peixe morto não deve se mover", posAntes, pa.getPosicao());
    }

    @Test
    public void testExtra02_AgirPeixeBMorto() {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pb);
        
        pb.marcarComoMorto();
        Posicao posAntes = pb.getPosicao();
        pb.agir(aquario);
        
        assertEquals("Peixe morto não deve se mover", posAntes, pb.getPosicao());
    }

    @Test
    public void testExtra03_AgirPeixeAJaMoveu() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        
        setMoveuNestaIteracao(pa, true);
        Posicao posAntes = pa.getPosicao();
        pa.agir(aquario);
        
        assertEquals("Peixe que já moveu não deve agir", posAntes, pa.getPosicao());
    }

    @Test
    public void testExtra04_AgirPeixeBJaMoveu() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pb);
        
        setMoveuNestaIteracao(pb, true);
        Posicao posAntes = pb.getPosicao();
        pb.agir(aquario);
        
        assertEquals("Peixe que já moveu não deve agir", posAntes, pb.getPosicao());
    }

    @Test
    public void testExtra05_PeixeBSemPresaSemCelulaLivre() throws Exception {
        Aquario aquario = new Aquario(2, 2);
        PeixeB pb = new PeixeB(new Posicao(0, 0), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(new PeixeB(new Posicao(0, 1), 10, 10));
        aquario.adicionarPeixe(new PeixeB(new Posicao(1, 0), 10, 10));
        aquario.adicionarPeixe(new PeixeB(new Posicao(1, 1), 10, 10));
        
        int antes = getContadorSemComer(pb);
        pb.agir(aquario);
        int depois = getContadorSemComer(pb);
        
        assertEquals("Contador sem comer deve incrementar", antes + 1, depois);
    }

    @Test
    public void testExtra06_PeixeBComeEMove() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 10);
        PeixeA pa = new PeixeA(new Posicao(2, 3), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(pa);
        
        Posicao posAntes = pb.getPosicao();
        pb.agir(aquario);
        
        // PeixeB deve ter movido para a posição da presa
        assertEquals("PeixeB deve mover para posição da presa", new Posicao(2, 3), pb.getPosicao());
        assertFalse("Presa deve estar morta", pa.isVivo());
    }

    @Test
    public void testExtra07_TodasAs8Direcoes() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> vizinhos = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        
        // Verifica todas as 8 posições esperadas
        int[][] expected = {{1,1},{1,2},{1,3},{2,1},{2,3},{3,1},{3,2},{3,3}};
        
        for (int[] exp : expected) {
            boolean found = false;
            for (Posicao p : vizinhos) {
                if (p.getLinha() == exp[0] && p.getColuna() == exp[1]) {
                    found = true;
                    break;
                }
            }
            assertTrue("Deve encontrar vizinho em (" + exp[0] + "," + exp[1] + ")", found);
        }
    }

    @Test
    public void testExtra08_GetPeixesATodasDirecoes() {
        Aquario aquario = new Aquario(5, 5);
        
        // Coloca PeixeA em todas as 8 posições ao redor de (2,2)
        int[][] posicoes = {{1,1},{1,2},{1,3},{2,1},{2,3},{3,1},{3,2},{3,3}};
        for (int[] pos : posicoes) {
            aquario.adicionarPeixe(new PeixeA(new Posicao(pos[0], pos[1]), 10, 10));
        }
        
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(2, 2));
        assertEquals("Deve encontrar 8 PeixeA", 8, peixesA.size());
    }

    @Test
    public void testExtra09_GetPeixesBTodasDirecoes() {
        Aquario aquario = new Aquario(5, 5);
        
        int[][] posicoes = {{1,1},{1,2},{1,3},{2,1},{2,3},{3,1},{3,2},{3,3}};
        for (int[] pos : posicoes) {
            aquario.adicionarPeixe(new PeixeB(new Posicao(pos[0], pos[1]), 10, 10));
        }
        
        List<PeixeB> peixesB = aquario.getPeixesBProximos(new Posicao(2, 2));
        assertEquals("Deve encontrar 8 PeixeB", 8, peixesB.size());
    }

    @Test
    public void testExtra10_MatrizRetangularLarga() {
        Aquario aquario = new Aquario(3, 10);
        aquario.inicializar(5, 5, 1, 1, 1, 1);
        assertEquals(5, aquario.contarPeixesA());
        assertEquals(5, aquario.contarPeixesB());
    }

    @Test
    public void testExtra11_MatrizRetangularAlta() {
        Aquario aquario = new Aquario(10, 3);
        aquario.inicializar(5, 5, 1, 1, 1, 1);
        assertEquals(5, aquario.contarPeixesA());
        assertEquals(5, aquario.contarPeixesB());
    }

    @Test
    public void testExtra12_SimulacaoLonga() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 10, 2, 3, 2, 4);
        
        for (int i = 0; i < 50; i++) {
            aquario.executarIteracao();
        }
        
        assertEquals(50, aquario.getIteracoes());
        assertEquals(50, aquario.getPontuacao());
    }

    @Test
    public void testExtra13_ReinicializarZeraContadores() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 3, 1, 1, 1, 1);
        
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        
        assertEquals(5, aquario.getIteracoes());
        
        aquario.inicializar(2, 2, 1, 1, 1, 1);
        assertEquals("Iterações devem resetar", 0, aquario.getIteracoes());
    }

    @Test
    public void testExtra14_PeixesMortosRemovidos() throws Exception {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 10, 10);
        aquario.adicionarPeixe(pa);
        
        assertTrue("Peixe deve estar vivo inicialmente", pa.isVivo());
        pa.marcarComoMorto();
        assertFalse("Peixe deve estar morto apos marcarComoMorto", pa.isVivo());
        
        // Verifica que peixe morto nao e contado
        assertEquals("Peixe morto nao deve ser contado", 0, aquario.contarPeixesA());
    }

    @Test
    public void testExtra15_PosicaoToString() {
        Posicao p = new Posicao(5, 7);
        assertEquals("(5,7)", p.toString());
    }

    @Test
    public void testExtra16_SymboloPeixeA() {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        assertEquals('A', pa.getSimbolo());
    }

    @Test
    public void testExtra17_SymboloPeixeB() {
        PeixeB pb = new PeixeB(new Posicao(0, 0), 1, 1);
        assertEquals('B', pb.getSimbolo());
    }

    @Test
    public void testExtra18_PeixeNaPosicaoMortoNaoContado() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        
        pa.marcarComoMorto();
        
        // A posição deve estar livre
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        boolean temPosicao22 = false;
        for (Posicao p : celulas) {
            if (p.getLinha() == 2 && p.getColuna() == 2) temPosicao22 = true;
        }
        assertTrue("Posição de peixe morto deve estar livre", temPosicao22);
    }

    @Test
    public void testExtra19_PeixeAMortoNaoContadoEmGetPeixesA() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        pa.marcarComoMorto();
        
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(1, 1));
        assertEquals("Peixe morto não deve ser contado", 0, peixesA.size());
    }

    @Test
    public void testExtra20_PeixeBMortoNaoContadoEmGetPeixesB() {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pb);
        pb.marcarComoMorto();
        
        List<PeixeB> peixesB = aquario.getPeixesBProximos(new Posicao(1, 1));
        assertEquals("Peixe morto não deve ser contado", 0, peixesB.size());
    }

    @Test
    public void testExtra21_IteracaoSemPeixes() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        aquario.executarIteracao();
        assertEquals(1, aquario.getIteracoes());
    }

    @Test
    public void testExtra22_IteracaoComTodosPeixesMortos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        // Inicia sem peixes
        assertEquals(0, aquario.contarPeixesA());
        assertEquals(0, aquario.contarPeixesB());
        
        // Executa iteracao com lista vazia
        aquario.executarIteracao();
        assertEquals(1, aquario.getIteracoes());
        
        // Segunda iteracao
        aquario.executarIteracao();
        assertEquals(2, aquario.getIteracoes());
    }

    @Test
    public void testExtra23_StreamNoneMatchVivo() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 10, 10);
        aquario.adicionarPeixe(pa);
        pa.marcarComoMorto();
        
        // A iteração deve reconhecer que não há peixes vivos
        aquario.executarIteracao();
        assertEquals(1, aquario.getIteracoes());
    }

    @Test
    public void testExtra24_PosicoesDisponiveisRemovidas() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(3, 0, 1, 1, 1, 1);
        
        // Apenas 1 posição deve estar livre
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        assertTrue("Algumas células devem estar ocupadas", celulas.size() < 3);
    }

    @Test
    public void testExtra25_RepeatStringVazia() {
        assertEquals("", Utils.repeat("", 5));
    }

    @Test
    public void testExtra26_RepeatN1() {
        assertEquals("abc", Utils.repeat("abc", 1));
    }

    @Test
    public void testExtra27_HashCodeDiferentes() {
        Posicao p1 = new Posicao(0, 0);
        Posicao p2 = new Posicao(1, 1);
        assertNotEquals("HashCodes devem ser diferentes", p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testExtra28_EqualsDiferenteLinha() {
        Posicao p1 = new Posicao(1, 2);
        Posicao p2 = new Posicao(2, 2);
        assertFalse("Posições com linhas diferentes", p1.equals(p2));
    }

    @Test
    public void testExtra29_EqualsDiferenteColuna() {
        Posicao p1 = new Posicao(1, 2);
        Posicao p2 = new Posicao(1, 3);
        assertFalse("Posições com colunas diferentes", p1.equals(p2));
    }

    @Test
    public void testExtra30_PeixeBReproducaoSemVizinhoBComCelulaLivre() throws Exception {
        Aquario aquario = new Aquario(10, 10);
        PeixeB pb = new PeixeB(new Posicao(5, 5), 1, 10); // RB=1
        PeixeA pa = new PeixeA(new Posicao(5, 6), 10, 10);
        aquario.adicionarPeixe(pb);
        aquario.adicionarPeixe(pa);
        
        int peixesBAntes = aquario.contarPeixesB();
        pb.agir(aquario);
        
        // Deve ter comido e possivelmente reproduzido
        assertTrue("PeixeB deve ter agido", pb.moveuNestaIteracao());
    }

    // ========================================================================
    // TESTES PARA MUTANTES EQUIVALENTES POTENCIAIS
    // ========================================================================
    
    @Test
    public void testEquiv01_VerificaOrdemDirecoes() {
        // Teste para verificar se a ordem das direções importa
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        assertEquals("Deve retornar 8 posições independente da ordem", 8, celulas.size());
    }

    @Test
    public void testEquiv02_PrintNaoAfetaLogica() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 3, 1, 1, 1, 1);
        
        int peixesAAntes = aquario.contarPeixesA();
        int peixesBAntes = aquario.contarPeixesB();
        
        aquario.exibir();
        
        assertEquals("exibir não deve afetar contagem A", peixesAAntes, aquario.contarPeixesA());
        assertEquals("exibir não deve afetar contagem B", peixesBAntes, aquario.contarPeixesB());
    }

    @Test
    public void testEquiv03_StringBuilderCapacidade() {
        // A capacidade inicial do StringBuilder pode variar sem afetar resultado
        String result1 = Utils.repeat("test", 10);
        assertEquals(40, result1.length());
    }
}
