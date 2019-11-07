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
    
    /**
     * Método construtor, responsável por instanciar os vetores (atributos),
     * da classe
     * @param vertices quantidade de vértices do grafo 
     */
    public AlgoritmosEmGrafos(int vertices) {
        super(vertices);
        distanciaProfundidade = new int[vertices];
        verticePredecessor = new int[vertices];
    }
    
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
}
