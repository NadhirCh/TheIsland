package org.example.GUI.mainGame;

import org.example.Logic.Model.Tuile;

public class Matrice {

    public Tuile[][] matrix;

    public Matrice(int size) {
        matrix = new Tuile[size][size];
        initializeMatrix();
    }

    private void initializeMatrix() {
        int plage = 1;
        int montagne = 2;
        int foret = 3;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new Tuile(0, 0);

            }
        }

        //Beach
        matrix[3][5].setType(1);
        matrix[3][5].setEffect(1);

        matrix[4][3].setType(1);
        matrix[4][3].setEffect(2);

        matrix[4][7].setType(1);
        matrix[4][7].setEffect(1);

        matrix[5][2].setType(1);
        matrix[5][2].setEffect(2);

        matrix[5][5].setType(1);
        matrix[5][5].setEffect(1);

        matrix[5][6].setType(1);
        matrix[5][6].setEffect(3);

        matrix[5][8].setType(1);
        matrix[5][8].setEffect(7);

        matrix[6][2].setType(1);
        matrix[6][2].setEffect(2);

        matrix[6][3].setType(1);
        matrix[6][3].setEffect(7);

        matrix[7][5].setType(1);
        matrix[7][5].setEffect(6);

        matrix[7][6].setType(1);
        matrix[7][6].setEffect(6);

        matrix[7][7].setType(1);
        matrix[7][7].setEffect(8);

        matrix[6][8].setType(1);
        matrix[6][8].setEffect(9);

        matrix[8][3].setType(1);
        matrix[8][3].setEffect(6);

        matrix[8][7].setType(1);
        matrix[8][7].setEffect(11);

        matrix[9][4].setType(1);
        matrix[9][4].setEffect(10);

        //Mountain
        matrix[3][3].setType(2);
        ;
        matrix[3][3].setEffect(1);

        matrix[3][4].setType(2);
        matrix[3][4].setEffect(3);

        matrix[4][5].setType(2);
        matrix[4][5].setEffect(2);

        matrix[4][6].setType(2);
        matrix[4][6].setEffect(1);

        matrix[5][4].setType(2);
        matrix[5][4].setEffect(2);

        matrix[5][9].setType(2);
        matrix[5][9].setEffect(3);

        matrix[6][4].setType(2);
        matrix[6][4].setEffect(4);

        matrix[6][6].setType(2);
        matrix[6][6].setEffect(6);

        matrix[6][7].setType(2);
        matrix[6][7].setEffect(3);

        matrix[7][3].setType(2);
        matrix[7][3].setEffect(8);

        matrix[7][4].setType(2);
        matrix[7][4].setEffect(4);

        matrix[7][9].setType(2);
        matrix[7][9].setEffect(12);

        matrix[8][5].setType(2);
        matrix[8][5].setEffect(10);

        matrix[8][6].setType(2);
        matrix[8][6].setEffect(9);

        matrix[9][3].setType(2);
        matrix[9][3].setEffect(11);

        matrix[9][5].setType(2);
        matrix[9][5].setEffect(12);

        //Forest
        matrix[3][6].setType(3);
        matrix[3][6].setEffect(1);

        matrix[4][4].setType(3);
        matrix[4][4].setEffect(4);

        matrix[5][3].setType(3);
        matrix[5][3].setEffect(5);

        matrix[5][7].setType(3);
        matrix[5][7].setEffect(4);

        matrix[7][2].setType(3);
        matrix[7][2].setEffect(11);

        matrix[7][8].setType(3);
        matrix[7][8].setEffect(4);

        matrix[8][4].setType(3);
        matrix[8][4].setEffect(12);

        matrix[9][6].setType(3);
        matrix[9][6].setEffect(4);

        matrix [1][0].setType(4);
        matrix [2][10].setType(4);
        matrix[10][0].setType(4);
        matrix[11][9].setType(4);
        matrix[6][5].setType(4);
    }

    public void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }
}