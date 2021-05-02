package caixeiroViajante;

/**
 * Trabalho Prático - Caixeiro Viajante
 *
 * @author Jonathan Douglas Diego Tavares
 * @matricula 201622040228
 * @disciplina Laboratório de Algoritmos e Estrutura de Dados II / Algoritmos e
 * Estrutura de Dados II
 */
public class Main {

    /**
     * Calcula um número randômico entre min e max
     * @param min
     * @param max
     * @return número sorteado entre min e max
     */
    public static double getRandomDoubleBetweenRange(double min, double max) {
        double x = (Math.random() * ((max - min) + 1)) + min;
        return x;
    }

    /**
     * Gera uma matriz com valores aleatórios entre min e max
     * @param grafo contém a matriz que vai ser gerada
     * @param min valor mínimo
     * @param max valor máximo
     */
    public static void gerarMatrizAleatoria(Grafo grafo, double min, double max) {
        for (int i = 0; i < grafo.numVertices(); i++) {
            for (int j = i + 1; j < grafo.numVertices(); j++) {
                grafo.insereArestaNaoOrientada(i, j, (int) Main.getRandomDoubleBetweenRange(min, max));
            }
        }
    }

    public static void main(String[] args) {

        int cidadeInicial = 0; //cidade inicial do percurso
        int quantidadeDeCidades = 100; //quantidade de cidades do problema
                
        //algoritmos
        Guloso guloso;
        ForcaBruta forcaBruta;
        Heuristica heuristica;
        
        //problema e solução
        Problema problema;
        Solucao solucao;
        
        //habilitar a linha abaixo e desabilitar a outra contendo a criação da variável grafo 2 linhas abaixo
        //caso se deseje utilizar instâncias geradas aleatoriamente
        Grafo grafo = new Grafo(quantidadeDeCidades); //parâmetro construtor é a quantidade de cidades
        
        //habilitar a linha abaixo e desabilitar a de cima caso se deseje obter os dados a partir de um arquivo
        //Grafo grafo; 
        
        //variáveis para cálculo do tempo de execução
        long tempoInicial;
        long tempoFinal;
        
        //habilitar esta linha para gerar uma instância aleatória
        Main.gerarMatrizAleatoria(grafo, 0, 500);

        //Habilitar este trecho de código caso deseje obter a instância do problema
        //a partir de um arquivo
        /*int[][] matriz = Arquivo.getMatrizFromFile();
        grafo = new Grafo(matriz.length);
        grafo.setMatriz(matriz);*/
        
        problema = new Problema(grafo);
        guloso = new Guloso(problema);
        forcaBruta = new ForcaBruta(problema);
        heuristica = new Heuristica(problema);

        /**
         * *************************************
         * Solução Força bruta
         * Máx cidades: 11
         * 
         * Comentar trecho de código caso
         * não vá utilizar o algoritmo
         *************************************
         */
        /*tempoInicial = System.currentTimeMillis();

        solucao = forcaBruta.getSolucao(cidadeInicial);
        
        tempoFinal = System.currentTimeMillis();
        
        //habilitar a linha abaixo caso deseje ver o caminho o obtido pela solução
        //solucao.mostrarCaminho();
        
        System.out.println("Solução Força Bruta - Cidade inicial (" + cidadeInicial + "): " + solucao.getDistanciaTotal());

        System.out.printf("Tempo total de execução: %.3f ms%n", (tempoFinal - tempoInicial) / 1000d);
        System.out.println("");*/

        /**
         * *************************************
         * Solução Heurística Gulosa
         * Vizinho mais próximo
         * 
         * Comentar trecho de código caso
         * não vá utilizar o algoritmo
         *************************************
         */
        tempoInicial = System.currentTimeMillis();

        solucao = guloso.getSolucao(cidadeInicial);

        tempoFinal = System.currentTimeMillis();
        
        //habilitar a linha abaixo caso deseje ver o caminho o obtido pela solução
        //solucao.mostrarCaminho();
        
        System.out.println("Solução Heurística Gulosa(Vizinho mais próximo) - Cidade inicial (" + cidadeInicial + "): " 
                + solucao.getDistanciaTotal());

        System.out.printf("Tempo total de execução: %.3f ms%n", (tempoFinal - tempoInicial) / 1000d);
        System.out.println("");

        /**
         * *************************************
         * Solução Heurística Inserção mais barata
         * 
         * Comentar trecho de código caso
         * não vá utilizar o algoritmo
         *************************************
         */
        tempoInicial = System.currentTimeMillis();

        solucao = heuristica.getSolucao(cidadeInicial);
        
        tempoFinal = System.currentTimeMillis();
        
        //habilitar a linha abaixo caso deseje ver o caminho o obtido pela solução
        //solucao.mostrarCaminho();
        
        System.out.println("Solução Heurística(Inserção mais barata) - Cidade inicial (" + cidadeInicial + "): " 
                + solucao.getDistanciaTotal());
        
        System.out.printf("Tempo total de execução: %.3f ms%n", (tempoFinal - tempoInicial) / 1000d);
    }
}
