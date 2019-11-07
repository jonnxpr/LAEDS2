/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listasdegrafos;

import java.util.ArrayList;

/**
 *
 * @author Jonathan Douglas Diego Tavares
 * @matricula 201622040228
 * @disciplina Laboratório de Algoritmos e Estrutura de Dados II
 */
public class AlgoritmosEmGrafos extends Grafo {
    
    //Atributos da classe
    private final int[] distanciaProfundidade; // vetor contendo as distâncias obtidas a partir da busca em profunidade
    private final int[] verticePredecessor; // vetor contendo os predecessores obtidos a partir da busca em profundidade
    private final int[] distanciasCMC;
    private final int[] verticeAntecessorCMC;
    private final MinHeap heapMin;
    
    /**
     * Método construtor, responsável por instanciar os vetores (atributos),
     * da classe
     * @param vertices quantidade de vértices do grafo 
     */
    public AlgoritmosEmGrafos(int vertices) {
        super(vertices);
        distanciaProfundidade = new int[vertices];
        verticePredecessor = new int[vertices];
        distanciasCMC = new int[vertices];
        verticeAntecessorCMC = new int[vertices];
        heapMin = new MinHeap(vertices);
    }
    
    /***********************************************************************
     * BUSCA EM PROFUNDIDADE
     **********************************************************************/
    /**
     * Método iniciaBuscaEmProfundidade, responsável por inicializar os
     * vetores de distância e predecessores, e chamar a busca em profundidade
     * a partir de determinado vértice
     * @param vertice vértice a partir de onde será realizada a busca
     */
    public void iniciaBuscaEmProfundidade(int vertice) {
        int tamanhoVetor = distanciaProfundidade.length;
        
        //Diz que a profundidade do vértice inicial é 0
        distanciaProfundidade[vertice] = 0;
        
        //Percorre o vetor de distâncias inicializando todas as posições com o valor V+1
        for (int i = 0; i < tamanhoVetor; i++) {
            if (!(i == vertice)) {
                distanciaProfundidade[i] = tamanhoVetor + 1;
            }
            //diz que o predecessor não existe ainda
            verticePredecessor[i] = -1;
        }
        //chama a busca em profundidade
        buscaProfundidade(vertice);
    }
    
    /**
     * Método buscaProfundidade, responsável por realizar efetivamente a busca
     * a partir de um determinado vértice inicial
     * @param vertice vértice inicial
     */
    private void buscaProfundidade(int vertice) {
        ArrayList<Integer> lista = listaDeAdjacencia(vertice);
        
        //Percorre a lista de adjacência
        for (int i = 0; i < lista.size(); i++){
            //Se a distância é "infinito" então descobre o vértice e atualiza sua distância e seu predecessor
            if (distanciaProfundidade[lista.get(i)] == distanciaProfundidade.length + 1){    
                distanciaProfundidade[lista.get(i)] = distanciaProfundidade[vertice] + 1;
                verticePredecessor[lista.get(i)] = vertice;

                //chama a busca em profundidade novamente, portanto o método é recursivo
                buscaProfundidade(lista.get(i));
            }
        }     
    }
    
    /**
     * Método getDistanciaProfundidade, resposável por retornar o vetor
     * de distâncias obtido na busca
     * @return vetor de distância
     */
    public int[] getDistanciaProfundidade() {
        return this.distanciaProfundidade;
    }
    
    /**
     * Método getVerticePai, responsável por retornar o vetor
     * de vértices predecessores
     * @return vetor de vértices predecessores
     */
    public int[] getVerticePai() {
        return verticePredecessor;
    }
    
    /**
     * Método getDistanciaVetorProf
     * @return tamanho do vetor de distância
     */
    public int getDistanciaVetorProf(){
        return distanciaProfundidade.length;
    }
    
    /***********************************************************************/
    
    /***********************************************************************
     * DJIKSTRA
     **********************************************************************/
    /**
     * Método inicializaDijkstra, responsável por inicializar os parâmetros
     * para o cálculo do menor caminho e construir o heap com os vértices do
     * grafo
     * @param verticeInicial 
     */
    private void inicializaDijkstra(int verticeInicial){
        for (int i = 0; i < numVertices(); i++){
            //Substituição do Integer.MAX_VALUE por 9999999
            //pois o MAX_VALUE estourava ao ser somado com mais
            //alguma coisa
            distanciasCMC[i] = 9999999;
            verticeAntecessorCMC[i] = -1;
        }
        
        distanciasCMC[verticeInicial] = 0;
        constroiHeapComVertices();
    }
    
    /**
     * Método iniciaCaminhoMaisCurto, responsável por chamar a função para obter
     * o caminho mais curto de um vértice inicial até os demais vértices, porém
     * retornando apenas a distância de vInicial até um determinado vFinal
     * @param verticeInicial
     * @param verticeFinal
     * @return distância de vInicial até vFinal
     */
    public int iniciaCaminhoMaisCurto(int verticeInicial, int verticeFinal){
        inicializaDijkstra(verticeInicial);
        dijkstra(verticeInicial);
        return distanciasCMC[verticeFinal];
    }
    
    /**
     * Método iniciaCaminhoMaisCurto, responsável por chamar a função para obter
     * o caminho mais curto de um determinado vértice inicial até os demais vérti
     * ces
     * @param verticeInicial
     * @return array contendo as distâncias obtidas 
     */
    public int[] iniciaCaminhoMaisCurto(int verticeInicial){
        inicializaDijkstra(verticeInicial);
        dijkstra(verticeInicial);
        return distanciasCMC;
    }
    
    /**
     * Método constroiHeapComVertices, responsável por inicializar o heap de
     * prioridade mínima com os vértices do grafo
     */
    private void constroiHeapComVertices(){
        for (int i = 0; i < numVertices(); i++){
            heapMin.insert(i);
        }
        //heapMin.print();
    }
    
    /**
     * Método dijkstra, responsável por realizar o cálculo do menor caminho entre
     * um vértice inicial e os demais vértices do grafo
     * @param verticeInicial 
     */
    private void dijkstra(int verticeInicial){
        //se heap não está vazio
        while (!heapMin.isEmpty()){
            //obtém valor mínimo do heap
            int u = heapMin.remove();
            //System.out.println("u = " + u);
            //recupera lista de adjacência do vértice obtido do heap
            ArrayList<Integer> listaAdj = listaDeAdjacencia(u);
            
            //percorrre para todos os elementos na lista de adj
            for (int v = 0; v < listaAdj.size(); v++){
                //se a distância entre vértice inicial até vértiec final é maior que a distância inicial + peso aresta 
                if (distanciasCMC[listaAdj.get(v)] > distanciasCMC[u] + matrizAdjacencia[u][listaAdj.get(v)]){
                    //atualiza a distância atual com o novo valor obtido
                    distanciasCMC[listaAdj.get(v)] = distanciasCMC[u] + matrizAdjacencia[u][listaAdj.get(v)];
                    //marca o antecessor
                    verticeAntecessorCMC[listaAdj.get(v)] = u;
                }
            }
        }
    }
    
    /**
     * Método getVerticeAntecessorCMC, responsável por obter os vértices anteces
     * sores
     * @return array contendo os vértices antecessores 
     */
    public int[] getVerticeAntecessorCMC(){
        return verticeAntecessorCMC;
    }
}
