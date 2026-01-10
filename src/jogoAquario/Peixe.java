package jogoAquario;

public abstract class Peixe {
    protected Posicao posicao;
    protected int contadorMovimentos;
    protected int contadorSemAcao;
    protected boolean vivo;
    protected boolean moveuNestaIteracao;

    public Peixe(Posicao posicao) {
        this.posicao = posicao;
        this.contadorMovimentos = 0;
        this.contadorSemAcao = 0;
        this.vivo = true;
        this.moveuNestaIteracao = false;
    }

    public Posicao getPosicao() {
        return posicao;
    }

    public void setPosicao(Posicao posicao) {
        this.posicao = posicao;
    }

    public boolean isVivo() {
        return vivo;
    }

    public void marcarComoMorto() {
        this.vivo = false;
    }

    public boolean moveuNestaIteracao() {
        return moveuNestaIteracao;
    }

    public void resetarMovimentoIteracao() {
        this.moveuNestaIteracao = false;
    }

    public abstract void agir(Aquario aquario);

    public abstract char getSimbolo();
}
