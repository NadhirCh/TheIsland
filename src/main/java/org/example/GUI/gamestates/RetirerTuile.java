package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.TuileEffectOverlay;
import org.example.Logic.Model.Pion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static java.awt.geom.Point2D.distance;

public class RetirerTuile extends State implements StateInterface {
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

    private boolean tuileSelected =false;
    private Hexagon selectedHex;

    private TuileEffectOverlay tuileEffectOverlay;


    public Hexagon getSelectedHex() {
        return selectedHex;
    }

    public void setSelectedHex(Hexagon selectedHex) {
        this.selectedHex = selectedHex;
    }

    public boolean isTuileSelected() {
        return tuileSelected;
    }

    public void setTuileSelected(boolean tuileSelected) {
        this.tuileSelected = tuileSelected;
    }




    public RetirerTuile(Game game)  {
        super(game);
        initClasses();
        loadImages();
        System.out.println("Succesfuly init Playing Game");
    }

    public void setHexagons(List<Hexagon>hexagons){
        this.hexagons = hexagons;
    }

    private String getHexagonEffect(int effectValue){
        switch (effectValue){
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


    @Override
    public void update() {
        tuileEffectOverlay.update();
    }

    private void initClasses()  {
        this.tuileEffectOverlay=new TuileEffectOverlay(this,this.game);
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
    @Override
    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }

        if(isTuileSelected()){
            tuileEffectOverlay.draw(g,selectedHex);
        }


    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println("mouse clicked\n");

        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex) && !hex.isClicked() ) {
                handleHexagonClick(hex);
                break;
            }
        }
    }
    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    private void handleHexagonClick(Hexagon hex) {
            if(hex.getType()!= Hexagon.Type.NONE){
                setTuileSelected(true);
                setSelectedHex(hex);
                hex.setClicked(true);
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    public void mouseMoved(MouseEvent e){
        this.setRectPos(e.getX(), e.getY());

    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (isTuileSelected()) {
                setTuileSelected(false);
                game.nextTurn();
            }
        }
    }


    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }


    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }
}