package org.example.GUI.ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import org.example.GUI.gamestates.GameState;
import org.example.GUI.mainGame.Game;
import static org.example.GUI.ui.VolumeButton.*;
import static org.example.GUI.ui.SoundButton.*;

/**
 * Handles the audio options in the game, including volume and sound buttons.
 */
public class AudioOptions {

    private VolumeButton volumeButton;
    private SoundButton musicButton, sfxButton;

    private Game game;

    /**
     * Constructs an AudioOptions object.
     *
     * @param game The game instance.
     */
    public AudioOptions(Game game) {
        this.game = game;
        createSoundButtons();
        createVolumeButton();
    }

    /**
     * Creates the volume button with specified position and size.
     */
    private void createVolumeButton() {
        int vX = (int) (309 * 1.5f);
        int vY = (int) (15 * 1.5f);
        volumeButton = new VolumeButton(vX, vY, SLIDER_WIDTH, VOLUME_HEIGHT);
    }

    /**
     * Creates the sound buttons for music and sound effects.
     */
    private void createSoundButtons() {
        int soundX = (int) (450 * 1.5f);
        int musicY = (int) (140 * 1.5f);
        int sfxY = (int) (186 * 1.5f);
        musicButton = new SoundButton(soundX, musicY + 72, SOUND_SIZE, SOUND_SIZE);
        sfxButton = new SoundButton(soundX, sfxY + 72, SOUND_SIZE, SOUND_SIZE);
    }

    /**
     * Updates the state of the sound buttons.
     */
    public void update() {
        musicButton.update();
        sfxButton.update();
        // volumeButton.update();
    }

    /**
     * Draws the sound buttons and volume button.
     *
     * @param g The Graphics object used for drawing.
     */
    public void draw(Graphics g) {
        musicButton.draw(g);
        sfxButton.draw(g);
        // volumeButton.draw(g);
    }

    /**
     * Handles mouse dragging events to adjust volume.
     *
     * @param e The MouseEvent object containing mouse event details.
     */
    public void mouseDragged(MouseEvent e) {
        if (volumeButton.isMousePressed()) {
            float valueBefore = volumeButton.getFloatValue();
            volumeButton.changeX(e.getX());
            float valueAfter = volumeButton.getFloatValue();
            if (valueBefore != valueAfter)
                game.getAudioPlayer().setVolume(valueAfter, valueAfter);
        }
    }

    /**
     * Handles mouse press events to check if any button is pressed.
     *
     * @param e The MouseEvent object containing mouse event details.
     */
    public void mousePressed(MouseEvent e) {
        if (isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMousePressed(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMousePressed(true);
    }

    /**
     * Handles mouse release events to toggle mute settings.
     *
     * @param e The MouseEvent object containing mouse event details.
     */
    public void mouseReleased(MouseEvent e) {
        if (isIn(e, musicButton)) {
            if (musicButton.isMousePressed()) {
                musicButton.setMuted(!musicButton.isMuted());
                game.getAudioPlayer().toggleSongMute();
            }
        } else if (isIn(e, sfxButton)) {
            if (sfxButton.isMousePressed()) {
                sfxButton.setMuted(!sfxButton.isMuted());
                game.getAudioPlayer().toggleEffectsMute();
            }
        }

        musicButton.resetBools();
        sfxButton.resetBools();
        volumeButton.resetBools();
    }

    /**
     * Handles mouse movement events to update button hover states.
     *
     * @param e The MouseEvent object containing mouse event details.
     */
    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sfxButton.setMouseOver(false);
        volumeButton.setMouseOver(false);

        if (isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sfxButton))
            sfxButton.setMouseOver(true);
        else if (isIn(e, volumeButton))
            volumeButton.setMouseOver(true);
    }

    /**
     * Checks if a mouse event occurred within the bounds of a button.
     *
     * @param e The MouseEvent object containing mouse event details.
     * @param b The PauseButton to check.
     * @return True if the mouse event is within the button bounds, false otherwise.
     */
    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}
