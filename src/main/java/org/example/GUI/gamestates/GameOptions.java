package org.example.GUI.gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


import org.example.GUI.mainGame.Game;
import org.example.GUI.ui.*;
import static org.example.GUI.ui.UrmButton.*;

    /**
    * The GameOptions class represents the game state for configuring game options.
    * It manages the audio settings and provides a way for players to navigate back to the main menu.
    */
public class GameOptions extends State implements StateInterface {

    private AudioOptions audioOptions;
    private BufferedImage backgroundImg, optionsBackgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;

   /**
     * Constructor for the GameOptions class.
    * Initializes the game options state with the provided game instance.
     *
    * @param game the Game instance associated with this state
    */
    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    /**
    * Loads and initializes the menu button with specific coordinates and dimensions.
    */
    private void loadButton() {
        int menuX = (int) (387 * 1.5f);
        int menuY = (int) (325 * 1.5f);
        menuB = new UrmButton(menuX, menuY + 72, URM_SIZE, URM_SIZE, 2);
    }

    /**
    * Loads the background images for the options menu.
    */
    private void loadImgs() {
        backgroundImg = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.MENU_BACKGROUND);
        optionsBackgroundImg = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.OPTIONS_MENU);
        bgW = (int) (optionsBackgroundImg.getWidth() * 1.5f);
        bgH = (int) (optionsBackgroundImg.getHeight() * 1.5f);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2 + 20;
        bgY = (int) (80 * 1.5f);
    }

    /**
     * Updates the state of the menu button and audio options.
    */
    @Override
    public void update() {
        menuB.update();
        audioOptions.update();
    }

    /**
     * Draws the background, options menu background, menu button, and audio options.
    *
    * @param g the Graphics object used for drawing
    */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);
        menuB.draw(g);
        audioOptions.draw(g);
    }

    /**
     * Handles mouse dragged events and updates audio options accordingly.
    *
    * @param e the MouseEvent containing the mouse drag information
    */
    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }


    /**
    * Handles mouse pressed events.
    * If the menu button is pressed, sets the button as pressed.
    * Otherwise, delegates the event to the audio options.
    *
    * @param e the MouseEvent containing the mouse press information
    */
    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
        menuB.setMousePressed(true);
        } else {
            audioOptions.mousePressed(e);
        }
    }


    /**
    * Handles mouse released events.
    * If the menu button is pressed, plays a sound effect and navigates back to the main menu.
    * Otherwise, delegates the event to the audio options.
    *
    * @param e the MouseEvent containing the mouse release information
    */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed()) {
                game.getAudioPlayer().playEffect(Audio.MENUCLICK);
                GameState.state = GameState.MENU;
            }
        } else {
            audioOptions.mouseReleased(e);
        }
        menuB.resetBools();
    }


    /**
    * Handles mouse moved events.
    * Sets the menu button as hovered over if the mouse is within its bounds.
    * Otherwise, delegates the event to the audio options.
    *
    * @param e the MouseEvent containing the mouse movement information
    */
    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);
        if (isIn(e, menuB)) {
            menuB.setMouseOver(true);
        } else {
            audioOptions.mouseMoved(e);
        }
    }


    /**
    * Handles key pressed events.
    * If the Escape key is pressed, navigates back to the main menu.
    *
    * @param e the KeyEvent containing the key press information
    */
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            GameState.state = GameState.MENU;
        }
    }

    /*
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    */

    /**
    * Handles mouse clicked events.
     * Currently, this method does not have an implementation.
    *
    * @param e the MouseEvent containing the mouse click information
    */
    @Override
    public void mouseClicked(MouseEvent e) {
        // No implementation needed for now
    }


    /**
    * Checks if the mouse event coordinates are within the bounds of the provided button.
     *
    * @param e the MouseEvent containing the mouse click information
    * @param b the PauseButton to check the bounds against
    * @return true if the mouse event is within the bounds of the button, false otherwise
    */
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }


}