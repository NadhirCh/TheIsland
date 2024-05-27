package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.Logic.Model.Bateau;
import org.example.Logic.Model.Pion;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;

public class DeplacerElement extends State implements StateInterface {
    private final int MAX_MOVES = 3;
    private int moveCount = 0;
    private BufferedImage backgroundImage;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;

    private BufferedImage bateauImage;
    private List<Hexagon> adjascentHexagons;
    private List<Hexagon> hexagons;
    private final int radius = 40;
    private final int rows = 13;
    private final int cols = 7;
    private float xDelta = 100, yDelta = 100;

    private Pion pionSelected = null;
    private Bateau bateauSelected = null;

    private boolean gridGenerated = false;
    private List<Pion> pionsMoved;



    public DeplacerElement(Game game) {
        super(game);
        adjascentHexagons = new ArrayList<Hexagon>();
        initClasses();
        loadImages();
        pionsMoved = new ArrayList<Pion>();
    }

    public void setHexagons(List<Hexagon>hexagons){
        this.hexagons = hexagons;
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
            bateauImage = ImageIO.read(getClass().getResource("/jeton_bateau.png"));


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
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }
        BufferedImage pionImage = getPionImage();
        if (pionSelected != null) {
            g.drawImage(pionImage, (int) xDelta, (int) yDelta, pionImage.getWidth()/3, pionImage.getHeight()/3, null);
        }
        if (bateauSelected !=null){
            g.drawImage(bateauImage, (int) xDelta, (int) yDelta, bateauImage.getWidth()/2, bateauImage.getHeight()/2, null);
        }
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex) ) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    handleHexagonRightClick(hex);
                    break;
                }
                else if (SwingUtilities.isLeftMouseButton(e)) {
                    handleHexagonLeftClick(hex);
                    break;
                }
            }
        }
    }
    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    private void handleHexagonLeftClick(Hexagon hex) {

        // Partie sélection du pion
        if(pionSelected == null) {
            for (Pion pion : hex.getListPion()) {
                if (pion.getColor() == game.getCurrentPlayer().getColor() && pion.deplacer()) {
                    pionSelected = pion;
                    pionsMoved.add(pion);
                    hex.getListPion().remove(pion);
                    adjascentHexagons = hex.getAdjacentHexagons(hexagons);
                    break;
                }
            }
        }
        // Partie mis en place des pions
        else {
            if (adjascentHexagons.contains(hex)) {
                // déplacement d'un pion vers un bateau
                if (hex.getBateau() != null) {
                    if (hex.getBateau().addExplorer(pionSelected)) {
                        pionSelected.quitIsland();
                        pionSelected = null;
                        adjascentHexagons.clear();
                        moveCount++;
                        if (moveCount == MAX_MOVES) {
                            game.nextTurn();
                            moveCount = 0;
                            for(Pion pion : pionsMoved){
                                pion.setMoveCounter(0);
                            }
                            pionsMoved.clear();
                        }
                    }
                    ;
                } else if(hex.getType()!= Hexagon.Type.NONE && !pionSelected.isNageur())
                {
                    // déplacement d'un pion vers une case normale
                    hex.addPawnToHexagon(pionSelected);
                    if(hex.getType()== Hexagon.Type.NONE){
                        pionSelected.quitIsland();
                        pionSelected.setNageur(true);
                    }
                    pionSelected = null;
                    adjascentHexagons.clear();
                    moveCount++;
                    if (moveCount == MAX_MOVES) {
                        game.nextTurn();
                        for(Pion pion : pionsMoved){
                            pion.setMoveCounter(0);
                        }
                        pionsMoved.clear();
                        moveCount = 0;
                    }
                }
            }
        }
        }


    private void handleHexagonRightClick(Hexagon hex) {
        if(bateauSelected == null ){
            if(hex.getBateau()!=null) {
                if (hex.getBateau().getControlleurBateau().contains(game.getCurrentPlayer().getColor())){
                    bateauSelected = hex.getBateau();
                    hex.setBateau(null);
                }
            }
        }
        else {
            if (hex.getBateau() == null) {
                hex.setBateau(bateauSelected);
                bateauSelected = null;
                moveCount++;
                if (moveCount == MAX_MOVES) {
                    game.nextTurn();
                    for(Pion pion : pionsMoved){
                        pion.setMoveCounter(0);
                    }
                    pionsMoved.clear();
                    moveCount = 0;
                }
            }
        }
    }




        @Override
    public void mousePressed(MouseEvent e) {
        this.setRectPos(e.getX(), e.getY());
    }

    public void mouseMoved(MouseEvent e){
        this.setRectPos(e.getX(), e.getY());

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }
    public List<Hexagon> getHexagons(){
        return this.hexagons;
    }

}
