package test;

import static org.junit.Assert.*;
import org.junit.Test;
import jogoAquario.Utils;
import jogoAquario.Posicao;
import jogoAquario.PeixeA;
import jogoAquario.PeixeB;
import jogoAquario.Peixe;

public class UtilsAndPosicaoTest {
    @Test
    public void testRepeatNormal() {
        assertEquals("aaa", Utils.repeat("a", 3));
        assertEquals("", Utils.repeat("a", 0));
        assertEquals("", Utils.repeat("a", -1));
        assertNull(Utils.repeat(null, 3));
    }

    @Test
    public void testPosicaoEqualsAndHashCode() {
        Posicao p1 = new Posicao(1, 2);
        Posicao p2 = new Posicao(1, 2);
        Posicao p3 = new Posicao(2, 1);
        assertTrue(p1.equals(p2));
        assertEquals(p1.hashCode(), p2.hashCode());
        assertFalse(p1.equals(p3));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals("string"));
    }

    @Test
    public void testPosicaoToString() {
        Posicao p = new Posicao(3, 4);
        assertEquals("(3,4)", p.toString());
    }

    @Test
    public void testPeixeAGetSimbolo() {
        PeixeA pa = new PeixeA(new Posicao(0,0), 1, 1);
        assertEquals('A', pa.getSimbolo());
    }

    @Test
    public void testPeixeBGetSimbolo() {
        PeixeB pb = new PeixeB(new Posicao(0,0), 1, 1);
        assertEquals('B', pb.getSimbolo());
    }

    @Test
    public void testPeixeVivoEMorte() {
        PeixeA pa = new PeixeA(new Posicao(0,0), 1, 1);
        assertTrue(pa.isVivo());
        pa.marcarComoMorto();
        assertFalse(pa.isVivo());
    }

    @Test
    public void testPeixeSetGetPosicao() {
        PeixeA pa = new PeixeA(new Posicao(0,0), 1, 1);
        Posicao nova = new Posicao(2,2);
        pa.setPosicao(nova);
        assertEquals(nova, pa.getPosicao());
    }

    @Test
    public void testPeixeMoveuNestaIteracao() {
        PeixeA pa = new PeixeA(new Posicao(0,0), 1, 1);
        assertFalse(pa.moveuNestaIteracao());
        pa.resetarMovimentoIteracao();
        assertFalse(pa.moveuNestaIteracao());
    }
}
