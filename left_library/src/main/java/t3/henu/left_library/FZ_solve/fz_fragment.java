package t3.henu.left_library.FZ_solve;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import t3.henu.left_library.R;

/**
 * Created by 117 on 2017/6/2.
 */

public class fz_fragment extends Fragment{
    View fz_View;
    private GridView gview;
    private TextView t1,t2,t3,t4,t5;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    // 图片封装为一个数组
    private int[] icon = { R.mipmap.fz_pic1,R.mipmap.fz_pic2,R.mipmap.fz_pic3,
            R.mipmap.fz_pic4,R.mipmap.fz_pic5,R.mipmap.fz_pic6,R.mipmap.fz_pic7
            ,R.mipmap.fz_pic8,R.mipmap.fz_pic9
            ,R.mipmap.fz_pic10,R.mipmap.fz_pic11,R.mipmap.fz_pic12};
    private String[]  iconName = {"校园合唱", "一切随遇而安", "最后的夏天", "情爱无智者，智者无爱河", "独自等候","畅游大世界",  "喜欢雨天", "哥哥",
            "跨界歌王",  "高考加油", "你若安好，便是晴天", "春风十里不如你"};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fz_View=inflater.inflate(R.layout.fz_fragment,container,false);
        gview= (GridView) fz_View.findViewById(R.id.fz_gview);
        t1=(TextView)fz_View.findViewById(R.id.fz_content1);
        t2=(TextView)fz_View.findViewById(R.id.fz_content2);
        t3=(TextView)fz_View.findViewById(R.id.fz_content3);
        t4=(TextView)fz_View.findViewById(R.id.fz_content4);
        t5=(TextView)fz_View.findViewById(R.id.fz_content5);
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon = new int[]{R.mipmap.fz_pic1, R.mipmap.fz_pic2, R.mipmap.fz_pic3,
                        R.mipmap.fz_pic4, R.mipmap.fz_pic5, R.mipmap.fz_pic6, R.mipmap.fz_pic7
                        , R.mipmap.fz_pic8, R.mipmap.fz_pic9
                        , R.mipmap.fz_pic10,R.mipmap.fz_pic11,R.mipmap.fz_pic12};
                iconName = new String[]{"校园合唱", "一切随遇而安", "最后的夏天", "情爱无智者，智者无爱河", "独自等候","畅游大世界",  "喜欢雨天", "哥哥",
                        "跨界歌王",  "高考加油", "你若安好，便是晴天", "春风十里不如你"};
                test();
                t1.setTextColor(Color.rgb(234,32,0));
                t2.setTextColor(Color.rgb(0,0,0));
                t3.setTextColor(Color.rgb(0,0,0));
                t4.setTextColor(Color.rgb(0,0,0));
                t5.setTextColor(Color.rgb(0,0,0));
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon = new int[]{R.mipmap.fz_pic24, R.mipmap.fz_pic21, R.mipmap.fz_pic20,
                        R.mipmap.fz_pic23, R.mipmap.fz_pic22, R.mipmap.fz_pic19, R.mipmap.fz_pic18
                        , R.mipmap.fz_pic17, R.mipmap.fz_pic16
                        , R.mipmap.fz_pic15, R.mipmap.fz_pic14, R.mipmap.fz_pic13};
                iconName = new String[]{"迷幻女声，百变旋律", "一人一首主打歌", "NBA歌，爱篮球，无理由", "百首热门华语歌曲", "精选电音", "我若在你心上，情敌三千又何妨", "盘点这些年压轴级电音",
                        "游戏伴奏", "在青春的怀里轻歌曼舞", "那年的时光，你还怀念吗", "薄荷味的清凉小调", "爸爸的收音机"};
                test();
                t2.setTextColor(Color.rgb(234,32,0));
                t1.setTextColor(Color.rgb(0,0,0));
                t3.setTextColor(Color.rgb(0,0,0));
                t4.setTextColor(Color.rgb(0,0,0));
                t5.setTextColor(Color.rgb(0,0,0));
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon = new int[]{R.mipmap.fz_pic13, R.mipmap.fz_pic16, R.mipmap.fz_pic12,
                        R.mipmap.fz_pic9, R.mipmap.fz_pic22, R.mipmap.fz_pic8
                        , R.mipmap.fz_pic17, R.mipmap.fz_pic16
                        , R.mipmap.fz_pic15, R.mipmap.fz_pic14, R.mipmap.fz_pic13,R.mipmap.fz_pic1};
                iconName = new String[]{"爸爸的收音机", "游戏伴奏", "春风十里不如你", "跨界歌王", "精选电音", "哥哥", "盘点这些年压轴级电音",
                        "游戏伴奏", "在青春的怀里轻歌曼舞", "那年的时光，你还怀念吗", "薄荷味的清凉小调", "校园合唱"};
                test();
                t3.setTextColor(Color.rgb(234,32,0));
                t1.setTextColor(Color.rgb(0,0,0));
                t2.setTextColor(Color.rgb(0,0,0));
                t4.setTextColor(Color.rgb(0,0,0));
                t5.setTextColor(Color.rgb(0,0,0));
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon = new int[]{R.mipmap.fz_pic15, R.mipmap.fz_pic19, R.mipmap.fz_pic6,
                        R.mipmap.fz_pic12, R.mipmap.fz_pic2, R.mipmap.fz_pic7
                        , R.mipmap.fz_pic17, R.mipmap.fz_pic16
                        , R.mipmap.fz_pic9, R.mipmap.fz_pic18, R.mipmap.fz_pic3,R.mipmap.fz_pic1};
                iconName = new String[]{"那年的时光，你还怀念吗", "游戏伴奏", "春风十里不如你", "跨界歌王", "精选电音", "哥哥", "盘点这些年压轴级电音",
                        "游戏伴奏", "在青春的怀里轻歌曼舞", "那年的时光，你还怀念吗", "薄荷味的清凉小调", "校园合唱"};
                test();
                t4.setTextColor(Color.rgb(234,32,0));
                t1.setTextColor(Color.rgb(0,0,0));
                t2.setTextColor(Color.rgb(0,0,0));
                t3.setTextColor(Color.rgb(0,0,0));
                t5.setTextColor(Color.rgb(0,0,0));
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                icon = new int[]{R.mipmap.fz_pic9, R.mipmap.fz_pic18, R.mipmap.fz_pic16,
                        R.mipmap.fz_pic7, R.mipmap.fz_pic2, R.mipmap.fz_pic8
                        , R.mipmap.fz_pic17, R.mipmap.fz_pic5
                        , R.mipmap.fz_pic9, R.mipmap.fz_pic14, R.mipmap.fz_pic3,R.mipmap.fz_pic1};
                iconName = new String[]{"跨界歌王", "怎奈何", "春风十里不如你", "跨界歌王", "精选电音", "哥哥", "盘点这些年压轴级电音",
                        "游戏伴奏", "在青春的怀里轻歌曼舞", "那年的时光，你还怀念吗", "薄荷味的清凉小调", "校园合唱"};
                test();
                t5.setTextColor(Color.rgb(234,32,0));
                t1.setTextColor(Color.rgb(0,0,0));
                t2.setTextColor(Color.rgb(0,0,0));
                t4.setTextColor(Color.rgb(0,0,0));
                t3.setTextColor(Color.rgb(0,0,0));
            }
        });
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from ={"image","text"};
        int [] to = {R.id.fz_item_image,R.id.fz_item_text};
        sim_adapter = new SimpleAdapter(getContext(), data_list, R.layout.fz_item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
        gview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 1:
                    default:
                        Toast.makeText(getContext(),"该功能尚未实现", Toast.LENGTH_SHORT).show();

                }
            }
        });
        return fz_View;
    }
    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

    private  void test()
    {
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String[] from ={"image","text"};
        int [] to = {R.id.fz_item_image,R.id.fz_item_text};
        sim_adapter = new SimpleAdapter(getContext(), data_list, R.layout.fz_item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
    }
}
