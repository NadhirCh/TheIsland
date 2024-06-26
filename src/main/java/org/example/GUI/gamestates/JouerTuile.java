package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.Audio;
import org.example.GUI.ui.TuileEffectOverlay;
import org.example.GUI.ui.TuileRougeOverlay;
import org.example.Logic.Model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;
/**
 * The JouerTuile class represents the game state for playing tuiles (tiles) in the game.
 * It handles the selection and usage of tuiles, as well as interactions with game elements such as boats, sharks, whales, and serpents.
 */


public class JouerTuile extends State implements StateInterface{

    private final TuileRougeOverlay tuileRougeOverlay;
    private List<Hexagon> sideBar;
    private List<Hexagon> hexagons;
    private List<Pion>[] islands;
    private BufferedImage backgroundImage;
    private Hexagon selectedTuile = null;
    private Bateau bateauSelected = null;
    private List<Hexagon> adjascentHexagons = new ArrayList<Hexagon>();


    private Requin requinSelected = null;
    private Baleine baleineSelected =null;
    private boolean canSelectBateau = false;
    private int moveCounter = 0;
    private boolean canSelectRequin = false;
    private boolean canSelectBaleine = false;
    private Serpent serpentSelected;
    private boolean canSelectSerpent = false;
    private boolean canDraw = false;
    private boolean canMoveNageur = false;
    private Pion pionNageur;

     /**
     * Constructs a new JouerTuile instance.
     *
     * @param game the Game instance associated with this state
     */
    public JouerTuile(Game game){
        super(game);
        loadImages();
        tuileRougeOverlay = new TuileRougeOverlay(this,game);

    }

    /**
     * Sets the sidebar containing tuiles to be displayed.
     *
     * @param sideBar the list of Hexagons representing the sidebar
     */
    public void setSideBar(List<Hexagon> sideBar) {
        this.sideBar = sideBar;
    }
    /**
     * Updates the sidebar with the tuiles available to the current player.
     *
     * @param i the index of the tuile to be used
     */
    public void updateSideBar(int i){
        game.getAudioPlayer().playEffect(Audio.TILE);
        game.getCurrentPlayer().UsePower(i);
        setSideBar(game.getCurrentPlayer().getPouvoires());
        selectedTuile = game.getCurrentPlayer().getPowerInUse();
    }

    /**
     * Plays the effect associated with the selected tuile.
     */
    private void playEffect(){
        if(selectedTuile != null) {
            System.out.println(selectedTuile.getEffet().name());
            switch (selectedTuile.getEffet()) {
                case REDBOAT:
                    canSelectBateau = true;
                    break;
                case REDSHARK:
                    canSelectRequin = true;
                    break;
                case REDWHALE:
                    canSelectBaleine = true;
                    break;
                case SNAKE:
                    canSelectSerpent = true;
                    break;
                case DAULPHIN:
                    canMoveNageur = true;
                    break;
            }
        }
    }

    /**
     * Sets the sidebar with the tuiles from the provided list.
     *
     * @param playerPower the list of Hexagons representing tuiles available to the player
     */
    public void setSideBar(ArrayList<Hexagon> playerPower) {
        int i = 0;
        for (Hexagon hex : playerPower) {
            this.sideBar.get(i).setEffet(hex.getEffet());
            this.sideBar.get(i).setType(hex.getType());
            i++;
        }
    }

