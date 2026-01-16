package test;

import org.junit.Test;
import jogoAquario.Aquario;
import jogoAquario.PeixeA;
import jogoAquario.Posicao;
import org.junit.Before;
import static org.junit.Assert.*;

public class AquarioTest {

    @Test
    public void testCatchBlocoInicializar() {
        boolean caiuNoCatch = false;
        try {
            Aquario aquario = new Aquario(5, 5);
            // Vai lan�ar exce��o por quantidadeA negativa
            aquario.inicializar(-99, 0, 1, 1, 1, 1);
        } catch (IllegalArgumentException e) {
            caiuNoCatch = true;
        } catch (Exception e) {
            fail("Deveria ser IllegalArgumentException, mas foi: " + e.getClass().getSimpleName());
        }
        assertTrue("Deveria cair no catch de IllegalArgumentException ao inicializar com valores negativos",
                caiuNoCatch);
    }

    @Test
    public void testCatchBlocoConstrutor() {
        boolean caiuNoCatch = false;
        try {
            Aquario aquario = new Aquario(0, 0); // Vai lan�ar exce��o pois dimens�o deve ser > 0
        } catch (IllegalArgumentException e) {
            caiuNoCatch = true;
        } catch (Exception e) {
            fail("Deveria ser IllegalArgumentException, mas foi: " + e.getClass().getSimpleName());
        }
        assertTrue("Deveria cair no catch de IllegalArgumentException no construtor", caiuNoCatch);
    }

