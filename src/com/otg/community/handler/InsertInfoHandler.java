package com.otg.community.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import com.otg.community.domain.LardLord;
import com.otg.community.domain.Tenant;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;
import org.json.JSONObject;
import it.sauronsoftware.base64.Base64;


/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 */
public class InsertInfoHandler {
    private static final String TAG = "InsertInfoHandler";
    public static final int RESULT_INSERT_SUCCESS = 201;
    public static final int RESULT_INSERT_ERROR = 202;
    private Context context;
    private Handler handler;

    public InsertInfoHandler(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    void postData(LardLord lardLord, String idx) {
        //修改密码
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port", Server.admin_port);
        String url = "http://" + remote_ip + ":" + remote_port + "/LardLordAction_insertByClient.action";
        PostUtils postUtils = new PostUtils();
        String[][] params = null;

        if (lardLord.getOcr() == 1) {
            String before = null;
            byte[] beforebytes =  lardLord.getCardPicBefore();
            if(beforebytes!=null){
                before = new String(Base64.encode(beforebytes));
            }
            params = new String[][]{
                    {"lardLord.phone", lardLord.getPhone().trim()},
                    {"lardLord.ocr", String.valueOf(lardLord.getOcr())},
                    {"idCard_before", before},
                    {"lardLord.xzz", lardLord.getXzz()},
                    {"qrCode", idx}
            };
        } else {
            String b = null;
            byte[] bytes = lardLord.getBytes();
            if (bytes != null) {
                b = new String(Base64.encode(lardLord.getBytes()));
            }

            params = new String[][]{
                    {"lardLord.name", lardLord.getName().trim()},
                    {"lardLord.idCard", lardLord.getIdCard().trim()},
                    {"lardLord.sex", lardLord.getSex().trim()},
                    {"lardLord.mz", lardLord.getMz().trim()},
                    {"lardLord.birth", lardLord.getBirth().trim()},
                    {"lardLord.address", lardLord.getAddress().trim()},
                    {"lardLord.sign", lardLord.getSign().trim()},
                    {"lardLord.validity", lardLord.getValidity().trim()},
                    {"lardLord.phone", lardLord.getPhone().trim()},
                    {"lardLord.DN", lardLord.getDN().trim()},
                    {"lardLord.ocr", String.valueOf(lardLord.getOcr())},
                    {"lardLord.xzz", lardLord.getXzz()},
                    {"bytes", b},
                    {"qrCode", idx}
            };
        }

        try {
            JSONObject object = postUtils.postJsonObject(url, params);
            boolean flag = object.getBoolean("success");
            if (flag) {
                Message message = new Message();
                message.what = RESULT_INSERT_SUCCESS;
                message.obj = object.getString("msg");
                handler.sendMessage(message);
            } else {
                Message message = new Message();
                message.what = RESULT_INSERT_ERROR;
                message.obj = object.getString("msg");
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = RESULT_INSERT_ERROR;
            message.obj = "新增户主失败";
            handler.sendMessage(message);
        }
    }





    void postData(Tenant tenant, String idx) {
        //修改密码
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port", Server.admin_port);
        String url = "http://" + remote_ip + ":" + remote_port + "/TenantAction_insertByClient.action";
        String[][] params = null;
        PostUtils postUtils = new PostUtils();

        if (tenant.getOcr() == 1) {
            String before = null;
            byte[] beforebytes =  tenant.getCardPicBefore();
            if(beforebytes!=null){
                before = new String(Base64.encode(beforebytes));
            }
            params = new String[][]{
                    {"tenant.phone", tenant.getPhone().trim()},
                    {"tenant.ocr", String.valueOf(tenant.getOcr())},
                    {"idCard_before", before},
                    {"room", String.valueOf(tenant.getRoom())},
                    {"qrCode", idx}
            };
        } else {
            String b = null;
            byte[] bytes = tenant.getBytes();
            if (bytes != null) {
                b = new String(Base64.encode(tenant.getBytes()));
            }

            params = new String[][]{
                    {"tenant.name", tenant.getName().trim()},
                    {"tenant.idCard", tenant.getIdCard().trim()},
                    {"tenant.sex", tenant.getSex().trim()},
                    {"tenant.mz", tenant.getMz().trim()},
                    {"tenant.birth", tenant.getBirth().trim()},
                    {"tenant.address", tenant.getAddress().trim()},
                    {"tenant.sign", tenant.getSign().trim()},
                    {"tenant.validity", tenant.getValidity().trim()},
                    {"tenant.phone", tenant.getPhone().trim()},
                    {"tenant.DN", tenant.getDN().trim()},
                    {"tenant.ocr", String.valueOf(tenant.getOcr())},
                    {"room", String.valueOf(tenant.getRoom())},
                    {"bytes", b},
                    {"qrCode", idx}
            };
        }
        if (params == null) {
            Log.e("InsertHandler", "params is null");
        } else {
            Log.e("InsertHandler", params[0][0]+params[0][1]);
        }


        try {
            JSONObject object = postUtils.postJsonObject(url, params);
            boolean flag = object.getBoolean("success");
            if (flag) {
                Message message = new Message();
                message.what = RESULT_INSERT_SUCCESS;
                message.obj = object.getString("msg");
                handler.sendMessage(message);
            } else {
                Message message = new Message();
                message.what = RESULT_INSERT_ERROR;
                message.obj = object.getString("msg");
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = RESULT_INSERT_ERROR;
            message.obj = "新增租客失败";
            handler.sendMessage(message);
        }
    }

    public void handler(final LardLord lardLord, final String idx) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(lardLord, idx);
            }
        }).start();
    }

    public void handler(final Tenant tenant, final String idx) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(tenant, idx);
            }
        }).start();
    }


}
