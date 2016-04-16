package com.otg.community.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import com.otg.community.R;
import com.otg.community.adapter.DoorplateAdapter;
import com.otg.community.domain.*;
import com.otg.community.utils.PostUtils;
import com.otg.community.utils.Server;
import com.otg.community.utils.Toaster;
import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainRoomActivity extends Activity implements View.OnClickListener {

    private TextView tv_community_ssxq;
    private TextView tv_community_address;
    private TextView tv_community_doorplate;
    private Button bt_sys;
    private String idx = null;

    private DoorplateAdapter adapter = null;
    private ListView listView = null;
    private List<Object> list = new ArrayList<Object>();
    private List<String> listTag = new ArrayList<String>();
    //定义进度条
    private ProgressDialog dia;
    private Context context;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_admin:
                startActivity(new Intent(MainRoomActivity.this, SetAdminActivity.class));
                return true;
            case R.id.action_menu_otg:
                startActivity(new Intent(MainRoomActivity.this, SetOtgActivity.class));
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
        setContentView(R.layout.layout_community_room);
        this.context = this;
        Intent intent = getIntent();
        idx = intent.getStringExtra("idx");
//        idx = "CXSQRK-330108-1-12-1-1-1";
        if (idx != null) {
            tv_community_doorplate = (TextView) findViewById(R.id.community_doorplate);
            bt_sys = (Button) findViewById(R.id.sys);
            bt_sys.setOnClickListener(this);
            tv_community_address = (TextView) findViewById(R.id.community_address);
            tv_community_ssxq = (TextView) findViewById(R.id.community_ssqx);

            listView = (ListView) findViewById(R.id.community_doorplate_list);

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
    class CommunityDoorplateAsyncTask extends AsyncTask<String, Void, CommunityRoom> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dia.show();
        }

        @Override
        protected CommunityRoom doInBackground(String... params) {
            return getDatas(params[0]);
        }

        @Override
        protected void onPostExecute(CommunityRoom b) {
            super.onPostExecute(b);
            dia.dismiss();
            if (b != null) {
                tv_community_doorplate.setText(b.getCommunityDoorplate().getCommunityBuildUnit().getCommunityBuild().getCommunity().getName() +
                        b.getCommunityDoorplate().getCommunityBuildUnit().getCommunityBuild().getValue() +"幢"+
                        b.getCommunityDoorplate().getCommunityBuildUnit().getValue() +"单元"+
                        b.getCommunityDoorplate().getDoorplate()+"室"+
                        b.getRoom()+"房间"
                );
                tv_community_address.setText(b.getCommunityDoorplate().getCommunityBuildUnit().getCommunityBuild().getCommunity().getAddress());
                tv_community_ssxq.setText(b.getCommunityDoorplate().getCommunityBuildUnit().getCommunityBuild().getCommunity().getSsxq());
                adapter = new DoorplateAdapter(MainRoomActivity.this, list, listTag);
                listView.setAdapter(adapter);
            } else {
                Toaster.show(MainRoomActivity.this, "加载数据异常.....");
            }
        }
    }

    public void setData(List<Tenant> tenantList) {
        list.add("租客信息:");
        listTag.add("租客信息:");
        for (int i = 0; i < tenantList.size(); i++) {
            Tenant lardLord = tenantList.get(i);
            list.add(lardLord);
        }
    }

    /**
     * 获取制定的 json 数据转换成我们需要的 CommunityDoorplate 对象
     * @return
     */
    public CommunityRoom getDatas(String id) {
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String remote_ip = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
        String remote_port = mSharedPrefs.getString("remote_admin_port", Server.admin_port);
        String url = "http://" + remote_ip + ":" + remote_port + "/CommunityRoomAction_findByClient.action";
        PostUtils postUtils = new PostUtils();
        String[][] params = new String[][]{
                {"id", id}
        };
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
                int room = object.getInt("room");

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

                CommunityDoorplate communityDoorplate = new CommunityDoorplate();
                communityDoorplate.setDoorplate(doorplate);
                communityDoorplate.setId(dId);

                communityDoorplate.setCommunityBuildUnit(communityBuildUnit);

                CommunityRoom communityRoom = new CommunityRoom();
                communityRoom.setRoom(room);
                communityRoom.setQrCode(id);
                communityRoom.setCommunityDoorplate(communityDoorplate);
                communityRoom.setId(dId);


                List<Tenant> tenantList = new ArrayList<>();
                JSONArray lardlordsArray = object.getJSONArray("tenants");
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
                    String description = job.getString("description");
                    int status = job.getInt("status");
                    int attention = job.getInt("attention");

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
                    entity.setDescription(description);
                    entity.setStatus(status);
                    entity.setAttention(attention);
                    entity.setRoom(room);
                    tenantList.add(entity);
                }
                list.clear();
                listTag.clear();
                setData(tenantList);
                return communityRoom;
            }
        } catch (Exception e) {
            return null;
        }
        return null;
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
            case R.id.add_tenant:
                Intent ii = new Intent(context, TenantInsertActivity.class);
                ii.putExtra("idx", idx);
                ii.putExtra("doorplate", false);
                startActivity(ii);
                break;
            case R.id.sys:
                startActivity(new Intent(context, MainFragmentActivity.class));
                break;
            default:
                break;
        }
    }

}



