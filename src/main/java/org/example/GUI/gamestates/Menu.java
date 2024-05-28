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


public class Menu extends State implements StateInterface {

    private MenuButton[] buttons = new MenuButton[3];
    private BufferedImage backgroundImg;
    private int menuX, menuY, menuWidth, menuHeight;

    public Menu(Game game) {
        super(game);
        loadButtons();
        loadBackground();

    }
    private void loadBackground() {
        backgroundImg = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.MENU_BACKGROUND);
        menuWidth = Game.GAME_WIDTH;
        menuHeight = Game.GAME_HEIGHT;
        menuX = 0 ;

        menuY = 0;
    }

    private void loadButtons() {
        int buttonWidth = B_WIDTH_DEFAULT +50;
        int buttonHeight = B_HEIGHT_DEFAULT + 50;// Assuming B_WIDTH_DEFAULT is the width of the button
        int offsetX = 100; // Adjust this value as needed to move the buttons slightly to the right
        int offsetY = 200; // Adjust this value as needed to move the buttons slightly down
        int xPosition = Game.GAME_WIDTH / 2 - buttonWidth / 2 + offsetX; // Center the buttons horizontally with an additional offset

        buttons[0] = new MenuButton(xPosition, 150 + offsetY, 0, GameState.PIONS_SELECTION);
        buttons[1] = new MenuButton(xPosition, 220 + offsetY, 1, GameState.OPTIONS);
        buttons[2] = new MenuButton(xPosition, 290 + offsetY, 2, GameState.QUIT);
    }





    @Override
    public void update() {
        for (MenuButton mb : buttons)
            mb.update();
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);


        for (MenuButton mb : buttons)
            mb.draw(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        for (MenuButton mb : buttons) {
            if (isIn(e,mb)) {
                mb.setMousePressed(true);
            }
        }

    }

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
                break;
            }
        }

        resetButtons();

    }

    private void resetButtons() {
        for (MenuButton mb : buttons)
            mb.resetBools();

    }


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


