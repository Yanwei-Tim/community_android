package com.otg.community.activities;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import com.otg.community.R;
import com.otg.community.handler.LoginHandler;
import com.otg.community.utils.Base64Util;
import com.otg.community.utils.Toaster;
import com.otg.community.utils.ValidUtils;
import com.otg.community.utils.des.DesUtils;

import java.lang.reflect.Method;
import java.security.Key;

public class LoginActivity extends Activity {
    public static final int RESULT_SUCCESS = 1;
    public static final int RESULT_ERROR = 2;
    public static final int ENCRYPT_ERROR = 3; // 加密出错
    private EditText phoneEdit;
    private EditText pswEdit;
    ProgressDialog  dia =null;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_admin:
                startActivity(new Intent(LoginActivity.this, SetAdminActivity.class));
                return true;
            case R.id.action_menu_otg:
                startActivity(new Intent(LoginActivity.this, SetOtgActivity.class));
                return true;
        }
        return false;
    }



    /**
     * 设置menu显示icon
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu){

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_login);
        phoneEdit = (EditText) findViewById(R.id.phone);
        pswEdit = (EditText) findViewById(R.id.psw);
        CheckBox remBox = (CheckBox)findViewById(R.id.rember);
        dia = new ProgressDialog(this);
        dia.setTitle("信息");
        dia.setMessage("正在登陆...");
        dia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dia.setCancelable(false);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        boolean save = prefs.getBoolean("rember_pwd",false);
        if(save) {
            if(!remBox.isChecked()){
                remBox.setChecked(true);
            }
            String s_phone = prefs.getString("phone", null);
            //记住的md5密码，使用des,base64加密
            String s_pwd = prefs.getString("pwd",null);
            if(s_phone!=null&&s_pwd!=null){
                //提交数据
                Key key = DesUtils.getKey(this);
                if (key != null) {
                    try {
                        //base64解码
                        byte[] base64_pwd = Base64Util.decode(s_pwd);
                        //des解码
                        byte[] b_pwd = DesUtils.decrypt(base64_pwd, key);
                        //解码后的密码
                        String pwd = new String(b_pwd);
                        pswEdit.setText(pwd);
                        phoneEdit.setText(s_phone);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void onWelClick(View v){
        switch (v.getId()){
           /* case R.id.register:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;*/
            case R.id.rember:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                if (((CheckBox) v).isChecked()) {
                   boolean save = prefs.getBoolean("rember_pwd",false);
                    if(!save) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("rember_pwd", true);
                        editor.commit();
                    }
                } else {
                    boolean save = prefs.getBoolean("rember_pwd",false);
                    if(save) {
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("rember_pwd", false);
                        editor.commit();
                    }
                }
                break;
            case R.id.forget:
                startActivity(new Intent(LoginActivity.this, ForgetPswActivity.class));
                break;
            case R.id.login:
                if (!ValidUtils.isValidPhoneNumber(this.phoneEdit.getText().toString())){
                    Toaster.show(this, R.string.please_insert_success_phone_number);
                    return;
                }
                if (!ValidUtils.isValidPass(this.pswEdit.getText().toString()))
                {
                    Toaster.show(this, R.string.valid_pass);
                    return;
                }
                LoginHandler loginHandler = new LoginHandler(this,mHandler);
                loginHandler.handler(phoneEdit.getText().toString(),pswEdit.getText().toString());
                dia.show();
                break;
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ENCRYPT_ERROR:
                    dia.dismiss();
                    Toaster.show(LoginActivity.this, (String)msg.obj);
                    break;
                case RESULT_SUCCESS:
                    dia.dismiss();
                    Toaster.show(LoginActivity.this, (String)msg.obj);
                    startActivity(new Intent(LoginActivity.this,MainFragmentActivity.class));
                    finish();
                    break;
                case RESULT_ERROR:
                    dia.dismiss();
                    Toaster.show(LoginActivity.this, (String)msg.obj);
                    break;
            }
        }
    };


}
