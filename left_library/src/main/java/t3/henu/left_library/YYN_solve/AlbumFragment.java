package t3.henu.left_library.YYN_solve;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import t3.henu.left_library.R;

/**
 * Created by 202 on 2017/5/20.
 */

public class AlbumFragment extends Fragment {


    private DBAdapter dbAdapter;
    private FindSongs finder;
    private List<Map<String, Object>> csongs = null;
    private List<Song> songs;
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.layout_album, container, false);
        listView = (ListView) rootView.findViewById(R.id.id_listView_album);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songs = (List<Song>) csongs.get(position).get("SONGLIST");
                Intent newIntent = new Intent(getActivity(), NewSongActivity.class);
                DataBean dataBean = new DataBean();
                dataBean.setSongs(songs);
                Bundle bundle = new Bundle();
                bundle.putSerializable("SONGLIST", dataBean);
                newIntent.putExtras(bundle);
                startActivity(newIntent);
            }
        });
        MusicAlbumListAdapter.setListAdapter(
                getActivity().getApplicationContext(), csongs, listView);

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbAdapter = new DBAdapter(getContext());
        dbAdapter.open();
        finder = new FindSongs(dbAdapter);
        finder.getSongsIntoDB(getActivity().getContentResolver());
        if (dbAdapter != null && finder != null) {
            csongs = dbAdapter.queryPartData("ALBUM");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
