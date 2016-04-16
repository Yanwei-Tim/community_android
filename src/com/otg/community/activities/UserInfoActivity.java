package com.otg.community.activities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import com.otg.community.R;
import com.otg.community.handler.ReadUserInfoHandler;
import com.otg.community.handler.WriteUserInfoHandler;
import com.otg.community.utils.Toaster;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 16-2-1.
 */
public class UserInfoActivity extends Activity implements View.OnClickListener{
    public static final int RESULT_WRITE_SUCCESS = 1;
    public static final int RESULT_WRITE_ERROR = 2;
    public static final int RESULT_READ_SUCCESS = 3;
    public static final int RESULT_READ_ERROR = 4;
    private EditText phoneEdit;
    private EditText id_cardEdit;
    private EditText nameEdit;
    private EditText numberEdit;
    private EditText community_nameEdit;
    private EditText community_addressEdit;
    private EditText modify_timeEdit;
    private EditText register_timeEdit;
    private Button submit;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_user_setting_info);
        phoneEdit = (EditText) findViewById(R.id.phone);
        id_cardEdit = (EditText) findViewById(R.id.id_card);
        nameEdit = (EditText) findViewById(R.id.name);
        numberEdit = (EditText) findViewById(R.id.number);
        community_nameEdit = (EditText) findViewById(R.id.community_name);
        community_addressEdit = (EditText) findViewById(R.id.community_address);
        modify_timeEdit = (EditText) findViewById(R.id.modify_time);
        register_timeEdit = (EditText) findViewById(R.id.register_time);
        submit = (Button) findViewById(R.id.user_info_submit_button);
        submit.setOnClickListener(this);

        back = (ImageButton)findViewById(R.id.header_left_small);
        back.setOnClickListener(this);

        SharedPreferences sharedPreferences = UserInfoActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String phone = sharedPreferences.getString("phone","");
        ReadUserInfoHandler readUserInfoHandler = new ReadUserInfoHandler(this,mHandler);
        readUserInfoHandler.handler(phone);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.user_info_submit_button:
                SharedPreferences sharedPreferences = UserInfoActivity.this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                String phone = sharedPreferences.getString("phone","");
                WriteUserInfoHandler writeUserInfoHandler = new WriteUserInfoHandler(this,mHandler);
                writeUserInfoHandler.handler(phone,id_cardEdit.getText().toString(),
                        nameEdit.getText().toString(),
                        numberEdit.getText().toString());
                break;
            case R.id.header_left_small:
                finish();
                break;
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT_WRITE_SUCCESS:
                    Toaster.show(UserInfoActivity.this, (String) msg.obj);
                    break;
                case RESULT_WRITE_ERROR:
                    Toaster.show(UserInfoActivity.this, (String)msg.obj);
                    break;
                case RESULT_READ_SUCCESS:
                    JSONObject jsonObject = (JSONObject)msg.obj;
                    try {
                        String idCard = jsonObject.getString("idCard");
                        String name = jsonObject.getString("name");
                        String phone = jsonObject.getString("phone");
                        String community_name = jsonObject.getString("community_name");
                        String community_address = jsonObject.getString("community_address");
                        String number = jsonObject.getString("number");
                        String register_time = jsonObject.getString("register_time");
                        String modify_time = jsonObject.getString("modify_time");
                        phoneEdit.setText(phone);
                        id_cardEdit.setText(idCard);
                        nameEdit.setText(name);
                        numberEdit.setText(number);
                        community_nameEdit.setText(community_name);
                        community_addressEdit.setText(community_address);
                        register_timeEdit.setText(register_time);
                        modify_timeEdit.setText(modify_time);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    break;
                case RESULT_READ_ERROR:
                    Toaster.show(UserInfoActivity.this, (String)msg.obj);
                    break;
            }
        }
    };
}
