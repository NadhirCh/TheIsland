package org.example.GUI.gamestates;

import org.example.GUI.animations.DiceAnimation;
import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
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
import java.util.List;

import static java.awt.geom.Point2D.distance;

public class LancerDe extends State implements StateInterface {
    private final int MAX_MOVES = 3;
    private int moveCount = 0;
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
    private final int rows = 13;
    private final int cols = 7;
    private float xDelta = 100, yDelta = 100;

    private boolean gridGenerated = false;
    private boolean deLancee = false;

    public LancerDe(Game game) {
        super(game);
        initClasses();
        loadImages();
        initTimer();
    }

    public void setHexagons(List<Hexagon> hexagons) {
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
            dePhaseBaleine = ImageIO.read(getClass().getResource("/dee_PhaseBaleine.png"));
            dePhaseRequin = ImageIO.read(getClass().getResource("/dee_PhaseRequin.png"));
            dePhaseSerpent = ImageIO.read(getClass().getResource("/dee_PhaseSerpent.png"));

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

    }

    public void drawDe(Graphics g) {
        g.drawImage(currentDiceImage, Game.GAME_WIDTH - 100, 100,50,50, null);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        if(deLancee) {
            if(game.getGameBoard().getBaleinesOnBoard().isEmpty() && currentDiceImage==dePhaseBaleine){
                deLancee =false;
                game.nextPlayerRound();
            }
            else if(game.getGameBoard().getRequinsOnBoard().isEmpty() && currentDiceImage==dePhaseRequin){
                deLancee =false;

                game.nextPlayerRound();
            }
            for (Hexagon hex : hexagons) {
                if (isPointInsideHexagon(mouseX, mouseY, hex)) {
                    handleMonsterClick(hex);
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
            handleSerpentClick(hex);
        } else if (currentDiceImage == dePhaseRequin) {
            handleRequinClick(hex);
        } else if (currentDiceImage == dePhaseBaleine) {
            handleBaleineClick(hex);
        }
    }

    private void handleSerpentClick(Hexagon hex) {
        if (serpentSelected == null) {
            if (hex.getSerpent() != null) {
                serpentSelected = hex.getSerpent();
                hex.setSerpent(null);
            }
        } else {
            hex.setSerpent(serpentSelected);
            serpentSelected = null;
            game.nextPlayerRound();
        }
    }

    private void handleRequinClick(Hexagon hex) {
        if (requinSelected == null) {
            if (hex.getRequin() != null) {
                requinSelected = hex.getRequin();
                hex.setRequin(null);
            }
        } else {
            hex.setRequin(requinSelected);
            requinSelected = null;
            game.nextPlayerRound();
        }
    }

    private void handleBaleineClick(Hexagon hex) {
        if (baleineSelected == null) {
            if (hex.getBaleine() != null) {
                baleineSelected = hex.getBaleine();
                hex.setBaleine(null);
            }
        } else {
            hex.setBaleine(baleineSelected);
            baleineSelected = null;
            game.nextPlayerRound();
        }
    }



    private void startDiceRollAnimation() {
        diceRollDuration = 3000 + (int) (Math.random() * 2000);
        startTime = System.currentTimeMillis();
        diceRollTimer.start();
        deLancee = true;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
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
                startDiceRollAnimation();
        }
    }

    public List<Hexagon> getHexagons() {
    return this.hexagons;
    }
}
