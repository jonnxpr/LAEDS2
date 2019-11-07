/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista2;

/**
 *
 * @author Jonathan Douglas Diego Tavares
 * @matricula 201622040228
 * @disciplina Labóratorio de Algoritmos e Estrutura de Dados II
 */
public class Lista2 {
    
    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        ArvoreSBB arv = new ArvoreSBB();
        int n = 100000;
            
        for (int i = 1; i <= n; i++){
            int valor = (int)Lista2.getRandomDoubleBetweenRange(1, 500000);
            if (arv.pesquisa(new Item(valor)) != null){
                while (arv.pesquisa(new Item(valor)) != null){
                    valor = (int)Lista2.getRandomDoubleBetweenRange(1, 500000);
                }
                arv.insere(new Item(valor));
            } else {
                arv.insere(new Item(valor));
            }
        }
        
        System.out.println("Valor raiz = " + arv.getValorRaiz());
        
        long startTime = System.nanoTime();
        
        arv.pesquisa(new Item(500001));
        
        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        
        System.out.println("Tamanho arv = " + arv.retornaQuantidadeNiveis());
        System.out.println(totalTime + " nanoseconds");
        System.out.println(arv.getNosVisitados() + " nós visitados");
    }
    
}