    /**
     * Handles mouse click events in the game state for playing tuiles.
    * It checks for the position of the mouse click and performs appropriate actions based on the selected tuile.
    *
    * @param e the MouseEvent representing the mouse click event
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        System.out.println(mouseX + "   " + mouseY);

        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex)) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if(canSelectBateau) {
                        handleBateauClick(hex);
                    }
                    else if(canSelectRequin){
                        handleRequinClick(hex);
                    }
                    else if (canSelectBaleine){
                        handleBaleineClick(hex);
                    }
                    else if(canSelectSerpent){
                        handleSerpent(hex);
                    }
                    else if(canMoveNageur){
                        handleNageur(hex);
                    }
                    break;
                }
            }
        }
    }




    private void handleNageur(Hexagon hex) {
        if(pionNageur == null) {
            selectPionNageur(hex);
        }
        else{
            placePionNageur(hex);
        }
    }

    private void placePionNageur(Hexagon hex) {
        if(hex.getType() == Hexagon.Type.NONE && adjascentHexagons.contains(hex)){
            if(moveCounter<3){
                hex.addPawnToHexagon(pionNageur);
                pionNageur = null;
                moveCounter++;
                if(moveCounter == 3){
                    canMoveNageur = false;
                    adjascentHexagons.clear();
                    game.nextTurn();
                }
            }
        }
    }

    private void selectPionNageur(Hexagon hex) {
        if(hex.getListPion()!=null && hex.getType() == Hexagon.Type.NONE){
            for(Pion pion : hex.getListPion()){
                if(pion.getColor()==game.getCurrentPlayer().getColor() && pion.isNageur()){
                    pionNageur = pion;
                    adjascentHexagons = hex.getAdjacentHexagons(hexagons);
                    hex.getListPion().remove(pionNageur);
                    break;
                }
            }
        }
    }

    /**
     * Handles the click event for selecting a bateau.
     *
     * @param hex the Hexagon representing the position where the bateau is to be selected
     */
    private void handleBateauClick(Hexagon hex) {
        if (bateauSelected == null) {
            selectBateau(hex);
        } else if(bateauSelected!=null){
            placeBateau(hex);
        }
    }

    /**
     * Selects a bateau at the specified hexagon position.
    *
    * @param hex the Hexagon representing the position where the bateau is to be selected
    */
    private void selectBateau(Hexagon hex) {

        if (hex.getBateau() != null && hex.getBateau().getControlleurBateau().contains(game.getCurrentPlayer().getColor())) {
            bateauSelected = hex.getBateau();
            adjascentHexagons = hex.getAdjacentHexagons(hexagons);
            hex.setBateau(null);
        }
    }

    /**
    * Places the selected bateau at the specified hexagon position.
    *
    * @param hex the Hexagon representing the position where the bateau is to be placed
    */
    private void placeBateau(Hexagon hex) {
        if (hex.getBateau() == null && adjascentHexagons.contains(hex) ) {
            if(hex.getType()== Hexagon.Type.NONE) {
                hex.setBateau(bateauSelected);
                bateauSelected = null;
                moveCounter++;
                if(moveCounter == 3){
                    canSelectBateau = false;
                    moveCounter = 0;
                    adjascentHexagons.clear();
                    game.nextTurn();
                }
            }
        }
    }

    /**
    * Handles the click event for selecting a baleine.
    *
    * @param hex the Hexagon representing the position where the baleine is to be selected
    */
    private void handleBaleineClick(Hexagon hex) {
        if (baleineSelected == null) {
            if (hex.getBaleine() != null) {
                baleineSelected = hex.getBaleine();
                hex.setBaleine(null);
            }
        } else {
            if(hex.isEmpty())
            {
                hex.setBaleine(baleineSelected);
                canSelectBaleine = false;
                baleineSelected = null;
                game.nextTurn();
            }
        }
    }

