package org.example.GUI.mainGame;

import org.example.Logic.Model.Tuile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrice {

    public Tuile[][] matrix;

    public Matrice(int size) {
        matrix = new Tuile[size][size];
        initializeMatrix();
    }

    private void initializeMatrix() {
        // Initialize matrix with default Tuile objects
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = new Tuile(0, 0);
            }
        }

        // List of predefined positions
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(3, 5));
        positions.add(new Position(4, 3));
        positions.add(new Position(4, 7));
        positions.add(new Position(5, 2));
        positions.add(new Position(5, 5));
        positions.add(new Position(5, 6));
        positions.add(new Position(5, 8));
        positions.add(new Position(6, 2));
        positions.add(new Position(6, 3));
        positions.add(new Position(7, 5));
        positions.add(new Position(7, 6));
        positions.add(new Position(7, 7));
        positions.add(new Position(6, 8));
        positions.add(new Position(8, 3));
        positions.add(new Position(8, 7));
        positions.add(new Position(9, 4));
        positions.add(new Position(3, 3));
        positions.add(new Position(3, 4));
        positions.add(new Position(4, 5));
        positions.add(new Position(4, 6));
        positions.add(new Position(5, 4));
        positions.add(new Position(5, 9));
        positions.add(new Position(6, 4));
        positions.add(new Position(6, 6));
        positions.add(new Position(6, 7));
        positions.add(new Position(7, 3));
        positions.add(new Position(7, 4));
        positions.add(new Position(7, 9));
        positions.add(new Position(8, 5));
        positions.add(new Position(8, 6));
        positions.add(new Position(9, 3));
        positions.add(new Position(9, 5));
        positions.add(new Position(3, 6));
        positions.add(new Position(4, 4));
        positions.add(new Position(5, 3));
        positions.add(new Position(5, 7));
        positions.add(new Position(7, 2));
        positions.add(new Position(7, 8));
        positions.add(new Position(8, 4));
        positions.add(new Position(9, 6));

        // List of types and effects
        List<Integer> types = new ArrayList<>();
        List<Integer> effects1 = new ArrayList<>();
        List<Integer> effects2 = new ArrayList<>();
        List<Integer> effects3 = new ArrayList<>();

        int[] effectsType1 = {1, 2, 1, 2, 1, 3, 7, 2, 7, 6, 6, 8, 9, 6, 11, 10};
        int[] effectsType2 = {1, 3, 2, 1, 2, 3, 4, 6, 3, 8, 4, 12, 10, 9, 11, 12};
        int[] effectsType3 = {1, 4, 5, 4, 11, 4, 12, 4};

        for (int i = 0; i < 16; i++) types.add(1); // Beach
        for (int effect : effectsType1) effects1.add(effect);

        for (int i = 0; i < 16; i++) types.add(2); // Forest
        for (int effect : effectsType2) effects2.add(effect);

        for (int i = 0; i < 8; i++) types.add(3); // Mountain
        for (int effect : effectsType3) effects3.add(effect);

        Collections.shuffle(positions);
        Collections.shuffle(types);
        Collections.shuffle(effects1);
        Collections.shuffle(effects2);
        Collections.shuffle(effects3);


        for (int i = 0; i < positions.size(); i++) {
            Position pos = positions.get(i);
            int type = types.get(i);
            int effect ;
            int j=0;
            if(type==1){
                 effect = effects1.remove(j);
            }
            else if(type==2){
                effect = effects2.get(j);
            }
            else{
                effect = effects3.get(j);
            }
            matrix[pos.row][pos.col].setType(type);
            matrix[pos.row][pos.col].setEffect(effect);
        }

        matrix[1][0].setType(4);
        matrix[2][10].setType(4);
        matrix[10][0].setType(4);
        matrix[11][9].setType(4);
        matrix[6][5].setType(4);
    }


    static class Position {
        int row;
        int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

}
