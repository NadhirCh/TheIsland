package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.mainGame.Matrice;
import org.example.GUI.ui.Audio;
import org.example.Logic.Model.Pion;
import org.example.GUI.ui.PawnSelectionOverlay;
import org.example.Logic.Model.Serpent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;

/**
 * Represents the state for pawn selection in the game.
 */
public class PionSelection extends State implements StateInterface {
    private BufferedImage backgroundImage;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;

    private List<Hexagon> hexagons;
    private final int radius = 40;

    private float xDelta = 100, yDelta = 100;
    private int pawnPlacedCount = 0;
    private final int TOTAL_PAWNS = 40;

    private PawnSelectionOverlay pawnSelectionOverlay;

     /**
     * Constructs a new PionSelection object.
     *
     * @param game The Game object.
     */
    public PionSelection(Game game) {
        super(game);
        initClasses();
        loadImages();
        System.out.println("Successfully initialized PionSelection");
    }

    /**
     * Updates the pawn selection state.
     */

    @Override
    public void update() {
        pawnSelectionOverlay.update();
    }

     /**
     * Initializes the classes required for pawn selection.
     */
    private void initClasses() {
        this.pawnSelectionOverlay = new PawnSelectionOverlay(this, game);
    }

    /**
     * Loads the images required for pawn selection.
     */
    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
            pionBleuImage = ImageIO.read(getClass().getResource("/pion_bleu.png"));
            pionRougeImage = ImageIO.read(getClass().getResource("/pion_rouge.png"));
            pionVertImage = ImageIO.read(getClass().getResource("/pion_vert.png"));
            pionJauneImage = ImageIO.read(getClass().getResource("/pion_jaune.png"));
        } catch (IOException e) {
            System.err.println("Error loading images");
            e.printStackTrace();
        }
    }

    /**
     * Gets the image corresponding to the current player's color.
     *
     * @return The image of the pawn for the current player's color.
     */
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

     /**
     * Draws the pawn selection screen.
     *
     * @param g The Graphics object.
     */
    @Override
    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }

        if (!pawnSelectionOverlay.getPawnSelected()) {
            pawnSelectionOverlay.draw(g);
        } else {
            BufferedImage pionImage = getPionImage();
            if (pionImage != null) {
                g.drawImage(pionImage, (int) xDelta, (int) yDelta, pionImage.getWidth() / 3, pionImage.getHeight() / 3, null);
            }
        }
    }

     /**
     * Handles mouse clicks during pawn selection.
     *
     * @param e The MouseEvent representing the mouse click.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex) && pawnSelectionOverlay.getPawnSelected()) {
                if (handleHexagonClick(hex) != 0) {
                    pawnSelectionOverlay.setPawnSelected(false);
                    game.nextPlayerRound();
                    break;
                }
            }
        }
    }

    /**
     * Checks if a point is inside a hexagon.
     *
     * @param mouseX The x-coordinate of the point.
     * @param mouseY The y-coordinate of the point.
     * @param hex    The hexagon to check against.
     * @return True if the point is inside the hexagon, false otherwise.
     */

    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    /**
     * Handles clicking on a hexagon during pawn selection.
     *
     * @param hex The hexagon that was clicked.
     * @return 1 if a pawn was successfully placed on the hexagon, 0 otherwise.
     */
    private int handleHexagonClick(Hexagon hex) {
        if (hex.getListPion().isEmpty() && hex.getType()!= Hexagon.Type.NONE ) {
            game.getAudioPlayer().playEffect(Audio.PIECEMOVE);
            hex.addPawnToHexagon(new Pion(game.getCurrentPlayer().getColor(), 1));
            pawnPlacedCount++;
            if (pawnPlacedCount == TOTAL_PAWNS) {
                game.startBateauSelection();
            }
            return 1;
        }
        return 0;
    }

    /**
     * Handles mouse presses during pawn selection.
     *
     * @param e The MouseEvent representing the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    /**
     * Handles mouse movement during pawn selection.
     *
     * @param e The MouseEvent representing the mouse movement.
     */
    public void mouseMoved(MouseEvent e) {
        setRectPos(e.getX(), e.getY());
    }

    /**
     * Handles mouse releases during pawn selection.
     *
     * @param e The MouseEvent representing the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
    }

    /**
     * Sets the position of the rectangle representing the pawn.
     *
     * @param x The x-coordinate of the position.
     * @param y The y-coordinate of the position.
     */
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    /**
     * Gets the pawn selection overlay.
     *
     * @return The pawn selection overlay.
     */
    public PawnSelectionOverlay getPawnSelectionOverlay() {
        return pawnSelectionOverlay;
    }

     /**
     * Gets the list of hexagons.
     *
     * @return The list of hexagons.
     */
    public List<Hexagon> getHexagons() {
        return hexagons;
    }

    /**
     * Sets the list of hexagons.
     *
     * @param hexagons The list of hexagons to set.
     */
    public void setHexagons(List<Hexagon> hexagons) {
        this.hexagons =hexagons;
    }
}
