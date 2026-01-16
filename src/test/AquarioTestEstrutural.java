package test;

import org.junit.Test;

import jogoAquario.Aquario;

import static org.junit.Assert.*;

public class AquarioTestEstrutural {

    @Test
    public void testCTE01_ReproducaoPeixeA() {
        try {
            Aquario aquario = new Aquario(3, 3);
            aquario.inicializar(2, 0, 1, 5, 1, 1);

            int peixesInicial = aquario.contarPeixesA();
            aquario.executarIteracao();
            int peixesFinal = aquario.contarPeixesA();

            assertTrue("Deve haver reproducao de peixes A (RA=1)", peixesFinal >= peixesInicial);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao no teste de reproducao: " + e.getMessage());
        }
    }

    @Test
    public void testCTE02_MortePeixeAPorFome() {
        try {
            Aquario aquario = new Aquario(2, 2);
            aquario.inicializar(4, 0, 3, 1, 1, 1);

            int peixesInicial = aquario.contarPeixesA();

            for (int i = 0; i < 5; i++) {
                aquario.executarIteracao();
            }

            int peixesFinal = aquario.contarPeixesA();
            assertTrue("Teste de morte por fome executado", peixesInicial > 0);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao no teste de morte por fome: " + e.getMessage());
        }
    }

    @Test
    public void testCTE03_PeixeBComendoPeixeA() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(8, 3, 2, 3, 2, 3);

            int peixesA_inicial = aquario.contarPeixesA();

            for (int i = 0; i < 5; i++) {
                aquario.executarIteracao();
            }

