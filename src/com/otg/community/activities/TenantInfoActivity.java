package com.otg.community.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.otg.community.R;
//import com.otg.community.domain.CommunityDoorplate;
//import com.otg.community.domain.LardLord;
import com.otg.community.domain.Tenant;
import com.otg.community.handler.InsertInfoHandler;
import com.otg.community.handler.LoadImageHandler;
import com.otg.community.handler.TuiZuHandler;
import com.otg.community.utils.Toaster;

public class TenantInfoActivity extends Activity {

    private TextView name;
    private TextView nametext;
    private TextView sex;
    private TextView sextext;
    private TextView mingzu;
    private TextView mingzutext;
    private TextView birthday;
    private TextView birthdaytext;
    private TextView address;
    private TextView addresstext;
    private TextView number;
    private TextView numbertext;
    private TextView qianfa;
    private TextView qianfatext;
    private TextView start;
    private TextView starttext;
    private TextView phone;
    private TextView phonetext;
    private TextView fh;
    private TextView fhtext;
    private  ImageButton header_left_small;
    private TextView header_title;
    private Button fragment_surrender_button;
//    private TextView dncodetext;
//    private TextView dncode;
    private ImageView idimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_tenant_info);
        Intent intent = getIntent();
                final Tenant tenant = (Tenant) intent.getSerializableExtra("tenant");
                if (tenant != null) {
                    name = (TextView) findViewById(R.id.name);
                    sex = (TextView) findViewById(R.id.sex);
                    nametext = (TextView) findViewById(R.id.nametext);
                    sextext = (TextView) findViewById(R.id.sextext);
                    mingzu = (TextView) findViewById(R.id.mingzu);
                    mingzutext = (TextView) findViewById(R.id.mingzutext);
                    birthday = (TextView) findViewById(R.id.birthday);
                    birthdaytext = (TextView) findViewById(R.id.birthdaytext);
                    address = (TextView) findViewById(R.id.address);
                    addresstext = (TextView) findViewById(R.id.addresstext);
                    number = (TextView) findViewById(R.id.number);
                    numbertext = (TextView) findViewById(R.id.numbertext);
                    qianfa = (TextView) findViewById(R.id.qianfa);
                    qianfatext = (TextView) findViewById(R.id.qianfatext);
                    start = (TextView) findViewById(R.id.start);
                    starttext = (TextView) findViewById(R.id.starttext);

                    phone = (TextView) findViewById(R.id.phone);
                    phonetext = (TextView) findViewById(R.id.phonetext);

                    fh = (TextView) findViewById(R.id.fh);
                    fhtext = (TextView) findViewById(R.id.fhtext);

                    idimg = (ImageView) findViewById(R.id.idimg);

                    fragment_surrender_button = (Button) findViewById(R.id.fragment_surrender_button);
                    fragment_surrender_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TuiZuHandler tuiZuHandler = new TuiZuHandler(TenantInfoActivity.this, mHandler);
                            tuiZuHandler.handler(String.valueOf(tenant.getId()));
                        }
                    });
//                    dncodetext = (TextView) findViewById(R.id.dncodetext);
//                    dncode = (TextView) findViewById(R.id.dncode);

                    header_left_small = (ImageButton) findViewById(R.id.header_left_small);
                    header_left_small.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
                    header_title  = (TextView) findViewById(R.id.header_title);
                    header_title.setText("租客信息");

                    name.setText("姓名：");
                    sex.setText("性别：");
                    mingzu.setText("民族：");
                    birthday.setText("出生年月：");
                    address.setText("地址：");
                    number.setText("身份证号码：");
                    qianfa.setText("签发机关：");
                    start.setText("有效时间：");
                    phone.setText("电话号码：");
                    fh.setText("房号：");
//                    dncode.setText("DN码：");
                    nametext.setText(tenant.getName());
                    sextext.setText(tenant.getSex());
                    mingzutext.setText(tenant.getMz());
                    birthdaytext.setText(tenant.getBirth());
                    addresstext.setText(tenant.getAddress());
                    numbertext.setText(tenant.getIdCard());
                    qianfatext.setText(tenant.getSign());
                    starttext.setText(tenant.getValidity());
                    phonetext.setText(tenant.getPhone());
                    fhtext.setText(String.valueOf(tenant.getRoom()));
//                    dncodetext.setText(tenant.getDN());

                    LoadImageHandler loadImageHandler = new LoadImageHandler(this,mHandler);
                    loadImageHandler.handlerTenant(String.valueOf(tenant.getId()));
                }
        }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TuiZuHandler.RESULT_SUCCESS:
                    Toaster.show(TenantInfoActivity.this, (String) msg.obj);
                    finish();
                    break;
                case TuiZuHandler.RESULT_ERROR:
                    Toaster.show(TenantInfoActivity.this, (String)msg.obj);
                    break;
                case LoadImageHandler.RESULT_SUCCESS:
                    Bitmap bitmap = (Bitmap)msg.obj;
                    if(bitmap!=null){
                        idimg.setImageBitmap(bitmap);
                    }
                    break;
                case LoadImageHandler.RESULT_ERROR:
                    break;
            }
        }
    };

}
