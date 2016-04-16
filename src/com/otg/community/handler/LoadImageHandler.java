package com.otg.community.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;

import java.io.IOException;
import java.io.InputStream;


/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 */
public class LoadImageHandler {
    private static final String TAG = "LoadImageHandler";
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_ERROR = 2;
    private Context context;
    private Handler handler;

    public LoadImageHandler(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    void postDataLardLord(String id){
        //修改密码

        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port",Server.admin_port);

        String url = "http://" + remote_ip + ":" + remote_port + "/LardLordAction_loadLardHead.action";
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"id", id}
        };

        InputStream object = null;
        try {
            object = postUtils.postInputStream(url, params);
            if(object!=null){
                Bitmap bitmap = BitmapFactory.decodeStream(object);
                Message message = new Message();
                message.what = LoadImageHandler.RESULT_SUCCESS;
                message.obj = bitmap;
                handler.sendMessage(message);
            }else {
                Message message = new Message();
                message.what = LoadImageHandler.RESULT_ERROR;
                message.obj = "读取图片失败";
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = LoadImageHandler.RESULT_ERROR;
            message.obj = "读取图片失败";
            handler.sendMessage(message);
        }finally {
            if(object!=null)
                try {
                    object.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    void postDataTenant(String id){
        //修改密码

        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port",Server.admin_port);

        String url = "http://" + remote_ip + ":" + remote_port + "/TenantAction_loadTenantHead.action";
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"id", id}
        };
        InputStream object = null;
        try {
             object = postUtils.postInputStream(url, params);
            if(object!=null){
                Bitmap bitmap = BitmapFactory.decodeStream(object);
                Message message = new Message();
                message.what = LoadImageHandler.RESULT_SUCCESS;
                message.obj = bitmap;
                handler.sendMessage(message);
            }else {
                Message message = new Message();
                message.what = LoadImageHandler.RESULT_ERROR;
                message.obj = "读取图片失败";
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = LoadImageHandler.RESULT_ERROR;
            message.obj = "读取图片失败";
            handler.sendMessage(message);
        }finally {
            if(object!=null)
                try {
                    object.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public void handlerLardLord(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postDataLardLord(id);
            }
        }).start();
    }


    public void handlerTenant(final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postDataTenant(id);
            }
        }).start();
    }

}
