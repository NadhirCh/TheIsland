package org.example.GUI.gamestates;

import org.example.GUI.animations.DiceAnimation;
import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.Audio;
import org.example.Logic.Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static java.awt.geom.Point2D.distance;

public class LancerDe extends State implements StateInterface {
    private  List<Pion>[] islands;
    private BufferedImage backgroundImage;
    private BufferedImage dePhaseSerpent;
    private BufferedImage dePhaseBaleine;
    private BufferedImage dePhaseRequin;
    private BufferedImage[] diceImages;
    private BufferedImage currentDiceImage;
    private Timer diceRollTimer;
    private int diceRollDuration;
    private long startTime;
    private Serpent serpentSelected = null;
    private Baleine baleineSelected = null;
    private Requin requinSelected =null ;
    private DiceAnimation diceAnimation;

    private List<Hexagon> hexagons;
    private final int radius = 40;

    private float xDelta = 100, yDelta = 100;

    private boolean deLancee = false;
    private List<Hexagon> adjascentHexagons;
    private BufferedImage baleineImage;
    private BufferedImage requinImage;
    private BufferedImage serpentImage;

    public LancerDe(Game game) {
        super(game);
        initClasses();
        loadImages();
        initTimer();
        adjascentHexagons = new ArrayList<Hexagon>();
    }
    public void setIslands(List<Pion>[] islands){
        this.islands = islands;
    }

    public void setHexagons(List<Hexagon> hexagons) {
        this.hexagons = hexagons;
    }

    @Override
    public void update() {
        for (Hexagon hex : hexagons) {
            hex.update();
        }

        if (deLancee) {
            if (game.getGameBoard().getBaleinesOnBoard().isEmpty() && currentDiceImage == dePhaseBaleine) {
                // sleep 0.5 sec here
                resetTurnState();
                game.nextTurn();
            } else if (game.getGameBoard().getRequinsOnBoard().isEmpty() && currentDiceImage == dePhaseRequin) {
                // sleep 0.5 sec here
                resetTurnState();
                game.nextTurn();
            }
        }
    }
    private void initClasses() {
    }

    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
            dePhaseBaleine = ImageIO.read(getClass().getResource("/dee_PhaseBaleine.png"));
            dePhaseRequin = ImageIO.read(getClass().getResource("/dee_PhaseRequin.png"));
            dePhaseSerpent = ImageIO.read(getClass().getResource("/dee_PhaseSerpent.png"));
            requinImage = ImageIO.read(getClass().getResource("/jeton_requin.png"));
            baleineImage = ImageIO.read(getClass().getResource("/jeton_baleine.png"));
            serpentImage = ImageIO.read(getClass().getResource("/jeton_serpent.png"));


            diceImages = new BufferedImage[]{
                    dePhaseBaleine,
                    dePhaseRequin,
                    dePhaseSerpent
            };
            diceAnimation = new DiceAnimation(diceImages);

