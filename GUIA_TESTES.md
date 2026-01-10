# Guia de Testes - Jogo do Aquário

## Estrutura do Projeto

```
jogoAquario/
├── src/              # Código fonte
│   ├── Aquario.java
│   ├── Main.java
│   ├── Peixe.java
│   ├── PeixeA.java
│   ├── PeixeB.java
│   ├── Posicao.java
│   └── Utils.java
└── test/             # Testes JUnit
    └── AquarioTest.java
```

## Parte II-A: Automatização do Teste Funcional

### Casos de Teste Implementados

A classe `AquarioTest.java` implementa 10 casos de teste baseados em:
- **Particionamento em Classes de Equivalência**
- **Análise de Valores Limite**

#### Resumo dos Casos de Teste

| ID | Descrição | Classes Exercitadas | Tipo |
|----|-----------|---------------------|------|
| CT01 | Valores válidos normais | V1-V7 | Válido |
| CT02 | Matriz inválida (M=0) | I1 | Inválido |
| CT02b | Matriz inválida (N=0) | I1 | Inválido |
| CT02c | Matriz inválida (M<0) | I1 | Inválido |
| CT03 | Peixes A inválido (X<0) | I2 | Inválido |
| CT03b | Peixes B inválido (Y<0) | I3 | Inválido |
| CT04 | RA inválido (RA=0) | I4 | Inválido |
| CT04b | MA inválido (MA=0) | I5 | Inválido |
| CT04c | RB inválido (RB=0) | I6 | Inválido |
| CT04d | MB inválido (MB=0) | I7 | Inválido |
| CT05 | Valores limítrofes mínimos | V1-V7 | Válido |
| CT06 | Matriz mínima (1x1) | V1-V7 | Válido |
| CT07 | Peixes excedem matriz | - | Inválido |
| CT08 | Execução de iteração | V1-V7 | Válido |
| CT09 | Jogo terminado (sem peixes B) | V1-V7 | Válido |
| CT10 | Aquário vazio | V1-V7 | Válido |

### Classes de Equivalência

#### Classes Válidas (V)
- **V1**: M, N >= 1 (tamanho da matriz)
- **V2**: X >= 0 (quantidade de peixes A)
- **V3**: Y >= 0 (quantidade de peixes B)
- **V4**: RA > 0 (movimentos para reprodução A)
- **V5**: MA > 0 (iterações sem movimento para morte A)
- **V6**: RB > 0 (peixes comidos para reprodução B)
- **V7**: MB > 0 (iterações sem comer para morte B)

#### Classes Inválidas (I)
- **I1**: M <= 0 ou N <= 0
- **I2**: X < 0
- **I3**: Y < 0
- **I4**: RA <= 0
- **I5**: MA <= 0
- **I6**: RB <= 0
- **I7**: MB <= 0

## Como Executar os Testes no Eclipse

### 1. Configurar o Projeto no Eclipse

1. Abra o Eclipse IDE
2. Crie um novo Java Project: `File > New > Java Project`
3. Nome: `jogoAquario`
4. Configure para Java 8 (ou superior)
5. Clique em `Finish`

### 2. Importar os Arquivos

1. Copie os arquivos de `src/` para `src` do projeto Eclipse
2. Crie uma source folder `test`:
   - Clique direito no projeto > `New > Source Folder`
   - Nome: `test`
3. Copie `AquarioTest.java` para a pasta `test`

### 3. Adicionar JUnit ao Classpath

1. Clique direito no projeto > `Properties`
2. Selecione `Java Build Path`
3. Aba `Libraries`
4. Clique `Add Library...`
5. Selecione `JUnit`
6. Escolha `JUnit 4` (compatível com Java 8)
7. Clique `Finish` e `Apply`

### 4. Executar os Testes

**Executar todos os testes:**
1. Clique direito em `AquarioTest.java`
2. Selecione `Run As > JUnit Test`

**Executar teste específico:**
1. Abra `AquarioTest.java`
2. Clique direito no método de teste
3. Selecione `Run As > JUnit Test`

### 5. Visualizar Resultados

A aba `JUnit` mostrará:
- ✅ Testes que passaram (verde)
- ❌ Testes que falharam (vermelho)
- Total de testes executados
- Tempo de execução

## Parte II-B: Análise de Cobertura

### EclEmma (Cobertura de Fluxo de Controle)

Instalar: Help → Eclipse Marketplace… → buscar "EclEmma" → instalar "EclEmma Java Code Coverage" → reiniciar Eclipse.

#### Como usar:

1. Clique direito em `AquarioTest.java`
2. Selecione `Coverage As > JUnit Test`
3. Visualize a cobertura:
   - **Verde**: Código coberto
   - **Amarelo**: Código parcialmente coberto
   - **Vermelho**: Código não coberto

#### Métricas do EclEmma:

