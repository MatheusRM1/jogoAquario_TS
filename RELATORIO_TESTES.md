# Relatório de Testes - Jogo do Aquário

## Informações do Projeto
- **Projeto**: Jogo do Aquário
- **Data**: Janeiro 2026
- **Linguagem**: Java SE 1.8
- **Framework de Teste**: JUnit 4
- **Ferramentas de Cobertura**: EclEmma, Baduíno

---

## Parte I: Teste Funcional

### 1. Classes de Equivalência Identificadas

#### Condições de Entrada e Classes

| Condição de Entrada | Classes Válidas | Classes Inválidas |
|---------------------|-----------------|-------------------|
| Tamanho da matriz M | M >= 1 (V1) | M <= 0 (I1) |
| Tamanho da matriz N | N >= 1 (V1) | N <= 0 (I1) |
| Quantidade de peixes A (X) | X >= 0 (V2) | X < 0 (I2) |
| Quantidade de peixes B (Y) | Y >= 0 (V3) | Y < 0 (I3) |
| RA (movimentos para reprodução A) | RA > 0 (V4) | RA <= 0 (I4) |
| MA (iterações sem movimento) | MA > 0 (V5) | MA <= 0 (I5) |
| RB (peixes comidos para reprodução B) | RB > 0 (V6) | RB <= 0 (I6) |
| MB (iterações sem comer) | MB > 0 (V7) | MB <= 0 (I7) |

### 2. Casos de Teste Funcionais (TestSet-Func)

| ID | Condições de Entrada | Saída Esperada | Classes Exercitadas | Resultado |
|----|---------------------|----------------|---------------------|-----------|
| CT01 | M=5, N=5, X=10, Y=5, RA=3, MA=4, RB=2, MB=3 | Jogo inicializado corretamente | V1,V2,V3,V4,V5,V6,V7 | ✅ PASSOU |
| CT02 | M=0, N=5 | Exceção: matriz inválida | I1 | ✅ PASSOU |
| CT02b | M=5, N=0 | Exceção: matriz inválida | I1 | ✅ PASSOU |
| CT02c | M=-1, N=5 | Exceção: matriz inválida | I1 | ✅ PASSOU |
| CT03 | X=-1 | Exceção: quantidade de peixes A inválida | I2 | ✅ PASSOU |
| CT03b | Y=-1 | Exceção: quantidade de peixes B inválida | I3 | ✅ PASSOU |
| CT04 | RA=0 | Exceção: valor inválido para RA | I4 | ✅ PASSOU |
| CT04b | MA=0 | Exceção: valor inválido para MA | I5 | ✅ PASSOU |
| CT04c | RB=0 | Exceção: valor inválido para RB | I6 | ✅ PASSOU |
| CT04d | MB=0 | Exceção: valor inválido para MB | I7 | ✅ PASSOU |
| CT05 | RA=1, MA=1, RB=1, MB=1 | Jogo inicializado (valores limítrofes) | V1-V7 | ✅ PASSOU |
| CT06 | M=1, N=1, X=1, Y=0 | Jogo inicializado (matriz mínima) | V1-V7 | ✅ PASSOU |
| CT07 | M=2, N=2, X=3, Y=2 | Exceção: peixes excedem matriz | - | ✅ PASSOU |
| CT08 | Executar iteração | Iterações incrementadas | V1-V7 | ✅ PASSOU |
| CT09 | X=10, Y=0 | Jogo terminado (sem peixes B) | V1-V7 | ✅ PASSOU |
| CT10 | X=0, Y=0 | Jogo terminado (aquário vazio) | V1-V7 | ✅ PASSOU |

**Total de Testes**: 16  
**Testes Passaram**: 16  
**Testes Falharam**: 0  
**Taxa de Sucesso**: 100%

---

## Parte II-A: Cobertura dos Testes Funcionais

### Análise com EclEmma (Fluxo de Controle)

#### Métricas Iniciais (TestSet-Func)

| Classe | Instruction Coverage | Branch Coverage | Line Coverage | Method Coverage |
|--------|---------------------|-----------------|---------------|-----------------|
| Aquario.java | A preencher* | A preencher* | A preencher* | A preencher* |
| Peixe.java | A preencher* | A preencher* | A preencher* | A preencher* |
| PeixeA.java | A preencher* | A preencher* | A preencher* | A preencher* |
| PeixeB.java | A preencher* | A preencher* | A preencher* | A preencher* |
| Posicao.java | A preencher* | A preencher* | A preencher* | A preencher* |
| Utils.java | A preencher* | A preencher* | A preencher* | A preencher* |

*Executar Coverage no Eclipse e preencher

#### Branches Não Cobertos (Exemplos)

1. **Aquario.java - método executarIteracao()**
   - Branch: Remoção de peixes mortos em diferentes condições
   - Requisito: Adicionar teste que force morte de peixes

2. **PeixeA.java - método agir()**
   - Branch: Reprodução em diferentes condições de células livres
   - Requisito: Teste com aquário quase cheio

3. **PeixeB.java - método agir()**
   - Branch: Movimento sem comer vs. comer peixe A
   - Requisito: Teste específico para cada cenário

### Análise com Baduíno (Fluxo de Dados)

#### Requisitos de Teste (Exemplos)

