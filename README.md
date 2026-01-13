# Jogo do Aquário

Este projeto contém a simulação de um ecossistema aquático com dois tipos de peixes.

Instruções de compilação e execução (Windows):

1) A partir do diretório raiz do projeto (`c:\Users\mathe\Documents\jogoAquario`), crie a pasta `bin` se não existir:

```powershell
mkdir bin
```

2) Compile as classes (fonte + testes) direcionando os .class para `bin` (Java 8):

```powershell
javac -cp "lib/*" -source 1.8 -target 1.8 -d bin src/jogoAquario/*.java src/test/*.java
```

3) Executar o jogo a partir do diretório raiz (classpath incluindo `bin`):

```powershell
java -cp "bin;lib/*" jogoAquario.Main
```

4) Executar os testes JUnit (se `lib` contiver os jars do JUnit/hamcrest):

```powershell
java -cp "bin;lib/*" org.junit.runner.JUnitCore test.AquarioTestEstrutural
```


Estrutura principal dos fontes:

```
src/jogoAquario/ - código fonte principal
src/test/        - testes JUnit
bin/             - saída de compilação (.class)
lib/             - bibliotecas (JUnit, JaCoCo, etc.)
```