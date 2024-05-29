package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
import org.example.GUI.ui.Audio;
import org.example.GUI.ui.PowerBar;
import org.example.GUI.ui.TuileEffectOverlay;
import org.example.Logic.Model.Pion;
import org.example.Logic.Model.Tuile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static java.awt.geom.Point2D.distance;

/**
 * Represents the game state where players remove tiles from the game board.
 * Players can select tiles adjacent to water bodies and remove them from the board.
 */
public class RetirerTuile extends State implements StateInterface {
     // Member variables
    
    /**
     * Represents the islands in the game.
     */
    private List<Pion>[] islands;
    
    /**
     * The background image of the game state.
     */
    private BufferedImage backgroundImage;
    
    /**
     * Images representing different player pawns.
     */
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;
    
    /**
     * The hexagons present on the game board.
     */
    private List<Hexagon> hexagons;
    
    /**
     * The radius of the hexagons.
     */
    private final int radius = 40;
    
    /**
     * The number of rows and columns in the game board.
     */
    private final int rows = 13;
    private final int cols = 7;
    
    /**
     * Delta values for positioning.
     */
    private float xDelta = 100, yDelta = 100;
    
    /**
     * Flags if the grid has been generated.
     */
    private boolean gridGenerated = false;
    
    /**
     * Total number of tiles for each type.
     */
    private final int TOTAL_TUILE_PLAGE = 16;
    private final int TOTAL_TUILE_FORET = 12;
    private final int TOTAL_TUILE_MONTAGNE = 12;
    
    /**
     * Counters for the current number of tiles.
     */
    private int currentTuilePlageCounter = 16;
    private int currentTuileForetCounter = 12;
    private int currentTuileMontagneCounter = 12;
    
    /**
     * Flag indicating if a tile has been selected.
     */
    private boolean tuileSelected = false;
    
    /**
     * The hexagon representing the selected tile.
     */
    private Hexagon selectedHex;
    
    /**
     * Overlay for displaying tile effects.
     */
    private TuileEffectOverlay tuileEffectOverlay;
    
    /**
     * Lists of hexagons adjacent to water bodies for each terrain type.
     */
    private List<Hexagon> listPlageAdjascentToWater;
    private List<Hexagon> listForetAdjascentToWater;
    private List<Hexagon> listMontagneAdjascentToWater;
    
    /**
     * Power bar for the game state.
     */
    private PowerBar Bar;
    
    /**
     * Sidebar containing player power hexagons.
     */
    private ArrayList<Hexagon> SideBar;

    /**
 * Constructs a new instance of RetirerTuile with the specified game.
 * Initializes necessary classes, lists, and generates the sidebar grid.
 * Loads images required for the game state.
 * 
 * @param game The game instance.
 */
    public RetirerTuile(Game game)  {
        super(game);
        initClasses();
        listForetAdjascentToWater = new ArrayList<>();
        listPlageAdjascentToWater = new ArrayList<>();
        listMontagneAdjascentToWater = new ArrayList<>();
        this.SideBar=new ArrayList<>();
        generateSideBarGrid();

        loadImages();
    }

    /**
 * Gets the power bar associated with the game state.
 * 
 * @return The power bar.
 */
    public PowerBar getBar() {
        return Bar;
    }

    /**
 * Gets the sidebar containing player power hexagons.
 * 
 * @return The sidebar.
 */
    public ArrayList<Hexagon> getSideBar() {
        return SideBar;
    }

    /**
 * Sets the sidebar with the provided player power hexagons.
 * 
 * @param playerPower The player power hexagons to set.
 */
    public void setSideBar(ArrayList<Hexagon> playerPower) {
        int i = 0;
        for (Hexagon hex : playerPower) {
            this.SideBar.get(i).setEffet(hex.getEffet());
            this.SideBar.get(i).setType(hex.getType());
            i++;
        }
    }

    /**
 * Initializes the sidebar by setting all hexagons' effect and type to NONE.
 */
    public void InitSideBar(){
        for(Hexagon hex:SideBar){
            hex.setEffet(Hexagon.Effect.NONE);
            hex.setType(Hexagon.Type.NONE);
        }
    }

    /**
 * Gets the selected hexagon.
 * 
 * @return The selected hexagon.
 */
    public Hexagon getSelectedHex() {
        return selectedHex;
    }

