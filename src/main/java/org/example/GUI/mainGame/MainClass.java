package org.example.GUI.mainGame;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * The main class that starts the game.
 * <p>
 * Created by the team  HALF.
 * </p>
 */
public class MainClass {

    /**
     * The main method that launches the game.
     *
     * @param args 
     * @throws UnsupportedAudioFileException if the audio file is unsupported
     * @throws LineUnavailableException if the audio line is unavailable
     * @throws IOException if an I/O error occurs
     */
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        new Game();
    }

}
