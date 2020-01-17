package t3.henu.left_library.GYB_solve.RobotFragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import t3.henu.left_library.GYB_solve.RobotFragment.ChatMessage1.ChatMessage;
import t3.henu.left_library.GYB_solve.RobotFragment.tools.HttpUties;
import t3.henu.left_library.R;


public class ChatFragment extends Fragment {

    TextView textView;
    String str;
    private View rootView;
    private ListView recycleView;
    private LayoutInflater mInflater;
    private EditText editText;
    private List<ChatMessage> list_message = new ArrayList<ChatMessage>();
    private BaseAdapter chatMessageAdapter;
    Handler myhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            ChatMessage s = (ChatMessage) msg.obj;
            list_message.add(s);
            chatMessageAdapter.notifyDataSetChanged();
            recycleView.smoothScrollToPosition(list_message.size() - 1);
        }
    };
    private Button btn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.gyb_robotchat, container, false);
        initView();
        initDatas();
        return rootView;
    }

    private void initDatas() {
        ChatMessage ch = new ChatMessage();
        ch.setMsg("你好，我是小机器人枯瑜，可以陪你聊天哦，但要保持网络畅通哦！！");
        ch.setType(ChatMessage.Type.INCOMING);
        ch.setDate(new Date());
        if (list_message.size() <= 0) {
            list_message.add(ch);
        }

        chatMessageAdapter = new ChatMessageAdapter(getContext(), list_message);
        recycleView.setAdapter(chatMessageAdapter);
    }

    private void initView() {
        recycleView = (ListView) rootView.findViewById(R.id.id_main_recyleListView);
        editText = (EditText) rootView.findViewById(R.id.id_input);
        btn = (Button) rootView.findViewById(R.id.id_send);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_click(v);
            }
        });
    }

    public void btn_click(View v) {

        String toms = editText.getText().toString();
        if (TextUtils.isEmpty(toms)) {
            Toast.makeText(getContext(), "消息不能为空!!", Toast.LENGTH_SHORT).show();
            return;
        }
        ChatMessage tochat = new ChatMessage();
        tochat.setMsg(toms);
        tochat.setDate(new Date());
        tochat.setType(ChatMessage.Type.OUTCOMING);
        list_message.add(tochat);
        chatMessageAdapter.notifyDataSetChanged();
        recycleView.smoothScrollToPosition(list_message.size() - 1);
        new Thread() {
            @Override
            public void run() {
                ChatMessage cj = HttpUties.sendMessage(editText.getText().toString());
                Message ms = Message.obtain();

                ms.obj = cj;
                myhandler.sendMessage(ms);
            }
        }.start();
        editText.setText("");
    }

}