    /**
 * Sets the selected hexagon.
 * 
 * @param selectedHex The hexagon to set as selected.
 */
    public void setSelectedHex(Hexagon selectedHex) {
        this.selectedHex = selectedHex;
    }

    /**
 * Checks if a tile is selected.
 * 
 * @return True if a tile is selected, false otherwise.
 */
    public boolean isTuileSelected() {
        return tuileSelected;
    }

    /**
 * Sets the selected state of a tile.
 * 
 * @param tuileSelected The selected state to set.
 */
    public void setTuileSelected(boolean tuileSelected) {
        this.tuileSelected = tuileSelected;
    }

    /**
 * Updates the lists of tiles adjacent to water bodies.
 * Checks each hexagon on the game board and adds it to the appropriate list
 * if it is adjacent to water.
 */
    public void updateTuilesPlageAdjascentToWater(){
        for(Hexagon hex : hexagons){
            if(isAdjacentToWater(hex)){
                switch (hex.getType()){
                    case LAND :
                        if(!listPlageAdjascentToWater.contains(hex)){
                        listPlageAdjascentToWater.add(hex);
                            break;
                        }
                    case  FOREST:
                       if(!listForetAdjascentToWater.contains(hex))
                        {listForetAdjascentToWater.add(hex);}
                        break;
                    case MOUNTAIN:
                        if(!listMontagneAdjascentToWater.contains(hex)){
                            listMontagneAdjascentToWater.add(hex);
                        }
                        break;
                }
            }

        }
    }


    /**
 * Sets the islands for the game state.
 * 
 * @param islands The islands to set.
 */
    
    public void setIslands(List<Pion>[] islands){
        this.islands = islands;
    }

    /**
 * Sets the hexagons for the game state.
 * 
 * @param hexagons The hexagons to set.
 */
    public void setHexagons(List<Hexagon>hexagons){
        this.hexagons = hexagons;
    }

    /**
 * Updates the state.
 * This method updates the tuile effect overlay, updates the list of hexagons adjacent to water,
 * and updates each hexagon in the list of hexagons.
 */
    @Override
    public void update() {
        tuileEffectOverlay.update();
        updateTuilesPlageAdjascentToWater();
        for(Hexagon hex : hexagons){
            hex.update();
        }
    }

    /**
 * Initializes the overlay and power bar classes.
 */
    private void initClasses()  {
        this.tuileEffectOverlay=new TuileEffectOverlay(this,this.game);
        this.Bar=new PowerBar(this.game);

    }

    /**
 * Generates the sidebar grid by creating hexagons and adding them to the sidebar list.
 */
    public void generateSideBarGrid(){
        int rayon=41;
        int OffsetY= (int) (rayon * 1.5);
        int StartY=(int) (rayon*1.5);
        int x=(int) (rayon * 1.5);

        //tableau representant la sideBar (au max un joueur peut avoir 18
        Tuile Bar[]=new Tuile[10];

        for(int row=0;row<10;row++){
            int y= StartY + OffsetY * row;

            String type= String.valueOf(Hexagon.Type.NONE);
            String effet=String.valueOf(Hexagon.Type.NONE);;

            this.SideBar.add(new Hexagon(x,y,rayon,type,effet));
        }

    }

