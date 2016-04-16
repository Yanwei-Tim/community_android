package com.otg.community.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.otg.community.R;
import com.otg.community.domain.LardLord;
import com.otg.community.handler.LoadImageHandler;
import com.otg.community.utils.Toaster;

public class LardLordInfoActivity extends Activity {

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
    private TextView xzz;
    private TextView xzztext;
    private  ImageButton header_left_small;
    private TextView header_title;
//    private Button fragment_surrender_button;
//    private TextView dncodetext;
//    private TextView dncode;
    private ImageView idimg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lardlord_info);
        Intent intent = getIntent();
                LardLord lardLord = (LardLord) intent.getSerializableExtra("lardLord");
                if (lardLord != null) {
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

                    xzz = (TextView) findViewById(R.id.xzz);
                    xzztext = (TextView) findViewById(R.id.xzztext);

                    idimg = (ImageView) findViewById(R.id.idimg);




                   header_left_small = (ImageButton) findViewById(R.id.header_left_small);
                    header_left_small.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    });
                   header_title  = (TextView) findViewById(R.id.header_title);
                    header_title.setText("户主信息");
//                    dncodetext = (TextView) findViewById(R.id.dncodetext);
//                    dncode = (TextView) findViewById(R.id.dncode);

                    name.setText("姓名：");
                    sex.setText("性别：");
                    mingzu.setText("民族：");
                    birthday.setText("出生年月：");
                    address.setText("地址：");
                    number.setText("身份证号码：");
                    qianfa.setText("签发机关：");
                    start.setText("有效时间：");
                    phone.setText("电话号码：");
                    xzz.setText("现住址：");
//                    dncode.setText("DN码：");
//                    idimg = (ImageView) findViewById(R.id.idimg);
                    nametext.setText(lardLord.getName());
                    sextext.setText(lardLord.getSex());
                    mingzutext.setText(lardLord.getMz());
                    birthdaytext.setText(lardLord.getBirth());
                    addresstext.setText(lardLord.getAddress());
                    numbertext.setText(lardLord.getIdCard());
                    qianfatext.setText(lardLord.getSign());
                    starttext.setText(lardLord.getValidity());
                    phonetext.setText(lardLord.getPhone());
                    xzztext.setText(lardLord.getXzz());
//                    dncodetext.setText(lardLord.getDN());

                    LoadImageHandler loadImageHandler = new LoadImageHandler(this,mHandler);
                    loadImageHandler.handlerLardLord(String.valueOf(lardLord.getId()));

                }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
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
