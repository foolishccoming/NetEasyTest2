package t3.henu.left_library.GYB_solve.Activities.NetWork;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.GYB_solve.Activities.MusicInfo.MusicResponse;
import t3.henu.left_library.GYB_solve.Activities.MusicInfo.Result;
import t3.henu.left_library.GYB_solve.Activities.MusicInfo.Song;
import t3.henu.left_library.GYB_solve.All_View;
import t3.henu.left_library.GYB_solve.Collect;
import t3.henu.left_library.GYB_solve.MainActivity;
import t3.henu.left_library.R;


public class SearchActivity extends MainActivity {

    private static final String TAG = "SearchActivity";
    private static final int DO_SEARCH = 1;
    private EditText etSearchContent;
    private TextView tvSearch;
    private ListView mListViewResult;
    private ListView mListViewHistory;
    private ArrayAdapter<String> historyAdapter;
    private LinearLayout llHistory;
    private LinearLayout llResult;
    private All_View all_view=null;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            SearchSong();//开始搜索操作  请求网络数据
        }
    };

    private void SearchSong() {
        String text = etSearchContent.getText().toString();
        if (TextUtils.isEmpty(text)) {
            return;
        }
        MusicNetWork.SearchMusic(this,text,100,1,0, new MusicNetWork.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                ParaseResult(result);
            }
        });

    }

    private void ParaseResult(String result) {
        Gson gson=new Gson();
        MusicResponse music_res=gson.fromJson(result,MusicResponse.class);
        Result re=music_res.getResult();
        if(re==null){
            Toast.makeText(getBaseContext(),"没有找到记录",Toast.LENGTH_SHORT).show();
            return;
        }
        final List<String> list=new ArrayList<String>();
        final List<Song> list_song=re.getSongs();
        if(list_song!=null&&list_song.size()>0){
            for(int i=0;i<list_song.size();i++){
                list.add(list_song.get(i).getName());
            }
            ArrayAdapter adapter=new ArrayAdapter(SearchActivity.this,android.R.layout.simple_list_item_1,
                    list);
            mListViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(SearchActivity.this,SearchResult.class);
                    intent.putExtra("music_name",list.get(position));
                    intent.putExtra("music_id",list_song.get(position).getId());
                    startActivity(intent);
                }
            });
            mListViewResult.setAdapter(adapter);
        }else{
            Toast.makeText(getBaseContext(),"没有找到记录",Toast.LENGTH_SHORT).show();
        }

    }

    private void toast(String s) {
        Toast.makeText(getBaseContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gyb_activity_search);
        initViews();
        all_view=new All_View(SearchActivity.t_singer,SearchActivity.t_songname,
                SearchActivity.imageView,SearchActivity.btn_play);
        Collect.addView(all_view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Collect.removeView(all_view);
    }

    public void initViews() {
//		vsNetError = (ViewStub) findViewById(R.id.vs_net_error);
//		vsBlankContent = (ViewStub) findViewById(R.id.vs_blank_content);


        etSearchContent = (EditText) findViewById(R.id.et_search_content);
        llHistory = (LinearLayout) findViewById(R.id.ll_search_history);
        llResult = (LinearLayout) findViewById(R.id.ll_search_result);
        mListViewHistory = (ListView) findViewById(R.id.lv_search_history);
        mListViewResult = (ListView) findViewById(R.id.lv_search_result);
        etSearchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {//相关课程listview隐藏 搜索历史显示
                    llResult.setVisibility(View.GONE);
                    llHistory.setVisibility(View.VISIBLE);
                } else {//相关课程listview显示 搜索历史隐藏
                    if (llHistory.getVisibility() == View.VISIBLE) {
                        llHistory.setVisibility(View.GONE);
                        llResult.setVisibility(View.VISIBLE);
                    }

                }

                mHandler.sendEmptyMessageDelayed(DO_SEARCH,1000);//延迟搜索，在用户输入的时候就进行搜索，但是考虑到用户流量问题，延迟一秒
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0) {

                }
            }
        });

       // initSearchHistory();
    }

    private void initSearchHistory() {
       // String cache = SearchHistoryCacheUtils.getCache(SearchActivity.this);
       /* if (cache != null) {
            List<String> historyRecordList = new ArrayList<>();
            for (String record : cache.split(",")) {
                historyRecordList.add(record);
            }
            historyAdapter = new ArrayAdapter<String>(SearchActivity.this,
                    R.layout.item_search_history, historyRecordList);
            if (historyRecordList.size() > 0) {
                mListViewHistory.setAdapter(historyAdapter);
                mListViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        etSearchContent.setText("");
                        etSearchContent.setText(historyAdapter.getItem(position));
                    }
                });
            }
        } else {
            llHistory.setVisibility(View.GONE);
        }*/

    }

    public void ClearSearchHistory(View view) {
        //SearchHistoryCacheUtils.ClearCache(SearchActivity.this);
       // updateData();
    }


    public void retuen(View v){
        this.finish();
    }
}
