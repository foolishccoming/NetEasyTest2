package t3.henu.left_library.GYB_solve.RobotFragment.tools;


import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

import t3.henu.left_library.GYB_solve.RobotFragment.ChatMessage1.ChatMessage;



public class HttpUties {

    private static final String URL1 = "http://www.tuling123.com/openapi/api";
    private static final String APIKEY = "688aedd361c14ca198f0f018d8f71877";


    //发送一个消息
    public static ChatMessage sendMessage(String messa) {
        ChatMessage chatMessage = new ChatMessage();
        String jsons = getAns(messa);
        Gson gson = new Gson();
        HTTPResult hr = null;
        try {
            hr = gson.fromJson(jsons, HTTPResult.class);
            chatMessage.setMsg(hr.getText());
            //System.out.println(jsons);
            //System.out.println(hr.getCode());
        } catch (Exception e) {
            chatMessage.setMsg("服务器繁忙");
        }
        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);
        return chatMessage;
    }

    //根据发送的消息得到回答
    public static String getAns(String msg) {
        String result = "";
        String url = getParams(msg);
        InputStream ins = null;
        ByteArrayOutputStream bs = null;
        try {
            URL urlNet = new URL(url);
            HttpURLConnection httpCon = (HttpURLConnection) urlNet.openConnection();
            httpCon.setReadTimeout(5 * 1000);
            httpCon.setConnectTimeout(5 * 1000);
            httpCon.setRequestMethod("GET");
            httpCon.setDoInput(true);
            httpCon.setUseCaches(false);

            ins = httpCon.getInputStream();
            // Log.e("LOG_MSG",",,,,result:"+ins);
            int len = -1;
            byte[] buf = new byte[128];
            bs = new ByteArrayOutputStream();

            while ((len = ins.read(buf)) != -1) {
                bs.write(buf, 0, len);
            }
            result = new String(bs.toByteArray());
            // System.out.println(result);
            //Log.e("LOG_MSG",",,,,result:"+result);
        } catch (MalformedURLException e) {
            result = "服务器繁忙";
            e.printStackTrace();
        } catch (IOException e) {
            result = "服务器繁忙";
            e.printStackTrace();
        } catch (Exception e) {
            result = ins + " " + bs + " " + e.getMessage();
        } finally {
            try {
                if (ins != null) {
                    ins.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (bs != null) {
                    bs.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private static String getParams(String msg) {
        String url = "";
        try {
            url = URL1 + "?key=" + APIKEY + "&info=" + URLEncoder.encode(msg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return url;
    }


}
