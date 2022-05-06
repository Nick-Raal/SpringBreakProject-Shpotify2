
import java.io.*;

public class Song{

  private File songData;
  private File songInfo;
  private String name;
  private String artist;

  public Song(String name){
    this(name, "Unknown", null, null);
  }
  
  public Song(String name, String artist){
    this(name, artist, null, null);
  }
  
  public Song(String name, File file){
    this(name, "Unknown", file, null);
  }
  
   public Song(String name, String artist, File file){
    this(name, artist, file, null);
  }
  public Song(String name, File file, File f2){
    this(name, "Unknown", file, f2);
  }
  public Song(String name, String artist, File file, File f2){
    try{
      if(file != null){
         songData = file;
      }else{
        songData = new File(name.replaceAll("\\s", "") + ".wav");
      songData.createNewFile();
      }
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    this.name = name;
    this.artist = artist;
    try{
      if(f2 != null){
        songInfo = f2;
      }else{
        songInfo = new File(name.replaceAll("\\s", "") + ".dat");
        songData.createNewFile();
      }
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    try{
      FileWriter fw = new FileWriter(songInfo, false);
      fw.write(name + "\n");
      fw.write(artist);
      fw.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  public File getData(){
    return songData;
  }

  public File getInfo(){
    return songInfo;
  }

  public String getName(){
    return name;
  }

  public String getArtist(){
    return artist;
  }
}