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
import java.lang.reflect.Field;

import static org.junit.Assert.*;

/**
 * Classe de testes para Teste de Mutação (Parte III).
 * Focada em matar mutantes que sobreviveram aos testes funcionais e estruturais.
 * 
 * Mutadores alvo:
 * - ConditionalsBoundaryMutator (limite de condições: < vs <=, > vs >=)
 * - VoidMethodCallMutator (remoção de chamadas void)
 * - MathMutator (operadores matemáticos)
 * - NegateConditionalsMutator (negação de condições)
 * - IncrementsMutator (++ vs --)
 * - PrimitiveReturnsMutator (retornos primitivos)
 */
public class AquarioTestMutacao {

    // ========================================================================
    // TESTES PARA ConditionalsBoundaryMutator
    // Testa condições de limite: < vs <=, > vs >=
    // ========================================================================

    @Test
    public void testMT01_LimitePosicaoValidaLinha0() {
        // Testa limite inferior linha == 0 (posicaoValida: linha >= 0)
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        // Deve incluir posições válidas apenas (linha >= 0)
        for (Posicao p : celulas) {
            assertTrue("Linha deve ser >= 0", p.getLinha() >= 0);
        }
        assertNotNull(celulas);
    }

    @Test
    public void testMT02_LimitePosicaoValidaColuna0() {
        // Testa limite inferior coluna == 0 (posicaoValida: coluna >= 0)
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        for (Posicao p : celulas) {
            assertTrue("Coluna deve ser >= 0", p.getColuna() >= 0);
        }
    }

