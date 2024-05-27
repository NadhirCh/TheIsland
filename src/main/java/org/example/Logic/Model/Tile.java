package org.example.Logic.Model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    private int x, y, radius;
    private Polygon polygon;
    private TileType type;
    private BufferedImage pionRougeImage;
    private BufferedImage pionJauneImage;
    private BufferedImage pionVertImage;
    private BufferedImage pionBleuImage;

    private BufferedImage tuilePlage;
    private BufferedImage tuileForet;
    private BufferedImage tuileMontagne;
    private ArrayList<Pion> pions;
    private boolean isSelected;
    private boolean isValidDestination;

    public Tile(int x, int y, int radius, TileType type) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.type = type;
        this.polygon = createHexagon(x, y, radius);
        this.pions = new ArrayList<>();
        this.isSelected = false;
        this.isValidDestination = false;
        loadImages();
    }

    private void loadImages() {
        tuilePlage = loadImage("/tuile_plage.png");
        tuileForet = loadImage("/tuile_foret.png");
        tuileMontagne = loadImage("/tuile_montagne.png");
        pionRougeImage = loadImage("/pion_rouge.png");
        pionVertImage = loadImage("/pion_vert.png");
        pionJauneImage = loadImage("/pion_jaune.png");
        pionBleuImage = loadImage("/pion_bleu.png");
    }

    private BufferedImage loadImage(String imagePath) {
        try {
            return ImageIO.read(getClass().getResource(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

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

    public void draw(Graphics2D g) {
        BufferedImage tuileImageToDraw = getTuileImageToDraw();

        drawImage(g, tuileImageToDraw);
        drawBorder(g);

        if (isSelected) {
            drawHighlight(g, Color.YELLOW);
        } else if (isValidDestination) {
            drawHighlight(g, Color.GREEN);
        }

        // Draw pawns
        drawPawns(g);
    }

    private void drawHighlight(Graphics2D g, Color color) {
        g.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), 128)); // Semi-transparent
        g.fillPolygon(polygon);
    }

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

    private BufferedImage getPionImage(Pion pion) {
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

    private void drawImage(Graphics2D g, BufferedImage image) {
        if (image != null) {
            g.setClip(polygon);
            g.drawImage(image, x - radius, y - radius, radius * 2, radius * 2, null);
            g.setClip(null);
        } else {
            drawPlaceholder(g);
        }
    }

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

    private void drawBorder(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(2)); // Set the stroke thickness to 2
        g.drawPolygon(polygon);
    }

    public void addPawnToTile(Pion pion) {
        pions.add(pion);
    }

    public Pion getPion() {
        if (pions.isEmpty()) {
            return null;
        }
        return pions.get(0); // Pour simplifier, retourner le premier pion. Ajustez selon les besoins.
    }

    public void removePion(Pion pion) {
        pions.remove(pion);
    }

    private void drawPawns(Graphics2D g) {
        int offsetX = 0;
        int offsetY = 0;
        int step = 20;

        for (Pion pion : pions) {
            BufferedImage pionImage = getPionImage(pion);
            if (pionImage != null) {
                g.drawImage(pionImage, this.x - pionImage.getWidth() / 2 - offsetX, this.y - pionImage.getHeight() / 2 - offsetY, null);
            }
            offsetX += step;
            offsetY += step;
            if (offsetX > radius) offsetX = 0;
            if (offsetY > radius) offsetY = 0;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public void setValidDestination(boolean isValidDestination) {
        this.isValidDestination = isValidDestination;
    }

    public boolean isValidDestination() {
        return isValidDestination;
    }

    // Convert axial coordinates to cubic coordinates
    public int[] getCubicCoordinates() {
        int col = x;
        int row = y - (x - (x & 1)) / 2;
        int z = -col - row;
        return new int[]{col, row, z};
    }

    public List<Tile> getAdjacentTiles(Board board) {
        List<Tile> adjacentTiles = new ArrayList<>();
        Tile[][] tiles = board.getTiles();

        // Taille d'une tuile
        int tileWidth = 2 * radius;
        int tileHeight = (int) (Math.sqrt(3) * radius);

        // Vérifier les tuiles adjacentes
        int[][] directions = {
                {tileWidth, 0}, {-tileWidth, 0}, // Droite, Gauche
                {tileWidth / 2, tileHeight}, {-tileWidth / 2, tileHeight}, // Bas Droite, Bas Gauche
                {tileWidth / 2, -tileHeight}, {-tileWidth / 2, -tileHeight} // Haut Droite, Haut Gauche
        };

        for (int[] dir : directions) {
            int newX = x + dir[0];
            int newY = y + dir[1];
            Tile adjacentTile = getTileByCoordinates(tiles, newX, newY);
            if (adjacentTile != null) {
                adjacentTiles.add(adjacentTile);
            }
        }

        return adjacentTiles;
    }

    private Tile getTileByCoordinates(Tile[][] tiles, int x, int y) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                Tile tile = tiles[row][col];
                if (tile != null && tile.getX() == x && tile.getY() == y) {
                    return tile;
                }
            }
        }
        return null;
    }
}
