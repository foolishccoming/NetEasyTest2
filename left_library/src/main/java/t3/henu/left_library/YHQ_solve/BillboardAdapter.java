package t3.henu.left_library.YHQ_solve;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import t3.henu.left_library.R;
import t3.henu.left_library.YHQ_solve.clickBillboard.OnlineMusicActivity;
import t3.henu.left_library.YHQ_solve.http.HttpCallback;
import t3.henu.left_library.YHQ_solve.http.HttpClient;
import t3.henu.left_library.YHQ_solve.utils.ImageUtils;


/**
 * Created by 117 on 2017/5/18.
 */

public class BillboardAdapter extends RecyclerView.Adapter<BillboardAdapter.ViewHolder> {
    private List<BillboardListInfo>mBillboardlist;
    private Context mContext;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View billboardView;
        ImageView billboard_img;
        TextView billboard_music1,billboard_music2,billboard_music3;
        public ViewHolder(View view) {
            super(view);
            billboardView=view;
            billboard_img= (ImageView) view.findViewById(R.id.yhq_bv_cover);
            billboard_music1= (TextView) view.findViewById(R.id.yhq_bv_music_1);
            billboard_music2= (TextView) view.findViewById(R.id.yhq_bv_music_2);
            billboard_music3= (TextView) view.findViewById(R.id.yhq_bv_music_3);
        }
    }
    public BillboardAdapter(List<BillboardListInfo>mBillboardlist)
    {
        this.mBillboardlist=mBillboardlist;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view= LayoutInflater.from(mContext).inflate(R.layout.yhq_view_holder_song_list,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.billboardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                BillboardListInfo songListInfo = mBillboardlist.get(position);
                Intent intent = new Intent(mContext, OnlineMusicActivity.class);
                intent.putExtra("music_list_type", songListInfo);
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final BillboardListInfo billboard=mBillboardlist.get(position);
        if(billboard.getCoverUrl()==null)
        {
            holder.billboard_img.setTag(billboard.getTitle());
            holder.billboard_img.setImageResource(R.mipmap.default_cover);
            holder.billboard_music1.setText("1.加载中…");
            holder.billboard_music2.setText("2.加载中…");
            holder.billboard_music3.setText("3.加载中…");
            HttpClient.getSongListInfo(billboard.getType(), 3, 0, new HttpCallback<OnlineMusicList>() {
                @Override
                public void onSuccess(OnlineMusicList response) {
                    if (response == null || response.getSong_list() == null) {
                        return;
                    }
                    if (!billboard.getTitle().equals(holder.billboard_img.getTag())) {
                        return;
                    }

                    parse(response, billboard);
                    setData(billboard, holder);
                }

                @Override
                public void onFail(Exception e) {
                    Toast.makeText(mContext,"失败",Toast.LENGTH_LONG).show();
                }
            });
        }else {
            holder.billboard_img.setTag(null);
            setData(billboard, holder);
        }
    }
    private void parse(OnlineMusicList response, BillboardListInfo billboard) {
        List<OnlineMusic> onlineMusics = response.getSong_list();
        billboard.setCoverUrl(response.getBillboard().getPic_s260());

        if (onlineMusics.size() >= 1) {
            billboard.setMusic1(mContext.getString(R.string.yhq_song_list_item_title_1,
                    onlineMusics.get(0).getTitle(), onlineMusics.get(0).getArtist_name()));
        } else {
            billboard.setMusic1("");
        }
        if (onlineMusics.size() >= 2) {
            billboard.setMusic2(mContext.getString(R.string.yhq_song_list_item_title_2,
                    onlineMusics.get(1).getTitle(), onlineMusics.get(1).getArtist_name()));
        } else {
            billboard.setMusic2("");
        }
        if (onlineMusics.size() >= 3) {
            billboard.setMusic3(mContext.getString(R.string.yhq_song_list_item_title_3,
                    onlineMusics.get(2).getTitle(), onlineMusics.get(2).getArtist_name()));
        } else {
            billboard.setMusic3("");
        }

    }
    @Override
    public int getItemCount() {
        return mBillboardlist.size();
    }
    private void setData(BillboardListInfo billboardListInfo, final ViewHolder holder) {
        ImageLoader.getInstance().displayImage(billboardListInfo.getCoverUrl(), holder.billboard_img, ImageUtils.getCoverDisplayOptions());
        holder.billboard_music1.setText(billboardListInfo.getMusic1());
        holder.billboard_music2.setText(billboardListInfo.getMusic2());
        holder.billboard_music3.setText(billboardListInfo.getMusic3());
    }
}
