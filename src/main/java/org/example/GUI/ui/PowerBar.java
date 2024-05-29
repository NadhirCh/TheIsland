package org.example.GUI.ui;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.Logic.Model.Pion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Manages the drawing of power icons on hexagons.
 */
public class PowerBar {
    private BufferedImage Daulphin;
    private BufferedImage RedBoat;
    private BufferedImage Serpent;
    private BufferedImage RedShark;
    private BufferedImage RedWhale;

    private BufferedImage SharkDefens;
    private BufferedImage WhaleDefens;

    private int radius = 35;

    private Game game;

    /**
     * Constructs a PowerBar with the specified Game instance.
     *
     * @param game The Game instance.
     */
    public PowerBar(Game game) {
        this.game = game;
        loadImages();
    }

    /**
     * Loads the images used for power icons.
     */
    public void loadImages() {
        try {
            Daulphin = ImageIO.read(getClass().getResource("/deplacer_dauphin.png"));
            RedBoat = ImageIO.read(getClass().getResource("/deplacer_bateau.png"));
            Serpent = ImageIO.read(getClass().getResource("/deplacer_serpent.png"));
            RedShark = ImageIO.read(getClass().getResource("/deplacer_requin.png"));
            RedWhale = ImageIO.read(getClass().getResource("/deplacer_baleine.png"));
            SharkDefens = ImageIO.read(getClass().getResource("/defence_requin.png"));
            WhaleDefens = ImageIO.read(getClass().getResource("/defence_baleine.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws power icons on hexagons.
     *
     * @param g     The Graphics2D object.
     * @param liste The list of hexagons.
     */
    public void draw(Graphics2D g, ArrayList<Hexagon> liste) {
        for (Hexagon H : liste) {
            if (H.getEffet() != Hexagon.Effect.NONE) {
                BufferedImage tuileImageToDraw = getEffetImage(H);
                drawImage(g, tuileImageToDraw, H);
            }
        }
    }

    /**
 * Retrieves the BufferedImage corresponding to the effect of a Hexagon.
 *
 * @param hex The Hexagon object.
 * @return The BufferedImage corresponding to the effect of the Hexagon.
 * @throws IllegalStateException if the Hexagon's effect is unexpected.
 */
private BufferedImage getEffetImage(Hexagon hex) {
    switch (hex.getEffet()) {
        case DAULPHIN -> {
            return Daulphin;
        }
        case REDBOAT -> {
            return RedBoat;
        }
        case SNAKE -> {
            return Serpent;
        }
        case REDSHARK -> {
            return RedShark;
        }
        case REDWHALE -> {
            return RedWhale;
        }
        case SHARKDEFENSE -> {
            return SharkDefens;
        }
        case WHALEDEFENSE -> {
            return WhaleDefens;
        }
        case NONE -> {
            return null;
        }
        default -> throw new IllegalStateException("Unexpected value: " + hex.getEffet());
    }
}

    /**
    * Draws a BufferedImage on a Graphics2D object, clipped to the bounds of a Hexagon.
    *
    * @param g     The Graphics2D object.
    * @param image The BufferedImage to draw.
    * @param hex   The Hexagon object.
    */
    private void drawImage(Graphics2D g, BufferedImage image, Hexagon hex) {
        g.setClip(hex.getPolygon());
        g.drawImage(image, hex.getX() - radius, hex.getY() - radius, radius * 2, radius * 2, null);
        g.setClip(null);
    }

    /**
    * Draws a black border around a Hexagon on a Graphics2D object.
     *
    * @param g   The Graphics2D object.
    * @param hex The Hexagon object.
    */
    private void drawBorder(Graphics2D g, Hexagon hex) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2)); // Set the stroke thickness to 2
        g.drawPolygon(hex.getPolygon());
    }
}
