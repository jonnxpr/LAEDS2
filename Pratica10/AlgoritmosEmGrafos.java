package listasdegrafos;

import java.util.ArrayList;
import java.util.LinkedList;
import javafx.util.Pair;

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
    private final int[] verticePredecessorLargura;
    private final int[] distanciasCMC;
    private final int[] verticeAntecessorCMC;
    private final MinHeap heapMin;
    private final ArrayList< Pair< Integer, Integer>> arestasArvoreGeradoraMinima;
    private final int[] verticeAntecessorAGM;
    private final int[] distanciasAGM;
    private final ArrayList < ArrayList < Integer>> caminhosDeAumento;
    //fluxos mínimos de cada caminho
    private final ArrayList < Integer > capacidadeResidual;

    /**
     * Método construtor, responsável por instanciar os vetores (atributos), da
     * classe
     *
     * @param vertices quantidade de vértices do grafo
     */
    public AlgoritmosEmGrafos(int vertices) {
        super(vertices);
        distanciaProfundidade = new int[vertices];
        verticePredecessor = new int[vertices];
        verticePredecessorLargura = new int[vertices];
        distanciasCMC = new int[vertices];
        verticeAntecessorCMC = new int[vertices];
        heapMin = new MinHeap(vertices);
        arestasArvoreGeradoraMinima = new ArrayList<>();
        verticeAntecessorAGM = new int[vertices];
        distanciasAGM = new int[vertices];
        caminhosDeAumento = new ArrayList<>();
        capacidadeResidual = new ArrayList<>();
    }

    /**
     * *********************************************************************
     * BUSCA EM LARGURA
     *********************************************************************
     */
    
    /**
     * Método iniciaBuscaEmLargura, responsável por inicializar o array de 
     * vértices predecessores a ser preenchido durante a busca.
     * @param verticeInicial
     * @param verticeFinal
     * @return booleano indicando se existe um caminho entre o vértice inicial
     * e final
     */
    public boolean iniciaBuscaEmLargura(int verticeInicial, int verticeFinal) {

        int tamanhoVetor = verticePredecessorLargura.length;

        //Percorre o vetor de distâncias inicializando todas as posições com o valor V+1
        for (int i = 0; i < tamanhoVetor; i++) {
            //diz que o predecessor não existe ainda
            verticePredecessorLargura[i] = -1;
        }
        //chama a busca em profundidade
        return buscaLargura(verticeInicial, verticeFinal);
    }
    
    /**
     * Método buscaLargura, responsável por efetuar a busca em largura a partir
     * de um vértice inicial até o vértice final utilizando uma fila de vértices
     * a serem conhecidos e um vetor de vértices já visitados.
     * @param verticeInicial
     * @param verticeFinal
     * @return 
     */
    private boolean buscaLargura(int verticeInicial, int verticeFinal) {
        boolean visitados[] = new boolean[numVertices()];
        
        //seta os vértices como não visitados
        for (int i = 0; i < visitados.length; i++) {
            visitados[i] = false;
        }
        
        //cria a lista de vértices a serem conhecidos
        LinkedList<Integer> fila = new LinkedList<>();
        
        //marca o vértice inicial como visitado
        visitados[verticeInicial] = true;
        
        //adiciona o vértice inicial na fila de vértices conhecidos
        fila.add(verticeInicial);
        
        //enquanto a fila de vértices conhecidos não estiver vazia
        while (!fila.isEmpty()) {
            //pega o elemento presente no topo (posição inicial) da fila
            int v = fila.poll();
            //System.out.println("vAtual = " + v);
            
            //obtém a lista de adjacência do vértice obtido da fila
            ArrayList<Integer> listaAdj = listaDeAdjacencia(v);
            
            //percorre a lista de adjacência
            for (int w = 0; w < listaAdj.size(); w++) {
                //se o vértice atual não foi visitado ainda, então o visite,
                //marque que foi visitado e o adicione na lista de vértices a serem conhecidos
                //seta também o vértice predecessor
                if (visitados[listaAdj.get(w)] == false) {
                    visitados[listaAdj.get(w)] = true;
                    fila.add(listaAdj.get(w));
                    verticePredecessorLargura[listaAdj.get(w)] = v;
                }
            }
        }
        
        //retorna se existe um caminho entre o vértice final e inicial
        return (visitados[verticeFinal] == true);
    }

    /**
     * *********************************************************************
     * BUSCA EM PROFUNDIDADE
     *********************************************************************
     */
    /**
     * Método iniciaBuscaEmProfundidade, responsável por inicializar os vetores
     * de distância e predecessores, e chamar a busca em profundidade a partir
     * de determinado vértice
     *
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
     * Método buscaProfundidade, responsável por realizar efetivamente a busca a
     * partir de um determinado vértice inicial
     *
     * @param vertice vértice inicial
     */
    private void buscaProfundidade(int vertice) {
        ArrayList<Integer> lista = listaDeAdjacencia(vertice);

        //Percorre a lista de adjacência
        for (int i = 0; i < lista.size(); i++) {
            //Se a distância é "infinito" então descobre o vértice e atualiza sua distância e seu predecessor
            if (distanciaProfundidade[lista.get(i)] == distanciaProfundidade.length + 1) {
                distanciaProfundidade[lista.get(i)] = distanciaProfundidade[vertice] + 1;
                verticePredecessor[lista.get(i)] = vertice;

                //chama a busca em profundidade novamente, portanto o método é recursivo
                buscaProfundidade(lista.get(i));
            }
        }
    }

    /**
     * Método getDistanciaProfundidade, resposável por retornar o vetor de
     * distâncias obtido na busca
     *
     * @return vetor de distância
     */
    public int[] getDistanciaProfundidade() {
        return this.distanciaProfundidade;
    }

    /**
     * Método getVerticePai, responsável por retornar o vetor de vértices
     * predecessores
     *
     * @return vetor de vértices predecessores
     */
    public int[] getVerticePai() {
        return verticePredecessor;
    }

    /**
     * Método getDistanciaVetorProf
     *
     * @return tamanho do vetor de distância
     */
    public int getDistanciaVetorProf() {
        return distanciaProfundidade.length;
    }

    /**
     * ********************************************************************
     */
    /**
     * *********************************************************************
     * DJIKSTRA
     *********************************************************************
     */
    /**
     * Método inicializaDijkstra, responsável por inicializar os parâmetros para
     * o cálculo do menor caminho e construir o heap com os vértices do grafo
     *
     * @param verticeInicial
     */
    private void inicializaDijkstra(int verticeInicial) {
        for (int i = 0; i < numVertices(); i++) {
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
     *
     * @param verticeInicial
     * @param verticeFinal
     * @return distância de vInicial até vFinal
     */
    public int iniciaCaminhoMaisCurto(int verticeInicial, int verticeFinal) {
        inicializaDijkstra(verticeInicial);
        dijkstra(verticeInicial);
        return distanciasCMC[verticeFinal];
    }

    /**
     * Método iniciaCaminhoMaisCurto, responsável por chamar a função para obter
     * o caminho mais curto de um determinado vértice inicial até os demais
     * vérti ces
     *
     * @param verticeInicial
     * @return array contendo as distâncias obtidas
     */
    public int[] iniciaCaminhoMaisCurto(int verticeInicial) {
        inicializaDijkstra(verticeInicial);
        dijkstra(verticeInicial);
        return distanciasCMC;
    }

    /**
     * Método constroiHeapComVertices, responsável por inicializar o heap de
     * prioridade mínima com os vértices do grafo
     */
    private void constroiHeapComVertices() {
        for (int i = 0; i < numVertices(); i++) {
            heapMin.insert(i);
        }
        //heapMin.print();
    }

    /**
     * Método dijkstra, responsável por realizar o cálculo do menor caminho
     * entre um vértice inicial e os demais vértices do grafo
     *
     * @param verticeInicial
     */
    private void dijkstra(int verticeInicial) {
        //se heap não está vazio
        while (!heapMin.isEmpty()) {
            //obtém valor mínimo do heap
            int u = heapMin.remove();
            //System.out.println("u = " + u);
            //recupera lista de adjacência do vértice obtido do heap
            ArrayList<Integer> listaAdj = listaDeAdjacencia(u);

            //percorrre para todos os elementos na lista de adj
            for (int v = 0; v < listaAdj.size(); v++) {
                //se a distância entre vértipublicce inicial até vértiec final é maior que a distância inicial + peso aresta 
                if (distanciasCMC[listaAdj.get(v)] > distanciasCMC[u] + matrizAdjacencia[u][listaAdj.get(v)]) {
                    //atualiza a distância atual com o novo valor obtido
                    distanciasCMC[listaAdj.get(v)] = distanciasCMC[u] + matrizAdjacencia[u][listaAdj.get(v)];
                    //marca o antecessorpublic
                    verticeAntecessorCMC[listaAdj.get(v)] = u;
                }
            }
        }
    }

    /**
     * Método getVerticeAntecessorCMC, responsável por obter os vértices anteces
     * sores
     *
     * @return array contendo os vértices antecessores
     */
    public int[] getVerticeAntecessorCMC() {
        return verticeAntecessorCMC;
    }

    /**
     * *********************************************************************
     * AGM
     *********************************************************************
     */
    /**
     * return 0; Método iniciaArvoreGeradoraMinima, responsável por
     *
     * @param verticeInicial
     */
    public void inicializaArvoreGeradoraMinima(int verticeInicial) {
        for (int i = 0; i < numVertices(); i++) {
            //Substituição do Integer.MAX_VALUE por 9999999
            //pois o MAX_VALUE estourava ao ser somado com mais
            //alguma coisa
            distanciasAGM[i] = 9999999;
            verticeAntecessorAGM[i] = -1;
        }

        distanciasAGM[verticeInicial] = 0;
        constroiHeapComVertices();

    }

    public void arvoreGeradoraMinima(int verticeInicial) {
        inicializaArvoreGeradoraMinima(verticeInicial);
        arvoreGeradoraMinima();
    }

    private void arvoreGeradoraMinima() {
        while (!heapMin.isEmpty()) {
            //obtém valor mínimo do heap
            int u = heapMin.remove();
            //System.out.println("u = " + u);
            //recupera lista de adjacência do vértice obtido do heap
            ArrayList<Integer> listaAdj = listaDeAdjacencia(u);

            //percorrre para todos os elementos na lista de adj
            for (int v = 0; v < listaAdj.size(); v++) {
                //se a distância entre vértice inicial até vértiec final é maior que a distância inicial + peso aresta 
                if (heapMin.contains(v) && matrizAdjacencia[u][listaAdj.get(v)] < distanciasAGM[v]) {
                    //atualiza a distância atual com o novo valor obtido
                    arestasArvoreGeradoraMinima.add(new Pair(u, listaAdj.get(v)));
                    //marca o antecessor
                    verticeAntecessorCMC[listaAdj.get(v)] = u;
                }
            }
        }
    }

    /**
     * *********************************************************************
     * FLUXO MÁXIMO
     *********************************************************************
     */
    /**
     * Método fluxoMaximo, responsável por obter o fluxo máximo através de
     * sucessivas buscas em largura utilizando o algoritmo Ford-Fulkeson
     * @param verticeInicial
     * @param verticeFinal
     * @return inteiro represetando o valor do fluxo máximo obtido
     */
    public int fluxoMaximo(int verticeInicial, int verticeFinal) {
        //variáveis para guardar fluxo min e fluxo máx
        int fluxoMinimo;
        int fluxoMaximo = 0;
        
        //Enquanto existir um caminho entre o vértice inicial e vértice final, faça
        while (buscaLargura(verticeInicial, verticeFinal)) {
            
            //captura o caminho obtido entre o vértice inicial e final
            ArrayList<Pair<Integer, Integer>> caminho = obtemCaminhoLargura(verticeInicial, verticeFinal);
            //var auxiliar
            int min;
            
            
            //obtém os caminhos de aumento
            caminhosDeAumento.add(getCaminhoAumento(caminho));
            
            //determina que o fluxo mínimo atual é a primeira aresta conhecida do caminho entre vértice inicial e final
            fluxoMinimo = matrizAdjacencia[caminho.get(0).getKey()][caminho.get(0).getValue()];
            
            //percorre todas as arestas determinando aquela que tem peso mínimo
            for (int i = 1; i < caminho.size(); i++){
                if ( (min = Math.min(fluxoMinimo, matrizAdjacencia[caminho.get(i).getKey()][caminho.get(i).getValue()] )) != fluxoMinimo){
                    fluxoMinimo = min;
                }   
            }
            
            capacidadeResidual.add(fluxoMinimo);
            
            //subtraí o fluxo mínimo obtido de todas as arestas do caminho selecionado entre o vértice inicial e final
            for (int i = 0; i < caminho.size(); i++){
                setAresta(caminho.get(i).getKey(), caminho.get(i).getValue(), matrizAdjacencia[caminho.get(i).getKey()][caminho.get(i).getValue()] - fluxoMinimo);
            }
            
            //acrescenta o fluxo mínimo obtido ao fluxo máximo
            fluxoMaximo = fluxoMaximo + fluxoMinimo;
        }

        return fluxoMaximo;
    }

    
    /**
     * *******************************************************************
     */
    
    /**
     * Método getCapaidadesResiduais, responsável por retornar um array
     * contendo as capacidades residuais obtidas durante o cálculo do fluxo
     * máximo
     * @return array contendo as capacidades residuais (fluxos mínimos de cada
     * caminho obtido na busca em largura)
     */
    public ArrayList<Integer> getCapacidadesResiduais(){
        return capacidadeResidual;
    }
    
    /**
     * Método getCaminhoAumento, responsável por obter o caminho de aumento
     * a partir do conjunto de arestas do caminho existente entre o vértice
     * inicial e final
     * @param caminho
     * @return ArrayList contendo o caminho de aumento
     */
    private ArrayList<Integer> getCaminhoAumento(ArrayList<Pair<Integer, Integer>> caminho){
        ArrayList<Integer> caminhoAumento = new ArrayList<>();
        
        for (Pair p : caminho){
            if (!caminhoAumento.contains((Integer)p.getKey())){
                caminhoAumento.add((Integer)p.getKey());
            }
            
            if (!caminhoAumento.contains((Integer)p.getValue())){
                caminhoAumento.add((Integer)p.getValue());
            }
        }
        return caminhoAumento;
    }
    
    public ArrayList < ArrayList <Integer>> getCaminhosDeAumento(){
        return caminhosDeAumento;
    }
    
    public ArrayList< Pair< Integer, Integer>> getArestasArvore() {
        return arestasArvoreGeradoraMinima;
    }

    public int[] getVerticeAntecessorAGM() {
        return verticeAntecessorAGM;
    }

    public int[] getVerticePredecessorLargura() {
        return verticePredecessorLargura;
    }
    
    /**
     * Método obtemCaminhoLargura, responsável por obter o caminho enter o vértice
     * inicial e final da busca baseado no array de vértice predecessor resultante
     * da busca.
     * @param verticeInicial
     * @param verticeFinal
     * @return ArrayList de Pair contendo o caminho obtido da busca em largura entre
     * o vértice inicial e final
     */
    public ArrayList<Pair<Integer, Integer>> obtemCaminhoLargura(int verticeInicial, int verticeFinal) {
        ArrayList<Pair<Integer, Integer>> caminho = new ArrayList<>();
        int controle = verticeFinal;

        while (controle != verticeInicial) {
            caminho.add(new Pair(verticePredecessorLargura[controle], controle));
            controle = verticePredecessorLargura[controle];
        }
        return caminho;
    }
    
    /**
     * Método setAresta, responsável por setar o peso de uma aresta na matriz de adjacência
     * @param vertice1
     * @param vertice2
     * @param peso 
     */
    public void setAresta(int vertice1, int vertice2, int peso) {
        matrizAdjacencia[vertice1][vertice2] = peso;
    }
}
