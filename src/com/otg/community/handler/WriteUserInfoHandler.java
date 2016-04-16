package com.otg.community.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.otg.community.activities.UserInfoActivity;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;


/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 */
public class WriteUserInfoHandler {
    private static final String TAG = "WriteUserInfoHandler";
    private Context context;
    private Handler handler;

    public WriteUserInfoHandler(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    void postData(String phone,String idCard,String name,String number){
        //修改密码

        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port",Server.admin_port);

        String url = "http://" + remote_ip + ":" + remote_port + "/UserAction_modifyUser.action";
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"phone", phone},
                {"idCard", idCard},
                {"name", name},
                {"number", number}
        };

        try {
            boolean flag = postUtils.post(url,params);
            if(flag){
                Message message = new Message();
                message.what = UserInfoActivity.RESULT_WRITE_SUCCESS;
                message.obj = "修改成功";
                handler.sendMessage(message);
            }else {
                Message message = new Message();
                message.what = UserInfoActivity.RESULT_WRITE_ERROR;
                message.obj = "修改失败";
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = UserInfoActivity.RESULT_WRITE_ERROR;
            message.obj = "修改失败";
            handler.sendMessage(message);
        }
    }

    public void handler(final String phone, final String idCard, final String name, final String number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(phone,idCard,name,number);
            }
        }).start();
    }




}
