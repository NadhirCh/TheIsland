package org.example.GUI.mainGame;

import org.example.GUI.gamestates.Couleur;
import org.example.Logic.Model.*;

import javax.imageio.ImageIO;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents a hexagon on the game board.
 */

public class Hexagon {
    private int x, y, radius, row, col;
    private Polygon polygon;
    private Type type;
    private Effect effet;
    private BufferedImage pionRougeImage;
    private BufferedImage pionJauneImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionBleuImage;

    private BufferedImage tuilePlage;
    private BufferedImage tuileForet;
    private BufferedImage tuileMontagne;
    private BufferedImage serpentImage;
    private BufferedImage baleineImage;
    private BufferedImage requinImage;
    private BufferedImage bateauImage;

    private ArrayList<Pion> pions;
    private Bateau bateau;
    private Requin requin;
    private Serpent serpent;
    private Baleine baleine;

    private boolean isClicked;
    private Game game;

    public int getRadius() {
    return radius;
    }

    public void setEffet(Effect effet) {
        this.effet = effet;
    }

    /**
     * Enum representing the type of hexagon.
     */

    public enum Type {
        LAND, FOREST, MOUNTAIN, NONE
    }

    /**
     * Enum representing the effect of the hexagon.
     */

    public enum Effect {
        GREENSHARK, GREENWHALE, GREENBOAT, TOURBILLON, VOLCANO,
        DAULPHIN, REDBOAT, SNAKE, REDSHARK, REDWHALE,
        SHARKDEFENSE, WHALEDEFENSE,
        NONE;

        /**
         * Returns an ArrayList containing effects that are applicable to red pawns.
         * @return ArrayList of red effects.
         */

        public ArrayList<Effect>redList(){
            return new ArrayList<>(Arrays.asList(DAULPHIN,REDBOAT,SNAKE,REDSHARK,REDWHALE,
                    SHARKDEFENSE,WHALEDEFENSE));
        }
    }

     /**
     * Constructor for Hexagon.
     * @param x X-coordinate of the hexagon.
     * @param y Y-coordinate of the hexagon.
     * @param radius Radius of the hexagon.
     * @param type Type of the hexagon.
     * @param effet Effect of the hexagon.
     */

