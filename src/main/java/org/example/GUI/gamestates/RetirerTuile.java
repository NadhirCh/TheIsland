package org.example.GUI.gamestates;

import org.example.GUI.mainGame.Game;
import org.example.GUI.mainGame.Hexagon;
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

public class RetirerTuile extends State implements StateInterface {
    private  List<Pion>[] islands;
    private BufferedImage backgroundImage;
    private BufferedImage pionRougeImage;
    private BufferedImage pionBleuImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionJauneImage;

    private List<Hexagon> hexagons;

    private final int radius = 40;
    private final int rows = 13;
    private final int cols = 7;
    private float xDelta = 100, yDelta = 100;
    private boolean gridGenerated = false;

    private final int TOTAL_TUILE_PLAGE = 16;
    private final int TOTAL_TUILE_FORET = 12;
    private final int TOTAL_TUILE_MONTAGNE = 12;
    private int currentTuilePlageCounter = 16;
    private int currentTuileForetCounter = 12;
    private int currentTuileMontagneCounter = 12;


    private boolean tuileSelected =false;
    private Hexagon selectedHex;

    private TuileEffectOverlay tuileEffectOverlay;
    private List<Hexagon> listPlageAdjascentToWater;
    private List<Hexagon> listForetAdjascentToWater;
    private List<Hexagon> listMontagneAdjascentToWater;
    private PowerBar Bar;
    private ArrayList<Hexagon> SideBar;

    public PowerBar getBar() {
        return Bar;
    }
    public ArrayList<Hexagon> getSideBar() {
        return SideBar;
    }
    public void setSideBar(ArrayList<Hexagon> playerPower) {
        int i = 0;
        for (Hexagon hex : playerPower) {
            this.SideBar.get(i).setEffet(hex.getEffet());
            this.SideBar.get(i).setType(hex.getType());
            i++;
        }
    }
    public void InitSideBar(){
        for(Hexagon hex:SideBar){
            hex.setEffet(Hexagon.Effect.NONE);
            hex.setType(Hexagon.Type.NONE);
        }
    }






    public Hexagon getSelectedHex() {
        return selectedHex;
    }

    public void setSelectedHex(Hexagon selectedHex) {
        this.selectedHex = selectedHex;
    }

    public boolean isTuileSelected() {
        return tuileSelected;
    }

    public void setTuileSelected(boolean tuileSelected) {
        this.tuileSelected = tuileSelected;
    }

    public void updateTuilesPlageAdjascentToWater(){
        for(Hexagon hex : hexagons){
            if(isAdjacentToWater(hex)){
                switch (hex.getType()){
                    case LAND :
                        if(hex.getRow()==6 && hex.getCol()==5){
                            break;
                        }
                        else if(!listPlageAdjascentToWater.contains(hex)){
                        listPlageAdjascentToWater.add(hex);}
                        break;
                    case  FOREST:
                        if(hex.getRow()==6 && hex.getCol()==5){
                            break;
                        }
                        else if(!listForetAdjascentToWater.contains(hex))
                        {listForetAdjascentToWater.add(hex);}
                        break;
                    case MOUNTAIN:
                        if(hex.getRow()==6 && hex.getCol()==5){
                            break;
                        }
                        else if(!listMontagneAdjascentToWater.contains(hex)){
                            listMontagneAdjascentToWater.add(hex);
                        }
                        break;
                }
            }

        }
    }


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
    public void setIslands(List<Pion>[] islands){
        this.islands = islands;
    }

    public void setHexagons(List<Hexagon>hexagons){
        this.hexagons = hexagons;
    }


    @Override
    public void update() {
        tuileEffectOverlay.update();
        updateTuilesPlageAdjascentToWater();
        for(Hexagon hex : hexagons){
            hex.update();
        }
    }

    private void initClasses()  {
        this.tuileEffectOverlay=new TuileEffectOverlay(this,this.game);
        this.Bar=new PowerBar(this.game);

    }
    public void generateSideBarGrid(){
        int rayon=41;
        int OffsetY= (int) (rayon * 1.5);
        int StartY=(int) (rayon*1.5);
        int x=(int) (rayon * 1.5);

        //tableau representant la sideBar (au max un joueur peut avoir 10)
        Tuile Bar[]=new Tuile[10];

        for(int row=0;row<10;row++){
            int y= StartY + OffsetY * row;

            String type= String.valueOf(Hexagon.Type.NONE);
            String effet=String.valueOf(Hexagon.Type.NONE);;

            this.SideBar.add(new Hexagon(x,y,rayon,type,effet));
        }

    }

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
    public boolean PlayOrPreserve(Hexagon hex){
        if(hex.getEffet().redList().contains(hex.getEffet())){
            game.getCurrentPlayer().getPouvoires().add(hex);
            setSideBar(game.getCurrentPlayer().getPouvoires());
            return true;
        }
        return false;
    }

    public void updateSideBar(int i){
        game.getCurrentPlayer().UsePower(i);
        setSideBar(game.getCurrentPlayer().getPouvoires());
        //
    }
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

        if(game.isGameEnded()){

        }
    }


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
    private boolean isPointInsideHexagon(int mouseX, int mouseY, Hexagon hex) {
        double dist = distance(mouseX, mouseY, hex.getX(), hex.getY());
        return dist < radius;
    }

    public boolean isAdjacentToWater(Hexagon hex){
        List<Hexagon> adjacents = hex.getAdjacentHexagons(hexagons);
        for(Hexagon hexagon : adjacents){
            if(hexagon.getType()== Hexagon.Type.NONE){
                return true;
            }
        }
        return false;

    }

    private void handleHexagonClick(Hexagon hex) {
            if(hex.getType()!= Hexagon.Type.NONE){
                setTuileSelected(true);
                setSelectedHex(hex);
                hex.setClicked(true);
                hex.setType(Hexagon.Type.NONE);
                for(Pion pion : hex.getListPion()){
                    pion.setNageur(true);
                }

                if(!PlayOrPreserve(hex)){
                    tuileEffectOverlay.playCurrentEffect(hex);
                }
            }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // Handle mouse press event
    }

    public void mouseMoved(MouseEvent e){
        this.setRectPos(e.getX(), e.getY());

    }
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

    public void setRectPos(int x, int y) {
        this.xDelta = x;
        this.yDelta = y;
    }


    public List<Hexagon> getHexagons() {
        return this.hexagons;
    }
}