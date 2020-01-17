package t3.henu.left_library.YHQ_solve;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzwangchenyan on 2016/11/23.
 */
public class AppCache {
    private Context mContext;

    private final List<BillboardListInfo> mSongListInfos = new ArrayList<>();

    private AppCache() {
    }

   private static class SingletonHolder {
        private static AppCache sAppCache = new AppCache();
    }

    private static AppCache getInstance() {
        return SingletonHolder.sAppCache;
    }


    public static Context getContext() {
        return getInstance().mContext;
    }



    public static void updateNightMode(boolean on) {
        Resources resources = getContext().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        Configuration config = resources.getConfiguration();
        config.uiMode &= ~Configuration.UI_MODE_NIGHT_MASK;
        config.uiMode |= on ? Configuration.UI_MODE_NIGHT_YES : Configuration.UI_MODE_NIGHT_NO;
        resources.updateConfiguration(config, dm);
    }



    public static List<BillboardListInfo> getSongListInfos() {
        return getInstance().mSongListInfos;
    }

  
}
