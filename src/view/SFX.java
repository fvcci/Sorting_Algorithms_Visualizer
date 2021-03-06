package view;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SFX {
    
    private final int BUFFER_SIZE = 128000;
    private File soundFile;
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceLine;
    
    private String filename;
    
    public SFX () {
        this.filename = "fruiteat.wav";
    }
    
    public void playSound () {
        
        try {
            soundFile = new File(filename);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        try {
            audioStream = AudioSystem.getAudioInputStream(soundFile);
        } catch (Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        
        audioFormat = audioStream.getFormat();
        
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
        try {
            sourceLine = (SourceDataLine) AudioSystem.getLine(info);
            sourceLine.open(audioFormat);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        sourceLine.start();
        
        int nBytesRead = 0;
        byte[] abData = new byte[BUFFER_SIZE];
        while (nBytesRead != -1) {
            try {
                nBytesRead = audioStream.read(abData, 0, abData.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (nBytesRead >= 0) {
                @SuppressWarnings("unused")
                int nBytesWritten = sourceLine.write(abData, 0, nBytesRead);
            }
        }
        
        sourceLine.drain();
        sourceLine.close();
    }
    
}