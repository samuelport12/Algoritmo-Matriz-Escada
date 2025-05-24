package MatrizEscalonada;

import java.util.Arrays;

public class MatrizEscalonadaReduzida {

    private static final double EPSILON = 1e-10; // Tolerância para ponto flutuante

    /**
     * Transforma a matriz dada para a sua Forma Escalonada Reduzida por Linhas (RREF).
     * A matriz original é modificada.
     * @param matriz A matriz a ser transformada.
     */
    public static void transformarParaRREF(double[][] matriz) {
        if (matriz == null || matriz.length == 0) return;

        int numLinhas  = matriz.length;
        int numColunas = matriz[0].length;
        int linhaPivo  = 0;
        int colunaPivo = 0;

        // Gauss-Jordan com pivotamento parcial
        while (linhaPivo < numLinhas && colunaPivo < numColunas) {
            int linhaMax = linhaPivo;
            for (int i = linhaPivo + 1; i < numLinhas; i++) {
                if (Math.abs(matriz[i][colunaPivo]) > Math.abs(matriz[linhaMax][colunaPivo])) {
                    linhaMax = i;
                }
            }
            if (Math.abs(matriz[linhaMax][colunaPivo]) < EPSILON) {
                colunaPivo++;
            } else {
                trocarLinhas(matriz, linhaPivo, linhaMax);
                double piv = matriz[linhaPivo][colunaPivo];
                
                for (int j = colunaPivo; j < numColunas; j++) {
                    matriz[linhaPivo][j] /= piv;
                }
                matriz[linhaPivo][colunaPivo] = 1.0;
                // elimina outras linhas
                for (int k = 0; k < numLinhas; k++) {
                    if (k != linhaPivo) {
                        double fator = matriz[k][colunaPivo];
                        for (int j = colunaPivo; j < numColunas; j++) {
                            matriz[k][j] -= fator * matriz[linhaPivo][j];
                        }
                        // zera pequenos resíduos
                        if (Math.abs(matriz[k][colunaPivo]) < EPSILON) {
                            matriz[k][colunaPivo] = 0.0;
                        }
                    }
                }
                linhaPivo++;
                colunaPivo++;
            }
        }

       
        for (int i = 0; i < numLinhas; i++) {
            for (int j = 0; j < numColunas; j++) {
                double rounded = Math.round(matriz[i][j]);
                if (Math.abs(matriz[i][j] - rounded) < EPSILON) {
                    matriz[i][j] = rounded;
                }
            }
        }
    }

    // Troca duas linhas da matriz
    private static void trocarLinhas(double[][] m, int r1, int r2) {
        if (r1 == r2) return;
        double[] temp = m[r1];
        m[r1] = m[r2];
        m[r2] = temp;
    }

    // Imprime a matriz de forma legível
    public static void imprimirMatriz(double[][] matriz) {
        if (matriz == null) {
            System.out.println("Matriz nula.");
            return;
        }
        for (double[] linha : matriz) {
            System.out.println(Arrays.toString(linha));
        }
        System.out.println();
    }

    public static void main(String[] args) {
        double[][] m = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
        };
        System.out.println("Matriz original:");
        imprimirMatriz(m);
        transformarParaRREF(m);
        System.out.println("Forma Escalonada Reduzida (RREF):");
        imprimirMatriz(m);
    }
}
