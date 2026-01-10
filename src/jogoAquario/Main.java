package jogoAquario;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        exibirBoasVindas();

        System.out.println("\n=== CONFIGURACAO DO AQUARIO ===\n");

        int linhas = solicitarInteiro(scanner, "Numero de linhas (M): ", 1, 50);
        int colunas = solicitarInteiro(scanner, "Numero de colunas (N): ", 1, 50);

        int maxPeixes = linhas * colunas;

        System.out.println("\n--- Peixes ---");
        int quantidadeA = solicitarInteiro(scanner, "Quantidade de peixes tipo A (X): ", 0, maxPeixes);
        int quantidadeB = solicitarInteiro(scanner, "Quantidade de peixes tipo B (Y): ", 0, maxPeixes - quantidadeA);

        System.out.println("\n--- Parametros dos Peixes A ---");
        int ra = solicitarInteiro(scanner, "Movimentos para reproducao (RA): ", 1, 100);
        int ma = solicitarInteiro(scanner, "Iteracoes sem movimento para morrer (MA): ", 1, 100);

        System.out.println("\n--- Parametros dos Peixes B ---");
        int rb = solicitarInteiro(scanner, "Peixes A para comer antes de reproduzir (RB): ", 1, 100);
        int mb = solicitarInteiro(scanner, "Iteracoes sem comer para morrer (MB): ", 1, 100);

        Aquario aquario = new Aquario(linhas, colunas);
        aquario.inicializar(quantidadeA, quantidadeB, ra, ma, rb, mb);

        System.out.println("\nAquario inicializado com sucesso!");
        System.out.println("\nLegenda:");
        System.out.println("  A = Peixe tipo A (herbivoro)");
        System.out.println("  B = Peixe tipo B (carnivoro)");
        System.out.println("  . = Celula livre");

        boolean continuar = true;
        aquario.exibir();

        while (continuar && !aquario.jogoTerminou()) {
            System.out.println("\n" + Utils.repeat("-", 50));
            System.out.print("\nPressione ENTER para proxima iteracao (ou 's' para sair): ");
            String resposta = scanner.nextLine().trim().toLowerCase();

            if (resposta.equals("s") || resposta.equals("sair")) {
                continuar = false;
                break;
            }

            aquario.executarIteracao();
            aquario.exibir();
        }

        exibirResultadoFinal(aquario, continuar);

        scanner.close();
    }

    private static void exibirBoasVindas() {
        System.out.println("\n+-------------------------------------------+");
        System.out.println("|                                           |");
        System.out.println("|         JOGO DO AQUARIO                   |");
        System.out.println("|                                           |");
        System.out.println("|   Simulacao de Ecossistema Aquatico       |");
        System.out.println("|                                           |");
        System.out.println("+-------------------------------------------+");
    }

    private static void exibirResultadoFinal(Aquario aquario, boolean encerradoPeloJogador) {
        System.out.println("\n" + Utils.repeat("-", 50));
        System.out.println("\n        *** JOGO FINALIZADO ***\n");

        if (encerradoPeloJogador) {
            System.out.println("Motivo: Encerrado pelo jogador");
        } else {
            System.out.println("Motivo: Não há mais peixes do tipo B");
        }

        System.out.println("\n--- ESTATISTICAS FINAIS ---");
        System.out.println("Peixes A restantes: " + aquario.contarPeixesA());
        System.out.println("Peixes B restantes: " + aquario.contarPeixesB());
        System.out.println("\n+----------------------------+");
        System.out.println("|  PONTUACAO: " + String.format("%4d", aquario.getPontuacao()) + " pontos  |");
        System.out.println("+----------------------------+");

        if (aquario.getIteracoes() > 100) {
            System.out.println("\nExcelente! Voce criou um ecossistema sustentavel!");
        } else if (aquario.getIteracoes() > 50) {
            System.out.println("\nBom trabalho! O ecossistema durou bastante tempo.");
        } else {
            System.out.println("\nTente ajustar os parametros para um ecossistema mais duradouro.");
        }

        System.out.println("\n" + Utils.repeat("-", 50));
        System.out.println("\nObrigado por jogar!\n");
    }

    private static int solicitarInteiro(Scanner scanner, String mensagem, int min, int max) {
        int valor;
        while (true) {
            System.out.print(mensagem);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor >= min && valor <= max) {
                    return valor;
                } else {
                    System.out.println("Por favor, digite um valor entre " + min + " e " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Por favor, digite um numero inteiro.");
            }
        }
    }
}