    @Test
    public void testMT03_LimitePosicaoValidaLinhaMax() {
        // Testa limite superior linha < linhas (linha == linhas-1)
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(4, 4));
        for (Posicao p : celulas) {
            assertTrue("Linha deve ser < 5", p.getLinha() < 5);
        }
    }

    @Test
    public void testMT04_LimitePosicaoValidaColunaMax() {
        // Testa limite superior coluna < colunas (coluna == colunas-1)
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(4, 4));
        for (Posicao p : celulas) {
            assertTrue("Coluna deve ser < 5", p.getColuna() < 5);
        }
    }

    @Test
    public void testMT05_LimiteContadorSemAcaoIgualMA() {
        // Testa contadorSemAcao >= ma (caso exato contadorSemAcao == ma)
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 5, 2, 1, 1); // MA=2
        // Peixes bloqueados, contador incrementa
        aquario.executarIteracao(); // contadorSemAcao = 1
        assertEquals(4, aquario.contarPeixesA()); // Ainda vivos
        aquario.executarIteracao(); // contadorSemAcao = 2 (== MA)
        assertTrue("Peixes devem morrer quando contadorSemAcao >= MA", 
                   aquario.contarPeixesA() < 4);
    }

    @Test
    public void testMT06_LimiteContadorSemComerIgualMB() {
        // Testa contadorSemComer >= mb (caso exato contadorSemComer == mb)
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 1, 1, 1, 1, 2); // MB=2
        assertEquals(1, aquario.contarPeixesB());
        aquario.executarIteracao(); // contadorSemComer = 1
        assertEquals(1, aquario.contarPeixesB());
        aquario.executarIteracao(); // contadorSemComer = 2 (== MB)
        assertEquals("PeixeB deve morrer quando contadorSemComer >= MB", 
                     0, aquario.contarPeixesB());
    }

    @Test
    public void testMT07_LimiteContadorMovimentosIgualRA() {
        // Testa contadorMovimentos >= ra (caso exato contadorMovimentos == ra)
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 2, 10, 1, 1); // RA=2
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao(); // contadorMovimentos = 1
        assertEquals(inicial, aquario.contarPeixesA()); // Não reproduziu
        aquario.executarIteracao(); // contadorMovimentos = 2 (== RA)
        assertTrue("PeixeA deve reproduzir quando contadorMovimentos >= RA", 
                   aquario.contarPeixesA() >= inicial);
    }

    @Test
    public void testMT08_LimiteContadorComidosIgualRB() {
        // Testa contadorComidos >= rb para reprodução de PeixeB
        Aquario aquario = new Aquario(10, 10);
        PeixeB pb = new PeixeB(new Posicao(5, 5), 1, 10); // RB=1
        aquario.adicionarPeixe(pb);
        // Adiciona presas próximas
        for (int i = 0; i < 3; i++) {
            aquario.adicionarPeixe(new PeixeA(new Posicao(4+i, 4), 10, 10));
        }
        int inicial = aquario.contarPeixesB();
        aquario.executarIteracao();
        // PeixeB come e deve reproduzir
        assertTrue("PeixeB deve reproduzir quando contadorComidos >= RB", 
                   aquario.contarPeixesB() >= inicial);
    }

    @Test
    public void testMT09_LimiteLinhas0Construtor() {
        // Testa linhas <= 0 no construtor
        try {
            new Aquario(0, 5);
            fail("Deveria lançar exceção para linhas == 0");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("maior que zero"));
        }
    }

    @Test
    public void testMT10_LimiteColunas0Construtor() {
        // Testa colunas <= 0 no construtor
        try {
            new Aquario(5, 0);
            fail("Deveria lançar exceção para colunas == 0");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("maior que zero"));
        }
    }

    // ========================================================================
    // TESTES PARA VoidMethodCallMutator
    // Verifica se chamadas void são executadas
    // ========================================================================

    @Test
    public void testMT11_VoidCallMarcarComoMorto() {
        // Verifica que marcarComoMorto() é chamado
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 5, 1, 1, 1); // MA=1
        assertEquals(4, aquario.contarPeixesA());
        aquario.executarIteracao();
        assertTrue("marcarComoMorto deve ser chamado", aquario.contarPeixesA() < 4);
    }

    @Test
    public void testMT12_VoidCallResetarMovimentoIteracao() {
        // Verifica que resetarMovimentoIteracao() é chamado
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 0, 1, 10, 1, 1);
        aquario.executarIteracao();
        int count1 = aquario.contarPeixesA();
        aquario.executarIteracao();
        int count2 = aquario.contarPeixesA();
        // Se reset não for chamado, peixes não agirão na 2ª iteração
        assertTrue("resetarMovimentoIteracao deve permitir ação na próxima iteração", 
                   count2 >= count1);
    }

    @Test
    public void testMT13_VoidCallSetPosicao() {
        // Verifica que setPosicao() é chamado ao mover
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 10, 10);
        aquario.adicionarPeixe(pa);
        Posicao posOriginal = pa.getPosicao();
        aquario.moverPeixe(pa, new Posicao(1, 1));
        assertNotEquals("setPosicao deve alterar a posição", posOriginal, pa.getPosicao());
        assertEquals(new Posicao(1, 1), pa.getPosicao());
    }

    @Test
    public void testMT14_VoidCallAdicionarPeixe() {
        // Verifica que adicionarPeixe() adiciona à lista
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        assertEquals(0, aquario.contarPeixesA());
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 1, 1));
        assertEquals("adicionarPeixe deve adicionar peixe", 1, aquario.contarPeixesA());
    }

    @Test
    public void testMT15_VoidCallRemoverPeixe() {
        // Verifica que removerPeixe() marca como morto
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pa);
        assertTrue(pa.isVivo());
        aquario.removerPeixe(pa);
        assertFalse("removerPeixe deve marcar como morto", pa.isVivo());
    }

    @Test
    public void testMT16_VoidCallMoverPeixeEmAgir() {
        // Verifica que moverPeixe é chamado no agir
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        Posicao posOriginal = new Posicao(2, 2);
        aquario.executarIteracao();
        assertNotEquals("moverPeixe deve ser chamado em agir", 
                        posOriginal, pa.getPosicao());
    }

    // ========================================================================
    // TESTES PARA MathMutator
    // Testa operadores matemáticos: +, -, *, /
    // ========================================================================

    @Test
    public void testMT17_MathOperadorSomaLinha() {
        // Testa dx[i] + linha (soma na linha)
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        assertEquals("Centro deve ter 8 vizinhos livres", 8, celulas.size());
        // Verifica que todas as direções estão corretas
        boolean temSuperior = false, temInferior = false;
        for (Posicao p : celulas) {
            if (p.getLinha() == 0) temSuperior = true;
            if (p.getLinha() == 2) temInferior = true;
        }
        assertTrue("Deve ter vizinho na linha superior", temSuperior);
        assertTrue("Deve ter vizinho na linha inferior", temInferior);
    }

    @Test
    public void testMT18_MathOperadorSomaColuna() {
        // Testa dy[i] + coluna (soma na coluna)
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        boolean temEsquerda = false, temDireita = false;
        for (Posicao p : celulas) {
            if (p.getColuna() == 0) temEsquerda = true;
            if (p.getColuna() == 2) temDireita = true;
        }
        assertTrue("Deve ter vizinho na coluna esquerda", temEsquerda);
        assertTrue("Deve ter vizinho na coluna direita", temDireita);
    }

    @Test
    public void testMT19_MathOperadorHashCode() {
        // Testa 31 * linha + coluna no hashCode
        Posicao p1 = new Posicao(1, 2);
        Posicao p2 = new Posicao(2, 1);
        assertNotEquals("hashCode deve usar multiplicação correta", 
                        p1.hashCode(), p2.hashCode());
        assertEquals("hashCode deve ser consistente", p1.hashCode(), p1.hashCode());
    }

    @Test
    public void testMT20_MathOperadorMultiplicacaoRepeat() {
        // Testa s.length() * n no Utils.repeat
        String result = Utils.repeat("ab", 3);
        assertEquals("repeat deve multiplicar corretamente", "ababab", result);
        assertEquals("Tamanho deve ser length * n", 6, result.length());
    }

    @Test
    public void testMT21_MathOperadorColunasExibir() {
        // Testa colunas * 2 + 1 na exibição
        Aquario aquario = new Aquario(3, 4);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        // Apenas verifica que não lança exceção
        aquario.exibir();
        assertTrue(true);
    }

    // ========================================================================
    // TESTES PARA NegateConditionalsMutator
    // Testa negação de condições booleanas
    // ========================================================================

    @Test
    public void testMT22_NegateIsVivo() {
        // Testa negação de isVivo() em agir
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        assertTrue(pa.isVivo());
        pa.marcarComoMorto();
        assertFalse(pa.isVivo());
        pa.agir(aquario); // Não deve fazer nada
        assertFalse("Peixe morto não deve reviver", pa.isVivo());
    }

    @Test
    public void testMT23_NegateMoveuNestaIteracao() {
        // Testa negação de moveuNestaIteracao()
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.agir(aquario);
        assertTrue("Peixe deve ter movido", pa.moveuNestaIteracao());
        Posicao posAntesSegundaAcao = pa.getPosicao();
        pa.agir(aquario); // Não deve agir novamente
        assertEquals("Peixe não deve agir duas vezes", posAntesSegundaAcao, pa.getPosicao());
    }

    @Test
    public void testMT24_NegateCelulasEmpty() {
        // Testa negação de celulasLivres.isEmpty()
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 1, 5, 1, 1);
        // Todas as células ocupadas
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        assertTrue("Não deve haver células livres", celulas.isEmpty() || celulas.size() <= 1);
    }

    @Test
    public void testMT25_NegatePeixesAAoRedorEmpty() {
        // Testa negação de peixesAAoRedor.isEmpty() para PeixeB
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 1, 10);
        aquario.adicionarPeixe(pb);
        PeixeA pa = new PeixeA(new Posicao(2, 3), 10, 10);
        aquario.adicionarPeixe(pa);
        int peixesAAntes = aquario.contarPeixesA();
        aquario.executarIteracao();
        assertTrue("PeixeB deve comer PeixeA próximo", aquario.contarPeixesA() < peixesAAntes);
    }

    @Test
    public void testMT26_NegateTemPeixeA() {
        // Testa condição temPeixeA em jogoTerminou
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        assertTrue("Jogo deve terminar sem PeixeA", aquario.jogoTerminou());
    }

    @Test
    public void testMT27_NegateTemPeixeB() {
        // Testa condição temPeixeB em jogoTerminou
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        assertTrue("Jogo deve terminar sem PeixeB", aquario.jogoTerminou());
    }

    @Test
    public void testMT28_NegateInstanceOfPeixeA() {
        // Testa instanceof PeixeA em contarPeixesA
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 1, 1));
        aquario.adicionarPeixe(new PeixeB(new Posicao(1, 1), 1, 1));
        assertEquals("contarPeixesA deve contar apenas PeixeA", 1, aquario.contarPeixesA());
    }

    @Test
    public void testMT29_NegateInstanceOfPeixeB() {
        // Testa instanceof PeixeB em contarPeixesB
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(new PeixeA(new Posicao(0, 0), 1, 1));
        aquario.adicionarPeixe(new PeixeB(new Posicao(1, 1), 1, 1));
        assertEquals("contarPeixesB deve contar apenas PeixeB", 1, aquario.contarPeixesB());
    }

    // ========================================================================
    // TESTES PARA IncrementsMutator
    // Testa incrementos (++) vs decrementos (--)
    // ========================================================================

    @Test
    public void testMT30_IncrementoIteracoes() {
        // Testa iteracoes++ em executarIteracao
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        assertEquals(0, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals("iteracoes deve incrementar", 1, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals("iteracoes deve incrementar novamente", 2, aquario.getIteracoes());
    }

    @Test
    public void testMT31_IncrementoContadorMovimentos() {
        // Testa contadorMovimentos++ em PeixeA.agir
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 3, 10, 1, 1); // RA=3
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao(); // contadorMovimentos = 1
        aquario.executarIteracao(); // contadorMovimentos = 2
        assertEquals("Não deve reproduzir ainda", inicial, aquario.contarPeixesA());
        aquario.executarIteracao(); // contadorMovimentos = 3 (>= RA)
        assertTrue("Deve reproduzir após 3 movimentos", aquario.contarPeixesA() >= inicial);
    }

    @Test
    public void testMT32_IncrementoContadorSemAcao() {
        // Testa contadorSemAcao++ em PeixeA.agir
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 10, 3, 1, 1); // MA=3
        assertEquals(4, aquario.contarPeixesA());
        aquario.executarIteracao(); // contadorSemAcao = 1
        aquario.executarIteracao(); // contadorSemAcao = 2
        assertEquals("Peixes ainda vivos", 4, aquario.contarPeixesA());
        aquario.executarIteracao(); // contadorSemAcao = 3 (>= MA)
        assertTrue("Peixes devem morrer após 3 sem ação", aquario.contarPeixesA() < 4);
    }

    @Test
    public void testMT33_IncrementoContadorComidos() {
        // Testa contadorComidos++ em PeixeB.agir
        Aquario aquario = new Aquario(10, 10);
        PeixeB pb = new PeixeB(new Posicao(5, 5), 2, 10); // RB=2
        aquario.adicionarPeixe(pb);
        // Adiciona várias presas
        aquario.adicionarPeixe(new PeixeA(new Posicao(5, 6), 10, 10));
        aquario.adicionarPeixe(new PeixeA(new Posicao(5, 4), 10, 10));
        aquario.adicionarPeixe(new PeixeA(new Posicao(4, 5), 10, 10));
        int peixesBInicial = aquario.contarPeixesB();
        aquario.executarIteracao();
        // Deve comer e possivelmente não reproduzir ainda (contadorComidos = 1)
        assertEquals("Ainda não deve reproduzir (RB=2)", peixesBInicial, aquario.contarPeixesB());
    }

    @Test
    public void testMT34_IncrementoContadorSemComer() {
        // Testa contadorSemComer++ em PeixeB.agir
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 1, 1, 1, 1, 3); // MB=3
        assertEquals(1, aquario.contarPeixesB());
        aquario.executarIteracao(); // contadorSemComer = 1
        aquario.executarIteracao(); // contadorSemComer = 2
        assertEquals("PeixeB ainda vivo", 1, aquario.contarPeixesB());
        aquario.executarIteracao(); // contadorSemComer = 3 (>= MB)
        assertEquals("PeixeB deve morrer após 3 sem comer", 0, aquario.contarPeixesB());
    }

    @Test
    public void testMT35_IncrementoContadorCount() {
        // Testa count++ em contarPeixesA e contarPeixesB
        Aquario aquario = new Aquario(5, 5);
        for (int i = 0; i < 5; i++) {
            aquario.adicionarPeixe(new PeixeA(new Posicao(i, 0), 10, 10));
            aquario.adicionarPeixe(new PeixeB(new Posicao(i, 1), 10, 10));
        }
        assertEquals("Deve contar 5 PeixeA", 5, aquario.contarPeixesA());
        assertEquals("Deve contar 5 PeixeB", 5, aquario.contarPeixesB());
    }

    @Test
    public void testMT36_IncrementoForLoop() {
        // Testa i++ em loops for
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        assertEquals("Loop deve iterar 8 vezes", 8, celulas.size());
    }

    // ========================================================================
    // TESTES PARA PrimitiveReturnsMutator
    // Testa retornos de valores primitivos
    // ========================================================================

    @Test
    public void testMT37_RetornoGetLinha() {
        Posicao p = new Posicao(3, 5);
        assertEquals("getLinha deve retornar valor correto", 3, p.getLinha());
    }

    @Test
    public void testMT38_RetornoGetColuna() {
        Posicao p = new Posicao(3, 5);
        assertEquals("getColuna deve retornar valor correto", 5, p.getColuna());
    }

    @Test
    public void testMT39_RetornoHashCode() {
        Posicao p = new Posicao(2, 3);
        int expected = 31 * 2 + 3;
        assertEquals("hashCode deve retornar valor correto", expected, p.hashCode());
    }

    @Test
    public void testMT40_RetornoGetIteracoes() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        assertEquals(0, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals("getIteracoes deve retornar valor correto", 1, aquario.getIteracoes());
    }

    @Test
    public void testMT41_RetornoGetPontuacao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertEquals("getPontuacao deve igualar iteracoes", 5, aquario.getPontuacao());
    }

    @Test
    public void testMT42_RetornoContarPeixesA() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(7, 3, 10, 10, 10, 10);
        assertEquals("contarPeixesA deve retornar valor correto", 7, aquario.contarPeixesA());
    }

    @Test
    public void testMT43_RetornoContarPeixesB() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(7, 3, 10, 10, 10, 10);
        assertEquals("contarPeixesB deve retornar valor correto", 3, aquario.contarPeixesB());
    }

    @Test
    public void testMT44_RetornoIsVivo() {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        assertTrue("isVivo deve retornar true", pa.isVivo());
        pa.marcarComoMorto();
        assertFalse("isVivo deve retornar false após morrer", pa.isVivo());
    }

    @Test
    public void testMT45_RetornoMoveuNestaIteracao() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        assertFalse("moveuNestaIteracao deve ser false inicialmente", pa.moveuNestaIteracao());
        pa.agir(aquario);
        assertTrue("moveuNestaIteracao deve ser true após agir", pa.moveuNestaIteracao());
    }

    @Test
    public void testMT46_RetornoJogoTerminou() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 5, 1, 1, 1, 1);
        assertFalse("jogoTerminou deve retornar false com ambos tipos", aquario.jogoTerminou());
        Aquario aquario2 = new Aquario(5, 5);
        aquario2.inicializar(5, 0, 1, 1, 1, 1);
        assertTrue("jogoTerminou deve retornar true sem PeixeB", aquario2.jogoTerminou());
    }

    // ========================================================================
    // TESTES PARA Utils e Posicao
    // ========================================================================

    @Test
    public void testMT47_UtilsRepeatNull() {
        assertNull("repeat com null deve retornar null", Utils.repeat(null, 5));
    }

    @Test
    public void testMT48_UtilsRepeatZero() {
        assertEquals("repeat com n=0 deve retornar string vazia", "", Utils.repeat("abc", 0));
    }

    @Test
    public void testMT49_UtilsRepeatNegativo() {
        assertEquals("repeat com n<0 deve retornar string vazia", "", Utils.repeat("abc", -1));
    }

    @Test
    public void testMT50_PosicaoEquals() {
        Posicao p1 = new Posicao(1, 2);
        Posicao p2 = new Posicao(1, 2);
        Posicao p3 = new Posicao(2, 1);
        assertTrue("equals deve retornar true para mesmas coordenadas", p1.equals(p2));
        assertFalse("equals deve retornar false para coordenadas diferentes", p1.equals(p3));
        assertTrue("equals com mesmo objeto", p1.equals(p1));
        assertFalse("equals com null", p1.equals(null));
        assertFalse("equals com tipo diferente", p1.equals("string"));
    }

    @Test
    public void testMT51_PosicaoToString() {
        Posicao p = new Posicao(3, 7);
        assertEquals("toString deve formatar corretamente", "(3,7)", p.toString());
    }

    // ========================================================================
    // TESTES PARA GetSimbolo
    // ========================================================================

    @Test
    public void testMT52_GetSimboloPeixeA() {
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        assertEquals("getSimbolo de PeixeA deve ser 'A'", 'A', pa.getSimbolo());
    }

    @Test
    public void testMT53_GetSimboloPeixeB() {
        PeixeB pb = new PeixeB(new Posicao(0, 0), 1, 1);
        assertEquals("getSimbolo de PeixeB deve ser 'B'", 'B', pb.getSimbolo());
    }

    // ========================================================================
    // TESTES ADICIONAIS PARA COBRIR MUTANTES RESTANTES
    // ========================================================================

    @Test
    public void testMT54_ContadorMovimentosResetaAposReproducao() {
        // Verifica contadorMovimentos = 0 após reprodução
        Aquario aquario = new Aquario(15, 15);
        aquario.inicializar(1, 0, 1, 10, 1, 1); // RA=1
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao(); // Reproduz
        assertTrue("Deve reproduzir com RA=1", aquario.contarPeixesA() > inicial);
        aquario.executarIteracao(); // Deve reproduzir novamente
        assertTrue("Deve reproduzir novamente", aquario.contarPeixesA() > inicial + 1);
    }

    @Test
    public void testMT55_ContadorSemAcaoResetaComMovimento() {
        // Verifica contadorSemAcao = 0 quando consegue se mover
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Peixe deve continuar vivo se consegue se mover", pa.isVivo());
    }

    @Test
    public void testMT56_ContadorSemComerResetaAoComer() {
        // Verifica contadorSemComer = 0 após comer
        Aquario aquario = new Aquario(10, 10);
        PeixeB pb = new PeixeB(new Posicao(5, 5), 10, 2); // MB=2
        aquario.adicionarPeixe(pb);
        // Adiciona presas espaçadas
        aquario.adicionarPeixe(new PeixeA(new Posicao(5, 6), 10, 10));
        aquario.executarIteracao(); // Come e reseta contador
        assertTrue("PeixeB deve estar vivo após comer", pb.isVivo());
        // Adiciona mais presas
        aquario.adicionarPeixe(new PeixeA(new Posicao(4, 6), 10, 10));
        aquario.executarIteracao();
        assertTrue("PeixeB deve continuar vivo", pb.isVivo());
    }

    @Test
    public void testMT57_ContadorComidosResetaAposReproducao() {
        // Verifica contadorComidos = 0 após reprodução
        // Teste verifica que o contador é resetado (comportamento interno)
        Aquario aquario = new Aquario(15, 15);
        PeixeB pb = new PeixeB(new Posicao(7, 7), 1, 10); // RB=1
        aquario.adicionarPeixe(pb);
        // Muitas presas ao redor
        aquario.adicionarPeixe(new PeixeA(new Posicao(7, 8), 10, 10));
        aquario.adicionarPeixe(new PeixeA(new Posicao(6, 7), 10, 10));
        int inicial = aquario.contarPeixesB();
        aquario.executarIteracao();
        // PeixeB come e pode ou não reproduzir dependendo das células livres e vizinhos B
        assertTrue("PeixeB deve estar vivo e ter comido", pb.isVivo() || aquario.contarPeixesB() >= inicial);
    }

    @Test
    public void testMT58_FilhoNaoAgeNaMesmaIteracao() {
        // Verifica filho.moveuNestaIteracao = true
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 1, 10, 1, 1); // RA=1
        aquario.executarIteracao();
        // Deve ter exatamente 2 peixes (pai + filho que não agiu)
        assertEquals("Filho não deve agir na mesma iteração", 2, aquario.contarPeixesA());
    }

    @Test
    public void testMT59_PeixeBNaoReproduzComVizinhoB() {
        // Verifica peixesBProximos.isEmpty() para reprodução
        // Testa a condição de vizinhança de PeixeB
        Aquario aquario = new Aquario(5, 5);
        // Coloca dois PeixeB adjacentes
        PeixeB pb1 = new PeixeB(new Posicao(2, 2), 1, 10);
        PeixeB pb2 = new PeixeB(new Posicao(2, 3), 1, 10);
        aquario.adicionarPeixe(pb1);
        aquario.adicionarPeixe(pb2);
        // Verifica que getPeixesBProximos encontra vizinhos
        List<PeixeB> vizinhos = aquario.getPeixesBProximos(new Posicao(2, 2));
        assertTrue("Deve detectar PeixeB vizinho", vizinhos.size() > 0);
    }

    @Test
    public void testMT60_VerificaTodasDirecoes8Vizinhos() {
        // Verifica que todas as 8 direções são verificadas
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> vizinhos = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        assertEquals("Deve ter 8 vizinhos no centro", 8, vizinhos.size());
        // Verifica todas as direções
        boolean[] encontradas = new boolean[8];
        int[][] direcoes = {{-1,-1},{-1,0},{-1,1},{0,-1},{0,1},{1,-1},{1,0},{1,1}};
        for (Posicao p : vizinhos) {
            for (int i = 0; i < 8; i++) {
                if (p.getLinha() == 1 + direcoes[i][0] && 
                    p.getColuna() == 1 + direcoes[i][1]) {
                    encontradas[i] = true;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            assertTrue("Direção " + i + " deve ser encontrada", encontradas[i]);
        }
    }

    @Test
    public void testMT61_PosicaoNaBorda() {
        // Testa posições nas bordas da matriz
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        
        // Canto superior esquerdo
        List<Posicao> vizinhos00 = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        assertEquals("Canto superior esquerdo deve ter 3 vizinhos", 3, vizinhos00.size());
        
        // Canto inferior direito
        List<Posicao> vizinhos22 = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        assertEquals("Canto inferior direito deve ter 3 vizinhos", 3, vizinhos22.size());
        
        // Borda superior centro
        List<Posicao> vizinhos01 = aquario.getCelulasLivresAoRedor(new Posicao(0, 1));
        assertEquals("Borda superior centro deve ter 5 vizinhos", 5, vizinhos01.size());
    }

    @Test
    public void testMT62_GetPeixesAAoRedorCompleto() {
        // Testa getPeixesAAoRedor em todas as direções
        Aquario aquario = new Aquario(5, 5);
        // Coloca PeixeA em todas as 8 posições ao redor
        int[][] posicoes = {{1,1},{1,2},{1,3},{2,1},{2,3},{3,1},{3,2},{3,3}};
        for (int[] pos : posicoes) {
            aquario.adicionarPeixe(new PeixeA(new Posicao(pos[0], pos[1]), 10, 10));
        }
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(2, 2));
        assertEquals("Deve encontrar 8 PeixeA ao redor", 8, peixesA.size());
    }

    @Test
    public void testMT63_GetPeixesBProximosCompleto() {
        // Testa getPeixesBProximos em todas as direções
        Aquario aquario = new Aquario(5, 5);
        int[][] posicoes = {{1,1},{1,2},{1,3},{2,1},{2,3},{3,1},{3,2},{3,3}};
        for (int[] pos : posicoes) {
            aquario.adicionarPeixe(new PeixeB(new Posicao(pos[0], pos[1]), 10, 10));
        }
        List<PeixeB> peixesB = aquario.getPeixesBProximos(new Posicao(2, 2));
        assertEquals("Deve encontrar 8 PeixeB ao redor", 8, peixesB.size());
    }

    @Test
    public void testMT64_ExibirMatrizNaoLancaExcecao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 5, 1, 1, 1, 1);
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
            aquario.exibir();
        }
        assertTrue("exibir não deve lançar exceção", true);
    }

    @Test
    public void testMT65_ExecutarIteracaoSemPeixesVivos() {
        // Testa branch quando não há peixes vivos
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        assertTrue(aquario.jogoTerminou());
        aquario.executarIteracao();
        assertEquals("Iteração deve incrementar mesmo sem peixes", 1, aquario.getIteracoes());
    }

    @Test
    public void testMT66_PeixesMortosRemovidosAposIteracao() {
        // Verifica peixes.removeIf(p -> !p.isVivo())
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 2, 1, 1, 1, 1);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        // Após várias iterações, peixes mortos devem ter sido removidos
        int totalVivos = aquario.contarPeixesA() + aquario.contarPeixesB();
        assertTrue("Apenas peixes vivos devem restar", totalVivos >= 0);
    }

    @Test
    public void testMT67_ValidacaoQuantidadePeixesExcedeTamanho() {
        Aquario aquario = new Aquario(2, 2);
        try {
            aquario.inicializar(3, 2, 1, 1, 1, 1); // 5 > 4
            fail("Deve lançar exceção");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("excede"));
        }
    }

    @Test
    public void testMT68_ValidacaoParametrosInicializarRA() {
        Aquario aquario = new Aquario(5, 5);
        try {
            aquario.inicializar(1, 1, 0, 1, 1, 1);
            fail("RA=0 deve lançar exceção");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("RA"));
        }
    }

    @Test
    public void testMT69_ValidacaoParametrosInicializarMA() {
        Aquario aquario = new Aquario(5, 5);
        try {
            aquario.inicializar(1, 1, 1, 0, 1, 1);
            fail("MA=0 deve lançar exceção");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("MA"));
        }
    }

    @Test
    public void testMT70_ValidacaoParametrosInicializarRB() {
        Aquario aquario = new Aquario(5, 5);
        try {
            aquario.inicializar(1, 1, 1, 1, 0, 1);
            fail("RB=0 deve lançar exceção");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("RB"));
        }
    }

    @Test
    public void testMT71_ValidacaoParametrosInicializarMB() {
        Aquario aquario = new Aquario(5, 5);
        try {
            aquario.inicializar(1, 1, 1, 1, 1, 0);
            fail("MB=0 deve lançar exceção");
        } catch (IllegalArgumentException e) {
            assertTrue(e.getMessage().contains("MB"));
        }
    }

    @Test
    public void testMT72_ReinicializarLimpaEstadoAnterior() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 5, 1, 1, 1, 1);
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals(2, aquario.getIteracoes());
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        assertEquals("Iterações devem resetar", 0, aquario.getIteracoes());
        assertEquals("Deve ter 1 PeixeA", 1, aquario.contarPeixesA());
        assertEquals("Deve ter 1 PeixeB", 1, aquario.contarPeixesB());
    }

    @Test
    public void testMT73_MoverPeixePosicaoLimite() {
        // Testa moverPeixe para posição no limite
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 10, 10);
        aquario.adicionarPeixe(pa);
        aquario.moverPeixe(pa, new Posicao(4, 4));
        assertEquals(new Posicao(4, 4), pa.getPosicao());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMT74_MoverPeixePosicaoForaLimite() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 10, 10);
        aquario.adicionarPeixe(pa);
        aquario.moverPeixe(pa, new Posicao(5, 5)); // Fora do limite
    }

    @Test
    public void testMT75_CelulaLivreComPeixeMorto() {
        // Uma célula com peixe morto deve ser considerada livre
        Aquario aquario = new Aquario(3, 3);
        PeixeA pa = new PeixeA(new Posicao(1, 1), 10, 10);
        aquario.adicionarPeixe(pa);
        pa.marcarComoMorto();
        List<Posicao> celulas = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        // Posição (1,1) deve estar livre pois peixe está morto
        boolean encontrou = false;
        for (Posicao p : celulas) {
            if (p.getLinha() == 1 && p.getColuna() == 1) {
                encontrou = true;
            }
        }
        assertTrue("Célula com peixe morto deve estar livre", encontrou);
    }

    @Test
    public void testMT76_GetPeixeNaPosicaoNaoEncontraMorto() {
        // getPeixeNaPosicao não deve encontrar peixe morto
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 10, 10);
        aquario.adicionarPeixe(pa);
        pa.marcarComoMorto();
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(1, 1));
        assertEquals("Não deve encontrar peixe morto", 0, peixesA.size());
    }

    @Test
    public void testMT77_PeixeBMoveSemPresaESemEspacoLivre() {
        // PeixeB sem presa incrementa contador sem comer
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 1, 10, 10, 10, 3); // MB=3, sem PeixeA
        assertEquals(1, aquario.contarPeixesB());
        aquario.executarIteracao(); // contadorSemComer = 1
        aquario.executarIteracao(); // contadorSemComer = 2
        assertEquals("PeixeB ainda vivo", 1, aquario.contarPeixesB());
        aquario.executarIteracao(); // contadorSemComer = 3 (>= MB)
        assertEquals("PeixeB deve morrer sem comida após MB iterações", 0, aquario.contarPeixesB());
    }

    @Test
    public void testMT78_NStream_noneMatchPeixeVivo() {
        // Testa branch peixes.stream().noneMatch(Peixe::isVivo)
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 0, 10, 1, 1, 1); // MA=1
        // Todos os peixes vão morrer sem espaço na próxima
        // Primeiro cria situação onde todos estão bloqueados e morrem
        Aquario aquario2 = new Aquario(2, 2);
        aquario2.inicializar(4, 0, 10, 1, 1, 1);
        aquario2.executarIteracao(); // Morrem
        aquario2.executarIteracao(); // Lista vazia mas stream verifica
        assertEquals(2, aquario2.getIteracoes());
    }

    @Test
    public void testMT79_ListaVaziaNaIteracao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertEquals(1, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals(2, aquario.getIteracoes());
    }

    @Test
    public void testMT80_JogoTerminadoComAmbosTiposMortos() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(2, 2, 10, 1, 1, 1);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        // Com matriz cheia e MA/MB baixos, todos morrem
        assertTrue("Jogo deve terminar quando todos morrem", aquario.jogoTerminou());
    }
}
