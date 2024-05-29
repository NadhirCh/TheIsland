package org.example.GUI.mainGame;

import org.example.GUI.gamestates.*;
import org.example.GUI.gamestates.Menu;
import org.example.GUI.ui.Audio;
import org.example.GUI.ui.AudioOptions;
import org.example.Logic.Model.Board;
import org.example.Logic.Model.Pion;
import org.example.Logic.Model.Player;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.GUI.gamestates.Couleur.*;

public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    public static final int GAME_WIDTH = 1200;
    public  static final int GAME_HEIGHT = 800;
    public  static final int PLAYER_COUNT = 0;
    private Board gameBoard;
    private PionSelection pionSelection;
    private BateauSelection bateauSelection;
    private RetirerTuile retirerTuile;
    private LancerDe lancerDe;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private DeplacerElement deplacerElement;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionJauneImage;
    private BufferedImage pionVertImage;
    private AudioOptions audioOptions;
    private Audio audioPlayer;
    private GameOptions gameOptions;

    // island 0 => bottom left / island 1 => bottom right / island 2 => top right / island 3 => top left
    private List<Pion>[] islands = new ArrayList[4];

    private boolean gameEnded;
    private Menu menu;
    private JouerTuile jouerTuile;
    private Couleur winner;


    public Game() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        initClasses();
        for (int i = 0; i < islands.length; i++) {
            islands[i] = new ArrayList<>();
        }
        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
        startGameLoop();
    }
    private void initClasses() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        audioOptions = new AudioOptions(this);
        gameBoard = new Board(this);
        pionSelection = new PionSelection(this);
        pionSelection.setHexagons(gameBoard.getHexagons());
        jouerTuile = new JouerTuile(this);
        menu = new Menu(this);
        audioPlayer = new Audio();
        gameOptions = new GameOptions(this);
        bateauSelection = new BateauSelection(this);
        retirerTuile = new RetirerTuile(this);
        lancerDe = new LancerDe(this);
        deplacerElement = new DeplacerElement(this);
        deplacerElement.setIslands(islands);
        players = new ArrayList<>(4);
            Player P1 = new Player("¨Player1",new int[]{3, 2, 2, 1, 1, 1},ROUGE);
            Player P2 = new Player("Player2",new int[]{3, 2, 2, 1, 1, 1},BLEU);
            Player P3 = new Player("Player3",new int[]{3, 2, 2, 1, 1, 1},JAUNE);
            Player P4 = new Player("Player4",new int[]{3, 2, 2, 1, 1, 1},VERT);
        players.add(P1);
        players.add(P2);
        players.add(P3);
        players.add(P4);
        try {
            pionBleuImage = ImageIO.read(getClass().getResource("/pion_bleu.png"));
            pionRougeImage = ImageIO.read(getClass().getResource("/pion_rouge.png"));
            pionVertImage = ImageIO.read(getClass().getResource("/pion_vert.png"));
            pionJauneImage = ImageIO.read(getClass().getResource("/pion_jaune.png"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        GameState.state = GameState.MENU;
    }

    public List<Player> getListPlayers(){
        return this.players;
    }
    public void startBateauSelection(){
        bateauSelection.setHexagons(pionSelection.getHexagons());
        GameState.state = GameState.BATEAU_SELECTION;
    }
    public void startGame(){
        GameState.state = GameState.PLAYING;
        CurrentTurn.currentTurn = CurrentTurn.DEPLACER_ELEMENT;
        deplacerElement.setHexagons(bateauSelection.getHexagons());
    }
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayerRound() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }
    public Board getGameBoard(){
        return this.gameBoard;
    }

    public void nextTurn() {
        switch (CurrentTurn.currentTurn){
            case JOUER_TUILE :
                deplacerElement.setIslands(islands);
                deplacerElement.setHexagons(jouerTuile.getHexagons());
                CurrentTurn.currentTurn = CurrentTurn.DEPLACER_ELEMENT;
                break;
            case DEPLACER_ELEMENT:
                retirerTuile.setIslands(islands);
                retirerTuile.setHexagons(deplacerElement.getHexagons());
                retirerTuile.update();
                CurrentTurn.currentTurn = CurrentTurn.RETIRER_TUILE;
                break;
            case RETIRER_TUILE:
                lancerDe.setIslands(islands);
                lancerDe.setHexagons(retirerTuile.getHexagons());
                CurrentTurn.currentTurn=CurrentTurn.LANCER_DE;
                break;
            case LANCER_DE:
                jouerTuile.setIslands(islands);
                jouerTuile.setHexagons(lancerDe.getHexagons());
                CurrentTurn.currentTurn = CurrentTurn.JOUER_TUILE;
                GameState.state = GameState.PLAYING;
                nextPlayerRound();
                break;

        }
    }

    public JouerTuile getJouerTuile() {
        return jouerTuile;
    }

    public RetirerTuile getRetirerTuile(){
        return this.retirerTuile;
    }
    public GameState getCurrentState() {
        return GameState.state;
    }

    public void setCurrentState(GameState currentState) {
       GameState.state  = currentState;
    }

    public DeplacerElement getMoveElement(){
        return this.deplacerElement;
    }
    private void drawPawnsArrived(Graphics g) {
        int numPions;
        for (int j = 0; j < islands.length; j++) {
            numPions = islands[j].size();
            double angleStep = 2 * Math.PI / numPions;
            int circleRadius = 40 / 3;

            for (int i = 0; i < numPions; i++) {
                Pion pion = islands[j].get(i);
                BufferedImage pionImage = getPionImageByType(pion);
                if (pionImage != null) {
                    int imageWidth = pionImage.getWidth() / 4;
                    int imageHeight = pionImage.getHeight() / 4;
                    int x = (j == 3 || j == 0) ? 50 : 1500;
                    int y = (j == 3 || j == 2) ? 50 : 1000;


                    double angle = i * angleStep;
                    int drawX = (int) (x + circleRadius * Math.cos(angle) - imageWidth / 2);
                    int drawY = (int) (y + circleRadius * Math.sin(angle) - imageHeight / 2);

                    g.drawImage(pionImage, drawX, drawY, imageWidth, imageHeight, null);
                }
            }
        }
    }

    public BufferedImage getPionImageByType(Pion pion) {
        switch (pion.getColor()) {
            case ROUGE:
                return pionRougeImage;
            case BLEU:
                return pionBleuImage;
            case JAUNE:
                return pionJauneImage;
            case VERT:
                return pionVertImage;
            default:
                return null;
        }
    }
    public void drawSideBar(Graphics g){
        retirerTuile.InitSideBar();
        retirerTuile.setSideBar(this.getCurrentPlayer().getPouvoires());
        retirerTuile.getBar().draw((Graphics2D) g,retirerTuile.getSideBar());
    }


    public void render(Graphics g) {
        switch (GameState.state) {
            case PLAYING:
                switch (CurrentTurn.currentTurn) {
                    case JOUER_TUILE:
                        jouerTuile.draw(g);
                        break;  // Missing break statement
                    case DEPLACER_ELEMENT:
                        deplacerElement.draw(g);
                        break;
                    case LANCER_DE:
                        lancerDe.draw(g);
                        break;
                    case RETIRER_TUILE:
                        retirerTuile.draw(g);
                        break;
                }
                drawPawnsArrived(g);
                drawSideBar(g);
                break;
            case MENU:
                menu.draw(g);
                break;
            case OPTIONS:
                gameOptions.draw(g);
                break;
            case PIONS_SELECTION:
                pionSelection.draw(g);
                break;
            case BATEAU_SELECTION:
                bateauSelection.draw(g);
                break;
        }
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }



    public BateauSelection getBateauSelection() {
        return bateauSelection;
    }

    public void update() {
        switch (GameState.state){
            case PLAYING :
            switch (CurrentTurn.currentTurn){
                case DEPLACER_ELEMENT :
                    deplacerElement.update();
                    break;
                case LANCER_DE:
                    lancerDe.update();
                    break;
                case RETIRER_TUILE:
                    retirerTuile.update();
                    break;
                case JOUER_TUILE:
                    jouerTuile.update();
                    break;
            }
            break;
            case MENU:
                menu.update();
                break;
            case OPTIONS:
                gameOptions.update();
                break;
            case PIONS_SELECTION:
                pionSelection.update();
                break;
            case BATEAU_SELECTION:
                bateauSelection.update();
                break;
            case QUIT:
            default:
                break;
        }
    }
    public PionSelection getPionSelection() {
        return pionSelection;
    }

    public LancerDe getLancerDe(){return lancerDe;}

    @Override
    public void run() {

        double timePerFrame = 1000000000.0 / FPS_SET;
        double timePerUpdate = 1000000000.0 / UPS_SET;

        long previousTime = System.nanoTime();

        int frames = 0;
        int updates = 0;
        long lastCheck = System.currentTimeMillis();

        double deltaU = 0;
        double deltaF = 0;

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;

            }
        }

    }


    public void setEndGame(boolean gameEnded) {
        this.gameEnded = true;
    }

    public List<Pion>[] getIslands() {
        return this.islands;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public AudioOptions audioOptions(){
        return audioOptions;
    }
    public Audio getAudioPlayer(){
        return audioPlayer;
    }

    public GameOptions getGameOptions(){
        return gameOptions;
    }

    public AudioOptions getAudioOptions() {
        return audioOptions;
    }

    public Thread getGameThread() {
        return gameThread;
    }
    public boolean isGameEnded() {
        return gameEnded;
    }

    public void countScore(){
        List<Couleur> colorList= Arrays.asList(values());
        int scoreWinner =0;
        Couleur winner = null;

        for(Couleur coul:colorList){
            int score=0;
            for(List<Pion> Island: islands){

                for(Pion pion:Island){
                    if (pion.getColor()==coul)
                        score+=pion.getPoints();
                }

            }
            //Update player's score
            for(Player player:this.getListPlayers()){
                if(player.getColor()==coul)
                    player.setScore(score);
            }

            //to track the best score holder
            if(score>=scoreWinner){
                scoreWinner=score;
                winner=coul;
            }

        }
        this.winner=winner;
    }

    public Couleur getWinner() {
        return ROUGE;
    }

}

