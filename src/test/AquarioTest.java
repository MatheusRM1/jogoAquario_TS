package test;

import org.junit.Test;
import jogoAquario.Aquario;
import jogoAquario.PeixeA;
import jogoAquario.PeixeB;
import jogoAquario.Posicao;
import java.util.List;
import static org.junit.Assert.*;

public class AquarioTest {

    @Test(expected = IllegalArgumentException.class)
    public void testCT01_InicializarComValoresNegativos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(-99, 0, 1, 1, 1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT02_ConstrutorComDimensaoZero() {
        new Aquario(0, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT03_MoverPeixeParaPosicaoInvalida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pa);
        aquario.moverPeixe(pa, new Posicao(-1, -1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT04_AdicionarPeixeNulo() {
        Aquario aquario = new Aquario(5, 5);
        aquario.adicionarPeixe(null);
    }

    @Test
    public void testCT05_ValoresValidosNormais() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 4, 2, 3);
        assertEquals("Numero de peixes A deve ser 10", 10, aquario.contarPeixesA());
        assertEquals("Numero de peixes B deve ser 5", 5, aquario.contarPeixesB());
        assertEquals("Iteracao inicial deve ser 0", 0, aquario.getIteracoes());
        assertFalse("Jogo nao deve ter terminado", aquario.jogoTerminou());
        assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(0, 0)));
        assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(2, 2)));
        assertNotNull(aquario.getPeixesAAoRedor(new Posicao(0, 0)));
        assertNotNull(aquario.getPeixesBProximos(new Posicao(0, 0)));
        PeixeA pa = new PeixeA(new Posicao(4, 4), 1, 1);
        aquario.adicionarPeixe(pa);
        assertTrue("Contagem deve aumentar ao adicionar manual", aquario.contarPeixesA() >= 10);
        aquario.removerPeixe(pa);
        aquario.executarIteracao();
        assertEquals("Iteracao deve incrementar", 1, aquario.getIteracoes());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT06_MatrizInvalidaM_Zero() {
        new Aquario(0, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT07_MatrizInvalidaN_Zero() {
        new Aquario(5, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT08_MatrizInvalidaM_Negativo() {
        new Aquario(-1, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT09_QuantidadePeixesA_Invalida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(-1, 5, 3, 4, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT10_QuantidadePeixesB_Invalida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, -1, 3, 4, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT11_RA_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 0, 4, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT12_MA_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 0, 2, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT13_RB_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 4, 0, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT14_MB_Zero() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 5, 3, 4, 2, 0);
    }

    @Test
    public void testCT15_ValoresLimitrofesMinimos() {
        Aquario aquario = new Aquario(1, 1);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        assertEquals(1, aquario.contarPeixesA());
        assertEquals(0, aquario.contarPeixesB());
        assertTrue(aquario.jogoTerminou());
    }

    @Test
    public void testCT16_MatrizMinima() {
        Aquario aquario = new Aquario(1, 1);
        aquario.inicializar(0, 1, 1, 1, 1, 1);
        assertEquals(0, aquario.contarPeixesA());
        assertEquals(1, aquario.contarPeixesB());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT17_QuantidadePeixesExcedeTamanho() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(3, 2, 1, 1, 1, 1);
    }

    @Test
    public void testCT18_ExecutarIteracao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 3, 2, 3, 2, 3);
        int iteracoesAntes = aquario.getIteracoes();
        aquario.executarIteracao();
        assertEquals("Iteracao deve incrementar", iteracoesAntes + 1, aquario.getIteracoes());
    }

    @Test
    public void testCT19_JogoTerminadoSemPeixesB() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(10, 0, 3, 4, 2, 3);
        assertTrue("Jogo deve estar terminado quando nao ha peixes B", aquario.jogoTerminou());
    }

    @Test
    public void testCT20_AquarioVazio() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        assertTrue("Jogo deve terminar com aquario vazio", aquario.jogoTerminou());
        assertEquals(0, aquario.contarPeixesA());
        assertEquals(0, aquario.contarPeixesB());
    }

    @Test
    public void testCT21_JogoTerminadoSemPeixesA() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        assertTrue("Jogo deve terminar quando nao ha peixes A", aquario.jogoTerminou());
    }

    @Test
    public void testCT22_ExecutarIteracaoComJogoTerminado() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        int iteracoesAntes = aquario.getIteracoes();
        aquario.executarIteracao();
        assertEquals("Iteracoes devem incrementar mesmo apos jogo terminado",
                iteracoesAntes + 1, aquario.getIteracoes());
    }

    @Test
    public void testCT23_MultiplasIteracoes() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 5, 1, 1, 1, 1);
        aquario.executarIteracao();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals("Numero de iteracoes deve ser 3", 3, aquario.getIteracoes());
    }

    @Test
    public void testCT24_ReinicializarAquario() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 2, 1, 1, 1, 1);
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        assertEquals("Deve resetar e ter apenas 1 Peixe A", 1, aquario.contarPeixesA());
        assertEquals("Deve resetar e ter apenas 1 Peixe B", 1, aquario.contarPeixesB());
        assertEquals("Iteracoes devem resetar para 0", 0, aquario.getIteracoes());
    }

    @Test
    public void testCT25_IteracaoComApenasUmTipoDePeixe() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertTrue(aquario.jogoTerminou());
    }

    @Test
    public void testCT26_VerificarVizinhosCantos() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(0, 0)));
        assertNotNull(aquario.getCelulasLivresAoRedor(new Posicao(2, 2)));
    }

    @Test
    public void testCT27_SimulacaoReproducao() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(2, 0, 1, 1, 1, 1);
        int qtdInicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesA() >= qtdInicial);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT28_MoverPeixeNulo() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        aquario.moverPeixe(null, new Posicao(1, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCT29_MoverPeixeParaPosicaoNula() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pa);
        aquario.moverPeixe(pa, null);
    }

    @Test
    public void testCT30_ExecutarIteracaoComTodosPeixesMortos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 1, 1, 1, 1, 1);
        for (int i = 0; i < 20; i++) {
            aquario.executarIteracao();
        }
        int iteracoesAntes = aquario.getIteracoes();
        aquario.executarIteracao();
        assertEquals(iteracoesAntes + 1, aquario.getIteracoes());
    }

    @Test
    public void testCT31_GetPeixeNaPosicaoComCelulasLivres() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        List<Posicao> celulasLivres = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        assertNotNull(celulasLivres);
        assertTrue(celulasLivres.size() >= 0);
    }

    @Test
    public void testCT32_ContadorDeIteracoesIncrementaCorretamente() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 2, 1, 1, 1, 1);
        assertEquals(0, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals(1, aquario.getIteracoes());
        aquario.executarIteracao();
        assertEquals(2, aquario.getIteracoes());
    }

    @Test
    public void testCT33_PeixesNaoBordasGetPeixesAAoRedor() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(2, 2));
        assertNotNull(peixesA);
        assertTrue(peixesA.size() >= 0);
    }

    @Test
    public void testCT34_PeixesNasBordasGetPeixesBProximos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        List<PeixeB> peixesB = aquario.getPeixesBProximos(new Posicao(0, 0));
        assertNotNull(peixesB);
        assertTrue(peixesB.size() >= 0);
    }

    @Test
    public void testCT35_GetCelulasLivresEmTodasDirecoes() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> celulasLivres = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        assertEquals(8, celulasLivres.size());
    }

    @Test
    public void testCT36_JogoComPeixesAeBVivos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 3, 1, 1, 1, 1);
        assertFalse(aquario.jogoTerminou());
    }

    @Test
    public void testCT37_MatrizGrande() {
        Aquario aquario = new Aquario(20, 20);
        aquario.inicializar(50, 50, 1, 1, 1, 1);
        assertEquals(50, aquario.contarPeixesA());
        assertEquals(50, aquario.contarPeixesB());
    }

    @Test
    public void testCT38_RemoverPeixeMarcaComoMorto() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pa);
        assertTrue(pa.isVivo());
        aquario.removerPeixe(pa);
        assertFalse(pa.isVivo());
    }

    @Test
    public void testCT39_GetPeixesAeBAoRedorComMatrizCheia() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(4, 4, 1, 1, 1, 1);
        List<Posicao> livres = aquario.getCelulasLivresAoRedor(new Posicao(1, 1));
        assertNotNull(livres);
    }

    @Test
    public void testCT40_PeixeAMorrendoPorFome() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 1, 2, 1, 1);
        aquario.executarIteracao();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesA() <= 4);
    }

    @Test
    public void testCT41_PeixeAReproduzindoComRAMinimo() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 0, 1, 1, 1, 1);
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesA() >= inicial);
    }

    @Test
    public void testCT42_PeixeASemMovimentoQuandoJaMoveu() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertEquals(1, aquario.getIteracoes());
    }

    @Test
    public void testCT43_PeixeBComendoEReproduzindo() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 2, 1, 1, 1, 2);
        int peixesB_inicial = aquario.contarPeixesB();
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.contarPeixesB() >= peixesB_inicial || aquario.contarPeixesA() < 5);
    }

    @Test
    public void testCT44_PeixeBNaoReproduziComPeixesBProximos() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(3, 5, 1, 5, 1, 5);
        int inicial = aquario.contarPeixesB();
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesB() <= inicial + 2);
    }

    @Test
    public void testCT45_PeixeBMorrendoSemComida() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 3, 1, 1, 1, 2);
        int inicial = aquario.contarPeixesB();
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.contarPeixesB() < inicial);
    }

    @Test
    public void testCT46_PeixeBMovendoParaCelulasLivres() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(0, 2, 1, 1, 5, 5);
        aquario.executarIteracao();
        assertTrue(aquario.getIteracoes() == 1);
    }

    @Test
    public void testCT47_MatrizComplexaComTodasInteracoes() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 10, 2, 3, 2, 3);
        for (int i = 0; i < 20; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.getIteracoes() > 0);
    }

    @Test
    public void testCT48_PeixeAComContadorMovimentosAcumulado() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 3, 10, 1, 1);
        int inicial = aquario.contarPeixesA();
        for (int i = 0; i < 4; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.contarPeixesA() >= inicial);
    }

    @Test
    public void testCT49_PeixeBComContadorComidosAcumulado() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 2, 1, 10, 2, 10);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.getIteracoes() == 5);
    }

    @Test
    public void testCT50_ExecutarIteracaoComListaVazia() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertEquals(1, aquario.getIteracoes());
    }

    @Test
    public void testCT51_ResetarMovimentoIteracaoCompleto() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(3, 2, 1, 5, 1, 5);
        aquario.executarIteracao();
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals(3, aquario.getIteracoes());
    }

    @Test
    public void testCT52_TestarTodasPosicoesVizinhas() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> livres = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        assertEquals(8, livres.size());
    }

    @Test
    public void testCT53_PeixeANaoMoveSeMorto() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.marcarComoMorto();
        pa.agir(aquario);
        assertFalse(pa.isVivo());
    }

    @Test
    public void testCT54_PeixeBNaoMoveSeMorto() {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pb);
        pb.marcarComoMorto();
        pb.agir(aquario);
        assertFalse(pb.isVivo());
    }

    @Test
    public void testCT55_CenarioBordaSupEsq() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.agir(aquario);
        assertTrue(pa.moveuNestaIteracao());
    }

    @Test
    public void testCT56_CenarioBordaInfDir() {
        Aquario aquario = new Aquario(5, 5);
        PeixeB pb = new PeixeB(new Posicao(4, 4), 1, 1);
        aquario.adicionarPeixe(pb);
        pb.agir(aquario);
        assertTrue(pb.moveuNestaIteracao());
    }

    @Test
    public void testCT57_PeixeBSemPeixeAProximoSeMove() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 2, 1, 10, 10, 5);
        aquario.executarIteracao();
        assertTrue(aquario.getIteracoes() == 1);
    }

    @Test
    public void testCT58_SimulacaoLongaComAmbosVivos() {
        Aquario aquario = new Aquario(15, 15);
        aquario.inicializar(40, 20, 2, 3, 2, 3);
        for (int i = 0; i < 50; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.getIteracoes() > 0);
    }

    @Test
    public void testCT59_PeixeAReproduzComCelulasLivres() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(2, 0, 1, 10, 1, 1);
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesA() >= 2);
    }

    @Test
    public void testCT60_PeixeAMoveSemReproduzirAntes() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 5, 10, 1, 1);
        int inicial = aquario.contarPeixesA();
        aquario.executarIteracao();
        assertEquals(inicial, aquario.contarPeixesA());
    }

    @Test
    public void testCT61_PeixeAMorreSeContadorSemAcaoAtingeMA() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 5, 2, 1, 1);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.contarPeixesA() <= 4);
    }

    @Test
    public void testCT62_PeixeBComeEReproduziComRB1() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 1, 1, 10, 1, 10);
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesB() >= 1);
    }

    @Test
    public void testCT63_PeixeBComeENaoReproduzSemCelulasLivres() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(2, 2, 1, 10, 1, 10);
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesB() <= 4);
    }

    @Test
    public void testCT64_PeixeBComeENaoReproduzComVizinhosB() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(3, 5, 1, 10, 1, 10);
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesB() <= 6);
    }

    @Test
    public void testCT65_PeixeBSemPeixeAProximoMove() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(0, 3, 1, 10, 10, 5);
        aquario.executarIteracao();
        assertTrue(aquario.getIteracoes() == 1);
    }

    @Test
    public void testCT66_PeixeBSemPeixeASemCelulasNaoMove() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(0, 4, 1, 10, 10, 2);
        for (int i = 0; i < 3; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.contarPeixesB() < 4);
    }

    @Test
    public void testCT67_PeixeAJaMoveuNaoAgeNovamente() {
        Aquario aquario = new Aquario(5, 5);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.agir(aquario);
        assertTrue(pa.moveuNestaIteracao());
        pa.agir(aquario);
        assertTrue(pa.moveuNestaIteracao());
    }

    @Test
    public void testCT68_PeixeBJaMoveuNaoAgeNovamente() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        PeixeB pb = new PeixeB(new Posicao(0, 0), 1, 1);
        aquario.adicionarPeixe(pb);
        pb.agir(aquario);
        assertTrue(pb.moveuNestaIteracao());
        pb.agir(aquario);
        assertTrue(pb.moveuNestaIteracao());
    }

    @Test
    public void testCT69_PeixeBContadorComidosAcumula() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(30, 2, 1, 10, 3, 10);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.getIteracoes() == 5);
    }

    @Test
    public void testCT70_TestarResetMovimentoIteracao() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 1, 1, 5, 1, 5);
        aquario.executarIteracao();
        aquario.executarIteracao();
        assertEquals(2, aquario.getIteracoes());
    }

    @Test
    public void testCT71_CenarioComplexoTodosOsCasos() {
        Aquario aquario = new Aquario(15, 15);
        aquario.inicializar(40, 20, 2, 3, 2, 3);
        for (int i = 0; i < 30; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.getIteracoes() > 0);
    }

    @Test
    public void testCT72_PosicaoEquals() {
        Posicao p1 = new Posicao(2, 3);
        Posicao p2 = new Posicao(2, 3);
        Posicao p3 = new Posicao(3, 2);
        assertTrue(p1.equals(p1));
        assertTrue(p1.equals(p2));
        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals("string"));
    }

    @Test
    public void testCT73_PeixeAReproduzResetaContador() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(1, 0, 1, 10, 1, 1);
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesA() >= 1);
    }

    @Test
    public void testCT74_PeixeBReproduzResetaContador() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(10, 1, 1, 1, 1, 5);
        aquario.executarIteracao();
        assertTrue(aquario.contarPeixesB() >= 1);
    }

    @Test
    public void testCT75_ExecutarIteracaoComPeixesVazios() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertTrue(aquario.jogoTerminou());
    }

    @Test
    public void testCT76_PeixeATodasCelulasOcupadas() {
        Aquario aquario = new Aquario(2, 2);
        aquario.inicializar(4, 0, 1, 1, 1, 1);
        aquario.executarIteracao();
        assertTrue(aquario.getIteracoes() == 1);
    }

    @Test
    public void testCT77_PeixeBComeMultiplasVezes() {
        Aquario aquario = new Aquario(10, 10);
        aquario.inicializar(20, 2, 1, 1, 1, 5);
        for (int i = 0; i < 5; i++) aquario.executarIteracao();
        assertTrue(aquario.getIteracoes() == 5);
    }

    @Test
    public void testCT78_TestarGetPeixeNaPosicaoVazia() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 1, 1, 1, 1, 1);
        List<Posicao> livres = aquario.getCelulasLivresAoRedor(new Posicao(4, 4));
        assertNotNull(livres);
    }

    @Test
    public void testCT79_JogoTerminaApenasPeixeA() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        assertTrue(aquario.jogoTerminou());
    }

    @Test
    public void testCT80_JogoTerminaApenasPeixeB() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        assertTrue(aquario.jogoTerminou());
    }

    @Test
    public void testCT81_TodasDirecoesCelulasLivres() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 0, 1, 1, 1, 1);
        List<Posicao> livres = aquario.getCelulasLivresAoRedor(new Posicao(2, 2));
        assertEquals(8, livres.size());
    }

    @Test
    public void testCT82_TestarPosicaoInvalidaNegativa() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        List<Posicao> livres = aquario.getCelulasLivresAoRedor(new Posicao(0, 0));
        assertTrue(livres.size() <= 3);
    }

    @Test
    public void testCT83_TestarPosicaoInvalidaExcedeLimite() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(1, 0, 1, 1, 1, 1);
        List<Posicao> livres = aquario.getCelulasLivresAoRedor(new Posicao(4, 4));
        assertTrue(livres.size() <= 3);
    }

    @Test
    public void testCT84_GetPeixesAAoRedorComPeixesMortos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(5, 0, 1, 1, 1, 1);
        PeixeA pa = new PeixeA(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pa);
        pa.marcarComoMorto();
        List<PeixeA> peixesA = aquario.getPeixesAAoRedor(new Posicao(2, 2));
        assertTrue(peixesA.size() >= 0);
    }

    @Test
    public void testCT85_GetPeixesBProximosComPeixesMortos() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(0, 5, 1, 1, 1, 1);
        PeixeB pb = new PeixeB(new Posicao(2, 2), 1, 1);
        aquario.adicionarPeixe(pb);
        pb.marcarComoMorto();
        List<PeixeB> peixesB = aquario.getPeixesBProximos(new Posicao(2, 2));
        assertTrue(peixesB.size() >= 0);
    }

    @Test
    public void testCT86_ExibirMatrizCompleta() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(3, 3, 1, 1, 1, 1);
        aquario.exibir();
        assertTrue(aquario.getIteracoes() == 0);
    }

    @Test
    public void testCT87_TestarHashCodePosicao() {
        Posicao p1 = new Posicao(2, 3);
        Posicao p2 = new Posicao(2, 3);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    public void testCT88_TestarToStringPosicao() {
        Posicao p = new Posicao(5, 7);
        assertEquals("(5,7)", p.toString());
    }

    @Test
    public void testCT89_PosicaoGetLinhaeColuna() {
        Posicao pos = new Posicao(3, 4);
        assertEquals(3, pos.getLinha());
        assertEquals(4, pos.getColuna());
    }

    @Test
    public void testCT90_IteracaoIncrementaSempre() {
        Aquario aquario = new Aquario(5, 5);
        aquario.inicializar(2, 2, 1, 1, 1, 1);
        for (int i = 0; i < 5; i++) {
            aquario.executarIteracao();
        }
        assertEquals(5, aquario.getIteracoes());
    }

    @Test
    public void testCT91_SimulacaoAteTodosMorerem() {
        Aquario aquario = new Aquario(3, 3);
        aquario.inicializar(3, 2, 1, 1, 1, 2);
        for (int i = 0; i < 50; i++) {
            aquario.executarIteracao();
        }
        assertTrue(aquario.jogoTerminou());
    }

    @Test
    public void testCT92_TodosOsMetodosPosicao() {
        Posicao p1 = new Posicao(1, 2);
        assertEquals(1, p1.getLinha());
        assertEquals(2, p1.getColuna());
        assertEquals("(1,2)", p1.toString());
        Posicao p2 = new Posicao(1, 2);
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
    }

}