- **Instruction Coverage**: Cobertura de instruções
- **Branch Coverage**: Cobertura de desvios (if/else, switch)
- **Line Coverage**: Cobertura de linhas
- **Method Coverage**: Cobertura de métodos
- **Class Coverage**: Cobertura de classes

#### Gerar Relatório:

1. Após executar `Coverage As > JUnit Test`
2. Vá em `Window > Show View > Coverage`
3. Clique direito na sessão > `Export Session...`
4. Escolha formato HTML ou CSV
5. Salve o relatório

### Baduíno (Cobertura de Fluxo de Dados)

**Baduíno** é um plugin para Eclipse que analisa critérios de teste baseados em fluxo de dados.

#### Instalação do Baduíno:

1. `Help > Install New Software...`
2. Clique em `Add...`
3. Nome: `Baduino`
4. URL: (verifique a URL oficial do Baduíno)
5. Selecione o plugin Baduíno
6. Clique `Next` e siga as instruções
7. Reinicie o Eclipse

#### Como usar o Baduíno:

1. Clique direito no projeto
2. Selecione `Baduíno > Analyze`
3. Configure os critérios:
   - **All-Defs**: Todas as definições
   - **All-Uses**: Todos os usos
   - **All-Du-Paths**: Todos os caminhos definição-uso
4. Execute a análise
5. Visualize os requisitos cobertos/não cobertos

#### Gerar Relatório do Baduíno:

1. Após análise, vá na view do Baduíno
2. Exporte os resultados em formato HTML ou texto
3. Documente os requisitos não cobertos

## Estratégia para 100% de Cobertura

### Passo 1: Executar TestSet-Func (Testes Funcionais)

```bash
# No Eclipse:
1. Run As > JUnit Test (AquarioTest.java)
2. Coverage As > JUnit Test (verificar cobertura)
```

### Passo 2: Analisar Gaps de Cobertura

1. **EclEmma**: Identificar linhas/branches não cobertas (vermelho/amarelo)
2. **Baduíno**: Identificar requisitos de fluxo de dados não cobertos

### Passo 3: Adicionar Testes Estruturais (TestSet-Estr)

Crie novos casos de teste para cobrir:
- **Branches não exercitadas**: if/else não cobertos
- **Caminhos de exceção**: catch blocks
- **Loops**: diferentes iterações
- **Métodos não chamados**: métodos auxiliares
- **Definições-Usos**: variáveis definidas mas não usadas em testes

### Exemplo de teste estrutural adicional:

```java
@Test
public void testEstruturalPeixeAReproducao() {
    Aquario aquario = new Aquario(3, 3);
    aquario.inicializar(2, 0, 1, 5, 1, 1); // RA=1 para reproduzir rapido
    
    // Executar iteracoes ate reproduzir
    for (int i = 0; i < 3; i++) {
        aquario.executarIteracao();
    }
    
    // Verificar se houve reproducao
    assertTrue("Deve ter mais peixes A apos reproducao", 
               aquario.contarPeixesA() >= 2);
}
```

## Correção de Defeitos

### Processo:

1. **Identificar defeito** através dos testes
2. **Documentar** no relatório:
   - Caso de teste que falhou
   - Comportamento esperado vs. obtido
   - Linha do código com defeito
3. **Corrigir** o código fonte
4. **Reexecutar todos os testes** (regressão)
5. **Verificar cobertura** novamente

### Template de Relatório de Defeito:

```
ID Defeito: D01
Teste: CT01
Descrição: [Descrever o problema]
Comportamento Esperado: [...]
Comportamento Obtido: [...]
Causa Raiz: [Linha X do arquivo Y.java]
Correção: [Descrever a correção]
Status: Corrigido / Em Análise
```

## Comandos Rápidos no Eclipse

| Ação | Atalho |
|------|--------|
| Executar teste | `Alt + Shift + X, T` |
| Executar com cobertura | `Alt + Shift + E, T` |
| Reexecutar último teste | `Ctrl + F11` |
| Debug teste | `Alt + Shift + D, T` |

## Checklist Final

- [ ] Todos os testes funcionais (CT01-CT10) passam
- [ ] Cobertura EclEmma >= 90% (idealmente 100%)
- [ ] Cobertura Baduíno >= 90% dos requisitos
- [ ] Defeitos documentados e corrigidos
- [ ] Relatórios gerados (EclEmma + Baduíno)
- [ ] Testes estruturais adicionais implementados
- [ ] Regressão completa executada

## Observações

- Os testes estão escritos em **Java 8** compatível
- Mensagens sem acentos para compatibilidade com terminal Eclipse
- Use `@Test(expected = Exception.class)` para testar exceções
- Para testes mais avançados, considere usar `@Before` e `@After`
- Documente sempre as classes de equivalência exercitadas

## Referências

- JUnit 4 Documentation: https://junit.org/junit4/
- EclEmma User Guide: https://www.eclemma.org/userdoc/
- Baduíno: (documentação do plugin)
