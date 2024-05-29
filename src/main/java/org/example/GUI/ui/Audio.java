package org.example.GUI.ui;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

/**
 * Manages audio playback for the application, including background music and sound effects.
 */
public class Audio {

    public static final int MENU = 0;
    public static final int INGAME = 1;

    public static final int MENUCLICK = 0;
    public static final int PIECEMOVE = 1;
    public static final int TILE = 2;
    public static final int WATER = 3;
    public static final int BOAT = 4;
    public static final int WHALE = 5;
    public static final int SHARK = 6;
    public static final int DOLPHIN = 7;
    public static final int WHIRLPOOL = 8;
    public static final int VOLCANO = 9;
    public static final int DICE = 10;
    public static final int KRAKEN = 11;

    private Clip[] songs;
    private Clip[] effects;
    private int currentSongID;
    private float effectsVolume = 0.75f;
    private float songVolume = effectsVolume * 0.85f;
    private boolean songMute = false;
    private boolean effectMute = false;
    //private int viewIndex;
    //private Random rand = new Random();


    /**
     * Constructs an Audio object, loads songs and effects, and starts playing the menu song.
     *
     * @throws UnsupportedAudioFileException if the audio file format is unsupported
     * @throws LineUnavailableException if a line cannot be opened
     * @throws IOException if an I/O error occurs
     */
    public Audio() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        loadSong();
        loadEffects();
        playSong(MENU);
    }

    /**
     * Retrieves a Clip object for the specified audio file name.
     *
     * @param audioName the name of the audio file (without the extension)
     * @return the Clip object for the specified audio file
     * @throws UnsupportedAudioFileException if the audio file format is unsupported
     * @throws IOException if an I/O error occurs
     * @throws LineUnavailableException if a line cannot be opened
     */
    private Clip getClip(String audioName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL url = getClass().getResource("/audio/" + audioName + ".wav");
        try (AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url)) {
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
            System.out.println("Error during Clip Loading: " + exception.getMessage());
            throw exception;
        }
    }

    /**
     * Loads song clips into the songs array.
     *
     * @throws UnsupportedAudioFileException if the audio file format is unsupported
     * @throws LineUnavailableException if a line cannot be opened
     * @throws IOException if an I/O error occurs
     */
    private void loadSong() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String[] songNames = {"menuMusic", "inGameMusic"};
        songs = new Clip[songNames.length];
        for (int i = 0; i < songNames.length; i++) {
            songs[i] = getClip(songNames[i]);
        }
    }

    /**
     * Loads effect clips into the effects array.
     *
     * @throws UnsupportedAudioFileException if the audio file format is unsupported
     * @throws LineUnavailableException if a line cannot be opened
     * @throws IOException if an I/O error occurs
     */
    private void loadEffects() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        String[] effectNames = {"menuClick", "pieceMove", "tile", "water", "boat", "whale", "shark", "dolphin", "whirlpool", "volcano", "dice", "kraken"};
        effects = new Clip[effectNames.length];
        for (int i = 0; i < effectNames.length; i++) {
            effects[i] = getClip(effectNames[i]);
        }
        updateEffectsVolume();
    }


    /**
     * Sets the current song to be played based on the view index.
     *
     * @param viewIndex the index of the view to set the song for
     */
    public void setSongView(int viewIndex) {
        // Implementation for setting the song based on view index
    }

    /**
     * Stops the current song when the game ends.
     */
    public void gameEnded() {
        stopSong();
    }

    /**
     * Plays the specified sound effect.
     *
     * @param effect the index of the effect to play
     */
    public void playEffect(int effect) {

        effects[effect].setMicrosecondPosition(0);
        effects[effect].start();
    }

    /**
     * Sets the volume for both songs and effects.
     *
     * @param songVolume the volume for songs
     * @param effectsVolume the volume for effects
     */
    public void setVolume(float songVolume, float effectsVolume) {
        this.songVolume = songVolume;
        this.effectsVolume = effectsVolume;
        updateSongVolume();
        updateEffectsVolume();
    }

    /**
     * Stops the currently playing song.
     */
    public void stopSong() {
        if (songs[currentSongID].isActive()) {
            songs[currentSongID].stop();
        }
    }

    /**
     * Plays the specified song.
     *
     * @param song the index of the song to play
     */
    public void playSong(int song) {
        stopSong();
        currentSongID = song;
        updateSongVolume();
        songs[currentSongID].setMicrosecondPosition(0);
        songs[currentSongID].loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**
     * Toggles the mute state for songs.
     */
    public void toggleSongMute() {
        this.songMute = !songMute;
        for (Clip clip : songs) {
            BooleanControl boolControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            boolControl.setValue(songMute);
        }
    }

    /**
     * Toggles the mute state for effects.
     */
    public void toggleEffectsMute() {
        this.effectMute = !effectMute;
        for (Clip clip : effects) {
            BooleanControl boolControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
            boolControl.setValue(effectMute);
        }
        if (!effectMute) {
            playEffect(MENUCLICK);
        }
    }

    /**
     * Updates the volume for the currently playing song.
     */
    public void updateSongVolume() {
        FloatControl gainControl = (FloatControl) songs[currentSongID].getControl(FloatControl.Type.MASTER_GAIN);
        float range = gainControl.getMaximum() - gainControl.getMinimum();
        float value = (range * songVolume) + gainControl.getMinimum();
        gainControl.setValue(value);
    }

    /**
     * Updates the volume for all effects.
     */
    public void updateEffectsVolume() {
        for (Clip clip : effects) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float range = gainControl.getMaximum() - gainControl.getMinimum();
            float value = (range * effectsVolume) + gainControl.getMinimum();
            gainControl.setValue(value);
        }
    }


    public boolean getEffectMute(){
        return effectMute;
    }



}
