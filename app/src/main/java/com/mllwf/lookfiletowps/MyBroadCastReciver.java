package com.mllwf.lookfiletowps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.mllwf.lookfiletowps.WpsModel.Reciver;


public class MyBroadCastReciver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Reciver.ACTION_BACK)) {//返回键广播
            System.out.println(Reciver.ACTION_BACK);
        } else if (action.equals(Reciver.ACTION_CLOSE)) {//关闭文件时候的广播
            System.out.println(Reciver.ACTION_CLOSE);
        } else if (action.equals(Reciver.ACTION_HOME)) {//home键广播
            System.out.println(Reciver.ACTION_HOME);
        } else if (action.equals(Reciver.ACTION_SAVE)) {//保存广播
            System.out.println(Reciver.ACTION_SAVE);
        }
    }
}
