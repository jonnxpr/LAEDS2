/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listasdegrafos;

import java.util.ArrayList;

/**
 *
 * @author aluno
 */
public class Grafo {
    
    //Atributos da classe
    protected final int numeroVertices;
    protected final int[][] matrizAdjacencia;
    
    /**
     * Método construtor, responsável por inicializar o número de vértices
     * e a matriz de adjacência
     * @param vertices quantidade de vértices do grafo
     */
    public Grafo(int vertices){
        numeroVertices = vertices;
        matrizAdjacencia = new int[numeroVertices][numeroVertices];
        inicializaMatrizAdj();
    }
    
    /**
     * Método auxiliar para colocar o valor 0 nas posições da matriz
     * de adjacência.
     */
    private void inicializaMatrizAdj(){
        for (int i = 0; i < numeroVertices; i++){
            for (int j = 0; j < numeroVertices; j++){
                matrizAdjacencia[i][j] = 0;
            }
        }
    }
    
    /**
     * Método insereAresta, responsável por inserir uma aresta na matriz de
     * adjacência
     * @param vertice1 vértice linha
     * @param vertice2 vértice coluna
     * @param peso peso da aresta
     */
    public void insereAresta(int vertice1, int vertice2, int peso){
        //adiciona na posição [v1][v2] o valor do peso correspondente a aresta adicionada
        matrizAdjacencia[vertice1][vertice2] = peso;
    }
    
    /**
     * Método existeAresta, responsável por verificar se existe determinada aresta
     * na matriz de adjacência
     * @param vertice1
     * @param vertice2
     * @return true se existir, false caso não exista
     */
    public boolean existeAresta(int vertice1, int vertice2){
        return matrizAdjacencia[vertice1][vertice2] > 0;
    }
    
    /**
     * Método listaDeAdjacencia, responsável por retornar a lista de adjacência
     * obtida a partir da matriz
     * @param vertice1
     * @return ArrayList contendo as adjacências de determinado vértice
     */
    ArrayList<Integer> listaDeAdjacencia(int vertice1){
        ArrayList<Integer> listaAdj = new ArrayList();
        
        for (int i = 0; i < numeroVertices; i++){
          if(matrizAdjacencia[vertice1][i] != 0){
              listaAdj.add(i);
          }
        }
        
        return listaAdj;
    }
    
    /**
     * Método getPeso, responsável por retornar o peso de uma aresta.
     * @param vertice1
     * @param vertice2
     * @return o peso de determinada aresta contida na matriz
     */
    public int getPeso(int vertice1, int vertice2){
        return this.matrizAdjacencia[vertice1][vertice2];
    }
    
    /**
     * Método imprimeMatrizAdj, responsável por realizar a impressão
     * da matriz de adjacência
     */
    public void imprimeMatrizAdj(){
        System.out.println("Matriz de adjacência:");
        for (int i = 0; i < numeroVertices; i++){
            for (int j = 0; j < numeroVertices; j++){
                System.out.print(matrizAdjacencia[i][j] + " ");
            }
            System.out.println();
        }
    }
    
    /**
     * Método numVertices, responsável por retornar a quantidade de vértices
     * no grafo
     * @return quantidade de vértices no grafo
     */
    public int numVertices(){
        return this.numeroVertices;
    }
    
}
