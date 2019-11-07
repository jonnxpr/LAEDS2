/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista3;

/**
 *
 * @author aluno
 */
public class ArvoreB {

    //Atributos
    private Pagina pagRaiz;
    private final int minimoRegistrosPagina, maximoRegistrosPagina; //ordem da árvore e 2*ordem
    private boolean arvoreDesbalanceada; // flag para verificar se árvore está desbalanceada(+ elementos do que o possível)
    private int regRetorno; //dado a ser inserido em uma página
    private int paginasVisitadas; // quantidade de páginas visitadas
    private int numeroComparacoes; // quantidade de comparações executadas no método de pesquisa

    private static class Pagina {
        // Numero de itens que a pagina contem atualmente
        int numeroCorrenteItens;
        // Vetor que armazena todos os itens da pagina
        int itensPagina[];
        // Vetor que indica quem sao as paginas filhas ( ele aponta para todas as filhas )
        Pagina paginasFilhas[];
        // Metodo construtor da classe pagina

        public Pagina(int qtdemaximaRegistros) {
            this.numeroCorrenteItens = 0;
            this.itensPagina = new int[qtdemaximaRegistros];
            this.paginasFilhas = new Pagina[qtdemaximaRegistros + 1];
        }
    }
    
    /**
     * compara dois elementos inteiros
     * @param it
     * @param itPagina
     * @return -1 e 1 
     */
    public int compara(int it, int itPagina) {
        if (it < itPagina) {
            return -1;
        } else if (it > itPagina) {
            return 1;
        }
        return 0;
    }

    private void insereNaPagina(Pagina paginaAtual, int registro, Pagina filhaDireita) {
        int k = paginaAtual.numeroCorrenteItens - 1;
        while (k >= 0 && (registro - paginaAtual.itensPagina[k]) < 0) {
            paginaAtual.itensPagina[k + 1] = paginaAtual.itensPagina[k];
            paginaAtual.paginasFilhas[k + 2] = paginaAtual.paginasFilhas[k + 1];
            k--;
        }
        paginaAtual.itensPagina[k + 1] = registro;
        paginaAtual.paginasFilhas[k + 2] = filhaDireita;
        paginaAtual.numeroCorrenteItens++;
    }
    
    /**
     * construtor inicializando os atributos da classe
     * @param qtdeminimaRegistros 
     */
    public ArvoreB(int qtdeminimaRegistros) {
        this.pagRaiz = null;
        this.minimoRegistrosPagina = qtdeminimaRegistros; // ordem da árvore
        this.maximoRegistrosPagina = 2 * qtdeminimaRegistros; // 2 * ordem
        arvoreDesbalanceada = false;
        regRetorno = -1;
        paginasVisitadas = 0;
        numeroComparacoes = 0;
    }
    
    public void insere(int reg){
        Pagina retorno = this.insere(reg, pagRaiz);
        
        //se árvore desbalanceada, então cria nova raiz com o elemento mediana, senão apenas adiciona o elemento
        if (arvoreDesbalanceada){
            Pagina apTemp = new Pagina(maximoRegistrosPagina);
            apTemp.itensPagina[0] = reg;
            apTemp.paginasFilhas[0] = this.pagRaiz;
            apTemp.paginasFilhas[1] = retorno;
            this.pagRaiz = apTemp;
            this.pagRaiz.numeroCorrenteItens++;
        } else {
            this.pagRaiz = retorno;
        }
    }

    private Pagina insere(int registro, Pagina paginaAtual) {
        Pagina paginaRetorno = null;
        if (paginaAtual == null) {
            arvoreDesbalanceada = true;
            regRetorno = registro;
        } else {
            int i = 0;
            while ((i < paginaAtual.numeroCorrenteItens - 1) && (registro
                    - paginaAtual.itensPagina[i] > 0)) {
                i++;
            }
            if (registro == paginaAtual.itensPagina[i]) {
                System.out.println(" Erro : Registro ja existente ");
                arvoreDesbalanceada = false;
            } else {
                if (registro - paginaAtual.itensPagina[i] > 0) {
                    i++;
                }
                paginaRetorno = insere(registro, paginaAtual.paginasFilhas[i]);
                if (arvoreDesbalanceada) {
                    if (paginaAtual.numeroCorrenteItens < this.maximoRegistrosPagina) {
                        this.insereNaPagina(paginaAtual, regRetorno, paginaRetorno);
                        arvoreDesbalanceada = false;
                        paginaRetorno = paginaAtual;
                    } else {
                        Pagina apTemp = new Pagina(this.maximoRegistrosPagina);
                        apTemp.paginasFilhas[0] = null;
                        if (i <= this.minimoRegistrosPagina) {
                            this.insereNaPagina(apTemp, paginaAtual.itensPagina[this.maximoRegistrosPagina - 1], paginaAtual.paginasFilhas[this.maximoRegistrosPagina]);
                            paginaAtual.numeroCorrenteItens--;
                            this.insereNaPagina(paginaAtual, regRetorno,
                                    paginaRetorno);
                        } else {
                            this.insereNaPagina(apTemp, regRetorno, paginaRetorno);
                        }
                        for (int j = this.minimoRegistrosPagina + 1; j < this.maximoRegistrosPagina; j++) {
                            this.insereNaPagina(apTemp, paginaAtual.itensPagina[j],
                                    paginaAtual.paginasFilhas[j + 1]);
                            paginaAtual.paginasFilhas[j + 1] = null;
                        }
                        paginaAtual.numeroCorrenteItens = this.minimoRegistrosPagina;
                        apTemp.paginasFilhas[0] = paginaAtual.paginasFilhas[this.minimoRegistrosPagina + 1];
                        regRetorno = paginaAtual.itensPagina[this.minimoRegistrosPagina];
                        paginaRetorno = apTemp;
                    }
                }
            }
        }
        return (arvoreDesbalanceada ? paginaRetorno : paginaAtual);
    }
    
    /**
     * método público para a chamada do método privado de pesquisa
     * @param reg
     * @return elemento buscado ou -1
     */
    public int pesquisa(int reg){
        return this.pesquisa(reg, pagRaiz);
    }

    private int pesquisa(int registro, Pagina ap) {
        if (ap == null) { //verifica se a página atual é nula, ou seja, não existe
            this.numeroComparacoes++;
            return -1;
        } else {
            this.paginasVisitadas++;
            int i = 0;
            
            //loop para comparar os elementos dentro da página com o item buscado
            while ((i < ap.numeroCorrenteItens - 1) && (this.compara(registro, ap.itensPagina[i]) > 0)) {
                this.numeroComparacoes++;
                i++;
            }
            
            //compara o registro com os itens das páginas tentando decidir para onde ir
            if (this.compara(registro, ap.itensPagina[i]) == 0){
                //this.numeroComparacoes++;
                return ap.itensPagina[i];
            } else if (this.compara(registro, ap.itensPagina[i]) < 0){
                //this.numeroComparacoes++;
                return pesquisa(registro, ap.paginasFilhas[i]);
            } else {
                return pesquisa(registro, ap.paginasFilhas[i+1]);
            }                 
        }
    }
    
    //retorna o número de páginas visitadas
    public int getPaginasVisitadas(){
        return this.paginasVisitadas;
    }
    
    //retorna o número de comparações
    public int getNumeroComparacoes(){
        return this.numeroComparacoes;
    }
    
    public void getRelatorio(){
        System.out.println("Comparações = " + this.getNumeroComparacoes());
        System.out.println("Paginas visitadas = " + this.getPaginasVisitadas());
    }
}
