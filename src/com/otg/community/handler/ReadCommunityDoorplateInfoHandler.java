/*
package com.otg.community.handler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import com.otg.community.activities.LardLordTenantActivity;
import com.otg.community.domain.LardLord;
import com.otg.community.domain.Tenant;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


*/
/**
 * 状态校验接口
 * <p/>
 * Created by yf on 2014-12-26.
 *//*

public class ReadCommunityDoorplateInfoHandler {
    private static final String TAG = "ReadCommunityDoorplateInfoHandler";
    private Context context;
    private Handler handler;

    public ReadCommunityDoorplateInfoHandler(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    void postData(String id){
        //修改密码
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port",Server.admin_port);

        String url = "http://" + remote_ip + ":" + remote_port + "/CommunityDoorplateAction_findById.action";
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"id", id}
        };

        try {
            JSONObject object = postUtils.postJsonObject(url, params);
            boolean flag = object.getBoolean("success");
            if(flag){
                List<LardLord> lardLordList = new ArrayList<>();
                List<Tenant> tenantList = new ArrayList<>();
                JSONArray lardlordsArray = object.getJSONArray("lardlords");
                JSONArray tenantsArray = object.getJSONArray("tenants");
                for (int i=0;i<lardlordsArray.length();i++){
                    JSONObject job = lardlordsArray.getJSONObject(i);
                    String lid = job.getString("id");
                    String name = job.getString("name");
                    String sex = job.getString("sex");
                    String idCard = job.getString("idCard");
                    String mz = job.getString("mz");
                    String birth = job.getString("birth");
                    String number = job.getString("number");
                    String sign = job.getString("sign");
                    String address = job.getString("address");
                    String DN = job.getString("DN");
                    String validity = job.getString("validity");
                    String company = job.getString("company");
                    String phone = job.getString("phone");
                    String xzz = job.getString("xzz");
                    String description = job.getString("description");
                    String status = job.getString("status");
                    String type = job.getString("type");
                    LardLord entity = new LardLord();
                    entity.setId(Long.parseLong(lid));
                    entity.setName(name);
                    entity.setSex(sex);
                    entity.setIdCard(idCard);
                    entity.setMz(mz);
                    entity.setBirth(birth);
                    entity.setNumber(number);
                    entity.setSign(sign);
                    entity.setAddress(address);
                    entity.setDN(DN);
                    entity.setValidity(validity);
                    entity.setCompany(company);
                    entity.setPhone(phone);
                    entity.setXzz(xzz);
                    entity.setDescription(description);
                    entity.setStatus(Integer.parseInt(status));
                    entity.setType(Integer.parseInt(type));
                    lardLordList.add(entity);
                }

                for (int i=0;i<tenantsArray.length();i++){
                    JSONObject job = tenantsArray.getJSONObject(i);
                    String lid = job.getString("id");
                    String name = job.getString("name");
                    String sex = job.getString("sex");
                    String idCard = job.getString("idCard");
                    String mz = job.getString("mz");
                    String birth = job.getString("birth");
                    String number = job.getString("number");
                    String sign = job.getString("sign");
                    String address = job.getString("address");
                    String DN = job.getString("DN");
                    String validity = job.getString("validity");
                    String company = job.getString("company");
                    String phone = job.getString("phone");
                    String xzz = job.getString("xzz");
                    String description = job.getString("description");
                    String status = job.getString("status");
                    Tenant entity = new Tenant();
                    entity.setId(Long.parseLong(lid));
                    entity.setName(name);
                    entity.setSex(sex);
                    entity.setIdCard(idCard);
                    entity.setMz(mz);
                    entity.setBirth(birth);
                    entity.setNumber(number);
                    entity.setSign(sign);
                    entity.setAddress(address);
                    entity.setDN(DN);
                    entity.setValidity(validity);
                    entity.setCompany(company);
                    entity.setPhone(phone);
                    entity.setXzz(xzz);
                    entity.setDescription(description);
                    entity.setStatus(Integer.parseInt(status));
                    tenantList.add(entity);
                }

                Message message = new Message();
                message.what = LardLordTenantActivity.RESULT_READ_LARDLORD_SUCCESS;
                message.obj = lardLordList;
                handler.sendMessage(message);

                Message message_tenant = new Message();
                message_tenant.what = LardLordTenantActivity.RESULT_READ_TENANT_SUCCESS;
                message_tenant.obj = tenantList;
                handler.sendMessage(message_tenant);


                message.obj = tenantList;
            }else {
                Message message = new Message();
                message.what = LardLordTenantActivity.RESULT_READ_ERROR;
                message.obj = "查询用户信息失败";
                handler.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Message message = new Message();
            message.what = LardLordTenantActivity.RESULT_READ_ERROR;
            message.obj = "请求用户数据失败";
            handler.sendMessage(message);
        }
    }

    public void handler(final String phone) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                postData(phone);
            }
        }).start();
    }




}
*/
