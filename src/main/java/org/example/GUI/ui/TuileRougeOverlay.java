package org.example.GUI.ui;

import org.example.GUI.gamestates.JouerTuile;
import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.Logic.Model.Baleine;
import org.example.Logic.Model.Bateau;
import org.example.Logic.Model.Pion;
import org.example.Logic.Model.Requin;
import java.util.List;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Represents the overlay for displaying information about a red tile in the GUI.
 */
public class TuileRougeOverlay {

    private JouerTuile jouerTuile;
    private BufferedImage BackGround;



    private BufferedImage Daulphin;
    private BufferedImage RedBoat;
    private BufferedImage Serpent;
    private BufferedImage RedShark;
    private BufferedImage RedWhale;

    private BufferedImage SharkDefens;
    private BufferedImage WhaleDefens;

    private Game game;


    /**
     * Constructs a TuileRougeOverlay object with the specified game state and main game object.
     *
     * @param jouerTuile The game state for playing a tile.
     * @param game       The main game object.
     */
    public TuileRougeOverlay(JouerTuile jouerTuile, Game game) {
        this.game = game;
        this.jouerTuile = jouerTuile;
        LoadImages();
    }

    /**
     * Loads images for the overlay from the resources.
     */
    public void LoadImages() {
        try {
            BackGround = ImageIO.read(getClass().getResource("/Plaque.png"));
            Daulphin = ImageIO.read(getClass().getResource("/deplacer_dauphin.png"));
            RedBoat = ImageIO.read(getClass().getResource("/deplacer_bateau.png"));
            Serpent = ImageIO.read(getClass().getResource("/deplacer_serpent.png"));
            RedShark = ImageIO.read(getClass().getResource("/deplacer_requin.png"));
            RedWhale = ImageIO.read(getClass().getResource("/deplacer_baleine.png"));

            SharkDefens = ImageIO.read(getClass().getResource("/defence_requin.png"));
            WhaleDefens = ImageIO.read(getClass().getResource("/defence_baleine.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Draws the overlay on the graphics context.
     *
     * @param g    The Graphics object for rendering.
     * @param hex  The hexagon tile to display information about.
     */
    public void draw(Graphics g, Hexagon hex){
        if(BackGround!=null){
            g.drawImage(BackGround, Game.GAME_WIDTH / 4, Game.GAME_HEIGHT / 4, Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2, null);
        }
        String Tip="";
        switch(hex.getEffet()){
            case DAULPHIN -> {
                g.drawImage(Daulphin,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Un dauphin vient en aide à l’un de vos nageurs";
            }
            case REDBOAT -> {
                g.drawImage(RedBoat,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Les vents vous sont favorables";
            }
            case SNAKE -> {
                g.drawImage(Serpent,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Déplacez le serpent de mer de votre choix déjà présent sur le plateau de jeu sur n’importe quelle case de mer inoccupée";
            }
            case REDSHARK -> {
                g.drawImage(RedShark,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Déplacez le requin de votre choix déjà présent sur le plateau de jeu sur n’importe quelle case de mer inoccupée";
            }
            case REDWHALE -> {
                g.drawImage(RedWhale,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Déplacez la baleine de votre choix déjà présente sur le plateau de jeu sur n’importe quelle case de mer inoccupée";
            }
            case SHARKDEFENSE -> {
                g.drawImage(SharkDefens,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="";
            }
            case WHALEDEFENSE -> {
                g.drawImage(WhaleDefens,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="";
            }
            case NONE -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + hex.getEffet());
        }

        Font font = new Font("Arial", Font.BOLD, 22);
        g.setFont(font);

        g.setColor(Color.black);

        drawCenteredString(g, Tip, new Rectangle(Game.GAME_WIDTH*61/192, Game.GAME_HEIGHT*7/24, Game.GAME_WIDTH *35/96, Game.GAME_HEIGHT *10/24));
        String additionalText = "Press [Q] to continue and Enter to skip";
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(additionalText);
        int textHeight = metrics.getHeight();
        int textX = (Game.GAME_WIDTH - textWidth) / 2;
        int textY = Game.GAME_HEIGHT * 10 / 11;
        g.drawString(additionalText, textX, textY);

        g.dispose();



    }

    /**
 * Draws a string centered within a given rectangle on the graphics context.
 *
 * @param g     The Graphics object for rendering.
 * @param text  The text to be drawn.
 * @param rect  The rectangle within which the text should be centered.
 */
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