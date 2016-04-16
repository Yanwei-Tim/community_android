package com.otg.community.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.otg.community.activities.ResetPwdActivity;
import com.otg.community.utils.Md5Key;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;
import org.json.JSONObject;


/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 */
public class TuiZuHandler {
    private static final String TAG = "TuiZuHandler";
    public static final int RESULT_SUCCESS = 3;
    public static final int RESULT_ERROR = 4;
    private Context context;
    private Handler handler;

    public TuiZuHandler(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    void postData(String id){
        //修改密码

        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port",Server.admin_port);

        String url = "http://" + remote_ip + ":" + remote_port + "/TenantAction_exitByClient.action";
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"id", id}
        };

        try {
            JSONObject object = postUtils.postJsonObject(url, params);
            boolean flag = object.getBoolean("success");
            String msg = object.getString("msg");
            if(flag){
                Message message = new Message();
                message.what = TuiZuHandler.RESULT_SUCCESS;
                message.obj = msg;
                handler.sendMessage(message);
            }else {
                Message message = new Message();
                message.what = TuiZuHandler.RESULT_ERROR;
                message.obj = msg;
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = TuiZuHandler.RESULT_ERROR;
            message.obj = "退租失败";
            handler.sendMessage(message);
        }
    }

    public void handler(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(id);
            }
        }).start();
    }




}
