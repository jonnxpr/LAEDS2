package listasdegrafos;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
       
        int verticeInicial = 0;
        int verticeFinal = 5;
        
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
            //System.out.println("v1 " + vertice1 + " v2 " + vertice2);
        }
        
        //System.out.println(grafo.iniciaBuscaEmLargura(verticeInicial, verticeFinal));
        /*
        //Obtém antecessores
        int[] antecessores = grafo.getVerticePredecessorLargura();
        
        System.out.println("\n");
        //Mostra os antecessores dos vértices cujas distâncias foram calculadas
        for (int i = 0; i < antecessores.length; i++){
            //System.out.println("Antecessor: " + i + " = " + antecessores[i]);
        }
        
        ArrayList<Pair<Integer, Integer>> caminho = grafo.obtemCaminhoLargura(verticeInicial, verticeFinal);
        
        for (Pair p : caminho){
            //System.out.println(p.getKey() + " " + p.getValue());
        }
        */
        
        //recupera o valor obtido para o fluxo máximo
        System.out.println("Fluxo Maximo = " + grafo.fluxoMaximo(verticeInicial, verticeFinal));
        
        //recupera os caminhos de aumento e suas respectivas capacidades residuais
        ArrayList < ArrayList <Integer>> caminhosAumento = grafo.getCaminhosDeAumento();
        ArrayList<Integer> capacidades = grafo.getCapacidadesResiduais();
        
        
        System.out.println("");
        System.out.println("Caminhos de aumento: ");
        //percorre o arraylist de arraylist colocando os vértices encontrados
        //para cada caminho de aumento em ordem crescente para exibição.
        for (int i = 0; i < caminhosAumento.size(); i++){
            for (int j = 0; j < caminhosAumento.get(i).size(); j++){
                Collections.sort(caminhosAumento.get(i));
                System.out.print(caminhosAumento.get(i).get(j) + " ");
            }
            //imprime a capacidade residual do caminho de aumento atual
            System.out.println("\nCapacidade residual = " + capacidades.get(i));
            System.out.println("");
        }
        
        //Mostra distância de um vértice inicial até um vértice final
        //System.out.println("Distância: " + verticeInicial + " para " + verticeFinal + " = " + grafo.iniciaCaminhoMaisCurto(verticeInicial, verticeFinal));
        //Mostra antecessor de um vértice final
        //System.out.println("Antecessor: " + verticeFinal + " = " + antecessores[verticeFinal]);      
    }
    
}
