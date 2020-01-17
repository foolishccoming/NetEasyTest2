package t3.henu.left_library.GYB_solve;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.SongInfo;
import t3.henu.left_library.R;

/**
 * Created by 117 on 2017/6/7.
 */

public class DialogAdapter extends BaseAdapter {
    Context mContext;
    int current;
    private List<SongInfo> play_list;

    public DialogAdapter(List<SongInfo> play_list, Context mContext, int current) {
        this.play_list = play_list;
        this.mContext = mContext;
        this.current = current;
    }

    @Override
    public int getCount() {
        return play_list.size();
    }

    @Override
    public Object getItem(int position) {
        return play_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.yhq_musicdialog_list, null);
        TextView title = (TextView) convertView.findViewById(R.id.musicdialog_list_title);
        TextView artist = (TextView) convertView.findViewById(R.id.musicdialog_list_Artist);
        title.setText(play_list.get(position).getSong());
        artist.setText(play_list.get(position).getSinger());
        if (position == current) {
            title.setTextColor(0xffff0000);
            artist.setTextColor(0xffff0000);
        } else {
            title.setTextColor(0xff000000);
            artist.setTextColor(0xffb3b3b3);
        }
        return convertView;
    }

    public void setCurrent(int current) {
        this.current = current;
    }
}
