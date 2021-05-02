package caixeiroViajante;

//Importações
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Classe Arquivo
 *
 * @author Jonathan Douglas Diego Tavares
 */
public class Arquivo {

    /**
     * Retorna uma matriz obtida a partir de um arquivo passado pela entrada
     * principal.
     *
     * @return matriz de inteiros obtida através do arquivo passado pela entrada
     * principal
     */
    public static int[][] getMatrizFromFile() {
        //instancia entrada principal
        Scanner stdin = new Scanner(System.in);

        //nome do arquivo
        String filename;

        //matriz
        int[][] matriz = new int[1][1];

        //arquivo
        File file;

        //flag para verificar se os dados fornecidos vem como diagonal superior ou inferior
        boolean isDiagSup = false;

        System.out.print("Insira o nome do arquivo: ");
        filename = stdin.nextLine();
        System.out.println("");

        file = new File(filename);

        try {
            //tenta fazer abertura do arquivo
            Scanner in = new Scanner(file);

            //lê a primeira linha do arquivo
            String buffer = in.nextLine();

            //enquanto não for o fim do arquivo
            while (in.hasNextLine()) {

                //verifica se a leitura já pode ser finalizada
                if (buffer.contains("EOF") || buffer.contains("DISPLAY_DATA_SECTION")) {
                    break;
                }

                //busca a próxima linha
                buffer = in.nextLine();

                //se a palavra "DIMENSION" for encontrada então verifica o nome do arquivo
                //para posterior obtenção da dimensão da matriz
                if (buffer.contains("DIMENSION")) {
                    String tamanhoAux = (!filename.contains("pa")) ? buffer.substring(new String("DIMENSION").length() + 2) : buffer.substring(new String("DIMENSION").length() + 3);

                    int tamanhoMatriz = Integer.parseInt(tamanhoAux);
                    //System.out.println("tamanhoMatriz = " + tamanhoMatriz);
                    matriz = new int[tamanhoMatriz][tamanhoMatriz];
                }

                //descobre se os dados fornecidos estão em diagonal inferior
                if (buffer.contains("LOWER_DIAG_ROW")) {
                    isDiagSup = false;
                }

                //descobre se os dados fornecidos estão em diagonal superior
                if (buffer.contains("UPPER_DIAG_ROW")) {
                    isDiagSup = true;
                }

                //verifica se chegou no trecho do arquivo que contém os dados da matriz
                if (buffer.contains("EDGE_WEIGHT_SECTION")) {
                    //se os dados foram fornecidos em diagonal superior
                    if (isDiagSup) {
                        //percorre o arquivo preenchendo a matriz com índice j iniciando com o valor do índice i
                        for (int i = 0; i < matriz.length; i++) {
                            for (int j = i; j < matriz.length; j++) {
                                matriz[i][j] = matriz[j][i] = in.nextInt();
                            }
                        }
                    } else {
                        //percorre o arquivo preenchendo a matriz com índice j finalizando quando for igual ao
                        //índice i + 1
                        for (int i = 0; i < matriz.length; i++) {
                            for (int j = 0; j < i + 1; j++) {
                                matriz[i][j] = matriz[j][i] = in.nextInt();
                            }
                        }
                    }
                }
            }

        } catch (FileNotFoundException exception) {
            //Caso o arquivo não tenha sido encontrado, retorna mensagem de erro
            System.out.println("Não foi possível abrir o arquivo " + filename);
            exception.printStackTrace();
        }

        return matriz;
    }
}
