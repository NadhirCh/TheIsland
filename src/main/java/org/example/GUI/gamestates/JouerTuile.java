package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.TuileEffectOverlay;
import org.example.Logic.Model.Pion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JouerTuile extends State implements StateInterface{

    private List<Hexagon> sideBar;
    private List<Hexagon> hexagons;
    private List<Pion>[] islands;
    private TuileEffectOverlay tuileEffectOverlay;
    private BufferedImage backgroundImage;
    private Hexagon selectedTuile = null;

    public JouerTuile(Game game){
        super(game);
        loadImages();
        tuileEffectOverlay = new TuileEffectOverlay(this,game);

    }

    public void setSideBar(List<Hexagon> sideBar) {
        this.sideBar = sideBar;
    }

    public void updateSideBar(int i){
        game.getCurrentPlayer().UsePower(i);
        setSideBar(game.getCurrentPlayer().getPouvoires());
        selectedTuile = game.getCurrentPlayer().getPowerInUse();
    }
    public void setSideBar(ArrayList<Hexagon> playerPower) {
        int i = 0;
        for (Hexagon hex : playerPower) {
            this.sideBar.get(i).setEffet(hex.getEffet());
            this.sideBar.get(i).setType(hex.getType());
            i++;
        }
    }
    private void loadImages(){
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));

        } catch (IOException e) {
            System.err.println("Error loading background image ");
            e.printStackTrace();
        }
    }
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_0:
                updateSideBar(0);
                break;
            case KeyEvent.VK_1:
                updateSideBar(1);
                break;
            case KeyEvent.VK_2:
                updateSideBar(2);
                break;
            case KeyEvent.VK_3:
                updateSideBar(3);

                break;
            case KeyEvent.VK_4:
                updateSideBar(4);

                break;
            case KeyEvent.VK_5:
                updateSideBar(5);

                break;
            case KeyEvent.VK_6:
                updateSideBar(6);

                break;
            case KeyEvent.VK_7:
                updateSideBar(7);

                break;
            case KeyEvent.VK_8:
                updateSideBar(8);

                break;
            case KeyEvent.VK_9:
                updateSideBar(9);
            case KeyEvent.VK_ENTER:
                game.nextTurn();
                default:
                break;
        }

    }

    public void setIslands(List<Pion>[] islands) {
        this.islands = islands;
    }

    public void setHexagons(List<Hexagon> hexagons) {
        this.hexagons=hexagons;
    }

    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }

    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }
        if(selectedTuile!=null){
            tuileEffectOverlay.draw(g,selectedTuile);
        }
        drawCurrentTurn(g, new Rectangle(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT), new Font("Arial", Font.BOLD, 32));
    }
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
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    public void update() {
        for(Hexagon hex : hexagons){
            hex.update();
        }
    }
}
