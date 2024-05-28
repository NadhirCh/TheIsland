package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static java.awt.geom.Point2D.distance;

public class Playing extends State implements StateInterface {
    private BufferedImage backgroundImage;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;

    private List<Hexagon> hexagons;
    private final int radius = 40;
    private final int rows = 13;
    private final int cols = 7;
    private float xDelta = 100, yDelta = 100;
    private boolean gridGenerated = false;



    public Playing(Game game) {
        super(game);
        initClasses();
        loadImages();
        System.out.println("Succesfuly init Playing Game");
    }

    public void setHexagons(List<Hexagon>hexagons){
        this.hexagons = hexagons;
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

    private String getHexagonType(int matrixValue) {
        switch (matrixValue) {
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

    @Override
    public void update() {
    }
    private void initClasses() {
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
            pionBleuImage = ImageIO.read(getClass().getResource("/pion_bleu.png"));
            pionRougeImage = ImageIO.read(getClass().getResource("/pion_rouge.png"));
            pionVertImage = ImageIO.read(getClass().getResource("/pion_vert.png"));
            pionJauneImage = ImageIO.read(getClass().getResource("/pion_jaune.png"));

        } catch (IOException e) {
            System.err.println("Error loading background image ");
            e.printStackTrace();
        }
    }

    public BufferedImage getPionImage(){
        switch (game.getCurrentPlayer().getColor()){
            case ROUGE :
                return pionRougeImage;
            case BLEU:
                return pionBleuImage;
            case JAUNE:
                return pionJauneImage;
            case VERT:
                return pionVertImage;
            default:
                return null;
        }
    }

    @Override
    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }
    
        Graphics2D g2d = (Graphics2D) g;
        if (hexagons != null) {
            for (Hexagon hex : hexagons) {
                hex.draw(g2d);
            }
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
    
        if (hexagons != null) {
            for (Hexagon hex : hexagons) {
                if (isPointInsideHexagon(mouseX, mouseY, hex)) {
                    handleHexagonClick(hex);
                    break;
                }
            }
        }
    }
    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    /// SHOULD CHANGE THIS LATER
    private void handleHexagonClick(Hexagon hex) {
    }




    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    public void mouseMoved(MouseEvent e){
        this.setRectPos(e.getX(), e.getY());

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
    }
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }


}
