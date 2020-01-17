package t3.henu.left_library.GYB_solve.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.MusicUtils;
import t3.henu.left_library.GYB_solve.Activities.MyMusic;
import t3.henu.left_library.R;



public class LeftRecyclerView extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private List<RecyclerData> mDatas=new ArrayList<RecyclerData>();
    private String []texts;
    private String []numbers;
    private int images[]={R.drawable.icon_music_first,R.drawable.icon_bofang,R.drawable.icon_mydown,
                        R.drawable.icon_diantai,R.drawable.icon_shoucang};
    private Intent intent1=null;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gyb_fragment_recyclerview, container, false);
        recyclerView= (RecyclerView) rootView.findViewById(R.id.id_recycler_Allmusic);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        }) ;
        return rootView;
    }

    private void initView() {
        if(mDatas.size()<=0){
            texts=getResources().getStringArray(R.array.strs_music_liebiao);
            numbers=getResources().getStringArray(R.array.strs_music_number);
            for(int i=0;i<texts.length;i++){
                RecyclerData recyclerData;
                recyclerData=new RecyclerData(images[i],texts[i],numbers[i]);
                if(i==0){
                    recyclerData.setNum(MusicUtils.getMp3Infos(getContext()).size()+"");
                }
                mDatas.add(recyclerData);
            }
        }
        RecyclerAdapter adapter=new RecyclerAdapter(mDatas);
        recyclerView.setAdapter(adapter);
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if(i==0){
                    if(intent1==null){
                        intent1=new Intent(getContext(), MyMusic.class);
                    }
                    startActivity(intent1);
                }else{
                    toast(mDatas.get(i).getText());
                }


            }
        });
    }

    private void toast(String text) {
        Toast.makeText(getContext(),text,Toast.LENGTH_SHORT).show();
    }
}
