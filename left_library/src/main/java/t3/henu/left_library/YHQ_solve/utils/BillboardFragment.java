package t3.henu.left_library.YHQ_solve.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import t3.henu.left_library.R;
import t3.henu.left_library.YHQ_solve.AppCache;
import t3.henu.left_library.YHQ_solve.BillboardAdapter;
import t3.henu.left_library.YHQ_solve.BillboardListInfo;

/**
 * Created by 117 on 2017/5/21.
 */

public class BillboardFragment extends Fragment {
    View billboardView;
    private List<BillboardListInfo> mBillboardlist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        billboardView=inflater.inflate(R.layout.yhq_activity_billboard,container,false);
        RecyclerView recyclerView= (RecyclerView)billboardView.findViewById(R.id.yhq_billboard_list);
        init();
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        BillboardAdapter adapter=new BillboardAdapter(mBillboardlist);
        recyclerView.setAdapter(adapter);
        return billboardView;
    }
    protected void init() {
        mBillboardlist = AppCache.getSongListInfos();
        if (mBillboardlist.isEmpty()) {
            String[] titles = getResources().getStringArray(R.array.yhq_online_music_list_title);
            String[] types = getResources().getStringArray(R.array.yhq_online_music_list_type);
            for (int i = 0; i < titles.length; i++) {
                BillboardListInfo info = new BillboardListInfo();
                info.setTitle(titles[i]);
                info.setType(types[i]);
                mBillboardlist.add(info);
            }
        }
    }
}
