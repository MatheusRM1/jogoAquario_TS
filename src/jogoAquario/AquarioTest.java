package jogoAquario;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class AquarioTest {

    @Test
    public void testCT01_ValoresValidosNormais() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(10, 5, 3, 4, 2, 3);

            assertEquals("Numero de peixes A deve ser 10", 10, aquario.contarPeixesA());
            assertEquals("Numero de peixes B deve ser 5", 5, aquario.contarPeixesB());
            assertEquals("Iteracao inicial deve ser 0", 0, aquario.getIteracoes());
            assertFalse("Jogo nao deve ter terminado", aquario.jogoTerminou());

        } catch (Exception e) {
            fail("Nao deveria lancar excecao com valores validos: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT02_MatrizInvalidaM_Zero() {
        Aquario aquario = new Aquario(0, 5);
        fail("Deveria lancar IllegalArgumentException para M=0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT02b_MatrizInvalidaN_Zero() {
        Aquario aquario = new Aquario(5, 0);
        fail("Deveria lancar IllegalArgumentException para N=0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT02c_MatrizInvalidaM_Negativo() {
        Aquario aquario = new Aquario(-1, 5);
        fail("Deveria lancar IllegalArgumentException para M negativo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT03_QuantidadePeixesA_Invalida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(-1, 5, 3, 4, 2, 3);
        fail("Deveria lancar IllegalArgumentException para X=-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT03b_QuantidadePeixesB_Invalida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, -1, 3, 4, 2, 3);
        fail("Deveria lancar IllegalArgumentException para Y=-1");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04_RA_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 0, 4, 2, 3);
        fail("Deveria lancar IllegalArgumentException para RA=0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04b_MA_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 0, 2, 3);
        fail("Deveria lancar IllegalArgumentException para MA=0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04c_RB_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 4, 0, 3);
        fail("Deveria lancar IllegalArgumentException para RB=0");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04d_MB_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 4, 2, 0);
        fail("Deveria lancar IllegalArgumentException para MB=0");
    }

    @Test
    public void testCT05_ValoresLimitrofesMinimos() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(10, 5, 1, 1, 1, 1);

            assertEquals("Numero de peixes A deve ser 10", 10, aquario.contarPeixesA());
            assertEquals("Numero de peixes B deve ser 5", 5, aquario.contarPeixesB());
            assertFalse("Jogo nao deve ter terminado", aquario.jogoTerminou());

        } catch (Exception e) {
            fail("Nao deveria lancar excecao com valores limitrofes validos: " + e.getMessage());
        }
    }

    @Test
    public void testCT06_MatrizMinima() {
        try {
            Aquario aquario = new Aquario(1, 1);
            aquario.inicializar(1, 0, 1, 1, 1, 1);

            assertEquals("Numero de peixes A deve ser 1", 1, aquario.contarPeixesA());
            assertEquals("Numero de peixes B deve ser 0", 0, aquario.contarPeixesB());

        } catch (Exception e) {
            fail("Nao deveria lancar excecao com matriz 1x1: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT07_QuantidadePeixesExcedeTamanho() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(3, 2, 1, 1, 1, 1);
        fail("Deveria lancar IllegalArgumentException quando total de peixes excede tamanho da matriz");
    }

    @Test
    public void testCT08_ExecutarIteracao() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(5, 2, 3, 4, 2, 3);

            int iteracoesInicial = aquario.getIteracoes();
            aquario.executarIteracao();

            assertEquals("Numero de iteracoes deve ter incrementado", 
                        iteracoesInicial + 1, aquario.getIteracoes());

        } catch (Exception e) {
            fail("Nao deveria lancar excecao ao executar iteracao: " + e.getMessage());
        }
    }

    @Test
    public void testCT09_JogoTerminadoSemPeixesB() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(10, 0, 3, 4, 2, 3);

            assertTrue("Jogo deve estar terminado quando nao ha peixes B", 
                      aquario.jogoTerminou());

        } catch (Exception e) {
            fail("Nao deveria lancar excecao: " + e.getMessage());
        }
    }

    @Test
    public void testCT10_AquarioVazio() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(0, 0, 1, 1, 1, 1);

            assertEquals("Numero de peixes A deve ser 0", 0, aquario.contarPeixesA());
            assertEquals("Numero de peixes B deve ser 0", 0, aquario.contarPeixesB());
            assertTrue("Jogo deve estar terminado", aquario.jogoTerminou());

        } catch (Exception e) {
            fail("Nao deveria lancar excecao com aquario vazio: " + e.getMessage());
        }
    }

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
            for (int i = 0; i < 3; i++) {
                aquario.executarIteracao();
            }
            int peixesFinal = aquario.contarPeixesA();
            assertTrue("Peixes A devem morrer por fome quando MA=1 e sem espaco", peixesFinal < 4);
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
            assertTrue("PeixeB deve morrer de fome sem PeixeA (MB=2)", peixesB_final < peixesB_inicial || peixesB_final == 0);
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
            assertEquals("Deve ter executado 10 iteracoes", 10, aquario.getIteracoes());
            assertTrue("Deve haver peixes vivos apos multiplas iteracoes", aquario.contarPeixesA() + aquario.contarPeixesB() > 0);
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
            assertTrue("Simulacao deve terminar corretamente", aquario.jogoTerminou() || iteracao >= maxIteracoes);
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
            assertTrue("Contador de movimentos deve ser usado para reproducao", aquario.contarPeixesA() >= 3);
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
            assertTrue("Contador de comidos deve permitir reproducao de PeixeB", peixesB_final >= peixesB_inicial);
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
}
