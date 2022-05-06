import java.io.*;
import java.util.*;

public class Playlist{
  private File songs;
  private ArrayList<Song> songList = new ArrayList<Song>();
  private String name;
  private File songsInfo;

  public Playlist(String name){
    this(name, null, null);
  }

  public Playlist(String name, File f1, File f2){
    this.name = name;
    try{
      if(f1 != null){
        songs = f1;
      }else{
        songs = new File(name.replaceAll("\\s", "") + ".txt");        
        songs.createNewFile();
      }
      if(f2 != null){
        songsInfo  = f2;
      }else{
        songsInfo = new File(name.replaceAll("\\s", "") + "Info.txt");
        songsInfo.createNewFile();
      }
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    initializeList();
  }

  public void addSong(Song s){
    if(songExists(s)){
      return;
    }
    try{
      FileWriter fw = new FileWriter(songs, true);
      FileWriter fw1 = new FileWriter(songsInfo, true);
      fw.write(s.getData().getName() + "\n");
      fw1.write(s.getInfo().getName() + "\n");
      fw.close();
      fw1.close();
      songList.add(s);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    //initializeList();
  }

  public void removeSong(Song s){
    if(!songExists(s)){
      return;
    }
    try{
      FileWriter fw = new FileWriter(songs, false);
      FileWriter fw1 = new FileWriter(songsInfo, false);
      for(int i = 0; i < songList.size(); i++){
        if(!songList.get(i).getData().equals(s.getData())){
          fw.write(songList.get(i).getData().getName() + "\n");
          fw1.write(songList.get(i).getInfo().getName() + "\n");
        }
      }
      fw.close();
      fw1.close();
      songList.remove(s);
      s.getData().delete();
      s.getInfo().delete();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  
  public boolean songExists(Song s){

    try{
      Scanner in = new Scanner(songs);
      while(in.hasNext()){         
        if(in.nextLine().equals(s.getData().getName())){
          return true;
        }
      }
      in.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    try{
      Scanner in = new Scanner(songsInfo);
      while(in.hasNext()){         
        if(in.nextLine().equals(s.getInfo().getName())){
          return true;
        }
      }
      in.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    return false;
  }

  public void initializeList(){
    while(songList.size() > 0){
      if(songList.get(0) != null)
        songList.remove(0);
    }
    try{
      Scanner in = new Scanner(songs);
      Scanner in1 = new Scanner(songsInfo);
      while(in1.hasNext()){
        File infoFile = new File(in1.nextLine());
        Scanner data = new Scanner(infoFile);
        songList.add(new Song(data.nextLine(), new File(in.nextLine()), infoFile));
        data.close();
      }
      
      in.close();
      in1.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  public ArrayList<Song> getSongs(){
    return songList;
  }

  public File getData(){
    return songs;
  }

  public File getInfo(){
    return songsInfo;
  }

  public String getName(){
    return name;
  }

  public String toString(){
    String s = name + ":\n";
    for(int i = 1; i <= songList.size(); i++){
      s += i + ": " + songList.get(i - 1).getName() +  " - " + songList.get(i-1).getArtist() + "\n";
    }
    return s;
  }
}