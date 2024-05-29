package org.example.GUI.gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;


import org.example.GUI.mainGame.Game;
import org.example.GUI.ui.*;
import static org.example.GUI.ui.UrmButton.*;


public class GameOptions extends State implements StateInterface {

    private AudioOptions audioOptions;
    private BufferedImage backgroundImg, optionsBackgroundImg;
    private int bgX, bgY, bgW, bgH;
    private UrmButton menuB;

    public GameOptions(Game game) {
        super(game);
        loadImgs();
        loadButton();
        audioOptions = game.getAudioOptions();
    }

    private void loadButton() {
        int menuX = (int) (387 * 1.5f);
        int menuY = (int) (325 * 1.5f);

        menuB = new UrmButton(menuX, menuY + 72, URM_SIZE, URM_SIZE, 2);
    }

    private void loadImgs() {
        backgroundImg = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.MENU_BACKGROUND);
        optionsBackgroundImg = ImageDealingWith.GetSpriteAtlas(ImageDealingWith.OPTIONS_MENU);

        bgW = (int) (optionsBackgroundImg.getWidth() * 1.5f);
        bgH = (int) (optionsBackgroundImg.getHeight() * 1.5f);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2 + 20;
        bgY = (int) (80 * 1.5f);
    }

    @Override
    public void update() {
        menuB.update();
        audioOptions.update();

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(backgroundImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        g.drawImage(optionsBackgroundImg, bgX, bgY, bgW, bgH, null);

        menuB.draw(g);
        audioOptions.draw(g);

    }

    public void mouseDragged(MouseEvent e) {
        audioOptions.mouseDragged(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isIn(e, menuB)) {
            menuB.setMousePressed(true);
        } else
            audioOptions.mousePressed(e);

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, menuB)) {
            if (menuB.isMousePressed())
                game.getAudioPlayer().playEffect(Audio.MENUCLICK);
                GameState.state = GameState.MENU;
        } else
            audioOptions.mouseReleased(e);

        menuB.resetBools();

    }

    public void mouseMoved(MouseEvent e) {
        menuB.setMouseOver(false);

        if (isIn(e, menuB))
            menuB.setMouseOver(true);
        else
            audioOptions.mouseMoved(e);

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            GameState.state = GameState.MENU;

    }
    /*
    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub

    }
    */

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }

}