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

/**
 * The DeplacerElement class represents the game state for moving game elements.
 * It handles the movement of pawns and boats on the game board, as well as player turns.
 */
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

    /**
     * Constructs a DeplacerElement state with the given game instance.
     *
     * @param game the game instance
     */
    public DeplacerElement(Game game) {
        super(game);
        adjascentHexagons = new ArrayList<Hexagon>();
        initClasses();
        loadImages();
        pionsMoved = new ArrayList<Pion>();
    }

    /**
     * Sets the list of hexagons on the game board.
     *
     * @param hexagons the list of hexagons representing the game board
     */

     public void setHexagons(List<Hexagon> hexagons) {
        this.hexagons = hexagons;
    }

    /**
     * Sets the islands where pawns can be moved to.
     *
     * @param islands the islands represented as an array of lists of pawns
     */
    public void setIslands(List<Pion>[] islands){
        this.islands = islands;
    }

    /**
    * Updates the game state by invoking the update method of each hexagon.
    */
    @Override
    public void update() {
        for (Hexagon hex : hexagons) {
            hex.update();
        }
    }


    private void initClasses() {
    }

    /**
    * Loads the images used for rendering the game state.
    * This method loads background image, pawn images, and boat image.
    */
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


    /**
    * Returns the image corresponding to the current player's pawn color.
    *
    * @return the BufferedImage representing the pawn image
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
     * Returns the image corresponding to the given pawn's color.
    *
    * @param pion the pawn whose image is requested
    * @return the BufferedImage representing the pawn image
    */
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

    /**
     * Renders the game state by drawing the background, hexagons, pawns, boats, move counter, and current turn message.
    *
    * @param g the Graphics object used for rendering
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

    /**
    * Displays the message indicating the start of the current player's turn.
    */
    public void startTurnMessageDisplay() {
        this.messageDisplayEndTime = System.currentTimeMillis() + 5000; // 5000 milliseconds = 5 seconds
    }

   /**
     * Draws the move counter on the screen.
    *
    * @param g the Graphics object used for rendering
    */
    private void drawMoveCounter(Graphics g) {
        String moveCounterText = "Moves Played : " + moveCount;
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        g.drawString(moveCounterText, 10, 20);
    }

    /**
     * Draws the message indicating the current player's turn.
    *
    * @param g     the Graphics object used for rendering
    * @param rect  the rectangle defining the area to draw the message
    * @param font  the font to use for the message
    */
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

   /**
    * Handles mouse click events.
    *
    * @param e the MouseEvent object representing the mouse click event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

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
        if (isPointInsideInsland(mouseX, mouseY) != -1) {
        exitPions();
        }
    }

    /**
     * Determines if a given point is inside a hexagon.
    *
    * @param mouseX the x-coordinate of the point
    * @param mouseY the y-coordinate of the point
    * @param hex    the hexagon to check against
    * @return true if the point is inside the hexagon, false otherwise
    */
    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    /**
    * Determines which island a given point is inside, if any.
    *
    * @param mouseX the x-coordinate of the point
    * @param mouseY the y-coordinate of the point
    * @return the index of the island if the point is inside one, otherwise -1
    */
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

    /**
     * Handles left-click events on hexagons.
    *
    * @param hex     the hexagon that was clicked
    * @param mouseX  the x-coordinate of the mouse click
    * @param mouseY  the y-coordinate of the mouse click
    */
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

    /**
    * Selects a pawn on the hexagon and adds it to the list of moved pawns if valid.
     *
    * @param hex the hexagon containing the pawn
    */
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

    /**
     * Moves the selected pawns to the next island if they can exit.
    */
    public void exitPions() {
        int islandExited = pionSelected.canExit();
        System.out.println("CLOSEST ISLAND" + pionSelected.canExit());
        if (islandExited != -1) {
            this.islands[islandExited].add(pionSelected);
            pionSelected = null;
            moveCount++;
            checkMoveCount();
        }
    }

    /**
    * Moves the selected pawn either to a boat (if there is one on the specified hexagon),
    * or to the hexagon itself.
     *
    * @param hex the destination hexagon
    */
    private void movePion(Hexagon hex) {
        if (adjascentHexagons.contains(hex)) {
            if (hex.getBateau() != null) {
            movePionToBateau(hex);
            } else {
            movePionToHexagon(hex);
            }
        }
    }

    /**
    * Selects a pawn inside a boat and moves it if it belongs to the current player.
     *
    * @param hex the hexagon containing the boat
    */
    private void selectPionInsideBateau(Hexagon hex) {
        if (!hex.getBateau().isEmpty()) {
            for (Pion explorer : hex.getBateau().getExplorers()) {
                if (game.getCurrentPlayer().getColor() == explorer.getColor()) {
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

    /**
    * Moves the selected pawn to the boat located on the specified hexagon.
    *
    * @param hex the hexagon containing the destination boat
    */
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

    /**
    * Moves the selected pawn to the specified hexagon.
    *
    * @param hex the destination hexagon
    */
    private void movePionToHexagon(Hexagon hex) {
        if ((pionSelected.isNageur() && hex.getType() == Hexagon.Type.NONE) || (!pionSelected.isNageur())) {
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

    /**
    * Checks if the maximum number of allowed moves has been reached for the current turn.
    * If so, moves to the next turn, resets the move counter, and updates the pawns that have been moved.
    */
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

    /**
    * Handles right-clicks on a hexagon.
    * If no pawn or boat is currently selected, tries to select a boat on the hexagon.
    * If a boat is already selected, tries to place it on the hexagon.
     *
    * @param hex the hexagon that was clicked
    */
    private void handleHexagonRightClick(Hexagon hex) {
        if (pionSelected == null && bateauSelected == null) {
            selectBateau(hex);
        } else if (bateauSelected != null) {
            placeBateau(hex);
        }
    }

    /**
    * Selects a boat on the specified hexagon if the boat is controlled by the current player.
    * Plays a sound effect and updates the accessible adjacent hexagons.
    *
    * @param hex the hexagon containing the boat to select
    */
    private void selectBateau(Hexagon hex) {
        if (hex.getBateau() != null && hex.getBateau().getControlleurBateau().contains(game.getCurrentPlayer().getColor())) {
            game.getAudioPlayer().playEffect(Audio.BOAT);
            bateauSelected = hex.getBateau();
            adjascentHexagons = hex.getAdjacentHexagons(hexagons);
            hex.setBateau(null);
        }
    }

    /**
    * Places a boat on the specified hexagon if the hexagon does not already contain a boat
    * and it is among the accessible adjacent hexagons.
    * Plays a sound effect, updates the move counter, and checks if the next turn should be started.
     *
    * @param hex the hexagon on which to place the boat
    */
    private void placeBateau(Hexagon hex) {
        if (hex.getBateau() == null && adjascentHexagons.contains(hex)) {
            if (hex.getType() == Hexagon.Type.NONE) {
                game.getAudioPlayer().playEffect(Audio.WATER);
                hex.setBateau(bateauSelected);
                bateauSelected = null;
                moveCount++;
                checkMoveCount();
            }
        }
    }


    /**
    * Draws the pawns that have arrived on the islands.
    * The pawns are drawn in a circular pattern around the island.
    *
    * @param g the Graphics object used for drawing
    */
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

     /**
     * Handles the mouse pressed event by setting the rectangle's position to the coordinates of the mouse event.
    *
    * @param e the MouseEvent containing the mouse click information
    */
    @Override
    public void mousePressed(MouseEvent e) {
        this.setRectPos(e.getX(), e.getY());
    }

    /**
     * Handles the mouse moved event by updating the rectangle's position to the current mouse coordinates.
    *
    * @param e the MouseEvent containing the mouse movement information
    */
    public void mouseMoved(MouseEvent e) {
        this.setRectPos(e.getX(), e.getY());
    }

    /**
    * Handles the mouse released event.
    * Currently, this method does not have an implementation.
    *
    * @param e the MouseEvent containing the mouse release information
    */
    @Override
    public void mouseReleased(MouseEvent e) {
    // No implementation needed for now
    }

    /**
     * Sets the position of the rectangle to the specified x and y coordinates.
    *
    * @param x the x-coordinate of the new rectangle position
    * @param y the y-coordinate of the new rectangle position
    */
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    /**
    * Returns the list of hexagons used in the game.
    *
    * @return the list of hexagons
    */
    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }


}