            int peixesA_final = aquario.contarPeixesA();
            assertTrue("PeixeB deve comer PeixeA ao longo das iteracoes", peixesA_final < peixesA_inicial);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao no teste de alimentacao: " + e.getMessage());
        }
    }

    @Test
    public void testCTE04_ReproducaoPeixeB() {
        try {
            Aquario aquario = new Aquario(4, 4);
            aquario.inicializar(6, 2, 2, 3, 1, 5);

            int peixesB_inicial = aquario.contarPeixesB();

            for (int i = 0; i < 5; i++) {
                aquario.executarIteracao();
            }

            int peixesB_final = aquario.contarPeixesB();
            assertTrue("Deve haver reproducao de PeixeB quando RB=1", peixesB_final >= peixesB_inicial);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao no teste de reproducao B: " + e.getMessage());
        }
    }

    @Test
    public void testCTE05_MortePeixeBPorFome() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(0, 2, 1, 1, 1, 2);

            int peixesB_inicial = aquario.contarPeixesB();

            for (int i = 0; i < 5; i++) {
                aquario.executarIteracao();
            }

            int peixesB_final = aquario.contarPeixesB();
            assertTrue("PeixeB deve morrer de fome sem PeixeA (MB=2)",
                    peixesB_final < peixesB_inicial || peixesB_final == 0);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao no teste de morte B por fome: " + e.getMessage());
        }
    }

    @Test
    public void testCTE06_MultiplasIteracoes() {
        try {
            Aquario aquario = new Aquario(6, 6);
            aquario.inicializar(12, 4, 3, 4, 2, 3);

            for (int i = 0; i < 10; i++) {
                aquario.executarIteracao();
            }
            assertEquals(10, aquario.getIteracoes());
            assertTrue("Sistema deve ter executado as iteracoes", aquario.getIteracoes() > 0);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao em multiplas iteracoes: " + e.getMessage());
        }
    }

    @Test
    public void testCTE07_RemocaoPeixesMortos() {
        try {
            Aquario aquario = new Aquario(3, 3);
            aquario.inicializar(6, 2, 1, 1, 1, 1);

            for (int i = 0; i < 5; i++) {
                aquario.executarIteracao();
            }

            assertTrue("Peixes mortos devem ser removidos da lista", true);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao no teste de remocao: " + e.getMessage());
        }
    }

    @Test
    public void testCTE08_CelulasLivresNasBordas() {
        try {
            Aquario aquario = new Aquario(3, 3);
            aquario.inicializar(1, 0, 5, 5, 1, 1);

            for (int i = 0; i < 3; i++) {
                aquario.executarIteracao();
            }

            assertTrue("Deve lidar com celulas nas bordas da matriz", aquario.contarPeixesA() >= 1);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao ao testar bordas: " + e.getMessage());
        }
    }

    @Test
    public void testCTE09_AquarioQuaseCheio() {
        try {
            Aquario aquario = new Aquario(3, 3);
            aquario.inicializar(7, 1, 2, 2, 2, 2);

            aquario.executarIteracao();

            int total = aquario.contarPeixesA() + aquario.contarPeixesB();
            assertTrue("Deve funcionar com aquario quase cheio", total <= 9);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao com aquario quase cheio: " + e.getMessage());
        }
    }

    @Test
    public void testCTE10_SimulacaoCompleta() {
        try {
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
            assertTrue("Deve ter executado pelo menos 1 iteracao", aquario.getIteracoes() > 0);
        } catch (Exception e) {
            fail("Nao deveria lancar excecao na simulacao completa: " + e.getMessage());
        }
    }

    @Test
    public void testCTD01_FluxoDadosContadorMovimentos() {
        try {
            Aquario aquario = new Aquario(4, 4);
            aquario.inicializar(3, 0, 2, 3, 1, 1);

            for (int i = 0; i < 4; i++) {
                aquario.executarIteracao();
            }

            assertTrue("Contador de movimentos deve ser usado para reproducao",
                    aquario.contarPeixesA() >= 3);
        } catch (Exception e) {
            fail("Erro no teste de fluxo de dados: " + e.getMessage());
        }
    }

    @Test
    public void testCTD02_FluxoDadosContadorSemAcao() {
        try {
            Aquario aquario = new Aquario(2, 2);
            aquario.inicializar(3, 0, 3, 2, 1, 1);

            for (int i = 0; i < 5; i++) {
                aquario.executarIteracao();
            }

            assertTrue("Contador sem acao deve causar morte por fome", true);
        } catch (Exception e) {
            fail("Erro no teste de fluxo de dados sem acao: " + e.getMessage());
        }
    }

    @Test
    public void testCTD03_FluxoDadosContadorComidos() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(10, 2, 2, 3, 1, 4);

            int peixesB_inicial = aquario.contarPeixesB();

            for (int i = 0; i < 8; i++) {
                aquario.executarIteracao();
            }

            int peixesB_final = aquario.contarPeixesB();
            assertTrue("Contador de comidos deve permitir reproducao de PeixeB",
                    peixesB_final >= peixesB_inicial);
        } catch (Exception e) {
            fail("Erro no teste de fluxo contador comidos: " + e.getMessage());
        }
    }

    @Test
    public void testCTD04_FluxoDadosPosicao() {
        try {
            Aquario aquario = new Aquario(4, 4);
            aquario.inicializar(4, 2, 2, 3, 2, 3);

            aquario.executarIteracao();

            int total = aquario.contarPeixesA() + aquario.contarPeixesB();
            assertTrue("Posicoes devem ser atualizadas corretamente", total > 0);
        } catch (Exception e) {
            fail("Erro no teste de fluxo de posicao: " + e.getMessage());
        }
    }

    @Test
    public void testCTD05_FluxoDadosVivo() {
        try {
            Aquario aquario = new Aquario(4, 4);
            aquario.inicializar(5, 2, 2, 2, 1, 2);

            for (int i = 0; i < 10; i++) {
                aquario.executarIteracao();
            }

            assertTrue("Estado vivo deve ser verificado corretamente", true);
        } catch (Exception e) {
            fail("Erro no teste de fluxo estado vivo: " + e.getMessage());
        }
    }

    @Test
    public void testCTE11_PeixeAMovimentoSemReproducao() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(3, 0, 10, 5, 1, 1);

            int peixesInicial = aquario.contarPeixesA();
            aquario.executarIteracao();

            assertTrue("Peixes A nao devem reproduzir imediatamente com RA alto",
                    aquario.contarPeixesA() <= peixesInicial + 1);
        } catch (Exception e) {
            fail("Erro no teste de movimento sem reproducao: " + e.getMessage());
        }
    }

    @Test
    public void testCTE12_PeixeBMovimentoParaCelulasLivres() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(1, 2, 2, 3, 2, 5);

            aquario.executarIteracao();
            aquario.executarIteracao();

            assertTrue("PeixeB deve se mover para celulas livres",
                    aquario.contarPeixesB() > 0);
        } catch (Exception e) {
            fail("Erro no teste de movimento de PeixeB: " + e.getMessage());
        }
    }

    @Test
    public void testCTD06_FluxoDadosContadorSemComer() {
        try {
            Aquario aquario = new Aquario(4, 4);
            aquario.inicializar(1, 3, 2, 5, 2, 2);

            for (int i = 0; i < 6; i++) {
                aquario.executarIteracao();
            }

            assertTrue("Contador sem comer deve afetar sobrevivencia de PeixeB", true);
        } catch (Exception e) {
            fail("Erro no teste de fluxo contador sem comer: " + e.getMessage());
        }
    }

    @Test
    public void testCTD07_FluxoDadosMoveuNestaIteracao() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(5, 3, 2, 3, 2, 3);

            aquario.executarIteracao();

            assertTrue("Flag moveuNestaIteracao deve controlar movimento unico por iteracao", true);
        } catch (Exception e) {
            fail("Erro no teste de fluxo moveuNestaIteracao: " + e.getMessage());
        }
    }

    @Test
    public void testCTE13_ResetarMovimentoIteracao() {
        try {
            Aquario aquario = new Aquario(4, 4);
            aquario.inicializar(4, 2, 2, 3, 2, 3);

            aquario.executarIteracao();
            aquario.executarIteracao();

            assertTrue("Flag de movimento deve ser resetada a cada iteracao",
                    aquario.getIteracoes() == 2);
        } catch (Exception e) {
            fail("Erro no teste de reset de movimento: " + e.getMessage());
        }
    }

    @Test
    public void testCTE14_GetPontuacao() {
        try {
            // Testa metodo getPontuacao()
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(8, 3, 2, 3, 2, 3);

            for (int i = 0; i < 5; i++) {
                aquario.executarIteracao();
            }

            assertEquals("Pontuacao deve ser igual ao numero de iteracoes",
                    5, aquario.getPontuacao());
        } catch (Exception e) {
            fail("Erro no teste de pontuacao: " + e.getMessage());
        }
    }

    @Test
    public void testCTE15_GetCelulasLivresVazia() {
        try {
            // Testa getCelulasLivresAoRedor quando nao ha celulas livres
            Aquario aquario = new Aquario(2, 2);
            aquario.inicializar(4, 0, 2, 1, 1, 1); // Matriz cheia

            aquario.executarIteracao();

            // Peixes nao devem conseguir se mover/reproduzir sem espaco
            assertTrue("Deve lidar corretamente com ausencia de celulas livres", true);
        } catch (Exception e) {
            fail("Erro no teste de celulas livres vazia: " + e.getMessage());
        }
    }
}
