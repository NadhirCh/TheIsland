package org.example.GUI.ui;

import org.example.GUI.gamestates.JouerTuile;
import org.example.GUI.gamestates.RetirerTuile;
import org.example.GUI.mainGame.Game;
import org.example.Logic.Model.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class EndGameOverlay {
    private JouerTuile jouerTuile;
    private RetirerTuile retirerTuile;
    private BufferedImage BackGround;

    private BufferedImage GreenShark;
    private BufferedImage GreenWhale;
    private BufferedImage GreenBoat;
    private BufferedImage Tourbillon;
    private BufferedImage Volcano;

    private BufferedImage Daulphin;
    private BufferedImage RedBoat;
    private BufferedImage Serpent;
    private BufferedImage RedShark;
    private BufferedImage RedWhale;

    private BufferedImage SharkDefens;
    private BufferedImage WhaleDefens;

    private Game game;


    public EndGameOverlay(Game game){
        this.game=game;
    }

    public void LoadImages() {
        try {
            BackGround = ImageIO.read(getClass().getResource("/Plaque.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void draw(Graphics g){
        if(BackGround!=null){
            g.drawImage(BackGround, Game.GAME_WIDTH / 8, Game.GAME_HEIGHT / 8, Game.GAME_WIDTH*3 / 4, Game.GAME_HEIGHT*3 / 4, null);
        }
        String gameOver="GAME OVER";
        String colorWins="";

        Font font = new Font("Arial", Font.BOLD, 25);
        g.setFont(font);
        g.setColor(Color.black);
        drawCenteredString(g, gameOver, new Rectangle(Game.GAME_WIDTH/4, Game.GAME_HEIGHT/8, Game.GAME_WIDTH/2, Game.GAME_HEIGHT /8));

        switch(game.getWinner()){
            case ROUGE ->{
                g.setColor(Color.red);
                colorWins="RED WINS";
            }
            case VERT -> {
                g.setColor(Color.green);
                colorWins = "GREEN WINS";
            }
            case BLEU -> {
                g.setColor(Color.blue);
                colorWins = "BLUE WINS";
            }
            case JAUNE ->{
                g.setColor(Color.yellow);
                colorWins = "YELLOW WINS";
            }
        }

        drawCenteredString(g, colorWins, new Rectangle(Game.GAME_WIDTH/4, Game.GAME_HEIGHT/4, Game.GAME_WIDTH/2, Game.GAME_HEIGHT /8));

        font = new Font("Arial", Font.BOLD, 22);
        int offsetY=Game.GAME_HEIGHT/8;
        String PlayeScore="";

        for (Player player: game.getListPlayers()){
            switch (player.getColor()){
                case ROUGE -> PlayeScore=" RED   ";
                case VERT ->  PlayeScore=" GREEN   ";
                case BLEU ->  PlayeScore=" BLUE   ";
                case JAUNE ->  PlayeScore=" YELLOW   ";
            }
            PlayeScore=PlayeScore+player.getScore();
            drawCenteredString(g, PlayeScore, new Rectangle(Game.GAME_WIDTH/4, (Game.GAME_HEIGHT/4)+offsetY, Game.GAME_WIDTH/2, Game.GAME_HEIGHT /8));

        }

        g.dispose();

    }

    private void drawCenteredString(Graphics g, String text, Rectangle rect) {
        FontMetrics metrics = g.getFontMetrics();
        int lineHeight = metrics.getHeight();
        int y = rect.y;

        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();

        for (String word : words) {
            if (metrics.stringWidth(line + word) < rect.width) {
                line.append(word).append(" ");
            } else {
                int x = (rect.width - metrics.stringWidth(line.toString())) / 2;
                g.drawString(line.toString(), rect.x + x, y += lineHeight);
                line = new StringBuilder(word).append(" ");
            }
        }
        int x = (rect.width - metrics.stringWidth(line.toString())) / 2;
        g.drawString(line.toString(), rect.x + x, y += lineHeight);
    }




    public void update() {
    }

}