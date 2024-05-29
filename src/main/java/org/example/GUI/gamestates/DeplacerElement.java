package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.Audio;
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


    private List<Pion>[] islands = new ArrayList[4];

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
    private long messageDisplayEndTime = 0;


    public DeplacerElement(Game game) {
        super(game);
        adjascentHexagons = new ArrayList<Hexagon>();
        initClasses();
        loadImages();
        pionsMoved = new ArrayList<Pion>();
    }

    public void setHexagons(List<Hexagon> hexagons) {
        this.hexagons = hexagons;
    }

    public void setIslands(List<Pion>[] islands){
        this.islands = islands;
    }

    @Override
    public void update() {
        for (Hexagon hex : hexagons) {
            hex.update();
        }
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

    public BufferedImage getPionImage() {
        switch (game.getCurrentPlayer().getColor()) {
            case ROUGE:
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

    public BufferedImage getPionImageByType(Pion pion) {
        switch (pion.getColor()) {
            case ROUGE:
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
            g.drawImage(pionImage, (int) xDelta, (int) yDelta, pionImage.getWidth() / 3, pionImage.getHeight() / 3, null);
        }
        if (bateauSelected != null) {
            g.drawImage(bateauImage, (int) xDelta, (int) yDelta, bateauImage.getWidth() / 2, bateauImage.getHeight() / 2, null);
        }
        if (moveCount == 0) {
            startTurnMessageDisplay();
            drawCurrentTurn(g, new Rectangle(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT), new Font("Arial", Font.BOLD, 32));
        }
        drawMoveCounter(g);
        drawPawnsArrived(g);
    }



    public void startTurnMessageDisplay() {
        this.messageDisplayEndTime = System.currentTimeMillis() + 5000; // 5000 milliseconds = 5 seconds
    }
    private void drawMoveCounter(Graphics g) {
        String moveCounterText = "Moves Played : " + moveCount;
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString(moveCounterText, 10, 20);
    }


    private void drawCurrentTurn(Graphics g, Rectangle rect, Font font) {
        long currentTime = System.currentTimeMillis();
        if (currentTime > messageDisplayEndTime) {
            return;
        }

        FontMetrics metrics = g.getFontMetrics(font);
        String text = "";
        Color playerColor = Color.WHITE;
        switch (game.getCurrentPlayer().getColor()) {
            case BLEU:
                text = "Blue Player's Turn";
                playerColor = Color.BLUE;
                break;
            case ROUGE:
                text = "Red Player's Turn";
                playerColor = Color.RED;
                break;
            case JAUNE:
                text = "Yellow Player's Turn";
                playerColor = Color.YELLOW;
                break;
            case VERT:
                text = "Green Player's Turn";
                playerColor = Color.GREEN;
                break;
        }

        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = 40;

        g.setFont(font);

        g.setColor(new Color(playerColor.getRed(), playerColor.getGreen(), playerColor.getBlue()));
        g.drawString(text, x, y);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println(mouseX + "   " + mouseY);

        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex)) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    handleHexagonRightClick(hex);
                    break;
                } else if (SwingUtilities.isLeftMouseButton(e)) {
                    handleHexagonLeftClick(hex, mouseX, mouseY);
                    break;
                }
            }
        }
        if(isPointInsideInsland(mouseX,mouseY)!=-1){
            exitPions();
        }
    }

    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    private int isPointInsideInsland(int mouseX, int mouseY) {
        if (mouseX < 200 && mouseY < 200) {
            return 3;
        } else if (mouseX > 950 && mouseY < 200) {
            return 2;
        } else if (mouseX < 200 && mouseY > 600) {
            return 0;
        } else if (mouseX > 1000 && mouseY > 600) {
            return 1;
        }
        return -1;
    }

    private void handleHexagonLeftClick(Hexagon hex, int mouseX, int mouseY) {
        if (pionSelected == null && bateauSelected == null) {
            if (hex.getBateau() == null) {
                selectPion(hex);
            } else {
                selectPionInsideBateau(hex);
            }
        } else if (pionSelected != null) {
            movePion(hex);
        }
    }
    private void selectPion(Hexagon hex) {
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

    public void exitPions(){
       int islandExited = pionSelected.canExit();
       System.out.println("CLOSEST ISLAND"+ pionSelected.canExit());
       if(islandExited != -1){
           this.islands[islandExited].add(pionSelected);
           pionSelected=null;
           moveCount++;
           checkMoveCount();
           }
       }

    private void movePion(Hexagon hex) {
            if (adjascentHexagons.contains(hex)) {
                if (hex.getBateau() != null) {
                    movePionToBateau(hex);
                } else {
                    movePionToHexagon(hex);
                }
            }
        }


    private void selectPionInsideBateau(Hexagon hex){
        if(!hex.getBateau().isEmpty()){
            for(Pion explorer : hex.getBateau().getExplorers()){
                if(game.getCurrentPlayer().getColor()==explorer.getColor()){
                    game.getAudioPlayer().playEffect(Audio.BOAT);
                    pionSelected = explorer;
                    pionSelected.setNageur(true);
                    hex.getBateau().removeExplorer(pionSelected);
                    adjascentHexagons = hex.getAdjacentHexagons(hexagons);
                    break;
                }
            }
        }
    }

    private void movePionToBateau(Hexagon hex) {
         if (hex.getBateau().addExplorer(pionSelected)) {
            game.getAudioPlayer().playEffect(Audio.BOAT);
            pionSelected.quitIsland();
            pionSelected.setNageur(false);
            pionSelected = null;
            adjascentHexagons.clear();
            moveCount++;
            checkMoveCount();
        }
    }


    private void movePionToHexagon(Hexagon hex) {
        if((pionSelected.isNageur() && hex.getType()== Hexagon.Type.NONE )||(!pionSelected.isNageur())) {
            hex.addPawnToHexagon(pionSelected);
            if (hex.getType() == Hexagon.Type.NONE) {
                game.getAudioPlayer().playEffect(Audio.WATER);
                pionSelected.quitIsland();
                pionSelected.setNageur(true);
            }
            game.getAudioPlayer().playEffect(Audio.PIECEMOVE);
            pionSelected = null;
            adjascentHexagons.clear();
            moveCount++;
            checkMoveCount();
        }
    }

    private void checkMoveCount() {
        if (moveCount == MAX_MOVES) {
            game.nextTurn();
            moveCount = 0;
            for (Pion pion : pionsMoved) {
                pion.setMoveCounter(0);
            }
            pionsMoved.clear();
        }
    }



    private void handleHexagonRightClick(Hexagon hex) {
        if (pionSelected == null && bateauSelected == null) {
            selectBateau(hex);
        } else if(bateauSelected!=null){
            placeBateau(hex);
        }
    }

    private void selectBateau(Hexagon hex) {
        if (hex.getBateau() != null && hex.getBateau().getControlleurBateau().contains(game.getCurrentPlayer().getColor())) {
            game.getAudioPlayer().playEffect(Audio.BOAT);
            bateauSelected = hex.getBateau();
            adjascentHexagons = hex.getAdjacentHexagons(hexagons);
            hex.setBateau(null);
        }
    }

    private void placeBateau(Hexagon hex) {
        if (hex.getBateau() == null && adjascentHexagons.contains(hex) ) {
            if(hex.getType()== Hexagon.Type.NONE) {
                game.getAudioPlayer().playEffect(Audio.WATER);
                hex.setBateau(bateauSelected);
                bateauSelected = null;
                moveCount++;
                checkMoveCount();
            }
        }
    }


    private void drawPawnsArrived(Graphics g) {
        int numPions;
        for (int j = 0; j < islands.length; j++) {
            numPions = islands[j].size();
            double angleStep = 2 * Math.PI / numPions;
            int circleRadius = radius / 3;

            for (int i = 0; i < numPions; i++) {
                Pion pion = islands[j].get(i);
                BufferedImage pionImage = getPionImageByType(pion);
                if (pionImage != null) {
                    int imageWidth = pionImage.getWidth() / 4;
                    int imageHeight = pionImage.getHeight() / 4;
                    int x = (j == 3 || j == 0) ? 50 : 1500;
                    int y = (j == 3 || j == 2) ? 50 : 1000;


                    double angle = i * angleStep;
                    int drawX = (int) (x + circleRadius * Math.cos(angle) - imageWidth / 2);
                    int drawY = (int) (y + circleRadius * Math.sin(angle) - imageHeight / 2);

                    g.drawImage(pionImage, drawX, drawY, imageWidth, imageHeight, null);
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
