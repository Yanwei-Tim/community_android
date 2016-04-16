package com.otg.community.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;
import com.otg.community.R;
import com.otg.community.request.PostRequest;
import com.otg.community.utils.Server;
import com.zxing.activity.CaptureActivity;

import java.lang.reflect.Method;

public class QRCodeActivity extends Activity {

    private static final int PHOTO_PIC = 1;
    private static final int SETTING_ADMIN_IP = 11;
    public String remoteAdminIP;
    private String remoteAdminPort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        remoteAdminIP = mSharedPrefs.getString("remote_admin_ip",Server.admin_server);
        remoteAdminPort = mSharedPrefs.getString("remote_admin_port",Server.admin_port);
        //跳转到拍照界面扫描二维码
        Intent i = new Intent(this, CaptureActivity.class);
        startActivityForResult(i, PHOTO_PIC);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        this.getMenuInflater().inflate(R.menu.option_menu_readcode, menu);
        return true;
    }

    /**
     * 设置menu显示icon
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu)
    {

        if (featureId == Window.FEATURE_ACTION_BAR && menu != null)
        {
            if (menu.getClass().getSimpleName().equals("MenuBuilder"))
            {
                try
                {
                    Method m = menu.getClass().getDeclaredMethod(
                            "setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setadminip:
                Intent intent = new Intent(this, SetAdminActivity.class);
                startActivityForResult(intent, SETTING_ADMIN_IP);
                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTO_PIC:
                    String result = data.getExtras().getString("result");
                    //提交数据到后台服务器
                    if(remoteAdminIP ==null||"".equals(remoteAdminIP)){
                        new AlertDialog.Builder(this).setTitle("提示")//设置对话框标题
                                .setMessage("请先配置管理服务器地址！")//设置显示的内容
                                .setNegativeButton("返回", new DialogInterface.OnClickListener() {//添加返回按钮
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {//响应事件
                                        Log.i("alert dialog", " 请先配置管理服务器地址！");
                                    }
                                }).show();//在按键响应事件中显示此对话框
                        break;
                    }
                    PostRequest postRequest = new PostRequest(this, remoteAdminIP,result,remoteAdminPort);
                    postRequest.postData(new PostRequest.OnPostListener() {
                        @Override
                        public void onPostOk(String msg) {
                            Toast.makeText(QRCodeActivity.this, msg, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onPostErr(String msg) {
                            Toast.makeText(QRCodeActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    });
                    QRCodeActivity.this.finish();
                    break;
                default:
                    break;
            }
        }else  if(requestCode== SETTING_ADMIN_IP){
            SharedPreferences mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
            remoteAdminIP = mSharedPrefs.getString("remote_admin_ip", Server.admin_server);
            remoteAdminPort = mSharedPrefs.getString("remote_admin_port",Server.admin_port);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
