/*
package com.otg.community.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.otg.community.activities.LardLordActivity;
import com.otg.community.activities.LardLordTenantActivity;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;
import org.json.JSONObject;


*/
/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 *//*

public class ReadLardLordInfoHandler {
    private static final String TAG = "ReadLardLordInfoHandler";
    private Context context;
    private Handler handler;

    public ReadLardLordInfoHandler(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    void postData(String id,int start,int limit){
        //修改密码
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port",Server.admin_port);

        String url = "http://" + remote_ip + ":" + remote_port + "/LardLordAction_findByParent.action";
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"id", id},
                {"start", String.valueOf(start)},
                {"limit", String.valueOf(limit)},
        };

        try {
            JSONObject object = postUtils.postJsonObject(url, params);
            Message message = new Message();
            message.what = LardLordActivity.RESULT_READ_SUCCESS;
            message.obj = object;
            handler.sendMessage(message);
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = LardLordActivity.RESULT_READ_ERROR;
            message.obj = "请求房东数据失败";
            handler.sendMessage(message);
        }
    }

    public void handler(final String id, final int start, final int limit) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(id,start,limit);
            }
        }).start();
    }




}
*/