    /**
 * Loads the images required for the game state.
 */
    private void loadImages() {
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/the_island.png"));
            pionBleuImage = ImageIO.read(getClass().getResource("/pion_bleu.png"));
            pionRougeImage = ImageIO.read(getClass().getResource("/pion_rouge.png"));
            pionVertImage = ImageIO.read(getClass().getResource("/pion_vert.png"));
            pionJauneImage = ImageIO.read(getClass().getResource("/pion_jaune.png"));

        } catch (IOException e) {
            System.err.println("Error loading background image ");
            e.printStackTrace();
        }
    }

    /**
 * Handles the action of playing or preserving a power associated with a hexagon.
 * 
 * @param hex The hexagon containing the power.
 * @return True if the power was successfully played or preserved, false otherwise.
 */
    public boolean PlayOrPreserve(Hexagon hex){
        if(hex.getEffet().redList().contains(hex.getEffet())){
            game.getCurrentPlayer().getPouvoires().add(hex);
            setSideBar(game.getCurrentPlayer().getPouvoires());
            return true;
        }
        return false;
    }

    /**
 * Updates the sidebar with the powers of the current player after using a power.
 * 
 * @param i The index of the power used.
 */
    public void updateSideBar(int i){
        game.getCurrentPlayer().UsePower(i);
        setSideBar(game.getCurrentPlayer().getPouvoires());
        //
    }

    /**
 * Draws the game state including the background, hexagons, tuile effect overlay, and sidebar.
 * 
 * @param g The graphics context.
 */
    @Override
    public void draw(Graphics g) {
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
        }

        Graphics2D g2d = (Graphics2D) g;
        for (Hexagon hex : hexagons) {
            hex.draw(g2d);
        }

        if (isTuileSelected()) {
            tuileEffectOverlay.draw(g, selectedHex);
        }
        setSideBar(game.getCurrentPlayer().getPouvoires());
    }

    /**
 * Handles the mouse click event by checking if a hexagon is clicked and adjacent to water,
 * then performs actions based on the type of hexagon clicked.
 * 
 * @param e The mouse event.
 */
    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        for (Hexagon hex : hexagons) {
            if (isPointInsideHexagon(mouseX, mouseY, hex) && !hex.isClicked() && isAdjacentToWater(hex)) {
                if(listPlageAdjascentToWater.contains(hex)) {
                    handleHexagonClick(hex);
                    listPlageAdjascentToWater.remove(hex);
                    currentTuilePlageCounter--;
                }
                else if(listForetAdjascentToWater.contains(hex) && listPlageAdjascentToWater.isEmpty()){
                    handleHexagonClick(hex);
                    listForetAdjascentToWater.remove(hex);
                    currentTuileForetCounter--;
                }
                else if(listMontagneAdjascentToWater.contains(hex) && listPlageAdjascentToWater.isEmpty() && listForetAdjascentToWater.isEmpty() ){
                    handleHexagonClick(hex);
                    listMontagneAdjascentToWater.remove(hex);
                    currentTuileMontagneCounter--;
                }
                break;
            }
        }
    }

    /**
 * Checks if a point is inside a hexagon.
 * 
 * @param mouseX The x-coordinate of the point.
 * @param mouseY The y-coordinate of the point.
 * @param hex The hexagon to check.
 * @return True if the point is inside the hexagon, false otherwise.
 */    

    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    /**
 * Checks if a hexagon is adjacent to water.
 * 
 * @param hex The hexagon to check.
 * @return True if the hexagon is adjacent to water, false otherwise.
 */
    public boolean isAdjacentToWater(Hexagon hex){
        List<Hexagon> adjacents = hex.getAdjacentHexagons(hexagons);
        for(Hexagon hexagon : adjacents){
            if(hexagon.getType()== Hexagon.Type.NONE){
                return true;
            }
        }
        return false;

    }

    /**
 * Handles the action when a hexagon is clicked.
 * 
 * @param hex The hexagon that was clicked.
 */
    private void handleHexagonClick(Hexagon hex) {
        if(!tuileSelected) {
            if (hex.getType() != Hexagon.Type.NONE) {
                game.getAudioPlayer().playEffect(Audio.TILE);
                setTuileSelected(true);
                setSelectedHex(hex);
                hex.setClicked(true);
                hex.setType(Hexagon.Type.NONE);
                for (Pion pion : hex.getListPion()) {
                    pion.setNageur(true);
                }

                if (!PlayOrPreserve(hex)) {
                    tuileEffectOverlay.playCurrentEffect(hex);
                }
            }
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    /**
 * Handles the mouse movement event.
 * 
 * @param e The mouse event.
 */
    public void mouseMoved(MouseEvent e){
        this.setRectPos(e.getX(), e.getY());

    }

    /**
 * Handles the key press event.
 * 
 * @param e The key event.
 */
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                if (isTuileSelected()) {
                    setTuileSelected(false);
                    game.nextTurn();
                }
                break;
            default:
                break;
        }
    }



    @Override
    public void mouseReleased(MouseEvent e) {
        // Handle mouse release event
    }

    /**
 * Sets the position of the rectangle.
 * 
 * @param x The x-coordinate of the position.
 * @param y The y-coordinate of the position.
 */
    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }

    /**
 * Retrieves the list of hexagons.
 * 
 * @return The list of hexagons.
 */
    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }
}