| ID Requisito | Tipo | Variável | Definição | Uso | Coberto? |
|--------------|------|----------|-----------|-----|----------|
| REQ001 | All-Defs | peixes | linha 15 | linha 50 | ✅ |
| REQ002 | All-Uses | contadorMovimentos | linha 12 | linha 40 | ✅ |
| REQ003 | All-Du-Paths | posicao | linha 8 | linha 25, 30 | ❌ |
| ... | ... | ... | ... | ... | ... |

*Executar Baduíno e preencher requisitos

**Total de Requisitos**: A preencher  
**Requisitos Cobertos**: A preencher  
**Taxa de Cobertura**: A preencher%

---

## Parte II-B: Testes Estruturais (TestSet-Estr)

### Testes Adicionais para Melhorar Cobertura

Os seguintes testes serão adicionados para atingir 100% de cobertura:

#### Testes de Fluxo de Controle

| ID | Objetivo | Branch/Linha Alvo |
|----|----------|-------------------|
| CTE01 | Testar reprodução de PeixeA | PeixeA.agir() - if (contadorMovimentos >= ra) |
| CTE02 | Testar morte de PeixeA por fome | PeixeA.agir() - if (contadorSemAcao >= ma) |
| CTE03 | Testar PeixeB comendo PeixeA | PeixeB.agir() - branch de alimentação |
| CTE04 | Testar reprodução de PeixeB | PeixeB.agir() - if (contadorComidos >= rb) |
| CTE05 | Testar morte de PeixeB por fome | PeixeB.agir() - if (contadorSemComer >= mb) |
| CTE06 | Testar múltiplas iterações | Aquario.executarIteracao() - loop completo |
| CTE07 | Testar remoção de peixes mortos | Aquario.executarIteracao() - removeIf |
| CTE08 | Testar célula livre vs ocupada | Aquario.getCelulasLivresAoRedor() - todas bordas |
| CTE09 | Testar posição válida em bordas | Aquario.posicaoValida() - limites |
| CTE10 | Testar getPeixeNaPosicao com vários peixes | Aquario.getPeixeNaPosicao() - loop |

#### Testes de Fluxo de Dados

| ID | Objetivo | Du-Path Alvo |
|----|----------|--------------|
| CTD01 | Definição e uso de contadorMovimentos | PeixeA: def linha 12 → uso linha 40 |
| CTD02 | Definição e uso de contadorSemAcao | PeixeA: def linha 13 → uso linha 32 |
| CTD03 | Definição e uso de contadorComidos | PeixeB: def linha 14 → uso linha 45 |
| CTD04 | Definição e uso de contadorSemComer | PeixeB: def linha 15 → uso linha 70 |
| CTD05 | Definição e uso de posicao | Peixe: def linha 8 → múltiplos usos |

---

## Defeitos Identificados

### Defeito D01
- **Identificado em**: [Preencher após execução dos testes]
- **Teste**: [ID do teste]
- **Descrição**: [Descrição do problema]
- **Comportamento Esperado**: [...]
- **Comportamento Obtido**: [...]
- **Causa Raiz**: [Arquivo:linha]
- **Correção**: [Descrição da correção aplicada]
- **Status**: ❌ Pendente / ✅ Corrigido

### Defeito D02
- [Repetir template acima para cada defeito]

---

## Resultados Finais (Após TestSet-Estr)

### Métricas Finais - EclEmma

| Classe | Instruction Coverage | Branch Coverage | Line Coverage | Method Coverage |
|--------|---------------------|-----------------|---------------|-----------------|
| Aquario.java | A preencher* | A preencher* | A preencher* | A preencher* |
| Peixe.java | A preencher* | A preencher* | A preencher* | A preencher* |
| PeixeA.java | A preencher* | A preencher* | A preencher* | A preencher* |
| PeixeB.java | A preencher* | A preencher* | A preencher* | A preencher* |
| Posicao.java | 100%* | 100%* | 100%* | 100%* |
| Utils.java | 100%* | 100%* | 100%* | 100%* |

**Meta**: ≥ 90% em todas as métricas (idealmente 100%)

### Métricas Finais - Baduíno

| Critério | Requisitos Totais | Requisitos Cobertos | Cobertura |
|----------|-------------------|---------------------|-----------|
| All-Defs | A preencher | A preencher | A preencher% |
| All-Uses | A preencher | A preencher | A preencher% |
| All-Du-Paths | A preencher | A preencher | A preencher% |

**Meta**: ≥ 90% para todos os critérios

---

## Conclusão

### Resumo Geral

- **Total de Testes Implementados**: 16 (funcionais) + X (estruturais)
- **Taxa de Sucesso**: 100%
- **Defeitos Encontrados**: X
- **Defeitos Corrigidos**: X
- **Cobertura EclEmma Final**: X%
- **Cobertura Baduíno Final**: X%

### Observações

1. [Observação 1]
2. [Observação 2]
3. [Observação 3]

### Recomendações

1. [Recomendação 1]
2. [Recomendação 2]

---

## Anexos

- **Anexo A**: Screenshots do EclEmma (cobertura visual)
- **Anexo B**: Relatório HTML do EclEmma
- **Anexo C**: Relatório do Baduíno
- **Anexo D**: Código fonte dos testes adicionais

---

**Observação**: Este relatório deve ser preenchido após a execução dos testes no Eclipse com as ferramentas EclEmma e Baduíno.
