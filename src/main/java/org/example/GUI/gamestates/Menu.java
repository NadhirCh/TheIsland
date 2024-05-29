package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.Audio;
import org.example.GUI.ui.ImageDealingWith;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import static java.awt.geom.Point2D.distance;
import org.example.GUI.ui.MenuButton;
import static org.example.GUI.ui.Buttons.UI.Button.*;

/**
 * Represents the menu state of the game.
 */
public class Menu extends State implements StateInterface {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;
    private BufferedImage theIslandLogo;

     /**
     * Constructs a new Menu object.
     *
     * @param game The Game object.
     */
    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();
    }

     /**
     * Loads the background image for the menu.
     */
    private void loadBackground() {
        backgroundImg = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.MENU_BACKGROUND);
        menuWidth = Game.GAME_WIDTH;
        menuHeight = Game.GAME_HEIGHT;
        menuX = 0;
        menuY = 0;
        try {
            theIslandLogo = ImageIO.read(getClass().getResource("/theIslandLogo.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

      /**
     * Loads the buttons for the menu.
     */
    private void loadButtons() {
        int buttonWidth = B_WIDTH_DEFAULT + 50;
        int buttonHeight = B_HEIGHT_DEFAULT + 50; 
        int offsetX = 100; 
        int offsetY = 200; 
        int xPosition = Game.GAME_WIDTH / 2 - buttonWidth / 2 + offsetX; 

        buttons[0] = new MenuButton(xPosition, 150 + offsetY, 0, GameState.PIONS_SELECTION);
        buttons[1] = new MenuButton(xPosition, 220 + offsetY, 1, GameState.OPTIONS);
        buttons[2] = new MenuButton(xPosition, 290 + offsetY, 2, GameState.QUIT);
    }

      /**
     * Updates the menu state.
     */
    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

     /**
     * Draws the menu graphics.
     *
     * @param g The Graphics object.
     */
    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

        int logoWidth = theIslandLogo.getWidth()/2;
        int logoHeight = theIslandLogo.getHeight()/2;
        int logoX = (Game.GAME_WIDTH - logoWidth) / 2;
        int logoY = 50;

        // Draw the logo
        g.drawImage(theIslandLogo, logoX, logoY, logoWidth, logoHeight, null);

        for (MenuButton mb : buttons)
            mb.draw(g);
    }

     /**
     * Handles mouse clicks on the menu.
     *
     * @param e The MouseEvent representing the mouse click.
     */
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
    }


    /**
     * Handles mouse presses on the menu.
     *
     * @param e The MouseEvent representing the mouse press.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                mb.setMousePressed(true);
            }
        }
    }

     /**
     * Handles mouse releases on the menu.
     *
     * @param e The MouseEvent representing the mouse release.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e, mb)) {
                game.getAudioPlayer().playEffect(Audio.MENUCLICK);
                if (mb.isMousePressed())
                    mb.applyGamestate();
                if (mb.getState() == GameState.PIONS_SELECTION){
                    game.getAudioPlayer().playSong(Audio.INGAME);
                }
                if(mb.getState() == GameState.QUIT){

                }
                break;
            }
        }

        resetButtons();
    }

    /**
     * Resets the state of the buttons.
     */
    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();
    }

     /**
     * Handles mouse movement on the menu.
     *
     * @param e The MouseEvent representing the mouse movement.
     */
    public void mouseMoved(MouseEvent e) {
        for (MenuButton mb : buttons)
            mb.setMouseOver(false);

        for (MenuButton mb : buttons)
            if (isIn(e, mb)) {
                mb.setMouseOver(true);
                break;
            }
    }
}