            currentDiceImage = diceImages[0];
        } catch (IOException e) {
            System.err.println("Error loading images.");
            e.printStackTrace();
        }
    }

    private void initTimer() {
        diceRollTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                if (elapsedTime >= diceRollDuration) {
                    diceRollTimer.stop();
                    currentDiceImage = diceImages[(int) (Math.random() * diceImages.length)];
                    deLancee = true;
                } else {
                    currentDiceImage = diceImages[(int) (Math.random() * diceImages.length)];
                }
            }
        });
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
        drawDe(g);
        if (requinSelected != null) {
            game.getAudioPlayer().playEffect(Audio.SHARK);
            g.drawImage(requinImage, (int) xDelta, (int) yDelta, requinImage.getWidth()/2, requinImage.getHeight()/2, null);
        }
        else if (baleineSelected !=null){
            game.getAudioPlayer().playEffect(Audio.WHALE);
            g.drawImage(baleineImage, (int) xDelta, (int) yDelta, baleineImage.getWidth()/2, baleineImage.getHeight()/2, null);
        }
        else if(serpentSelected != null){
            game.getAudioPlayer().playEffect(Audio.KRAKEN);
            g.drawImage(serpentImage,(int) xDelta,(int) yDelta,serpentImage.getWidth()/2,serpentImage.getHeight()/2,null);
        }


    }

    public void drawDe(Graphics g) {
        g.drawImage(currentDiceImage, Game.GAME_WIDTH - 100, 100,80,80, null);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if(deLancee) {
            if(game.getGameBoard().getBaleinesOnBoard().isEmpty() && currentDiceImage==dePhaseBaleine){
                game.getAudioPlayer().playEffect(Audio.WHALE);
                resetTurnState();
                game.nextTurn();
            }
            else if(game.getGameBoard().getRequinsOnBoard().isEmpty() && currentDiceImage==dePhaseRequin){
                game.getAudioPlayer().playEffect(Audio.SHARK);
                resetTurnState();
                game.nextTurn();
            }
            else {
                for (Hexagon hex : hexagons) {
                    if (isPointInsideHexagon(mouseX, mouseY, hex)) {
                        handleMonsterClick(hex);
                    }
                }
            }
        }
    }

    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }
    private void handleMonsterClick(Hexagon hex) {
        if (currentDiceImage == dePhaseSerpent) {
            game.getAudioPlayer().playEffect(Audio.KRAKEN);
            handleSerpentClick(hex);
        } else if (currentDiceImage == dePhaseRequin) {
            game.getAudioPlayer().playEffect(Audio.SHARK);
            handleRequinClick(hex);
        } else if (currentDiceImage == dePhaseBaleine) {
            game.getAudioPlayer().playEffect(Audio.WHALE);
            handleBaleineClick(hex);
        }
    }

    private void handleSerpentClick(Hexagon hex) {
        if (serpentSelected == null) {
            if (hex.getSerpent() != null) {
                serpentSelected = hex.getSerpent();
                hex.setSerpent(null);
                adjascentHexagons = hex.getAdjacentHexagons(hexagons);
            }
        } else {
            if(adjascentHexagons.contains(hex)) {
                hex.setSerpent(serpentSelected);
                resetTurnState();
                game.nextTurn();
            }
        }
    }

    private void handleRequinClick(Hexagon hex) {
        if (requinSelected == null) {
            if (hex.getRequin() != null) {
                requinSelected = hex.getRequin();
                adjascentHexagons = hex.getAdjacentHexagons(hexagons);
                hex.setRequin(null);
            }
        } else {
            if(adjascentHexagons.contains(hex)) {
                hex.setRequin(requinSelected);
                resetTurnState();
                game.nextTurn();
            }
        }
    }

    private void handleBaleineClick(Hexagon hex) {
        if (baleineSelected == null) {
            if (hex.getBaleine() != null) {
                adjascentHexagons = hex.getAdjacentHexagons(hexagons);
                baleineSelected = hex.getBaleine();
                hex.setBaleine(null);
            }
        } else {
            if(adjascentHexagons.contains(hex)) {
                hex.setBaleine(baleineSelected);
                resetTurnState();
                game.nextTurn();
            }
        }
    }



    private void startDiceRollAnimation() {
        diceRollDuration = 3000 + (int) (Math.random() * 2000);
        startTime = System.currentTimeMillis();
        //game.getAudioPlayer().playEffect(Audio.DICE);
        diceRollTimer.start();
    }
    private void resetTurnState() {
        serpentSelected = null;
        baleineSelected = null;
        requinSelected = null;
        deLancee = false;
        adjascentHexagons.clear();
        currentDiceImage = diceImages[0];
    }


    @Override
    public void mousePressed(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
        this.setRectPos(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && !deLancee) {
            game.getAudioPlayer().playEffect(Audio.DICE);
                startDiceRollAnimation();
        }
    }


    public List<Hexagon> getHexagons() {
    return this.hexagons;
    }

}
