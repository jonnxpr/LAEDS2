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
public class Heap {

    private final int[] heap;
    private int n;
    private final int[] heapOrdenado;
    public static int[] staticHeap;
    private int trocasDePosicoes;
    private int trocasDePosicoesHeapsort;

    public Heap(int[] h) {
        heap = h;
        staticHeap = copiaVetor(h);
        heapOrdenado = copiaVetor(h);
        trocasDePosicoes = 0;
        trocasDePosicoesHeapsort = 0;
        n = h.length - 1;
        
        constroi();
    }
    
    private int[] copiaVetor(int[] vetor){
        int[] vetorNovo = new int[vetor.length];
        int tamanho = vetor.length;
        
        for (int i = 0; i < vetor.length; i++){
            vetorNovo[0] = vetor[0];
        }
        
        return vetorNovo;
    }

    private int compara(int it, int itPagina) {
        if (it < itPagina) {
            return -1;
        } else if (it > itPagina) {
            return 1;
        }
        return 0;
    }

    private void constroi() {
        int esq = n / 2 + 1;
        while (esq > 1) {
            esq--;
            this.refaz(esq, this.n);
        }
    }
    
    private void constroiOrdenado() {
        int esq = n / 2 + 1;
        while (esq > 1) {
            esq--;
            this.refaz(esq, this.n);
        }
    }

    private void refaz(int esq, int dir) {
        int j = esq * 2;
        int x = this.heap[esq];
        //System.out.println("j = " + j + " dir = " + dir);
        while (j <= dir) {
            if ((j < dir) && (compara(this.heap[j], (this.heap[j + 1])) < 0)) {
                j++;
            }
            if (compara(x, this.heap[j]) >= 0) {
                break;
            }
            this.heap[esq] = this.heap[j];
            trocasDePosicoes++;
            esq = j;
            j = esq * 2;
        }
        this.heap[esq] = x;
    }
    
    private void refazOrdenado(int esq, int dir) {
        int j = esq * 2;
        int x = this.heapOrdenado[esq];
        while (j <= dir) {
            if ((j < dir) && (compara(this.heapOrdenado[j], (this.heapOrdenado[j + 1])) < 0)) {
                j++;
            }
            if (compara(x, this.heapOrdenado[j]) >= 0) {
                break;
            }
            trocasDePosicoesHeapsort++;
            this.heapOrdenado[esq] = this.heapOrdenado[j];
            esq = j;
            j = esq * 2;
        }
        this.heapOrdenado[esq] = x;
    }

    public void aumentaChave(int i, int chaveNova)
            throws Exception {
        int x = this.heap[i];
        
        x = chaveNova;
        while ((i > 1) && (compara(x, this.heap[1/2]) >= 0)) {
            this.heap[i] = this.heap[i / 2];
            i /= 2;
        }
        this.heap[i] = x;
    }

    public void insere(int x) throws Exception {
        if (this.n == this.heap.length) {
            throw new Exception("Erro : heap cheio");
        }
        int chaveNova = x;
        this.heap[this.n] = x;
        this.heap[this.n] = (new Integer(Integer.MIN_VALUE));
        this.aumentaChave(this.n, chaveNova);
    }

    private int retiraMaximo(int tamanhoHeap) throws Exception{
        int maximo;
        if (this.n < 1) {
            throw new Exception("Erro : heap vazio");
        } else {
            maximo = this.heap[1];
            this.heap[1] = this.heap[this.n--];
            refaz(1, this.n);
        }
        return maximo;
    }

    public void heapsort() {
        int dir = n;
        this.constroiOrdenado();
        //this.imprimeHeapOrdenado();
        while (dir > 1){
            //System.out.println("oi");
            int x = heapOrdenado[1];
            heapOrdenado[1] = heapOrdenado[dir];
            heapOrdenado[dir] = x;
            trocasDePosicoesHeapsort++;
            dir--;
            refazOrdenado(1, dir);
        }
    }

    public int[] getHeap() {
        return this.heap;
    }

    public int[] getHeapOrdenado() {
        return this.heapOrdenado;
    }

    public int getTrocasDePosicoes() {
        return this.trocasDePosicoes;
    }
    
    public int getTrocasDePosicoesHeapsort(){
        return this.trocasDePosicoesHeapsort;
    }
    
    public void imprimeHeap(){
        for (int i = 0; i < this.heap.length; i++){
            System.out.print(this.heap[i] + " ");
        }
    }
    
    public void imprimeHeapOrdenado(){
        for (int i = 0; i < this.heapOrdenado.length; i++){
            System.out.print(this.heapOrdenado[i] + " ");
        }
    }
    
    public static boolean pesquisa(int n){
        for (int i = 0; i < staticHeap.length; i++){
            if (n == staticHeap[i]){
                return true;
            }
        }
        return false;
    }
}
