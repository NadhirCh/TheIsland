package org.example.GUI.ui;

import org.example.GUI.gamestates.JouerTuile;
import org.example.GUI.gamestates.RetirerTuile;
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


public class TuileEffectOverlay {

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


    public TuileEffectOverlay(RetirerTuile retirerTuile, Game game) {
        this.game = game;
        this.retirerTuile = retirerTuile;
        LoadImages();
    }
    public TuileEffectOverlay(JouerTuile jouerTuile, Game game) {
        this.game = game;
        this.jouerTuile = jouerTuile;
        LoadImages();
    }

    public void LoadImages() {
        try {
            BackGround = ImageIO.read(getClass().getResource("/Plaque.png"));

            GreenShark = ImageIO.read(getClass().getResource("/tuile_requin.png"));
            GreenWhale = ImageIO.read(getClass().getResource("/tuile_baleine.png"));
            GreenBoat = ImageIO.read(getClass().getResource("/tuile_bateau.png"));

            Tourbillon = ImageIO.read(getClass().getResource("/tourbillon.png"));
            Volcano = ImageIO.read(getClass().getResource("/erruption.png"));
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

    public void draw(Graphics g, Hexagon hex){
        if(BackGround!=null){
            g.drawImage(BackGround, Game.GAME_WIDTH / 4, Game.GAME_HEIGHT / 4, Game.GAME_WIDTH / 2, Game.GAME_HEIGHT / 2, null);
        }
        String Tip="";
        switch(hex.getEffet()){
            case GREENSHARK ->{
                g.drawImage(GreenShark,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT*10/24 ,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Prenez un pion requin mis de côté et placez sur la case de mer qu’occupait la tuile ";
            }
            case GREENWHALE -> {
                g.drawImage(GreenWhale,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Prenez un pion baleine mis de côté et placez-le sur la case de mer qu’occupait la tuile";
            }
            case GREENBOAT -> {
                g.drawImage(GreenBoat,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Prenez un pion bateau mis de côté et placez-le sur la case de mer qu’occupait la tuile";
            }
            case TOURBILLON -> {
                g.drawImage(Tourbillon,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="retirez du jeu tous ce qui occupe la tuile de terrain et de toutes les cases mer adjacentes";
            }
            case VOLCANO -> {
                g.drawImage(Volcano,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Fin du jeu";
            }
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

    public void playCurrentEffect(Hexagon hex) {
        switch (hex.getEffet()) {
            case GREENSHARK -> {
                Requin requin = new Requin();
                hex.setRequin(requin);
                game.getGameBoard().addRequin(requin);
            }
            case GREENWHALE -> {
                Baleine baleine = new Baleine();
                hex.setBaleine(baleine);
                game.getGameBoard().addBaleine(baleine);
            }
            case GREENBOAT -> {
                Bateau bateau = new Bateau();
                hex.setBateau(bateau);
                int count = 0;
                for(Pion pion: hex.getListPion()){
                        if(count<3){
                        bateau.addExplorer(pion);
                        hex.getListPion().remove(pion);
                        count++;
                    }
                }
            }
            case TOURBILLON -> {
                List<Hexagon> adjascents = hex.getAdjacentHexagons(retirerTuile.getHexagons());
                deleteSeaCreatures(hex);
                for(Hexagon hexagon : adjascents) {
                    if(hexagon.getType()== Hexagon.Type.NONE){
                        deleteSeaCreatures(hex);
                    }
                }
                deleteSeaCreatures(hex);
            }
            case VOLCANO -> {
                game.setEndGame(true);
            }
            case DAULPHIN -> {
            }
            case REDBOAT -> {
            }
            case SNAKE -> {
            }
            case REDSHARK -> {
            }
            case REDWHALE -> {
            }
            case SHARKDEFENSE -> {

            }
            case WHALEDEFENSE -> {
            }
            case NONE -> {
            }
            default -> throw new IllegalStateException("Unexpected value: " + hex.getEffet());
        }

    }
    public void deleteSeaCreatures(Hexagon hex){
        hex.setBateau(null);
        if(hex.getRequin()!=null){
            game.getGameBoard().removeRequin(hex.getRequin());
            hex.setRequin(null);
        }
        if(hex.getRequin()!=null){
            game.getGameBoard().removeBaleine(hex.getBaleine());
            hex.setBaleine(null);
        }
        hex.setSerpent(null);
        for(Pion pion : hex.getListPion()){
            if (pion.isNageur() || !pion.isOnIsland()){
                hex.getListPion().remove(pion);
            }
        }

    }

}