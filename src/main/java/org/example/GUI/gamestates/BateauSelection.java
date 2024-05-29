package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.Audio;
import org.example.Logic.Model.Bateau;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static java.awt.geom.Point2D.distance;
import static org.example.GUI.mainGame.Hexagon.Type.NONE;

/**
 * The BateauSelection class handles the selection and placement of boats on a hexagonal grid.
 * It includes functionality for loading images, drawing the game state, and handling user input.
 */
public class BateauSelection extends State implements StateInterface {
    private BufferedImage backgroundImage;
    private BufferedImage bateauImage;
    private boolean bateauPlaced = false;
    private List<Hexagon> hexagons;

    private static final int MAX_BATEAUX = 8;
    private int currentBateauCount = 0;

    private final int radius = 40;

    private float xDelta = 100, yDelta = 100;
    private boolean showStartGameMessage = false;

    /**
     * Constructs a BateauSelection state with the given game instance.
     *
     * @param game the game instance
     */
    public BateauSelection(Game game) {
        super(game);
        initClasses();
        loadImages();
    }

    /**
     * Sets the list of hexagons where boats can be placed.
     *
     * @param hexagons the list of hexagons
     */
    public void setHexagons(List<Hexagon> hexagons) {
        this.hexagons = hexagons;
    }

    @Override
    public void update() {
        // Update game state if necessary
    }

    private void initClasses() {
        // Initialize any necessary classes or variables
    }

     /**
     * Loads the images required for drawing the state.
     */
    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
            bateauImage = ImageIO.read(getClass().getResource("/jeton_bateau.png"));
        } catch (IOException e) {
            System.err.println("Error loading images.");
            e.printStackTrace();
        }
    }

     /**
     * Draws the current state.
     *
     * @param g The Graphics object to draw on.
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
        if (currentBateauCount < MAX_BATEAUX) {
            g.drawImage(bateauImage, (int) xDelta, (int) yDelta, bateauImage.getWidth() / 2, bateauImage.getHeight() / 2, null);
        } else {
            drawCenteredString(g, "Press Enter to start game", new Rectangle(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT), new Font("Arial", Font.BOLD, 32));
        }
    }

     /**
     * Draws a string centered on the screen.
     *
     * @param g     The Graphics object to draw on.
     * @param text  The text to draw.
     * @param rect  The rectangle in which to center the text.
     * @param font  The font of the text.
     */
    private void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        FontMetrics metrics = g.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = 40;
        g.setFont(font);
        g.setColor(Color.BLACK);
        g.drawString(text, x, y);
    }

    /**
     * Handles mouse click events.
     *
     * @param e The MouseEvent object representing the mouse click event.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex) && !bateauPlaced && hex.getType() == NONE) {
                if (currentBateauCount < MAX_BATEAUX) {
                    if (handleHexagonClick(hex) != 0) {
                        game.nextPlayerRound();
                        bateauPlaced = false;
                        break;
                    }
                } else {
                    showStartGameMessage = true;
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

    private int handleHexagonClick(Hexagon hex) {
        if (hex.getBateau() == null) {
            game.getAudioPlayer().playEffect(Audio.WATER);
            hex.setBateau(new Bateau());
            bateauPlaced = true;
            currentBateauCount++;
            return 1;
        }
        return 0;
    }

    /**
     * Handles mouse press events.
     *
     * @param e The MouseEvent object representing the mouse press event.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

     /**
     * Handles mouse release events.
     *
     * @param e The MouseEvent object representing the mouse release event.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
    }

    /**
     * Handles mouse movement events.
     *
     * @param e The MouseEvent object representing the mouse movement event.
     */
    public void mouseMoved(MouseEvent e) {
        this.setRectPos(e.getX(), e.getY());
    }

    /**
     * Sets the position of the rectangle used to display the boat image.
     *
     * @param x the x-coordinate
     * @param y the y-coordinate
     */
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    /**
     * Gets the list of hexagons.
     *
     * @return the list of hexagons
     */
    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }

    /**
     * Handles key press events. Starts the game if Enter is pressed and all boats are placed.
     *
     * @param e the key event
     */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER && currentBateauCount == MAX_BATEAUX) {
            game.startGame();
        }
    }
}