    public Hexagon(int x, int y, int radius, String type, String effet) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.type = Type.valueOf(type.toUpperCase());
        this.setClicked(false);
        //pour definir l'effet à partir de l'initialisation aussi
        this.effet=Effect.valueOf(effet.toUpperCase());
        this.polygon = createHexagon(x, y, radius);
        this.pions = new ArrayList<Pion>();
        loadImages();
    }

     /**
     * Parameterized constructor for Hexagon.
     * @param x X-coordinate of the hexagon.
     * @param y Y-coordinate of the hexagon.
     * @param radius Radius of the hexagon.
     * @param type Type of the hexagon.
     * @param effet Effect of the hexagon.
     * @param row Row index of the hexagon.
     * @param col Column index of the hexagon.
     * @param game The game instance.
     */

    public Hexagon(int x, int y, int radius, String type, String effet, int row, int col,Game game) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.type = Type.valueOf(type.toUpperCase());
        this.effet = Effect.valueOf(effet.toUpperCase());
        this.row = row;
        this.col = col;
        this.isClicked = false;
        this.game = game;

        this.polygon = createHexagon(x, y, radius);
        this.pions = new ArrayList<>();
        this.bateau = null;
        this.requin = null;
        this.baleine = null;
        this.serpent = null;

        loadImages();
    }

    /**
     * Retrieves the polygon representing the hexagon.
     *
     * @return The polygon representing the hexagon.
     */
    public Polygon getPolygon() {
        return polygon;
    }

    /**
     * Retrieves the type of the hexagon.
     *
     * @return The type of the hexagon.
     */
    public Type getType() {
        return this.type;
    }

    /**
     * Sets the type of the hexagon.
     *
     * @param type The type to set.
     */
    public void setType(Type type){
        this.type = type;
    }

    /**
     * Checks if any player has the effect of defending against whale attacks.
     *
     * @return The player who has the whale defense effect, or null if none.
     */
    public Player existsWhaleEffect(){
        for(Player player : game.getListPlayers()) {
            for ( Hexagon hexagon : player.getPouvoires()) {
                if (hexagon.getEffet() == Effect.WHALEDEFENSE) {
                    return player;
                }
            }
        }
        return null ;
    }

    /**
     * Checks if any player has the effect of defending against shark attacks.
     *
     * @return The player who has the shark defense effect, or null if none.
     */
    public Player existsSharkEffect(){
        for(Player player : game.getListPlayers()) {
            for ( Hexagon hexagon : player.getPouvoires()) {
                if (hexagon.getEffet() == Effect.SHARKDEFENSE) {
                    return player;
                }
            }
        }
        return null ;
    }

    /**
     * Updates the state of the hexagon.
     * - Handles effects like serpent, baleine, and requin.
     * - Sets the possible exit direction for pawns based on the hexagon's position.
     */
    public void update(){
        if(this.serpent != null){
            this.bateau = null;
            pions.clear();
        }
        if(this.baleine !=null)
        {
            if(existsWhaleEffect()==null) {
                if (bateau != null) {
                    for (Pion pion : pions) {
                        bateau.removeExplorer(pion);
                        pions.add(pion);
                        pion.setNageur(true);
                    }
                    this.bateau = null;
                }
            }
            else{
                Player player = existsWhaleEffect();
                player.UseWhaleDefensePower();
            }


        }
        if(this.requin !=null){
            if(existsSharkEffect()== null) {
                pions.clear();
            }
            else{
                Player player = existsSharkEffect();
                player.UseSharkDefensePower();
            }
        }
        if((row == 1 && col == 0)||(col == 0 && row == 2)){
            for(Pion pion : pions){
                pion.setCanExit(3);
            }
            if(bateau!=null){
                for(Pion pion : bateau.getExplorers()){
                    pion.setCanExit(3);
                }
            }
        }
        else if((row == 1 && col == 9)||(col == 10 && row == 2)){
            for(Pion pion : pions){
                pion.setCanExit(2);
            }
            if(bateau!=null){
                for(Pion pion : bateau.getExplorers()){
                    pion.setCanExit(2);
                }
            }
        }
        else if((row == 11 && col == 9)||(col == 10 && row == 10)){
            for(Pion pion : pions){
                pion.setCanExit(1);
            }
            if(bateau!=null){
                for(Pion pion : bateau.getExplorers()){
                    pion.setCanExit(1);
                }
            }
        }
        else if((row == 10 && col == 0)||(col == 0 && row == 11)){
            for(Pion pion : pions){
                pion.setCanExit(0);
            }
            if(bateau!=null){
                for(Pion pion : bateau.getExplorers()){
                    pion.setCanExit(0);
                }
            }
        }
    }

    /**
     * Returns a list of hexagons that are adjacent to this hexagon.
     *
     * @param hexagons the list of all hexagons on the board
     * @return a list of adjacent hexagons
     */
    public List<Hexagon> getAdjacentHexagons( List<Hexagon> hexagons) {
        List<Hexagon> adjacentHexagons = new ArrayList<>();
        int circle_radius = 69;

        int targetX = x;
        int targetY = y;

        for (Hexagon hex : hexagons) {
            if (hex != this) {
                int deltaX = hex.getX() - targetX;
                int deltaY = hex.getY() - targetY;
                double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
                if (distance <= circle_radius) {
                    adjacentHexagons.add(hex);
                }
            }
        }

        return adjacentHexagons;
    }

    /**
     * Loads the images used for the hexagon tile and its contents.
     */
    private void loadImages() {
        tuilePlage = loadImage("/tuile_plage.png");
        tuileForet = loadImage("/tuile_foret.png");
        tuileMontagne = loadImage("/tuile_montagne.png");
        pionRougeImage = loadImage("/pion_rouge.png");
        pionVertImage = loadImage("/pion_vert.png");
        pionJauneImage = loadImage("/pion_jaune.png");
        pionBleuImage = loadImage("/pion_bleu.png");
        bateauImage = loadImage("/jeton_bateau.png");
        requinImage = loadImage("/jeton_requin.png");
        baleineImage = loadImage("/jeton_baleine.png");
        serpentImage = loadImage("/jeton_serpent.png");
    }

    /**
     * Loads an image from the specified path.
     *
     * @param imagePath the path to the image file
     * @return the BufferedImage loaded from the file, or null if an error occurs
     */
    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Creates a hexagon shape with the specified center and radius.
     *
     * @param x      the x-coordinate of the center of the hexagon
     * @param y      the y-coordinate of the center of the hexagon
     * @param radius the radius of the hexagon
     * @return a Polygon object representing the hexagon
     */
    private Polygon createHexagon(int x, int y, int radius) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            int angle = 60 * i - 30; // Offset by -30 degrees to align the hexagon
            int xOffset = (int) (radius * Math.cos(Math.toRadians(angle)));
            int yOffset = (int) (radius * Math.sin(Math.toRadians(angle)));
            hex.addPoint(x + xOffset, y + yOffset);
        }
        return hex;
    }

     /**
     * Draws the hexagon tile and its contents using the specified Graphics2D object.
     *
     * @param g the Graphics2D object to use for drawing
     */
    public void draw(Graphics2D g) {
        if (!this.isClicked) {
            BufferedImage tuileImageToDraw = getTuileImageToDraw();
            drawImage(g, tuileImageToDraw);
        }
        drawBorder(g);
        drawPawns(g);
        drawBateau(g);
        drawRequin(g);
        drawBaleine(g);
        drawSerpent(g);
    }

    /**
     * Checks if the hexagon tile is empty (i.e., contains no boat, creature, or pawns).
     *
     * @return true if the hexagon tile is empty, false otherwise
     */
    public boolean isEmpty(){
        return(bateau == null && this.type == Type.NONE && baleine == null && serpent == null && pions.isEmpty());
    }

    /**
     * Retrieves the appropriate image for the tile based on its type.
     *
     * @return the BufferedImage representing the tile type, or null if no image is available
     */
    private BufferedImage getTuileImageToDraw() {
        switch (type) {
            case LAND:
                return tuilePlage;
            case FOREST:
                return tuileForet;
            case MOUNTAIN:
                return tuileMontagne;
            default:
                return null;
        }
    }

    /**
     * Retrieves the appropriate image for the specified pawn.
     *
     * @param pion the pawn whose image is to be retrieved
     * @return the BufferedImage representing the pawn, or null if no image is available
     */
    private BufferedImage getPionImage(Pion pion) {
        if (pion != null) {
            switch (pion.getColor()) {
                case ROUGE:
                    return pionRougeImage;
                case BLEU:
                    return pionBleuImage;
                case VERT:
                    return pionVertImage;
                case JAUNE:
                    return pionJauneImage;
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * Draws the specified image within the hexagon tile using the provided Graphics2D object.
     *
     * @param g     the Graphics2D object to use for drawing
     * @param image the image to be drawn
     */
    private void drawImage(Graphics2D g, BufferedImage image) {
        if (image != null) {
            g.setClip(polygon);
            g.drawImage(image, x - radius, y - radius, radius * 2, radius * 2, null);
            g.setClip(null);
        } else {
            drawPlaceholder(g);
        }
    }

    /**
     * Draws a placeholder fill for the hexagon tile based on its type.
     *
     * @param g the Graphics2D object to use for drawing
     */
    private void drawPlaceholder(Graphics2D g) {
        Color fillColor;
        switch (type) {
            case LAND:
                fillColor = Color.YELLOW;
                g.setColor(fillColor);
                g.fillPolygon(polygon);
                break;
            case FOREST:
                fillColor = Color.GREEN;
                g.setColor(fillColor);
                g.fillPolygon(polygon);
                break;
            case MOUNTAIN:
                fillColor = Color.GRAY;
                g.setColor(fillColor);
                g.fillPolygon(polygon);
                break;
            default:
                break;
        }
    }

    /**
     * Draws the border of the hexagon tile.
     *
     * @param g the Graphics2D object to use for drawing
     */
    private void drawBorder(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2));
        g.drawPolygon(polygon);
    }

    /**
     * Adds a pawn to the hexagon tile.
     *
     * @param pion the pawn to be added
     */
    public void addPawnToHexagon(Pion pion) {
        pions.add(pion);
    }

    /**
     * Draws the pawns on the hexagon tile.
     *
     * @param g the Graphics2D object to use for drawing
     */
    private void drawPawns(Graphics2D g) {
        int numPions = pions.size();
        if (numPions == 0) {
            return;
        }

        double angleStep = 2 * Math.PI / numPions;
        int circleRadius = radius / 3;

        for (int i = 0; i < numPions; i++) {
            Pion pion = pions.get(i);
            BufferedImage pionImage = getPionImage(pion);
            if (pionImage != null) {
                int imageWidth = pionImage.getWidth() / 4;
                int imageHeight = pionImage.getHeight() / 4;

                double angle = i * angleStep;
                int drawX = (int) (x + circleRadius * Math.cos(angle) - imageWidth / 2);
                int drawY = (int) (y + circleRadius * Math.sin(angle) - imageHeight / 2);

                g.drawImage(pionImage, drawX, drawY, imageWidth, imageHeight, null);
            }
        }
    }

    /**
     * Draws the boat on the hexagon tile, including stripes representing the explorers on the boat.
     *
     * @param g the Graphics2D object to use for drawing
     */
    private void drawBateau(Graphics2D g) {
        if (this.bateau != null) {

            int imageWidth = bateauImage.getWidth()/2;
            int imageHeight = bateauImage.getHeight()/2;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(bateauImage, drawX, drawY, imageWidth, imageHeight, null);

            // Draw stripes based on the explorers on the boat
            int stripeHeight = imageHeight / 6;
            int numStripes = 0;

            for (int counter=0;counter < bateau.getCount(Couleur.ROUGE);counter++ ) {
                g.setColor(java.awt.Color.RED);
                g.fillRect(drawX+5, drawY+imageHeight/3 + numStripes * stripeHeight, imageWidth*3/4, stripeHeight);
                numStripes++;
            }
            for (int counter=0;counter<bateau.getCount(Couleur.BLEU) ;counter++) {
                g.setColor(java.awt.Color.BLUE);
                g.fillRect(drawX+5, drawY +imageHeight/3+ numStripes * stripeHeight, imageWidth*3/4, stripeHeight);
                numStripes++;
            }
            for (int counter=0;counter<bateau.getCount(Couleur.VERT) ;counter++) {
                g.setColor(java.awt.Color.GREEN);
                g.fillRect(drawX+5, drawY+imageHeight/3 + numStripes * stripeHeight, imageWidth*3/4, stripeHeight);
                numStripes++;
            }
            for (int counter=0;counter<bateau.getCount(Couleur.JAUNE) ;counter++) {
                g.setColor(java.awt.Color.YELLOW);
                g.fillRect(drawX+5, drawY +imageHeight/3+ numStripes * stripeHeight, imageWidth*3/4, stripeHeight);
                numStripes++;
            }
        }
    }

    /**
     * Draws the whale on the hexagon tile.
     *
     * @param g the Graphics object to use for drawing
     */
    private void drawBaleine(Graphics g) {
        if (this.baleine != null) {
            int imageWidth = baleineImage.getWidth() / 4;
            int imageHeight = baleineImage.getHeight() / 4;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(baleineImage, drawX, drawY, imageWidth, imageHeight, null);
        }
    }

     /**
     * Draws the shark on the hexagon tile.
     *
     * @param g the Graphics object to use for drawing
     */
    private void drawRequin(Graphics g) {
        if (this.requin != null) {
            int imageWidth = requinImage.getWidth() / 4;
            int imageHeight = requinImage.getHeight() / 4;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(requinImage, drawX, drawY, imageWidth, imageHeight, null);
        }
    }

    /**
     * Draws the serpent on the hexagon tile.
     *
     * @param g the Graphics object to use for drawing
     */
    private void drawSerpent(Graphics g) {
        if (this.serpent != null) {
            int imageWidth = serpentImage.getWidth() / 3;
            int imageHeight = serpentImage.getHeight() / 3;
            int drawX = x - imageWidth / 2;
            int drawY = y - imageHeight / 2;
            g.drawImage(serpentImage, drawX, drawY, imageWidth, imageHeight, null);
        }
    }

    // Getters And Setters
    public ArrayList<Pion> getListPion() {
        return this.pions;
    }

    public void setBateau(Bateau bateau) {
        this.bateau = bateau;
    }
    public Bateau getBateau() {
        return this.bateau;
    }
    public Serpent getSerpent() {
        return this.serpent;
    }

    public Baleine getBaleine() {
        return this.baleine;
    }

    public Requin getRequin() {
        return this.requin;
    }

    public void setSerpent(Serpent serpent) {
        this.serpent = serpent;
    }

    public void setBaleine(Baleine baleine) {
        this.baleine = baleine;
    }

    public void setRequin(Requin requin) {
        this.requin = requin;
    }

    public Effect getEffet() {
        return effet;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }
    public int getX() {
        return x;
    }
    

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public int getY() {
        return y;
    }
}
