# Jogo do Aquário

## Descrição

Simulação de um ecossistema aquático onde dois tipos de peixes interagem seguindo regras específicas de movimento, reprodução e sobrevivência.

## Estrutura do Projeto

```
jogoAquario/
└── src/
    ├── Main.java          # Classe principal com interface do usuário
    ├── Aquario.java       # Gerencia a matriz e lógica do jogo
    ├── Peixe.java         # Classe abstrata para peixes
    ├── PeixeA.java        # Peixe herbívoro (come plâncton)
    ├── PeixeB.java        # Peixe carnívoro (come peixes A)
    └── Posicao.java       # Representa coordenadas na matriz
```

## Regras do Jogo

### Peixes Tipo A (Herbívoros - Símbolo: 'A')
1. **Movimento**: Se houver célula livre ao redor, move-se para ela
2. **Reprodução**: Após RA movimentos consecutivos, se há célula livre disponível, reproduz-se (permanece no lugar, filho nasce na célula livre)
3. **Morte**: Morre de fome após MA iterações sem movimento

### Peixes Tipo B (Carnívoros - Símbolo: 'B')
1. **Alimentação**: Se houver peixe A ao redor, move-se para lá e come-o. Caso contrário, move-se para célula livre
2. **Reprodução**: Após comer RB peixes A, se não há outro peixe B próximo e há célula livre, reproduz-se (permanece no lugar, filho nasce na célula livre)
3. **Morte**: Morre de fome após MB iterações sem comer

## Como Jogar

### Compilação

```bash
# Navegue até o diretório src
cd c:\Users\mathe\Documents\jogoAquario\src

# Compile todos os arquivos
javac *.java
```

### Execução

```bash
# Execute o jogo
java Main
```

### Parâmetros de Entrada

O jogo solicitará os seguintes parâmetros:

1. **M**: Número de linhas da matriz
2. **N**: Número de colunas da matriz
3. **X**: Quantidade inicial de peixes tipo A
4. **Y**: Quantidade inicial de peixes tipo B
5. **RA**: Movimentos necessários para reprodução do peixe A
6. **MA**: Iterações sem movimento para peixe A morrer de fome
7. **RB**: Peixes A que o peixe B precisa comer para reproduzir
8. **MB**: Iterações sem comer para peixe B morrer de fome

### Exemplo de Configuração Equilibrada

```
M = 10
N = 10
X = 20 (peixes A)
Y = 5  (peixes B)
RA = 3
MA = 5
RB = 3
MB = 4
```

### Exemplo de Configuração Sustentável

```
M = 15
N = 15
X = 40
Y = 8
RA = 4
MA = 6
RB = 4
MB = 5
```

## Mecânica do Jogo

1. **Iteração**: Em cada iteração, todos os peixes executam suas ações na sequência
2. **Visualização**: Após cada iteração, o estado do aquário é exibido
3. **Controle**: Pressione ENTER para avançar ou 's' para sair
4. **Pontuação**: O número total de iterações executadas
5. **Fim de Jogo**: Ocorre quando não há mais peixes B ou o jogador encerra

## Legenda Visual

```
A = Peixe tipo A (herbívoro)
B = Peixe tipo B (carnívoro)
. = Célula livre (água)
```

## Pontuação

A pontuação é o número total de iterações que o ecossistema conseguiu sustentar:

- **< 50 iterações**: Ecossistema instável
- **50-100 iterações**: Ecossistema moderado
- **> 100 iterações**: Ecossistema sustentável! 🏆

## Exemplo de Saída

```
╔═══════════════════════╗
║ . A . B . . A . . A . ║
║ A . . . A . . . B . . ║
║ . . A . . A . . . A . ║
║ . B . A . . . A . . . ║
║ A . . . . . . . A . . ║
╚═══════════════════════╝

Iteração: 15
Peixes A: 18
Peixes B: 4
Pontuação: 15
```

## Testes com JUnit

Para implementar testes unitários posteriormente, sugere-se testar:

- Inicialização do aquário
- Movimento dos peixes
- Reprodução dos peixes
- Morte por fome
- Contagem de peixes
- Detecção de fim de jogo

## Observações

- O jogo usa vizinhança de Moore (8 células ao redor)
- Posições são escolhidas aleatoriamente quando há múltiplas opções
- Peixes recém-nascidos não se movem na iteração em que nascem
- O jogo pode durar indefinidamente com parâmetros bem equilibrados

## Autor

Desenvolvido para execução no Eclipse IDE

## Licença

Projeto educacional
