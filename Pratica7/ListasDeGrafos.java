/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listasdegrafos;


import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Jonathan Douglas Diego Tavares
 * @matricula 201622040228
 * @disciplina Laboratório de Algoritmos e Estrutura de Dados II
 */
public class ListasDeGrafos {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //declara variáveis necessárias
        AlgoritmosEmGrafos grafo; //grafo
        Scanner fileReader = null; //Scanner para leitura de dados providos de um determinado arquivo
        Scanner in = new Scanner(System.in); //instanciação de um scanner para leitura da entrada padrão
        int vertices, arestas; //declara variáveis necessárias
        
        //realiza leitura do nome do aruivo
        System.out.println("Digite o nome do arquivo: ");
        String nomeArq = in.nextLine();
       
        int verticeInicial = 0; //vértice inicial do caminho mais curto
        int verticeFinal = 0; //vértice final do caminho mais curto
        
        //tenta realizar a abertura de um arquivo e caso haja exceção retorna o erro
        try{
            fileReader = new Scanner(new File(nomeArq));
        } catch(IOException e){
            System.err.printf("Erro na abertura do arquivo: %s. \n", e.getMessage());
        }
        
        //lê dois dados inteiros do arquivo aberto
        vertices = fileReader.nextInt();
        arestas = fileReader.nextInt();
        
        //instância um objeto do tipo AlgoritmosEmGrafos
        grafo = new AlgoritmosEmGrafos(vertices);
        
        //percorre arquivo lendo os dados e preenchendo o grafo com as arestas obtidas
        for (int i = 0; i < arestas; i++){
            int vertice1 = fileReader.nextInt();
            int vertice2 = fileReader.nextInt();
            int peso = fileReader.nextInt();
            
            grafo.insereAresta(vertice1, vertice2, peso);
        }
        
        //Obtém distâncias
        int[] distancias = grafo.iniciaCaminhoMaisCurto(verticeInicial);
        
        //Mostra a distância de um vértice inicial até todos os demais vértices
        for (int i = 0; i < distancias.length; i++){
            System.out.println("Distância: " + verticeInicial + " para " + i + ((distancias[i] != 9999999) ? (" = " + distancias[i]) : " = Infinito"));
        }
        
        //Obtém antecessores
        int[] antecessores = grafo.getVerticeAntecessorCMC();
        
        System.out.println("\n");
        //Mostra os antecessores dos vértices cujas distâncias foram calculadas
        for (int i = 0; i < antecessores.length; i++){
            System.out.println("Antecessor: " + i + " = " + antecessores[i]);
        }
        
        //Mostra distância de um vértice inicial até um vértice final
        //System.out.println("Distância: " + verticeInicial + " para " + verticeFinal + " = " + grafo.iniciaCaminhoMaisCurto(verticeInicial, verticeFinal));
        //Mostra antecessor de um vértice final
        //System.out.println("Antecessor: " + verticeFinal + " = " + antecessores[verticeFinal]);      
    }
    
}
