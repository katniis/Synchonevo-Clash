package utils;

import javax.sound.sampled.*;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

public class AudioPlayer {

    private static final String BG_PATH = "src/resources/background_sound/";
    private static final String SFX_PATH = "src/resources/sfx/";

    private static Clip bgMusicClip = null;
    private static final ConcurrentHashMap<String, Clip> sfxClips = new ConcurrentHashMap<>();


    // ===========================
    // PLAY BACKGROUND MUSIC (LOOP)
    // ===========================
    public static void playBG(String fileName) {
        stopBG();

        try {
            String fullPath = BG_PATH + fileName;

            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fullPath));
            bgMusicClip = AudioSystem.getClip();
            bgMusicClip.open(ais);
            bgMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            bgMusicClip.start();

        } catch (Exception e) {
            System.out.println("Error playing BG music: " + e.getMessage());
        }
    }


    // ===========================
    // STOP BACKGROUND MUSIC
    // ===========================
    public static void stopBG() {
        if (bgMusicClip != null) {
            bgMusicClip.stop();
            bgMusicClip.close();
            bgMusicClip = null;
        }
    }


    // ===========================
    // PLAY SOUND EFFECT
    // ===========================
    public static void playSFX(String fileName) {
        try {
            String fullPath = SFX_PATH + fileName;

            Clip clip = AudioSystem.getClip();
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fullPath));
            clip.open(ais);

            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (Exception e) {
            System.out.println("Error playing SFX: " + e.getMessage());
        }
    }


    // ===========================
    // PRELOAD SFX
    // ===========================
    public static void preloadSFX(String name, String fileName) {
        try {
            String fullPath = SFX_PATH + fileName;

            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fullPath));
            Clip clip = AudioSystem.getClip();
            clip.open(ais);

            sfxClips.put(name, clip);

        } catch (Exception e) {
            System.out.println("Error preloading SFX: " + e.getMessage());
        }
    }


    // PLAY PRELOADED SFX
    public static void playLoadedSFX(String name) {
        Clip clip = sfxClips.get(name);

        if (clip == null) return;

        if (clip.isRunning()) clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }
}
