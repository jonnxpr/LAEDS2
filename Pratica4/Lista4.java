/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista4;

/**
 *
 * @author aluno
 */
public class Lista4 {
    
    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 100000;
        int [] vetor = new int [n];
        Heap h = new Heap(vetor);
        //ordem crescente
        //for (int i = 1; i < n; i++){
            //vetor[i] = i;
        //}
        
        //ordem decrescente
        //for (int i = n; i >= 1; i--){
            //vetor[i] = i;
        //}
        
        //aleatório sem repetição
          
        for (int i = 0; i < n; i++){
            int valor = (int)Lista4.getRandomDoubleBetweenRange(1, 500000);
            if (Heap.pesquisa(valor)){
                while (Heap.pesquisa(valor)){
                    valor = (int)Lista4.getRandomDoubleBetweenRange(1, 500000);
                }
                vetor[i] = valor;
            } else {
                vetor[i] = valor;
            }
        }
        
        h = new Heap(vetor);
        
        System.out.println("trocas = " + h.getTrocasDePosicoes());
        h.heapsort();
        System.out.println("trocashsort = " + h.getTrocasDePosicoesHeapsort());
    }
    
}
