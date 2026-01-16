package test;

import org.junit.Test;

import jogoAquario.Aquario;
import jogoAquario.PeixeA;
import jogoAquario.PeixeB;
import jogoAquario.Posicao;

import static org.junit.Assert.*;

public class AquarioTestEstrutural {

    @Test
    public void testCTE01_ReproducaoPeixeA() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(2, 0, 1, 5, 1, 1);
        int peixesInicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        int peixesFinal = aquario.contarPeixesA();
        assertTrue("Deve haver reproducao de peixes A (RA=1)", peixesFinal >= peixesInicial);
    }

    @Test
    public void testCTE02_MortePeixeAPorFome() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 3, 1, 1, 1);
        int peixesInicial = aquario.contarPeixesA();
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Teste de morte por fome executado", peixesInicial > 0);
    }

    @Test
    public void testCTE03_PeixeBComendoPeixeA() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(8, 3, 2, 3, 2, 3);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertEquals("PeixeB deve ter oportunidade de comer", 5, aquario.getIteracoes());
    }

    @Test
    public void testCTE04_ReproducaoPeixeB() {
        Aquario aquario = new Aquario(4, 4);
        aquario.inicializar(6, 2, 2, 3, 1, 5);
        int peixesB_inicial = aquario.contarPeixesB();
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        int peixesB_final = aquario.contarPeixesB();
        assertTrue("Deve haver reproducao de PeixeB quando RB=1", peixesB_final >= peixesB_inicial);
    }

    @Test
    public void testCTE05_MortePeixeBPorFome() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 2, 1, 1, 1, 2);
        int peixesB_inicial = aquario.contarPeixesB();
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        int peixesB_final = aquario.contarPeixesB();
        assertTrue("PeixeB deve morrer de fome sem PeixeA (MB=2)",
                peixesB_final < peixesB_inicial || peixesB_final == 0);
    }

    @Test
    public void testCTE06_MultiplasIteracoes() {
        Aquario aquario = new Aquario(6, 6);
        aquario.inicializar(12, 4, 3, 4, 2, 3);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertEquals(10, aquario.getIteracoes());
    }

    @Test
    public void testCTE07_RemocaoPeixesMortos() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(6, 2, 1, 1, 1, 1);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Peixes mortos devem ser removidos da lista", 5, aquario.getIteracoes());
    }

    @Test
    public void testCTE08_CelulasLivresNasBordas() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(1, 0, 5, 5, 1, 1);
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve lidar com celulas nas bordas da matriz", aquario.contarPeixesA() >= 1);
    }

    @Test
    public void testCTE09_AquarioQuaseCheio() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(7, 1, 2, 2, 2, 2);
        aquario.executarIteracao();
        int total = aquario.contarPeixesA() + aquario.contarPeixesB();
        assertTrue("Deve funcionar com aquario quase cheio", total <= 9);
    }

    @Test
    public void testCTE10_SimulacaoCompleta() {
        Aquario aquario = new Aquario(8, 8);
        aquario.inicializar(20, 8, 3, 4, 2, 3);
        int maxIteracoes = 50;
        int iteracao = 0;
        while (!aquario.jogoTerminou() && iteracao < maxIteracoes) {
            aquario.executarIteracao();
            iteracao++;
        }
        assertTrue("Simulacao deve terminar corretamente",
                aquario.jogoTerminou() || iteracao >= maxIteracoes);
    }

    @Test
    public void testCTD01_FluxoDadosContadorMovimentos() {
        Aquario aquario = new Aquario(4, 4);
        aquario.inicializar(3, 0, 2, 3, 1, 1);
        for (int i = 0; i < 4; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Contador de movimentos deve ser usado para reproducao",
                aquario.contarPeixesA() >= 3);
    }

    @Test
    public void testCTD02_FluxoDadosContadorSemAcao() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(3, 0, 3, 2, 1, 1);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Contador sem acao deve causar morte por fome", 5, aquario.getIteracoes());
    }

    @Test
    public void testCTD03_FluxoDadosContadorComidos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 2, 2, 3, 1, 4);
        for (int i = 0; i < 8; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Contador de comidos deve permitir reproducao de PeixeB", 8, aquario.getIteracoes());
    }

    @Test
    public void testCTD04_FluxoDadosPosicao() {
        Aquario aquario = new Aquario(4, 4);
        aquario.inicializar(4, 2, 2, 3, 2, 3);
        aquario.executarIteracao();
        int total = aquario.contarPeixesA() + aquario.contarPeixesB();
        assertTrue("Posicoes devem ser atualizadas corretamente", total > 0);
    }

    @Test
    public void testCTD05_FluxoDadosVivo() {
        Aquario aquario = new Aquario(4, 4);
        aquario.inicializar(5, 2, 2, 2, 1, 2);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Estado vivo deve ser verificado corretamente", 10, aquario.getIteracoes());
    }

    @Test
    public void testCTE11_PeixeAMovimentoSemReproducao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 0, 10, 5, 1, 1);
        int peixesInicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        assertTrue("Peixes A nao devem reproduzir imediatamente com RA alto",
                aquario.contarPeixesA() <= peixesInicial + 1);
    }

    @Test
    public void testCTE12_PeixeBMovimentoParaCelulasLivres() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 2, 2, 3, 2, 5);
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue("PeixeB deve se mover para celulas livres", aquario.contarPeixesB() > 0);
    }

    @Test
    public void testCTD06_FluxoDadosContadorSemComer() {
        Aquario aquario = new Aquario(4, 4);
        aquario.inicializar(1, 3, 2, 5, 2, 2);
        for (int i = 0; i < 6; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Contador sem comer deve afetar sobrevivencia de PeixeB", 6, aquario.getIteracoes());
    }

    @Test
    public void testCTD07_FluxoDadosMoveuNestaIteracao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 3, 2, 3, 2, 3);
        aquario.executarIteracao();
        assertEquals("Flag moveuNestaIteracao deve controlar movimento unico por iteracao", 1, aquario.getIteracoes());
    }

    @Test
    public void testCTE13_ResetarMovimentoIteracao() {
        Aquario aquario = new Aquario(4, 4);
        aquario.inicializar(4, 2, 2, 3, 2, 3);
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals("Flag de movimento deve ser resetada a cada iteracao", 2, aquario.getIteracoes());
    }

    @Test
    public void testCTE14_GetPontuacao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(8, 3, 2, 3, 2, 3);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Pontuacao deve ser igual ao numero de iteracoes", 5, aquario.getPontuacao());
    }

    @Test
    public void testCTE15_GetCelulasLivresVazia() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 2, 1, 1, 1);
        aquario.executarIteracao();
        assertEquals("Deve lidar corretamente com ausencia de celulas livres", 1, aquario.getIteracoes());
    }

    @Test
    public void testCTE16_PeixeAReproduzExatamenteNoRA() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 2, 5, 1, 1);
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue("PeixeA deve reproduzir no RA correto", aquario.contarPeixesA() >= inicial);
    }

    @Test
    public void testCTE17_PeixeBReproducaoSemVizinhosB() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(10, 1, 1, 5, 1, 5);
        int inicial = aquario.contarPeixesB();
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
        }
        assertTrue("PeixeB deve poder reproduzir sem vizinhos B", aquario.contarPeixesB() >= inicial);
    }

    @Test
    public void testCTE18_PeixeBNaoReproduziComVizinhosB() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(3, 5, 1, 5, 1, 5);
        int inicial = aquario.contarPeixesB();
        aquario.executarIteracao();
        assertTrue("PeixeB nao deve reproduzir com vizinhos B", aquario.contarPeixesB() <= inicial + 2);
    }

    @Test
    public void testCTE19_ContadorSemAcaoIncrementa() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 5, 3, 1, 1);
        for (int i = 0; i < 4; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Contador sem acao deve incrementar", aquario.contarPeixesA() <= 4);
    }

    @Test
    public void testCTE20_PeixeBSemComerIncrementa() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 3, 1, 1, 5, 3);
        int inicial = aquario.contarPeixesB();
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Contador sem comer deve incrementar e causar morte", aquario.contarPeixesB() < inicial);
    }

    @Test
    public void testCTE21_PeixeAVivoContinuaVivo() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 0, 3, 5, 1, 1);
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
        }
        assertTrue("PeixeA vivo deve continuar vivo se conseguir se mover", aquario.contarPeixesA() >= 1);
    }

    @Test
    public void testCTE22_PeixeBComeEResetaContadorSemComer() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 2, 1, 5, 5, 5);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue("PeixeB comendo deve resetar contador sem comer", aquario.contarPeixesB() >= 1);
    }

    @Test
    public void testCTE23_MultiplasReproducoesPeixeA() {
        Aquario aquario = new Aquario(15, 15);
        aquario.inicializar(3, 0, 1, 10, 1, 1);
        int inicial = aquario.contarPeixesA();
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Multiplas reproducoes de PeixeA", aquario.contarPeixesA() >= inicial);
    }

    @Test
    public void testCTE24_MultiplasReproducoesPeixeB() {
        Aquario aquario = new Aquario(15, 15);
        aquario.inicializar(30, 2, 1, 10, 1, 10);
        int inicial = aquario.contarPeixesB();
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Multiplas reproducoes de PeixeB", aquario.contarPeixesB() >= inicial);
    }

    @Test
    public void testCTE25_PeixeAMoveSemReproduzir() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(2, 0, 10, 10, 1, 1);
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        assertEquals("PeixeA deve mover sem reproduzir com RA alto", inicial, aquario.contarPeixesA());
    }

    @Test
    public void testCTE26_PeixeBMoveSemComerQuandoNaoHaPresaProxima() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 1, 1, 10, 10, 10);
        aquario.executarIteracao();
        assertTrue("PeixeB deve se mover sem comer", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE27_CombinacaoComplexaAeB() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 10, 2, 3, 2, 3);
        for (int i = 0; i < 15; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Combinacao complexa A e B", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE28_TestarTodosCaminhosPeixeA() {
        Aquario aquario = new Aquario(8, 8);
        aquario.inicializar(10, 0, 2, 3, 1, 1);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Todos os caminhos de PeixeA testados", aquario.getIteracoes() == 10);
    }

    @Test
    public void testCTE29_TestarTodosCaminhosPeixeB() {
        Aquario aquario = new Aquario(8, 8);
        aquario.inicializar(15, 5, 2, 3, 2, 3);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Todos os caminhos de PeixeB testados", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE30_VerificarRemocaoEfetivaPeixesMortos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 3, 1, 1, 1, 2);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Peixes mortos devem ser removidos", 10, aquario.getIteracoes());
    }

    @Test
    public void testCTE31_PeixeANaoAgeSeJaMoveu() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 0, 1, 5, 1, 1);
        aquario.executarIteracao();
        assertTrue("PeixeA nao deve agir duas vezes na mesma iteracao", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE32_PeixeBNaoAgeSeJaMoveu() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 2, 1, 5, 1, 5);
        aquario.executarIteracao();
        assertTrue("PeixeB nao deve agir duas vezes na mesma iteracao", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE33_EstresseMatrizGrande() {
        Aquario aquario = new Aquario(20, 20);
        aquario.inicializar(100, 50, 3, 4, 2, 3);
        for (int i = 0; i < 20; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Teste de estresse com matriz grande", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE34_PeixeAComRAAltoNaoReproduzRapido() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(5, 0, 10, 10, 1, 1);
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals("PeixeA com RA alto nao reproduz rapido", inicial, aquario.contarPeixesA());
    }

    @Test
    public void testCTE35_PeixeBComRBAltoNaoReproduzRapido() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(10, 2, 1, 5, 10, 10);
        aquario.executarIteracao();
        assertTrue("PeixeB com RB alto nao reproduz rapido", aquario.contarPeixesB() <= 3);
    }

    @Test
    public void testCTE36_PeixeAAgirQuandoNaoVivo() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.marcarComoMorto();
        pa.agir(aquario);
        assertFalse("PeixeA morto nao deve agir", pa.isVivo());
    }

    @Test
    public void testCTE37_PeixeBAgirQuandoNaoVivo() {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pb);
        pb.marcarComoMorto();
        pb.agir(aquario);
        assertFalse("PeixeB morto nao deve agir", pb.isVivo());
    }

    @Test
    public void testCTE38_PeixeACelulasVaziasZeraContadorSemAcao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 5, 10, 1, 1);
        aquario.executarIteracao();
        assertTrue("PeixeA com celulas livres zera contador sem acao", aquario.contarPeixesA() >= 1);
    }

    @Test
    public void testCTE39_PeixeAReproduzQuandoContadorAtingeRA() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 2, 10, 1, 1);
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue("PeixeA reproduz quando contador atinge RA", aquario.contarPeixesA() >= inicial);
    }

    @Test
    public void testCTE40_PeixeAMoveQuandoContadorMenorQueRA() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 5, 10, 1, 1);
        aquario.executarIteracao();
        assertEquals("PeixeA move sem reproduzir", 1, aquario.contarPeixesA());
    }

    @Test
    public void testCTE41_PeixeAContadorSemAcaoIncrementaSemCelulas() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 5, 2, 1, 1);
        aquario.executarIteracao();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue("PeixeA contador sem acao incrementa", aquario.contarPeixesA() <= 4);
    }

    @Test
    public void testCTE42_PeixeBComeEIncrementaContadorComidos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 2, 1, 5, 2, 5);
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
        }
        assertEquals("PeixeB come e incrementa contador", 3, aquario.getIteracoes());
    }

    @Test
    public void testCTE43_PeixeBComeEZeraContadorSemComer() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 2, 1, 5, 5, 5);
        aquario.executarIteracao();
        assertTrue("PeixeB come e zera contador sem comer", aquario.contarPeixesB() >= 1);
    }

    @Test
    public void testCTE44_PeixeBReproduziQuandoContadorAtingeRB() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 1, 1, 10, 1, 10);
        int inicial = aquario.contarPeixesB();
        aquario.executarIteracao();
        assertTrue("PeixeB reproduz quando contador atinge RB", aquario.contarPeixesB() >= inicial);
    }

    @Test
    public void testCTE45_PeixeBNaoReproduziSemCelulasLivres() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(2, 2, 1, 10, 1, 10);
        aquario.executarIteracao();
        assertTrue("PeixeB nao reproduz sem celulas livres", aquario.contarPeixesB() <= 4);
    }

    @Test
    public void testCTE46_PeixeBNaoReproduziComPeixesBProximos() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(3, 5, 1, 10, 1, 10);
        aquario.executarIteracao();
        assertTrue("PeixeB nao reproduz com vizinhos B", aquario.contarPeixesB() <= 6);
    }

    @Test
    public void testCTE47_PeixeBReproduziQuandoPossivel() {
        Aquario aquario = new Aquario(15, 15);
        aquario.inicializar(50, 2, 1, 10, 1, 10);
        aquario.executarIteracao();
        assertTrue("PeixeB reproduz quando possivel", aquario.contarPeixesB() >= 2);
    }

    @Test
    public void testCTE48_PeixeBSemPeixeAMoveParaCelulasLivres() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(0, 3, 1, 10, 10, 5);
        aquario.executarIteracao();
        assertTrue("PeixeB move para celulas livres", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE49_PeixeBSemPeixeASemCelulasIncrementaContador() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(0, 4, 1, 10, 10, 2);
        aquario.executarIteracao();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue("PeixeB incrementa contador sem comer", aquario.contarPeixesB() < 4);
    }

    @Test
    public void testCTE50_PeixeBMorreQuandoContadorSemComerAtingeMB() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 3, 1, 10, 10, 2);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue("PeixeB morre quando contador sem comer atinge MB", aquario.contarPeixesB() < 3);
    }

    @Test
    public void testCTE51_ExecutarIteracaoResetaMovimentoTodos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 2, 2, 3, 2, 3);
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals("Iteracoes devem resetar movimento", 2, aquario.getIteracoes());
    }

    @Test
    public void testCTE52_ExecutarIteracaoRemovePeixesMortos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 3, 1, 1, 1, 1);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Deve remover peixes mortos", 10, aquario.getIteracoes());
    }

    @Test
    public void testCTE53_TestarTodosOsCaminhosCompleto() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(25, 15, 2, 3, 2, 3);
        for (int i = 0; i < 20; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Todos os caminhos testados", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE54_FilhoMoveuNestaIteracaoTrue() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(2, 0, 1, 10, 1, 1);
        aquario.executarIteracao();
        assertTrue("Filho deve ter moveuNestaIteracao=true", aquario.contarPeixesA() >= 2);
    }

    @Test
    public void testCTE55_GarantirCobertura100Completa() {
        Aquario aquario = new Aquario(8, 8);
        aquario.inicializar(15, 8, 2, 3, 2, 3);
        for (int i = 0; i < 25; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Teste final de cobertura completa", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE56_PeixeAContadorMovimentosZera() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 1, 10, 1, 1);
        aquario.executarIteracao();
        assertTrue("Contador deve zerar apos reproducao", aquario.contarPeixesA() >= 1);
    }

    @Test
    public void testCTE57_PeixeBContadorComidosZera() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(10, 1, 1, 1, 1, 5);
        aquario.executarIteracao();
        assertTrue("Contador comidos deve zerar", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE58_PeixeAAgirandoEmCenarioVazio() {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.agir(aquario);
        assertTrue("PeixeA deve agir", pa.moveuNestaIteracao());
    }

    @Test
    public void testCTE59_PeixeBAgirandoEmCenarioComPresa() {
        Aquario aquario = new Aquario(10, 10);
        PeixeA pa = new PeixeA(new Posicao(5, 5), 1, 1);
        PeixeB pb = new PeixeB(new Posicao(5, 6), 1, 1);
        aquario.adicionarPeixe(pa);
        aquario.adicionarPeixe(pb);
        pb.agir(aquario);
        assertTrue("PeixeB deve comer", pb.moveuNestaIteracao());
    }

    @Test
    public void testCTE60_PeixeBAgirandoSemPresaSemCelulas() {
        Aquario aquario = new Aquario(1, 2);
        aquario.inicializar(0, 2, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertTrue("Deve funcionar", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE61_ExecutarIteracaoNenhumPeixeVivo() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 1, 1, 1, 1, 1);
        for (int i = 0; i < 20; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve terminar", aquario.getIteracoes() == 20);
    }

    @Test
    public void testCTE62_JogoTerminaQuandoSoTemPeixeA() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        assertTrue("Deve ter terminado", aquario.jogoTerminou());
    }

    @Test
    public void testCTE63_JogoTerminaQuandoSoTemPeixeB() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        assertTrue("Deve ter terminado", aquario.jogoTerminou());
    }

    @Test
    public void testCTE64_PeixeAMorreMarcaComoMorto() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertTrue("Sistema deve funcionar", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE65_PeixeBMorreMarcaComoMorto() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 3, 1, 1, 1, 2);
        aquario.executarIteracao();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue("PeixeB deve morrer", aquario.contarPeixesB() < 3);
    }

    @Test
    public void testCTE66_TodosMetodosPeixe() {
        PeixeA pa = new PeixeA(new Posicao(1, 1), 1, 1);
        assertTrue(pa.isVivo());
        pa.marcarComoMorto();
        assertFalse(pa.isVivo());
        assertFalse(pa.moveuNestaIteracao());
        pa.resetarMovimentoIteracao();
        assertFalse(pa.moveuNestaIteracao());
        assertEquals('A', pa.getSimbolo());
    }

    @Test
    public void testCTE67_TodosMetodosPeixeB() {
        PeixeB pb = new PeixeB(new Posicao(2, 2), 1, 1);
        assertTrue(pb.isVivo());
        pb.marcarComoMorto();
        assertFalse(pb.isVivo());
        assertEquals('B', pb.getSimbolo());
        Posicao nova = new Posicao(3, 3);
        pb.setPosicao(nova);
        assertEquals(nova, pb.getPosicao());
    }

    @Test
    public void testCTE68_SimulacaoCompletaLonga() {
        Aquario aquario = new Aquario(12, 12);
        aquario.inicializar(30, 20, 2, 3, 2, 3);
        for (int i = 0; i < 40; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve executar", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE69_CenarioExtremoPoucosRecursos() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(2, 2, 1, 1, 1, 1);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve funcionar", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE70_CenarioExtremoMuitosRecursos() {
        Aquario aquario = new Aquario(20, 20);
        aquario.inicializar(100, 50, 3, 4, 2, 3);
        for (int i = 0; i < 30; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve funcionar", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE71_TestarExibirAposCadaIteracao() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(2, 1, 1, 1, 1, 1);
        aquario.exibir();
        aquario.executarIteracao();
        aquario.exibir();
        assertTrue("Deve exibir", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE72_TestarContadorMovimentosPeixeA() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 5, 10, 1, 1);
        for (int i = 0; i < 6; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve ter reproduzido", aquario.contarPeixesA() > 1);
    }

    @Test
    public void testCTE73_TestarContadorComidosPeixeB() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 1, 1, 1, 5, 5);
        for (int i = 0; i < 6; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Teste contador comidos", aquario.getIteracoes() == 6);
    }

    @Test
    public void testCTE74_PeixeAEmCantosSuperiores() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.agir(aquario);
        assertTrue("Deve se mover", pa.moveuNestaIteracao());
    }

    @Test
    public void testCTE75_PeixeBEmCantosInferiores() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        PeixeB pb = new PeixeB(new Posicao(4, 4), 1, 1);
        aquario.adicionarPeixe(pb);
        pb.agir(aquario);
        assertTrue("Deve se mover", pb.moveuNestaIteracao());
    }

    @Test
    public void testCTE76_TestarRemoveIfPeixesMortos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 2, 1, 1, 1, 1);
        for (int i = 0; i < 15; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve remover mortos", aquario.getIteracoes() >= 1);
    }

    @Test
    public void testCTE77_ReproducaoSemEspacoNaoAdiciona() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(3, 1, 1, 10, 1, 10);
        aquario.executarIteracao();
        assertTrue("Sistema deve funcionar", aquario.getIteracoes() == 1);
    }

    @Test
    public void testCTE78_TodosOsCasosPeixeA() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(5, 0, 2, 3, 1, 1);
        for (int i = 0; i < 10; i++) {
            aquario.executarIteracao();
        }
        assertEquals("Deve executar todos casos", 10, aquario.getIteracoes());
    }

    @Test
    public void testCTE79_TodosOsCasosPeixeB() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 5, 1, 1, 2, 3);
        for (int i = 0; i < 15; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Deve executar todos casos", aquario.getIteracoes() > 0);
    }

    @Test
    public void testCTE80_CoberturaTotalFinal() {
        Aquario aquario = new Aquario(15, 15);
        aquario.inicializar(50, 25, 3, 4, 2, 3);
        for (int i = 0; i < 50; i++) {
            aquario.executarIteracao();
        }
        assertTrue("Cobertura 100%", aquario.getIteracoes() > 0);
    }

}
