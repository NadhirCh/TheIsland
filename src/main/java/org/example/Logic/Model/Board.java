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

/**
 * The Board class represents the game board.
 * 
 * The board consists of a hexagonal grid of tiles (Hexagons).
 * It also contains lists of sharks and whales present on the board.
 */
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

     /**
     * Initializes a new game board.
     * 
     * @param game A reference to the Game object.
     */
    public Board(Game game){
        loadImages();
        hexagons = new ArrayList<Hexagon>();
        requinsOnBoard = new ArrayList<Requin>();
        baleinesOnBoard = new ArrayList<Baleine>();
        this.game = game;
        generateHexagonGrid();
    }

    public List<Requin> getRequinsOnBoard() {
        return requinsOnBoard;
    }

    public List<Baleine> getBaleinesOnBoard() {
        return baleinesOnBoard;
    }
   
    public List<Hexagon> getHexagons(){
        return this.hexagons;
    }

     /**
     * Adds a shark to the list of sharks present on the board.
     *
     * @param shark The shark to add.
     */
    public void addRequin(Requin requin){
        this.requinsOnBoard.add(requin);
    }

     /**
     * Adds a whale to the list of whales present on the board.
     *
     * @param whale The whale to add.
     */
    public void addBaleine(Baleine baleine){
        this.baleinesOnBoard.add(baleine);
    }

    /**
     * Generates the hexagonal grid of the board.
     * The method creates and adds hexagons to the hexagons list, based
     * on the parameters defined in the class.
     */
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
                Hexagon hex = new Hexagon(x, y, radius, type, effect,row,col,game);
                if (matrice.matrix[row][col].getType() == 4) {
                    hex.setSerpent(new Serpent());
                }

                hexagons.add(hex);
            }
        }
        gridGenerated = true;
    }

     /**
     * Calculates the starting X position for a given row in the hexagonal grid.
     *
     * @param row         The row for which to calculate the starting X position.
     * @param windowStart The default starting X position.
     * @param xOffset     The horizontal offset between hexagons.
     * @return The starting X position for the given row.
     */
    private int getStartX(int row, int windowStart, int xOffset) {
        if (row == 0 || row == rows - 1) {
            return windowStart + xOffset * 2;
        } else if (row == 5 || row == 7) {
            return windowStart - xOffset;
        } else {
            return windowStart;
        }
    }

     /**
     * Calculates the current number of columns for a given row in the hexagonal grid.
     *
     * @param row The row for which to calculate the current number of columns.
     * @return The current number of columns for the given row.
     */
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

     /**
     * Determines the type of the hexagon based on the type value.
     *
     * @param typeValue The value representing the type of the hexagon.
     * @return The type of the hexagon as a string.
     */
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

    /**
     * Determines the effect of the hexagon based on the effect value.
     *
     * @param effectValue The value representing the effect of the hexagon.
     * @return The effect of the hexagon as a string.
     */
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

    /**
     * Loads the background image for the board.
     * 
     * @throws RuntimeException if there is an error loading the image.
     */
    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        /**
     * Draws the board on the graphics context.
     * 
     * @param g The graphics context.
     */
        public void draw(Graphics g) {
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
            }
            Graphics2D g2d = (Graphics2D) g;
            for (Hexagon hex : hexagons) {
                hex.draw(g2d);
            }
        }

    /**
     * Removes a whale from the list of whales on the board.
     *
     * @param baleine The whale to remove.
     */
    public void removeBaleine(Baleine baleine) {
        baleinesOnBoard.remove(baleine);
    }

     /**
     * Removes a shark from the list of sharks on the board.
     *
     * @param requin The shark to remove.
     */
    public void removeRequin(Requin requin) {
        requinsOnBoard.remove(requin);
    }
}