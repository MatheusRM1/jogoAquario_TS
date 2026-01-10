package jogoAquario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Aquario {
    private int linhas;
    private int colunas;
    private List<Peixe> peixes;
    private int iteracoes;
    private Random random = new Random();

    public Aquario(int linhas, int colunas) {
        if (linhas <= 0 || colunas <= 0) {
            throw new IllegalArgumentException("Tamanho da matriz deve ser maior que zero (M > 0 e N > 0)");
        }
        this.linhas = linhas;
        this.colunas = colunas;
        this.peixes = new ArrayList<>();
        this.iteracoes = 0;
    }

    public void inicializar(int quantidadeA, int quantidadeB, int ra, int ma, int rb, int mb) {
        if (quantidadeA < 0) {
            throw new IllegalArgumentException("Quantidade de peixes A deve ser maior ou igual a zero");
        }
        if (quantidadeB < 0) {
            throw new IllegalArgumentException("Quantidade de peixes B deve ser maior ou igual a zero");
        }
        if (ra <= 0) {
            throw new IllegalArgumentException("RA deve ser maior que zero");
        }
        if (ma <= 0) {
            throw new IllegalArgumentException("MA deve ser maior que zero");
        }
        if (rb <= 0) {
            throw new IllegalArgumentException("RB deve ser maior que zero");
        }
        if (mb <= 0) {
            throw new IllegalArgumentException("MB deve ser maior que zero");
        }
        if (quantidadeA + quantidadeB > linhas * colunas) {
            throw new IllegalArgumentException("Quantidade total de peixes excede o tamanho da matriz");
        }

        List<Posicao> posicoesDisponiveis = new ArrayList<>();

        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                posicoesDisponiveis.add(new Posicao(i, j));
            }
        }

        for (int i = 0; i < quantidadeA && !posicoesDisponiveis.isEmpty(); i++) {
            int index = random.nextInt(posicoesDisponiveis.size());
            Posicao pos = posicoesDisponiveis.remove(index);
            peixes.add(new PeixeA(pos, ra, ma));
        }

        for (int i = 0; i < quantidadeB && !posicoesDisponiveis.isEmpty(); i++) {
            int index = random.nextInt(posicoesDisponiveis.size());
            Posicao pos = posicoesDisponiveis.remove(index);
            peixes.add(new PeixeB(pos, rb, mb));
        }
    }

    public void executarIteracao() {
        for (Peixe peixe : peixes) {
            peixe.resetarMovimentoIteracao();
        }

        List<Peixe> peixesParaAgir = new ArrayList<>(peixes);

        for (Peixe peixe : peixesParaAgir) {
            if (peixe.isVivo()) {
                peixe.agir(this);
            }
        }

        peixes.removeIf(p -> !p.isVivo());

        iteracoes++;
    }

    public boolean jogoTerminou() {
        for (Peixe peixe : peixes) {
            if (peixe instanceof PeixeB && peixe.isVivo()) {
                return false;
            }
        }
        return true;
    }

    public List<Posicao> getCelulasLivresAoRedor(Posicao pos) {
        List<Posicao> celulasLivres = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int novaLinha = pos.getLinha() + dx[i];
            int novaColuna = pos.getColuna() + dy[i];

            if (posicaoValida(novaLinha, novaColuna)) {
                Posicao novaPosicao = new Posicao(novaLinha, novaColuna);
                if (celulaLivre(novaPosicao)) {
                    celulasLivres.add(novaPosicao);
                }
            }
        }

        return celulasLivres;
    }

    public List<PeixeA> getPeixesAAoRedor(Posicao pos) {
        List<PeixeA> peixesA = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int novaLinha = pos.getLinha() + dx[i];
            int novaColuna = pos.getColuna() + dy[i];

            if (posicaoValida(novaLinha, novaColuna)) {
                Posicao novaPosicao = new Posicao(novaLinha, novaColuna);
                Peixe peixe = getPeixeNaPosicao(novaPosicao);

                if (peixe instanceof PeixeA && peixe.isVivo()) {
                    peixesA.add((PeixeA) peixe);
                }
            }
        }

        return peixesA;
    }

    public List<PeixeB> getPeixesBProximos(Posicao pos) {
        List<PeixeB> peixesB = new ArrayList<>();
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int novaLinha = pos.getLinha() + dx[i];
            int novaColuna = pos.getColuna() + dy[i];

            if (posicaoValida(novaLinha, novaColuna)) {
                Posicao novaPosicao = new Posicao(novaLinha, novaColuna);
                Peixe peixe = getPeixeNaPosicao(novaPosicao);

                if (peixe instanceof PeixeB && peixe.isVivo()) {
                    peixesB.add((PeixeB) peixe);
                }
            }
        }

        return peixesB;
    }

    private boolean posicaoValida(int linha, int coluna) {
        return linha >= 0 && linha < linhas && coluna >= 0 && coluna < colunas;
    }

    private boolean celulaLivre(Posicao pos) {
        return getPeixeNaPosicao(pos) == null;
    }

    private Peixe getPeixeNaPosicao(Posicao pos) {
        for (Peixe peixe : peixes) {
            if (peixe.isVivo() && peixe.getPosicao().equals(pos)) {
                return peixe;
            }
        }
        return null;
    }

    public void moverPeixe(Peixe peixe, Posicao novaPosicao) {
        peixe.setPosicao(novaPosicao);
    }

    public void adicionarPeixe(Peixe peixe) {
        peixes.add(peixe);
    }

    public void removerPeixe(Peixe peixe) {
        peixe.marcarComoMorto();
    }

    public int getIteracoes() {
        return iteracoes;
    }

    public int getPontuacao() {
        return iteracoes;
    }

    public int contarPeixesA() {
        int count = 0;
        for (Peixe peixe : peixes) {
            if (peixe instanceof PeixeA && peixe.isVivo()) {
                count++;
            }
        }
        return count;
    }

    public int contarPeixesB() {
        int count = 0;
        for (Peixe peixe : peixes) {
            if (peixe instanceof PeixeB && peixe.isVivo()) {
                count++;
            }
        }
        return count;
    }

    public void exibir() {
        char[][] matriz = new char[linhas][colunas];
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                matriz[i][j] = '.';
            }
        }

        for (Peixe peixe : peixes) {
            if (peixe.isVivo()) {
                Posicao pos = peixe.getPosicao();
                matriz[pos.getLinha()][pos.getColuna()] = peixe.getSimbolo();
            }
        }

        System.out.println("\n+" + Utils.repeat("-", colunas * 2 + 1) + "+");
        for (int i = 0; i < linhas; i++) {
            System.out.print("| ");
            for (int j = 0; j < colunas; j++) {
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println("|");
        }
        System.out.println("+" + Utils.repeat("-", colunas * 2 + 1) + "+");

        System.out.println("\nIteração: " + iteracoes);
        System.out.println("Peixes A: " + contarPeixesA());
        System.out.println("Peixes B: " + contarPeixesB());
        System.out.println("Pontuação: " + getPontuacao());
    }
}
