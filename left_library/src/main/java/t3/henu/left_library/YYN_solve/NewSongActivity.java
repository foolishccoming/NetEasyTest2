package t3.henu.left_library.YYN_solve;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.R;

public class NewSongActivity extends AppCompatActivity {

    private List<Song> songs;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_song);

        listView = (ListView) findViewById(R.id.id_listView_song);

        songs = new ArrayList<>();
        List<Song> songList;
        DataBean dataBean;
        Bundle bundle = getIntent().getExtras();

        dataBean = (DataBean) bundle.getSerializable("SONGLIST");
        songList = dataBean.getSongs();
        songs.addAll(songList);

        MusicSongListAdapter.setListAdapter(getApplicationContext(), songs, listView);
    }

}
