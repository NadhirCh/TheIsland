package org.example.GUI.mainGame;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.example.GUI.Inputs.KeyboardInputs;
import org.example.GUI.Inputs.MouseInputs;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;

    private Game game;

    /**
     * Constructor for GamePanel.
     * @param game The game object.
     */
    public GamePanel(Game game) {
        this.game = game;
        mouseInputs = new MouseInputs(this);
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * Sets the size of the panel.
     */
    private void setPanelSize() {
        Dimension size = new Dimension(1200, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    /**
     * Updates the game.
     */
    public void updateGame() {
        // Placeholder for future implementation
    }

    /**
     * Retrieves the game object.
     * @return The game object.
     */
    public Game getGame() {
        return this.game;
    }

    /**
     * Overrides the paintComponent method to render the game.
     * @param g The graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        game.render(g);
    }
}
