/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista1;

/**
 *
 * @author Jonthan Douglas Diego Tavares
 * @matricula 201622040228
 */
public class Lista1 {
    public static double getRandomDoubleBetweenRange(double min, double max){
        double x = (Math.random()*((max-min)+1))+min;
        return x;
    }
    
    public static void main(String[] args) {
        ArvoreBinaria arv = new ArvoreBinaria();
        int n = 1000;
        for (int i = 1; i <= n; i++){
            arv.insere(new Item(i));
        }
        long startTime = System.nanoTime();
        arv.pesquisa(new Item(n+1));
        long endTime = System.nanoTime() - startTime;
        
        System.out.println(arv.getComparacoes() + " comparações");
        System.out.println(endTime + " nanoseconds");
    }
}
