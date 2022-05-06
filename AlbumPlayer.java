import java.util.ArrayList;

public class AlbumPlayer extends AudioPlayer{
  
  public void play(Playlist p, boolean shuffle){
    ArrayList<Song> songs = p.getSongs();
    if(shuffle){
      for(int i = 0; i < songs.size() * 2; i++){
          songs.add((int)(Math.random() * songs.size()), songs.remove((int)(Math.random() * songs.size())));
      }
      for(Song s : songs){
        System.out.println(s.getData().getName());
      }
    }
    for(int i = 0; i < songs.size(); i++){
      super.play(songs.get(i));
    }
    
  }  
}