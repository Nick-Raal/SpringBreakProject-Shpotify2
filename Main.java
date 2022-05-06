
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.*;
import javax.swing.JFrame;

class Main {
  static File playlist = new File("playlists.dat");
  static File playlistInfo = new File("playlistInfo.dat");
  static ArrayList<Playlist> playlistList = new ArrayList<Playlist>();
  
  public static void main(String[] args) {
    Playlist master = new Playlist("Master");
    JFrame frame = new JFrame("Shpotify 2");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);
    frame.setVisible(true);
    addPlaylist(master);
    initializeList();
    AudioPlayer play = new AudioPlayer();
    AlbumPlayer ap = new AlbumPlayer();
    System.out.println("\u001B[34m███████ ██   ██ ██████   ██████  ████████ ██ ███████ ██    ██     ██████  \n██      ██   ██ ██   ██ ██    ██    ██    ██ ██       ██  ██           ██ \n███████ ███████ ██████  ██    ██    ██    ██ █████     ████        █████  \n     ██ ██   ██ ██      ██    ██    ██    ██ ██         ██        ██      \n███████ ██   ██ ██       ██████     ██    ██ ██         ██        ███████ ");
    System.out.println("\u001B[31mMAKE SURE TO CLICK THE 🎧 IN THE BOTTOM RIGHT OF OUTPUT\u001B[0m");
    System.out.println("\u001B[31mALL AUDIO FILES MUST BE .wav OR .aiff OR .au\u001B[0m");
    while(true){
      Scanner in = new Scanner(System.in);
      int selection = -1;
      System.out.println("0: Add a song, 1: Remove a song, 2: Add a playlist 3: Remove a playlist, 4: View a playlist, 5: View playlists, 6: Play song, 7: Play playlist, 8: Exit");
      while(selection < 0 || selection > 8){
        System.out.println("Please enter your choice:");
        try{
          selection = in.nextInt();
          in.nextLine();
        }catch(Exception e){
          System.out.println(e.getMessage());
          break;
        }
      }
      System.out.print("\033[2J");
      System.out.println("\033[H");
      switch(selection){
        case 0:{
          int n = -1;
          while(n != 0 && n != 1){
            System.out.println("Do you want to add a brand new song or add an existing song to a playlist");
            System.out.println("0: New Song, 1: Existing Song");
            try{
              n = Integer.parseInt(in.nextLine());
            }catch(Exception e){
              System.out.println("Invalid Input");
            }
          }
          System.out.println("\nPlaylists:");
          for(int i = 0; i < playlistList.size(); i++){
            System.out.println(i + ": " + playlistList.get(i).getName());
          }
          System.out.println();
          if(n == 0){
            n = -1;
            while(n < 0 || n > playlistList.size()){
              System.out.println("Please enter which playlist you would like to add to or zero if you want to add the song to the master playlist:");
              try{
                n = Integer.parseInt(in.nextLine());
              }catch(Exception e){
                System.out.println("Invalid Input");
              }
            }
            System.out.println("What is the name of your song?");
            String name = in.nextLine();
            System.out.println("Who is the creator of your song? (Optional)");
            String artist = in.nextLine();
            playlistList.get(n).addSong(new Song(name, artist, null, null));
            System.out.println("What is the URL of the music file? (Optional)");
            String url = in.nextLine();
            if (url != null && !url.isEmpty()){
              try (BufferedInputStream reader = new BufferedInputStream(new URL(url).openStream()); FileOutputStream fileOutputStream = new FileOutputStream(playlistList.get(n).getSongs().get(playlistList.get(n).getSongs().size() - 1).getData())) {
                byte dataBuffer[] = new byte[4096];
                int bytesRead;
                while ((bytesRead = reader.read(dataBuffer, 0, 4096)) != -1) {
                  fileOutputStream.write(dataBuffer, 0, bytesRead);
                }
              }catch (Exception e) {
                System.out.println("url error " + e.getMessage());
              }
            }
            break;
          }else{
            n = -1;
            while(n < 0 || n > playlistList.size()){
              System.out.println("Which playlist do you want to get the song from?");
              try{
                n = Integer.parseInt(in.nextLine());
              }catch(Exception e){
                System.out.println("Invalid Input");
              }
            }
            if(playlistList.get(n).getSongs().size() == 0){
              System.out.println("Playlist too short");
              break;
            }
            System.out.println(playlistList.get(n));
            System.out.println();
            int n1 = -1;
            while(n1 < 0 || n1 > playlistList.get(n).getSongs().size()){
              System.out.println("Which song do you want?");
              try{
                n1 = Integer.parseInt(in.nextLine());
              }catch(Exception e){
                System.out.println("Invalid Input");
              }
            }
            Song s = playlistList.get(n).getSongs().get(n1 - 1);
            n = -1;
            for(int i = 0; i < playlistList.size(); i++){
              System.out.println(i + ": " + playlistList.get(i).getName());
            }
            while(n < 0 || n > playlistList.size()){
              System.out.println("Which playlist do you want to add this song to?");
              try{
                n = Integer.parseInt(in.nextLine());
              }catch(Exception e){
                System.out.println("Invalid Input");
              }
            }
            playlistList.get(n).addSong(s);
            break;
          }
        }
        case 1:{
          int n = -1;
          for(int i = 0; i < playlistList.size(); i++){
            System.out.println(i + ": " + playlistList.get(i).getName());
          }
          while(n < 0 || n > playlistList.size()){
            System.out.println("Which playlist do you want to delete the song from?");
            try{
              n = Integer.parseInt(in.nextLine());
            }catch(Exception e){
              System.out.println("Invalid Input");
            }
          }
          if(playlistList.get(n).getSongs().size() == 0){
            System.out.println("Playlist too short");
            break;
          }
          System.out.println(playlistList.get(n));
          System.out.println();
          int n1 = -1;
          
          System.out.println("Which song do you want to delete? (or enter no to exit)");
          try{
            n1 = Integer.parseInt(in.nextLine());
          }catch(Exception e){
            System.out.println("Invalid Input");
            break;
          }
          try{
            playlistList.get(n).removeSong(playlistList.get(n).getSongs().get(n1 - 1));
          }catch(Exception e){
            System.out.println("Invalid Input");
          }
          break;
        }
        case 2:{
          System.out.println("What would you like to name your playlist?");
          String name = in.nextLine();
          addPlaylist(new Playlist(name));
          break;
        }
        case 3:{          
          for(int i = 0; i < playlistList.size(); i++){
            System.out.println(i + ": " + playlistList.get(i).getName());
          }
          System.out.println("Which playlist do you want to delete? (or enter no to exit)");
          int n = -1;
          try{
            n = Integer.parseInt(in.nextLine());
            if(n == 0){
              throw new RuntimeException();
            }
          }catch(Exception e){
            System.out.println("Invalid Input");
            break;
          }
          
          try{
            removePlaylist(playlistList.get(n));
          }catch(Exception e){
            System.out.println("Invalid Input");
          }
          break;
        }
        case 4:{
          int n = -1;
          for(int i = 0; i < playlistList.size(); i++){
            System.out.println(i + ": " + playlistList.get(i).getName());
          }
          System.out.println("Which playlist do you want to view?");
          while(n < 0 || n > playlistList.size()){
            try{
              n = Integer.parseInt(in.nextLine());
            }catch(Exception e){
              System.out.println("Invalid Input");
            }
          }
          System.out.println(playlistList.get(n));
          break;
        }
        case 5:{
          System.out.println("Playlists:");
          for(int i = 0; i < playlistList.size(); i++){
            System.out.println(i + ": " + playlistList.get(i).getName());
          }
          System.out.println();
          break;
        }
        case 6:{
          int n = -1;
          System.out.println("Playlists:");
          for(int i = 0; i < playlistList.size(); i++){
            System.out.println(i + ": " + playlistList.get(i).getName());
          }
          while(n < 0 || n > playlistList.size()){
            System.out.println("Which playlist do you want to get the song from?");
            try{
              n = Integer.parseInt(in.nextLine());
            }catch(Exception e){
              System.out.println("Invalid Input");
            }
          }
          if(playlistList.get(n).getSongs().size() == 0){
            System.out.println("Playlist too short");
            break;
          }
          System.out.println(playlistList.get(n));
          int n1 = -1;
          while(n1 < 0 || n1 > playlistList.get(n).getSongs().size()){
            System.out.println("Which song do you want?");
            try{
              n1 = Integer.parseInt(in.nextLine());
            }catch(Exception e){
              System.out.println("Invalid Input");
            }
          }
          play.play(playlistList.get(n).getSongs().get(n1 - 1));
          break;
        }
        case 7:{
          int n = -1;
          System.out.println("Playlists:");
          for(int i = 0; i < playlistList.size(); i++){
            System.out.println(i + ": " + playlistList.get(i).getName());
          }
          while(n < 0 || n > playlistList.size()){
            System.out.println("Which playlist do you want to play?");
            try{
              n = Integer.parseInt(in.nextLine());
            }catch(Exception e){
              System.out.println("Invalid Input");
            }
          }
          if(playlistList.get(n).getSongs().size() == 0){
            System.out.println("Playlist too short");
            break;
          }
          System.out.println("Do you want it to be shuffled?");
          boolean shuffle = false;
          try{
            shuffle = in.nextBoolean();
          }catch(Exception e){
            System.out.println("Invalid Input");
          }
          ap.play(playlistList.get(n), shuffle);
          break;
        }
        case 8:
          System.out.println("Thank you for using Shpotify 2, your data has been saved :)");
          in.close();
          System.exit(0);
        default:
          break;
      }
    }
    
  }

  public static void addPlaylist(Playlist p){
    if(playlistExists(p)){
      return;
    }
    try{
      FileWriter fw = new FileWriter(playlist, true);
      FileWriter fw1 = new FileWriter(playlistInfo, true);
      fw.write(p.getData().getName() + "\n");
      fw1.write(p.getInfo().getName() + "\n");
      fw.close();
      fw1.close();
      playlistList.add(p);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }

  public static boolean playlistExists(Playlist p){

    try{
      Scanner in = new Scanner(playlist);
      while(in.hasNext()){         
        if(in.nextLine().equals(p.getData().getName())){
          return true;
        }
      }
      in.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    try{
      Scanner in = new Scanner(playlistInfo);
      while(in.hasNext()){         
        if(in.nextLine().equals(p.getInfo().getName())){
          return true;
        }
      }
      in.close();
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
    return false;
  }

  public static void initializeList(){
    while(playlistList.size() > 0){
      if(playlistList.get(0) != null)
        playlistList.remove(0);
    }
    try{
      Scanner in = new Scanner(playlist);
      Scanner in1 = new Scanner(playlistInfo);
      while(in1.hasNext()){
        String s = in.nextLine();
        playlistList.add(new Playlist(s.replaceAll(".txt", ""), new File(s), new File(in1.nextLine())));
      }
      
      in.close();
      in1.close();
    }catch(Exception e){
      System.out.println("Error: " + e.getMessage());
    }
  }
  
  public static void removePlaylist(Playlist p){
    if(!playlistExists(p)){
      return;
    }
    try{
      FileWriter fw = new FileWriter(playlist, false);
      FileWriter fw1 = new FileWriter(playlistInfo, false);
      for(int i = 0; i < playlistList.size(); i++){
        if(!playlistList.get(i).getData().equals(p.getData())){
          fw.write(playlistList.get(i).getData().getName() + "\n");
          fw1.write(playlistList.get(i).getInfo().getName() + "\n");
        }
      }
      fw.close();
      fw1.close();
      p.getData().delete();
      p.getInfo().delete();
      playlistList.remove(p);
    }catch(Exception e){
      System.out.println(e.getMessage());
    }
  }
}