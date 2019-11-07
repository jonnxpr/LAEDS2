/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista3;

/**
 *
 * @author Jonathan Douglas Diego Tavares
 * @matricula 201622040228
 * @disciplina Laboratório de Algoritmo de Estrutura de Dados 2
 */
public class Lista3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //instância 10 árvores B
        ArvoreB [] arv = new ArvoreB[10];
        //cria parâmetro para quantidade de elementos na árvore
        int n = 100000;
        //cria parâmetro para ordem da árvore
        int ordem = 16;
        
        /*
            percorre o vetor de árvores instanciando cada posição como uma
            árvore B de uma determinada ordem. Em seguida insere um conjunto
            de elementos que vai de 1 até n em cada árvore do vetor. Segue
            calculando o tempo de pesquisa na árvore atual e imprimindo o
            resultado do número de comparações e páginas visitadas na tela
            atráves do método getRelatorio().
        */
        for (int i = 0; i < 10; i++){
            arv[i] = new ArvoreB(ordem);
            
            for (int j = 1; j <= n; j++){
                arv[i].insere(j);
            }
            
            long startTime = System.nanoTime();
        
            arv[i].pesquisa(n + 1);
        
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;
            
            arv[i].getRelatorio();
        }
    }
    
}
