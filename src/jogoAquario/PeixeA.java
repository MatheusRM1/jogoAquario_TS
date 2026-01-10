package jogoAquario;

import java.util.List;
import java.util.Random;

public class PeixeA extends Peixe {
    private int ra;
    private int ma;
    private Random random = new Random();

    public PeixeA(Posicao posicao, int ra, int ma) {
        super(posicao);
        this.ra = ra;
        this.ma = ma;
    }

    @Override
    public void agir(Aquario aquario) {
        if (!vivo || moveuNestaIteracao) {
            return;
        }

        List<Posicao> celulasLivres = aquario.getCelulasLivresAoRedor(posicao);

        if (celulasLivres.isEmpty()) {
            contadorSemAcao++;
            if (contadorSemAcao >= ma) {
                marcarComoMorto();
            }
        } else {
            contadorSemAcao = 0;
            contadorMovimentos++;

            if (contadorMovimentos >= ra) {
                Posicao posFilho = celulasLivres.get(random.nextInt(celulasLivres.size()));
                PeixeA filho = new PeixeA(posFilho, ra, ma);
                filho.moveuNestaIteracao = true;
                aquario.adicionarPeixe(filho);
                contadorMovimentos = 0;
            } else {
                Posicao novaPosicao = celulasLivres.get(random.nextInt(celulasLivres.size()));
                aquario.moverPeixe(this, novaPosicao);
            }

            moveuNestaIteracao = true;
        }
    }

    @Override
    public char getSimbolo() {
        return 'A';
    }
}