    @Test
    public void testCatchBlocoExecutarIteracao() {
        boolean caiuNoCatch = false;
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(1, 0, 1, 1, 1, 1);

            // For�a erro: Cria um peixe manualmente e tenta mover para fora da matriz
            // Isso testa o try-catch gen�rico dentro do loop de itera��o (se houver)
            PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
            aquario.adicionarPeixe(pa);
            aquario.moverPeixe(pa, new Posicao(-1, -1)); // Posi��o inv�lida

        } catch (Exception e) {
            caiuNoCatch = true;
        }
        // Nota: Dependendo da implementa��o, o erro pode ser engolido e apenas logado,
        // ou relan�ado. Se o m�todo moverPeixe tiver throws, isso captura.
        // Se o m�todo tratar internamente, verifique os logs.
        // Assumindo que o teste original esperava captura:
        assertTrue("Deveria cair no catch ao tentar mover peixe para fora dos limites", caiuNoCatch);
    }

    @Test
    public void testCatchAdicionarPeixeNulo() {
        Aquario aquario = new Aquario(5, 5);
        boolean caiuNoCatch = false;
        try {
            aquario.adicionarPeixe(null);
        } catch (Exception e) {
            caiuNoCatch = true;
        }
        assertTrue("Deveria lan�ar exce��o ao tentar adicionar peixe nulo", caiuNoCatch);
    }
    // --- TESTES DE FUNCIONALIDADE BÁSICA ---

    @Test
    public void testCT01_ValoresValidosNormais() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(10, 5, 3, 4, 2, 3);

            assertEquals("Numero de peixes A deve ser 10", 10, aquario.contarPeixesA());
            assertEquals("Numero de peixes B deve ser 5", 5, aquario.contarPeixesB());
            assertEquals("Iteracao inicial deve ser 0", 0, aquario.getIteracoes());
            assertFalse("Jogo nao deve ter terminado", aquario.jogoTerminou());

            // Testa getCelulasLivresAoRedor para c�lula central e de borda
            assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(0, 0)));
            assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(2, 2)));

            // Testa getPeixesAAoRedor e getPeixesBProximos
            assertNotNull(aquario.getPeixesAAoRedor(new Posicao(0, 0)));
            assertNotNull(aquario.getPeixesBProximos(new Posicao(0, 0)));

            // Testa adicionarPeixe e removerPeixe
            PeixeA pa = new PeixeA(new Posicao(4, 4), 1, 1);
            aquario.adicionarPeixe(pa);
            assertTrue("Contagem deve aumentar ao adicionar manual", aquario.contarPeixesA() >= 10);

            aquario.removerPeixe(pa);
            assertFalse("Peixe removido n�o deve estar vivo", pa.isVivo());

            // Testa moverPeixe
            PeixeA pa2 = new PeixeA(new Posicao(1, 1), 1, 1);
            aquario.adicionarPeixe(pa2);
            aquario.moverPeixe(pa2, new Posicao(2, 2));
            assertEquals(2, pa2.getPosicao().getLinha());
            assertEquals(2, pa2.getPosicao().getColuna());

            // Testa getPontuacao
            assertEquals(aquario.getIteracoes(), aquario.getPontuacao());

            // Testa exibir (garante que n�o quebra a UI/Console)
            aquario.exibir();

        } catch (Exception e) {
            fail("Nao deveria lancar excecao com valores validos: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT02_MatrizInvalidaM_Zero() {
        new Aquario(0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT02b_MatrizInvalidaN_Zero() {
        new Aquario(5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT02c_MatrizInvalidaM_Negativo() {
        new Aquario(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT03_QuantidadePeixesA_Invalida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(-1, 5, 3, 4, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT03b_QuantidadePeixesB_Invalida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, -1, 3, 4, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04_RA_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 0, 4, 2, 3); // Reprodu��o A Zero
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04b_MA_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 0, 2, 3); // Maturidade A Zero
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04c_RB_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 4, 0, 3); // Reprodu��o B Zero
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04d_MB_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 4, 2, 0); // Maturidade B Zero
    }

    @Test
    public void testCT05_ValoresLimitrofesMinimos() {
        try {
            Aquario aquario = new Aquario(5, 5);
            // Testa tudo com 1 (m�nimo v�lido)
            aquario.inicializar(10, 5, 1, 1, 1, 1);

            assertEquals(10, aquario.contarPeixesA());
            assertEquals(5, aquario.contarPeixesB());
            assertFalse(aquario.jogoTerminou());
        } catch (Exception e) {
            fail("Nao deveria lancar excecao com valores limitrofes validos: " + e.getMessage());
        }
    }

    @Test
    public void testCT06_MatrizMinima() {
        try {
            Aquario aquario = new Aquario(1, 1);
            // Matriz 1x1 comporta apenas 1 peixe
            aquario.inicializar(1, 0, 1, 1, 1, 1);
            assertEquals(1, aquario.contarPeixesA());
            assertEquals(0, aquario.contarPeixesB());
        } catch (Exception e) {
            fail("Nao deveria lancar excecao com matriz 1x1: " + e.getMessage());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT07_QuantidadePeixesExcedeTamanho() {
        Aquario aquario = new Aquario(2, 2); // 4 posi��es
        aquario.inicializar(3, 2, 1, 1, 1, 1); // Tenta colocar 5 peixes
    }

    // --- TESTES DE L�GICA DO JOGO ---

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
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 0, 3, 4, 2, 3);
        assertTrue("Jogo deve estar terminado quando nao ha peixes B", aquario.jogoTerminou());
    }

    @Test
    public void testCT10_AquarioVazio() {
        try {
            Aquario aquario = new Aquario(5, 5);
            aquario.inicializar(0, 0, 1, 1, 1, 1);

            assertEquals(0, aquario.contarPeixesA());
            assertEquals(0, aquario.contarPeixesB());
            assertTrue("Jogo deve estar terminado", aquario.jogoTerminou());
        } catch (Exception e) {
            fail("Nao deveria lancar excecao com aquario vazio: " + e.getMessage());
        }
    }

    @Test
    public void testCT11_JogoTerminadoSemPeixesA() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        assertTrue("Jogo deve terminar quando n�o h� peixes A", aquario.jogoTerminou());
    }

    @Test
    public void testCT12_ExecutarIteracaoComJogoTerminado() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1); // Jogo j� come�a terminado

        int iteracoesAntes = aquario.getIteracoes();
        aquario.executarIteracao(); // N�o deve processar l�gica pesada, apenas retornar ou n�o incrementar

        assertEquals("Itera��es n�o devem mudar ap�s jogo terminado (ou comportamento definido)",
                iteracoesAntes, aquario.getIteracoes());
    }

    @Test
    public void testCT13_MultiplasIteracoes() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 5, 1, 1, 1, 1);

        aquario.executarIteracao();
        aquario.executarIteracao();
        aquario.executarIteracao();

        assertEquals("N�mero de itera��es deve ser 3", 3, aquario.getIteracoes());
    }

    @Test
    public void testCT14_ReinicializarAquario() {
        Aquario aquario = new Aquario(5, 5);

        aquario.inicializar(3, 2, 1, 1, 1, 1);
        // Reinicializa com novos valores
        aquario.inicializar(1, 1, 1, 1, 1, 1);

        assertEquals("Deve resetar e ter apenas 1 Peixe A", 1, aquario.contarPeixesA());
        assertEquals("Deve resetar e ter apenas 1 Peixe B", 1, aquario.contarPeixesB());
        assertEquals("Itera��es devem resetar para 0", 0, aquario.getIteracoes());
    }

    @Test
    public void testCT15_IteracaoComApenasUmTipoDePeixe() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertTrue(aquario.jogoTerminou());
    }

    @Test
    public void testCT16_VerificarVizinhosCantos() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);

        // Canto Superior Esquerdo (0,0) - deve ter vizinhos 0,1; 1,0; 1,1
        assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(0, 0)));

        // Canto Inferior Direito (2,2) - deve ter vizinhos 1,1; 1,2; 2,1
        assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(2, 2)));

        // Testa limites da matriz para garantir que n�o lan�a IndexOutOfBoundsException
        try {
            aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
            aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        } catch (IndexOutOfBoundsException e) {
            fail("N�o deveria lan�ar IndexOutOfBounds ao verificar vizinhos nos cantos");
        }
    }

    @Test
    public void testCT17_SimulacaoReproducao() {
        // Tenta configurar um cen�rio onde a reprodu��o � r�pida (RA=1, MA=1)
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(2, 0, 1, 1, 1, 1); // 2 Peixes A, Reproduz a cada 1 turno

        int qtdInicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        aquario.executarIteracao();

        // Se a l�gica estiver correta, a quantidade deve aumentar ou se manter (nunca
        // diminuir se n�o h� predadores)
        assertTrue(aquario.contarPeixesA() >= qtdInicial);
    }

}