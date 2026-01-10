package jogoAquario;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class PeixeB extends Peixe {
    private int rb;
    private int mb;
    private int contadorComidos;
    private int contadorSemComer;
    private Random random = new Random();

    public PeixeB(Posicao posicao, int rb, int mb) {
        super(posicao);
        this.rb = rb;
        this.mb = mb;
        this.contadorComidos = 0;
        this.contadorSemComer = 0;
    }

    @Override
    public void agir(Aquario aquario) {
        if (!vivo || moveuNestaIteracao) {
            return;
        }

        List<PeixeA> peixesAAoRedor = aquario.getPeixesAAoRedor(posicao);

        if (!peixesAAoRedor.isEmpty()) {
            PeixeA presa = peixesAAoRedor.get(random.nextInt(peixesAAoRedor.size()));
            Posicao novaPosicao = presa.getPosicao();

            presa.marcarComoMorto();
            aquario.removerPeixe(presa);

            aquario.moverPeixe(this, novaPosicao);

            contadorComidos++;
            contadorSemComer = 0;
            moveuNestaIteracao = true;

            if (contadorComidos >= rb) {
                List<Posicao> celulasLivres = aquario.getCelulasLivresAoRedor(posicao);
                List<PeixeB> peixesBProximos = aquario.getPeixesBProximos(posicao);

                if (peixesBProximos.isEmpty() && !celulasLivres.isEmpty()) {
                    Posicao posFilho = celulasLivres.get(random.nextInt(celulasLivres.size()));
                    PeixeB filho = new PeixeB(posFilho, rb, mb);
                    filho.moveuNestaIteracao = true;
                    aquario.adicionarPeixe(filho);
                    contadorComidos = 0;
                }
            }
        } else {
            List<Posicao> celulasLivres = aquario.getCelulasLivresAoRedor(posicao);

            if (!celulasLivres.isEmpty()) {
                Posicao novaPosicao = celulasLivres.get(random.nextInt(celulasLivres.size()));
                aquario.moverPeixe(this, novaPosicao);
                moveuNestaIteracao = true;
            }

            contadorSemComer++;

            if (contadorSemComer >= mb) {
                marcarComoMorto();
            }
        }
    }

    @Override
    public char getSimbolo() {
        return 'B';
    }
}
