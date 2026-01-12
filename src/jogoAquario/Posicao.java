package jogoAquario;

/**
 * Representa uma posição (coordenada) na matriz do aquário
 */
public class Posicao {
    private int linha;
    private int coluna;
    
    public Posicao(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public int getLinha() {
        return linha;
    }
    
    public int getColuna() {
        return coluna;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Posicao posicao = (Posicao) obj;
        return linha == posicao.linha && coluna == posicao.coluna;
    }
    
    @Override
    public int hashCode() {
        return 31 * linha + coluna;
    }
    
    @Override
    public String toString() {
        return "(" + linha + "," + coluna + ")";
    }
}
