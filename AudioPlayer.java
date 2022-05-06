import java.io.*;
import javax.sound.sampled.*;

public class AudioPlayer{
   
  public void play(Song s){
    try{
      File music = s.getData();
      AudioInputStream audioStream =   AudioSystem.getAudioInputStream(music);
      AudioFormat format = audioStream.getFormat();
      DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
      SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
      audioLine.open(format);
      audioLine.start();
      byte[] bytesBuffer = new byte[4096];
      int bytesRead = -1;
      while ((bytesRead = audioStream.read(bytesBuffer)) != -1) {
        audioLine.write(bytesBuffer, 0, bytesRead);
      }      
      audioLine.drain();
      audioLine.close();
      audioStream.close();
    }catch(Exception e){
      System.out.println("there was a playback error");
      System.out.println(e.getMessage());
    }
  }
}