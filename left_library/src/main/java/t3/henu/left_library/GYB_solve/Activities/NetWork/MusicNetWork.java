package t3.henu.left_library.GYB_solve.Activities.NetWork;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * 音乐网络类

 */

public class MusicNetWork {


    public static JSONObject json = null;
    private static RequestQueue mRequestqueue;

	public static RequestQueue getmRequestqueue(Context context)
	{
		if(mRequestqueue == null)
		{
			mRequestqueue = Volley.newRequestQueue(context);
			return mRequestqueue;
		}
		else{
			return mRequestqueue;
		}
	}

    /**
	 * 网易音乐搜索API
	 * http://s.music.163.com/search/get/
	 * 获取方式：GET
	 * 参数：
	 * src: lofter //可为空
	 * type: 1
	 * filterDj: true|false //可为空
	 * s: //关键词
	 * limit: 10 //限制返回结果数
	 * offset: 0 //偏移
	 * callback: //为空时返回json，反之返回jsonp callback
	 * @param
	 * @param context
	 * @return
	 * 注意废数字才用‘’符号，要不不能用，否则出错！！
	 */



	public static void SearchMusic(final Context context, String s, int limit, int type, int offset, final VolleyCallback callback){

		String url = UrlConstants.CLOUD_MUSIC_API_SEARCH + "type="+type+"&s='" + s + "'&limit="+limit+"&offset="+offset;
		RequestQueue requestQueue = getmRequestqueue(context);
		JsonObjectRequest jsonRequest = new JsonObjectRequest(url,null,new Response.Listener<JSONObject>(){
			@Override
			public void onResponse(JSONObject s){
				Log.i("onResponse: ",s.toString());
				callback.onSuccess(s.toString());
			}
		},new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError volleyError){
				Log.i("onResponse: ",volleyError.toString(),volleyError);
				toast(context,"获取失败，或无网络连接");
			}
		});
		requestQueue.add(jsonRequest);

	}

	private static void toast(Context c,String s) {
		Toast.makeText(c,s,Toast.LENGTH_SHORT).show();
	}

	/**
	 * 网易云音乐歌曲信息API
	 * @param context
	 * @param id 歌曲id
	 * @param ids 用[]包裹起来的歌曲id 写法%5B %5D
	 * @return
	 */
	public static void Cloud_Music_MusicInfoAPI(final Context context, String id, String ids, final VolleyCallback call)
	{
		String url = UrlConstants.CLOUD_MUSIC_API_MUSICINGO + "id="+id+"&ids=%5B"+ids+"%5D";
		RequestQueue requestQueue = getmRequestqueue(context);
		StringRequest straingRequest = new StringRequest(url,new Response.Listener<String>(){
			@Override
			public void onResponse(String s){
				try {
					JSONObject json = new JSONObject(s);
					call.onSuccess(s);
					Log.i("onResponse: ",json.toString());
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		},new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError volleyError){
				Log.i("onResponse: ",volleyError.toString());
				toast(context,"获取失败，或无网络连接");
			}
		});
		requestQueue.add(straingRequest);
	}

	/**
	 * 获取歌曲歌词的API
	 *URL：

	 GET http://music.163.com/api/song/lyric

	 必要参数：

	 id：歌曲ID

	 lv：值为-1，我猜测应该是判断是否搜索lyric格式

	 kv：值为-1，这个值貌似并不影响结果，意义不明

	 tv：值为-1，是否搜索tlyric格式
	 * @param context
	 * @param os
	 * @param id
	 */
	public static void Cloud_Muisc_getLrcAPI(final Context context, String os, String id, final VolleyCallback callback)
	{
		String url = UrlConstants.CLOUD_MUSIC_API_MUSICLRC + "os="+os+"&id="+id+"&lv=-1&kv=-1&tv=-1";
		RequestQueue requestQueue = getmRequestqueue(context);
		StringRequest straingRequest = new StringRequest(url,new Response.Listener<String>(){
			@Override
			public void onResponse(String s){
				try {
					JSONObject json = new JSONObject(s);
					Log.i("onResponse: ",json.toString());
					callback.onSuccess(json.toString());
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		},new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError volleyError){
				Log.i("onResponse: ",volleyError.toString());
				toast(context,"获取失败，或无网络连接");
			}
		});
		requestQueue.add(straingRequest);

	}

	public static JSONObject getInfoFromUrl_Volley(String url, final Context context)
	{
		json = null;
		RequestQueue requestQueue = getmRequestqueue(context);
		StringRequest straingRequest = new StringRequest(url,new Response.Listener<String>(){
			@Override
			public void onResponse(String s){
				try {
					json = new JSONObject(s);
					Log.i("onResponse: ",json.toString());
				} catch(JSONException e) {
					e.printStackTrace();
				}
			}
		},new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError volleyError){
				Log.i("onResponse: ",volleyError.toString());
				toast(context,"获取失败，或无网络连接");
			}
		});
		requestQueue.add(straingRequest);
		return json;
	}

    public interface VolleyCallback {
        void onSuccess(String result);
    }

	public class UrlConstants {
		/**
		 * 云音乐搜索API网址
		 */
		public static final String CLOUD_MUSIC_API_SEARCH = "http://s.music.163.com/search/get/?";
		/**
		 * 歌曲信息API网址
		 */
		public static final String CLOUD_MUSIC_API_MUSICINGO = "http://music.163.com/api/song/detail/?";
		/**
		 * 获取歌曲的歌词
		 */
		public static final String CLOUD_MUSIC_API_MUSICLRC = "http://music.163.com/api/song/lyric?";
		/**
		 * 获取歌手专辑信息
		 */

		public static final String CLOUD_MUSIC_API_SINGER = "http://music.163.com/api/artist/albums/";
	}

}

