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

    private EndGameOverlay endGameOverlay;


    public TuileEffectOverlay(RetirerTuile retirerTuile, Game game) {
        this.game = game;
        this.retirerTuile = retirerTuile;
        this.endGameOverlay = new EndGameOverlay(game);
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
                Tip="Prenez un  requin mis de côté et placez sur la case de mer qu’occupait la tuile ";
            }
            case GREENWHALE -> {
                g.drawImage(GreenWhale,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Prenez un  baleine mis de côté et placez-le sur la case de mer qu’occupait la tuile";
            }
            case GREENBOAT -> {
                g.drawImage(GreenBoat,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Prenez un  bateau mis de côté et placez-le sur la case de mer qu’occupait la tuile";
            }
            case TOURBILLON -> {
                g.drawImage(Tourbillon,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="retirez du jeu tout ce qui occupe la tuile de terrain et de toutes les cases mer adjacentes";
            }
            case VOLCANO -> {
                g.drawImage(Volcano,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Fin du jeu";

                try {
                    game.getGameThread().sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                endGameOverlay.draw(g);
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
                Tip="Quand un autre joueur déplace un requin dans une case occupée par l’un de vos nageurs, vous pouvez jouer cette tuile pour retirer le requin du jeu.";
            }
            case WHALEDEFENSE -> {
                g.drawImage(WhaleDefens,Game.GAME_WIDTH *10/24, Game.GAME_HEIGHT *10/24,Game.GAME_WIDTH/6,Game.GAME_WIDTH/6,null);
                Tip="Quand un autre joueur déplace une baleine dans une case occupée par un de vos bateaux vous pouvez jouer cette tuile pour retirer la baleine du jeu.";
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
                game.getAudioPlayer().playEffect(Audio.SHARK);
                Requin requin = new Requin();
                hex.setRequin(requin);
                game.getGameBoard().addRequin(requin);
            }
            case GREENWHALE -> {
                game.getAudioPlayer().playEffect(Audio.WHALE);
                Baleine baleine = new Baleine();
                hex.setBaleine(baleine);
                game.getGameBoard().addBaleine(baleine);
            }
            case GREENBOAT -> {
                game.getAudioPlayer().playEffect(Audio.WATER);
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
                game.getAudioPlayer().playEffect(Audio.WHIRLPOOL);
                List<Hexagon> adjascents = hex.getAdjacentHexagons(retirerTuile.getHexagons());
                deleteSeaCreatures(hex);
                for(Hexagon hexagon : adjascents) {
                    if(hexagon.getType()== Hexagon.Type.NONE){
                        deleteSeaCreatures(hexagon);
                    }
                }
            }
            case VOLCANO -> {
                game.getAudioPlayer().stopSong();
                game.getAudioPlayer().playEffect(Audio.VOLCANO);
                game.countScore();
                game.setEndGame(true);
            }
            case DAULPHIN -> {
                game.getAudioPlayer().playEffect(Audio.DOLPHIN);
            }
            case REDBOAT -> {
                game.getAudioPlayer().playEffect(Audio.WATER);
            }
            case SNAKE -> {
            }
            case REDSHARK -> {
                game.getAudioPlayer().playEffect(Audio.SHARK);
            }
            case REDWHALE -> {
                game.getAudioPlayer().playEffect(Audio.WHALE);
            }
            case SHARKDEFENSE -> {
                game.getAudioPlayer().playEffect(Audio.SHARK);

            }
            case WHALEDEFENSE -> {
                game.getAudioPlayer().playEffect(Audio.WHALE);

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
        if(hex.getBaleine()!=null){
            game.getGameBoard().removeBaleine(hex.getBaleine());
            hex.setBaleine(null);
        }
        hex.setSerpent(null);

        hex.getListPion().clear();


    }

}