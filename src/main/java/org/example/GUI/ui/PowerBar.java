package org.example.GUI.ui;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.Logic.Model.Pion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PowerBar {
    private BufferedImage Daulphin;
    private BufferedImage RedBoat;
    private BufferedImage Serpent;
    private BufferedImage RedShark;
    private BufferedImage RedWhale;

    private BufferedImage SharkDefens;
    private BufferedImage WhaleDefens;

    private int radius=35;

    private Game game;

    public PowerBar(Game game) {
        this.game = game;
        loadImages();
    }

    public void loadImages()  {
        try {
            Daulphin = ImageIO.read(getClass().getResource("/deplacer_dauphin.png"));
            RedBoat = ImageIO.read(getClass().getResource("/deplacer_bateau.png"));
            Serpent = ImageIO.read(getClass().getResource("/deplacer_serpent.png"));
            RedShark = ImageIO.read(getClass().getResource("/deplacer_requin.png"));
            RedWhale = ImageIO.read(getClass().getResource("/deplacer_baleine.png"));
            SharkDefens = ImageIO.read(getClass().getResource("/defence_requin.png"));
            WhaleDefens = ImageIO.read(getClass().getResource("/defence_baleine.png"));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }


    public void draw(Graphics2D g, ArrayList<Hexagon>liste) {

        for (Hexagon H:liste){
            if(H.getEffet()!= Hexagon.Effect.NONE){
                BufferedImage tuileImageToDraw = getEffetImage(H);
                drawImage(g, tuileImageToDraw,H);
            }
        }



    }

    private BufferedImage getEffetImage(Hexagon hex) {
        switch(hex.getEffet()){

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


    private void drawImage(Graphics2D g, BufferedImage image,Hexagon hex) {

        g.setClip(hex.getPolygon());
        g.drawImage(image, hex.getX() - radius, hex.getY() - radius, radius * 2, radius * 2,null);
        g.setClip(null);

    }

    private void drawBorder(Graphics2D g,Hexagon hex) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2)); // Set the stroke thickness to 2
        g.drawPolygon(hex.getPolygon());
    }

}