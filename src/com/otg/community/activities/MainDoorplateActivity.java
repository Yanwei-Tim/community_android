package com.otg.community.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.*;
import android.widget.*;
import com.otg.community.R;
import com.otg.community.adapter.DoorplateAdapter;
import com.otg.community.domain.*;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;
import com.otg.community.utils.Toaster;
import com.otg.community.view.ScrollListviewDelete;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainDoorplateActivity extends Activity implements View.OnClickListener {

    private TextView tv_community_ssxq;
    private TextView tv_community_address;
    private TextView tv_community_doorplate;
    private Button bt_sys;
    private String idx = null;

    private DoorplateAdapter adapter = null;
    private ScrollListviewDelete listView = null;
    private List<Object> list = new ArrayList<Object>();
    private List<String> listTag = new ArrayList<String>();
    //定义进度条
    private ProgressDialog dia;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_admin:
                startActivity(new Intent(MainDoorplateActivity.this, SetAdminActivity.class));
                return true;
            case R.id.action_menu_otg:
                startActivity(new Intent(MainDoorplateActivity.this, SetOtgActivity.class));
                return true;
        }
        return false;
    }

    /**
     * 设置menu显示icon
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null) {
            if (menu.getClass().getSimpleName().equals("MenuBuilder")) {
                try {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_community_doorplate);
        Intent intent = getIntent();
        idx = intent.getStringExtra("idx");
//        idx = "CXSQRK-330108-1-12-1-1";

        if (idx != null) {
            tv_community_doorplate = (TextView) findViewById(R.id.community_doorplate);
            bt_sys = (Button) findViewById(R.id.sys);
            bt_sys.setOnClickListener(this);
            tv_community_address = (TextView) findViewById(R.id.community_address);
            tv_community_ssxq = (TextView) findViewById(R.id.community_ssqx);

            listView = (ScrollListviewDelete) findViewById(R.id.community_doorplate_list);

            Button add_lardlord = (Button) findViewById(R.id.add_lardlord);
            add_lardlord.setOnClickListener(this);
            Button add_tenant = (Button) findViewById(R.id.add_tenant);
            add_tenant.setOnClickListener(this);

            dia = new ProgressDialog(this);
            dia.setTitle("信息");
            dia.setMessage("正在读取...");
            dia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dia.setCancelable(false);

        } else {
            Toaster.showLong(getApplicationContext(), "请重新扫描二维码信息");
            this.finish();
        }
    }

    /**
     * 实现网络的异步访问
     *
     * @author Administrator
     */
    //Sting 表示传入的值， Integer  代表进度 ，  Bitmap 代表返回的值
    class CommunityDoorplateAsyncTask extends AsyncTask<String, Void, CommunityDoorplate> {

        //任务执行之前的准备工作。
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            dia.show();
        }

        @Override
        protected CommunityDoorplate doInBackground(String... params) {
            return getDatas(params[0]);
        }

        //主要完成耗时操作
        @Override
        protected void onPostExecute(CommunityDoorplate b) {
            super.onPostExecute(b);
            dia.dismiss();
            if (b != null) {
                tv_community_doorplate.setText(b.getCommunityBuildUnit().getCommunityBuild().getCommunity().getName() +
                        b.getCommunityBuildUnit().getCommunityBuild().getValue() + "幢" +
                        b.getCommunityBuildUnit().getValue() + "单元" +
                        b.getDoorplate() + "室");
                tv_community_address.setText(b.getCommunityBuildUnit().getCommunityBuild().getCommunity().getAddress());
                tv_community_ssxq.setText(b.getCommunityBuildUnit().getCommunityBuild().getCommunity().getSsxq());

                adapter = new DoorplateAdapter(MainDoorplateActivity.this, list, listTag);
                listView.setAdapter(adapter);
            } else {
                Toaster.show(MainDoorplateActivity.this, "加载数据异常.....");
            }
        }
    }

    public void setData(List<LardLord> lardLordList, List<Tenant> tenantList) {
        list.add("房东信息:");
        listTag.add("房东信息:");
        for (int i = 0; i < lardLordList.size(); i++) {
            LardLord lardLord = lardLordList.get(i);
            list.add(lardLord);
        }
        list.add("租客信息:");
        listTag.add("租客信息:");
        for (int i = 0; i < tenantList.size(); i++) {
            Tenant lardLord = tenantList.get(i);
            list.add(lardLord);
        }
    }

    /**
     * 获取制定的 json 数据转换成我们需要的 CommunityDoorplate 对象
     *
     * @return
     */
    public CommunityDoorplate getDatas(String id) {
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port", Server.admin_port);
        String url = "http://" + remote_ip + ":" + remote_port + "/CommunityDoorplateAction_findByClient.action";
        PostUtils postUtils = new PostUtils();

        String[][] params = new String[][]{
                {"id", id}
        };
        CommunityDoorplate communityDoorplate = null;
        try {
            JSONObject object = postUtils.postJsonObject(url, params);
            boolean flag = object.getBoolean("success");
            if (flag) {
                long dId = object.getLong("id");
                long community_id = object.getLong("community_id");
                String community_name = object.getString("community_name");
                String community_address = object.getString("community_address");
                int community_build = object.getInt("community_build");
                int community_build_unit = object.getInt("community_build_unit");
                String community_ssxq = object.getString("community_ssxq");
                String community_principal = object.getString("community_principal");
                String community_principal_phone = object.getString("community_principal_phone");
                int doorplate = object.getInt("doorplate");

                Community community = new Community();
                community.setId(community_id);
                community.setName(community_name);
                community.setAddress(community_address);
                community.setSsxq(community_ssxq);
                community.setPrincipal(community_principal);
                community.setPrincipal_phone(community_principal_phone);

                CommunityBuild communityBuild = new CommunityBuild();
                communityBuild.setValue(community_build);
                communityBuild.setCommunity(community);

                CommunityBuildUnit communityBuildUnit = new CommunityBuildUnit();
                communityBuildUnit.setValue(community_build_unit);
                communityBuildUnit.setCommunityBuild(communityBuild);

                communityDoorplate = new CommunityDoorplate();
                communityDoorplate.setDoorplate(doorplate);
                communityDoorplate.setId(dId);

                communityDoorplate.setCommunityBuildUnit(communityBuildUnit);

                List<LardLord> lardLordList = new ArrayList<>();
                List<Tenant> tenantList = new ArrayList<>();
                JSONArray lardlordsArray = object.getJSONArray("lardLords");
                JSONArray tenantsArray = object.getJSONArray("tenants");
                for (int i = 0; i < lardlordsArray.length(); i++) {
                    JSONObject job = lardlordsArray.getJSONObject(i);
                    String lid = job.getString("id");
                    String name = job.getString("name");
                    String sex = job.getString("sex");
                    String idCard = job.getString("idCard");
                    String mz = job.getString("mz");
                    String birth = job.getString("birth");
                    String sign = job.getString("sign");
                    String address = job.getString("address");
                    String DN = job.getString("DN");
                    String validity = job.getString("validity");
                    String phone = job.getString("phone");
                    String xzz = job.getString("xzz");
                    String description = job.getString("description");
                    String status = job.getString("status");
                    LardLord entity = new LardLord();
                    entity.setId(Long.parseLong(lid));
                    entity.setName(name);
                    entity.setSex(sex);
                    entity.setIdCard(idCard);
                    entity.setMz(mz);
                    entity.setBirth(birth);
                    entity.setSign(sign);
                    entity.setAddress(address);
                    entity.setDN(DN);
                    entity.setValidity(validity);
                    entity.setPhone(phone);
                    entity.setXzz(xzz);
                    entity.setDescription(description);
                    entity.setStatus(Integer.parseInt(status));
                    lardLordList.add(entity);
                }

                for (int i = 0; i < tenantsArray.length(); i++) {
                    JSONObject job = tenantsArray.getJSONObject(i);
                    String lid = job.getString("id");
                    String name = job.getString("name");
                    String sex = job.getString("sex");
                    String idCard = job.getString("idCard");
                    String mz = job.getString("mz");
                    String birth = job.getString("birth");
                    String sign = job.getString("sign");
                    String address = job.getString("address");
                    String DN = job.getString("DN");
                    String validity = job.getString("validity");
                    String phone = job.getString("phone");
                    int room = job.getInt("room");
                    String description = job.getString("description");
                    String status = job.getString("status");
                    Tenant entity = new Tenant();
                    entity.setId(Long.parseLong(lid));
                    entity.setName(name);
                    entity.setSex(sex);
                    entity.setIdCard(idCard);
                    entity.setMz(mz);
                    entity.setBirth(birth);
                    entity.setSign(sign);
                    entity.setAddress(address);
                    entity.setDN(DN);
                    entity.setValidity(validity);
                    entity.setPhone(phone);
                    entity.setRoom(room);
                    entity.setDescription(description);
                    entity.setStatus(Integer.parseInt(status));
                    tenantList.add(entity);
                }
                list.clear();
                listTag.clear();
                setData(lardLordList, tenantList);
            }
        } catch (Exception e) {
            return null;
        }
        return communityDoorplate;
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new CommunityDoorplateAsyncTask().execute(String.valueOf(idx));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_lardlord:
                Intent i = new Intent(MainDoorplateActivity.this, LardLordInsertActivity.class);
                i.putExtra("idx", idx);
                startActivity(i);
                break;
            case R.id.add_tenant:
                Intent ii = new Intent(MainDoorplateActivity.this, TenantInsertActivity.class);
                ii.putExtra("idx", idx);
                startActivity(ii);
                break;
            case R.id.sys:
                startActivity(new Intent(MainDoorplateActivity.this, MainFragmentActivity.class));
                break;
            default:
                break;
        }
    }

}



