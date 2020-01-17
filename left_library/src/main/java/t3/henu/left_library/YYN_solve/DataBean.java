package t3.henu.left_library.YYN_solve;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hippo on 2017/6/4.
 */

public class DataBean implements Serializable {
    private List<Song> songs;

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}
