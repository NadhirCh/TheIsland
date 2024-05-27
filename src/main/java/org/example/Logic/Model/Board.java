package org.example.Logic.Model;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.mainGame.Matrice;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.AreaAveragingScaleFilter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<Hexagon> hexagons;
    private List<Requin> requinsOnBoard;
    private List<Baleine> baleinesOnBoard;
    private final int radius = 40;
    private final int rows = 13;
    private final int cols = 7;
    private BufferedImage backgroundImage;
    private boolean gridGenerated = false;
    private Game game;


    public Board(){
        loadImages();
        hexagons = new ArrayList<Hexagon>();
        requinsOnBoard = new ArrayList<Requin>();
        baleinesOnBoard = new ArrayList<Baleine>();
        generateHexagonGrid();
    }

    public List<Requin> getRequinsOnBoard() {
        return requinsOnBoard;
    }

    public List<Baleine> getBaleinesOnBoard() {
        return baleinesOnBoard;
    }
    public void addRequin(Requin requin){
        this.requinsOnBoard.add(requin);
    }
    public void addBaleine(Baleine baleine){
        this.baleinesOnBoard.add(baleine);
    }

    public List<Hexagon> getHexagons(){
        return this.hexagons;
    }
    private void generateHexagonGrid() {
        hexagons.clear();
        int xOffset = (int) (radius * 1.71);
        int yOffset = (int) (radius * 1.5);

        Matrice matrice = new Matrice(13);
        int windowStart = 270;
        int startY = radius;
        for (int row = 0; row < rows; row++) {
            int startX = getStartX(row, windowStart, xOffset);
            int currentCols = getCurrentCols(row);

            for (int col = 0; col < currentCols; col++) {
                int x = startX + xOffset * col + (row % 2) * (xOffset / 2);
                int y = startY + yOffset * row;
                String type = getHexagonType(matrice.matrix[row][col].getType());
                String effect = getHexagonEffect(matrice.matrix[row][col].getEffect());
                Hexagon hex = new Hexagon(x, y, radius, type, effect,row,col);
                if (matrice.matrix[row][col].getType() == 4) {
                    hex.setSerpent(new Serpent());
                }

                hexagons.add(hex);
            }
        }
        gridGenerated = true;
    }

    private int getStartX(int row, int windowStart, int xOffset) {
        if (row == 0 || row == rows - 1) {
            return windowStart + xOffset * 2;
        } else if (row == 5 || row == 7) {
            return windowStart - xOffset;
        } else {
            return windowStart;
        }
    }

    private int getCurrentCols(int row) {
        if (row == 0 || row == rows - 1) {
            return 7;
        } else if (row == 5 || row == 7) {
            return 12;
        } else if (row % 2 == 0) {
            return 11;
        } else {
            return 10;
        }
    }

    private String getHexagonType(int typeValue) {
        switch (typeValue) {
            case 1:
                return "land";
            case 2:
                return "forest";
            case 3:
                return "mountain";
            default:
                return "none";
        }
    }

    private String getHexagonEffect(int effectValue) {
        switch (effectValue) {
            case 1:
                return "greenshark";
            case 2:
                return "greenwhale";
            case 3:
                return "greenboat";
            case 4:
                return "tourbillon";
            case 5:
                return "volcano";
            case 6:
                return "daulphin";
            case 7:
                return "redboat";
            case 8:
                return "snake";
            case 9:
                return "redshark";
            case 10:
                return "redwhale";
            case 11:
                return "sharkdefense";
            case 12:
                return "whaledefense";
            default:
                return "none";
        }
    }
    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }


                Graphics2D g2d = (Graphics2D) g;
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }


    }




}