    /**
    * Handles the click event for selecting a serpent.
    *
    * @param hex the Hexagon representing the position where the serpent is to be selected
    */
    private void handleSerpent(Hexagon hex) {
        if (serpentSelected == null) {
            if (hex.getSerpent() != null) {
                serpentSelected = hex.getSerpent();
                hex.setSerpent(null);
            }
        } else {
            if(hex.isEmpty())
            {
                hex.setSerpent(serpentSelected);
                canSelectSerpent = false;
                serpentSelected = null;
                game.nextTurn();
            }
        }
    }
    /**
    * Handles the click event for selecting a requin.
    *
    * @param hex the Hexagon representing the position where the requin is to be selected
    */
    private void handleRequinClick(Hexagon hex) {
        if (requinSelected == null) {
            if (hex.getRequin() != null) {
                requinSelected = hex.getRequin();
                hex.setRequin(null);
            }
        } else {
            if(hex.isEmpty()) {
                hex.setRequin(requinSelected);
                canSelectRequin = false;
                requinSelected = null;
                game.nextTurn();
            }
        }
    }

    /**
    * Checks if the given coordinates are inside the hexagon.
    *
    * @param mouseX the x-coordinate of the mouse click
    * @param mouseY the y-coordinate of the mouse click
    * @param hex    the Hexagon to check
    * @return true if the coordinates are inside the hexagon, false otherwise
    */
    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < 40;
    }

    
    /**
    * Loads the background image for the game.
    */
    private void loadImages(){
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));

        } catch (IOException e) {
            System.err.println("Error loading background image ");
            e.printStackTrace();
        }
    }

    /**
    * Handles the key pressed event for updating the sidebar and enabling drawing.
    *
    * @param e the KeyEvent representing the key pressed event
    */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_0:
                this.updateSideBar(0);
                canDraw = true;
                break;
            case KeyEvent.VK_1:
                this.updateSideBar(1);
                canDraw = true;
                break;
            case KeyEvent.VK_2:
                this.updateSideBar(2);
                canDraw = true;
                break;
            case KeyEvent.VK_3:
                this.updateSideBar(3);
                canDraw = true;
                break;
            case KeyEvent.VK_4:
                this.updateSideBar(4);
                canDraw = true;
                break;
            case KeyEvent.VK_5:
                this.updateSideBar(5);
                canDraw = true;

                break;
            case KeyEvent.VK_6:
                this.updateSideBar(6);
                canDraw = true;

                break;
            case KeyEvent.VK_7:
                this.updateSideBar(7);
                canDraw = true;
                break;
            case KeyEvent.VK_8:
                this.updateSideBar(8);
                canDraw = true;

                break;
            case KeyEvent.VK_9:
                this.updateSideBar(9);
                canDraw = true;
            case KeyEvent.VK_Q:
                canDraw = false;
                playEffect();
                break;
            case KeyEvent.VK_ENTER:
                game.nextTurn();
                break;

            default:
                break;
        }

    }
    /**
    * Sets the islands in the game.
     *
    * @param islands the islands to set
    */
    public void setIslands(List<Pion>[] islands) {
        this.islands = islands;
    }

    /**
    * Sets the hexagons in the game.
    *
    * @param hexagons the hexagons to set
    */
    public void setHexagons(List<Hexagon> hexagons) {
        this.hexagons=hexagons;
    }

    /**
    * Gets the list of hexagons in the game.
    *
    * @return the list of hexagons
    */
    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }

    /**
    * Draws the game graphics including the background image, hexagons, and current turn message.
    *
    * @param g the Graphics object for drawing
    */
    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }
        if(canDraw && selectedTuile!=null){
            tuileRougeOverlay.draw(g,selectedTuile);
        }
        drawCurrentTurn(g, new Rectangle(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT), new Font("Arial", Font.BOLD, 32));
    }

    /**
    * Draws the message indicating the current player's turn.
    *
    * @param g     the Graphics object for drawing
    * @param rect  the rectangle representing the area to draw the message
    * @param font  the font for the text
    */
    private void drawCurrentTurn(Graphics g, Rectangle rect, Font font) {
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
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * Updates the game state, including hexagons and the current player's turn.
    */
    public void update() {
        for(Hexagon hex : hexagons){
            hex.update();
        }
        if(game.getCurrentPlayer().getPouvoires().isEmpty() && selectedTuile==null){
            game.nextTurn();
        }
    }
}
