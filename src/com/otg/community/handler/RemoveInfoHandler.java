/*
package com.otg.community.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.otg.community.activities.LardLordTenantActivity;
import com.otg.community.domain.CommunityDoorplate;
import com.otg.community.domain.LardLord;
import com.otg.community.domain.Tenant;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;
import org.json.JSONObject;


*/
/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 *//*

public class RemoveInfoHandler {
    private static final String TAG = "RemoveLardLordInfoHandler";
    public static final int RESULT_REMOVE_SUCCESS = 101;
    public static final int RESULT_REMOVE_ERROR = 102;
    private Context context;
    private Handler handler;

    public RemoveInfoHandler(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    void postData(CommunityDoorplate communityDoorplate,String id,int pos){
        //修改密码
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port",Server.admin_port);
        String url = null;
        if(communityDoorplate.getType()==1) {
            url = "http://" + remote_ip + ":" + remote_port + "/LardLordAction_remove.action";
        }else {
            url = "http://" + remote_ip + ":" + remote_port + "/TenantAction_remove.action";
        }
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"id", id}
        };

        try {
            JSONObject object = postUtils.postJsonObject(url, params);
            boolean flag = object.getBoolean("success");
            if(flag){
                Message message = new Message();
                message.what = RESULT_REMOVE_SUCCESS;
                message.obj = pos;
                handler.sendMessage(message);
            }else {
                Message message = new Message();
                message.what = RESULT_REMOVE_ERROR;
                message.obj = "删除信息失败";
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = RESULT_REMOVE_ERROR;
            message.obj = "删除信息失败";
            handler.sendMessage(message);
        }
    }

    public void handler(final CommunityDoorplate communityDoorplate, final String id, final int pos) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(communityDoorplate,id,pos);
            }
        }).start();
    }




}
*/
