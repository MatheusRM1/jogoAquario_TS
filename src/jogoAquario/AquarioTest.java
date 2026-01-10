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
}
