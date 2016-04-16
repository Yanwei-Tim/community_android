package com.otg.community.request;

import android.content.Context;
import android.os.Handler;
import android.telephony.TelephonyManager;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 */
public class PostRequest {
    private Handler handler;
    private String ip = null;
    private String port = null;
    private Context context;
    private String r;
    TelephonyManager telephonyManager = null;

    public PostRequest(Context context, String ip,String r, String port) {
        handler = new Handler(context.getMainLooper());
        this.context = context;
        this.ip = ip;
        this.r = r;
        this.port = port;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
    }

    public interface OnPostListener {
        public void onPostOk(String msg);

        public void onPostErr(String msg);
    }

    public boolean post(String url,String r) throws Exception {
        HttpPost httpRequest = new HttpPost(url);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("info", r.trim()));
        httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
        HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String strResult = EntityUtils.toString(httpResponse.getEntity());
            JSONObject result = new JSONObject(strResult);
            boolean flag = result.getBoolean("success");
            return flag;
        }
        return false;
    }

    public void postData(final OnPostListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String url = "http://" + ip + ":"+port+"/ExpressRealNameAction_upload.action";
                boolean flag = false;
                try {
                    flag = post(url,r);
                    if (flag) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onPostOk("保存快递实名信息成功");
                            }
                        });
                    } else {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                listener.onPostErr("保存快递实名信息失败");
                            }
                        });
                    }
                } catch (Exception e1) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onPostErr("请求服务器异常！");
                        }
                    });
                }
            }
        }).start();
    }
}
