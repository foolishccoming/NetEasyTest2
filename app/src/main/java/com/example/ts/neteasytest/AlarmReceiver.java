package com.example.ts.neteasytest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by ts on 20-1-14.
 */

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 小于定时时间，重新启动定时服务
        if (AlarmService.MTIMECOUNT++ < AlarmService.MDURATION){
            Intent start = new Intent(context,AlarmService.class);
            context.startService(start);
        }
        else {
            // 除小于之外的情况，停止服务
            Intent stop = new Intent(context,AlarmService.class);
            context.stopService(stop);
            System.exit(0);
        }
        //在前三描提示
        if (AlarmService.MTIMECOUNT == AlarmService.MDURATION - 3){
            Toast.makeText(context,"服务即将关闭",Toast.LENGTH_LONG).show();
        }
    }
}
