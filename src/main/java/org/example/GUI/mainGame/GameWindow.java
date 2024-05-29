package org.example.GUI.mainGame;

import javax.swing.JFrame;

/**
 * Represents the window for the game.
 */
public class GameWindow {

    private JFrame jframe;

    /**
     * Constructor for GameWindow.
     * @param gamePanel The game panel to be added to the window.
     */
    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.setResizable(false);
        jframe.pack();
        jframe.setVisible(true);
    }
}
