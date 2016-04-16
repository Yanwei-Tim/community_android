package com.otg.community.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.usb.UsbManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.*;
import android.widget.*;
import com.otg.community.R;
import com.otg.community.device.AndroidDriver;
import com.otg.community.domain.IdentityCard;
import com.otg.community.domain.LardLord;
import com.otg.community.handler.InitHandler;
import com.otg.community.handler.InsertInfoHandler;
import com.otg.community.handler.LocationHandler;
import com.otg.community.handler.ReadHandler;
import com.otg.community.utils.Toaster;
import com.otg.community.utils.ValidUtils;

import java.lang.reflect.Method;

public class LardLordInsertActivity extends Activity {
    /**
     * otg
     */
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
    //    private TextView dncode;
//    private TextView dncodetext;
    private TextView phone;
    private EditText phonetext;
    private TextView xzz;
    private EditText xzztext;

//    private TextView status;
//    private ToggleButton statusToggle;

    //    private static Button onredo;
    private ImageButton readIdCardBt;
    private TextView Readingtext;
    private ImageView idimg;
    private ImageButton imageButton;
    private ImageButton photoButton;
    private ImageButton header_left_small;
    private TextView header_title;
    private LardLord lardLord;
    public static final int NFC_NET_NETCONNECT_ERROR = 1; // 网络连接错误
    public static final int NFC_NET_NETRECV_ERROR = 2;// 网络接收错误
    public static final int NFC_NET_NETSEND_ERROR = 3;// 网络发送错误
    public static final int NFC_NET_NFCOPEN_ERROR = 4;// NFC打开错误
    public static final int NFC_NET_NFCREADCARD_ERROR = 5; // NFC读卡错误
    public static final int NFC_NET_AUTHENTICATION_FAIL = 6;// 认证失败
    public static final int NFC_NET_SEARCHCARD_FAIL = 7;// NFC寻卡失败
    public static final int NFC_NET_STARTNETREADCARD_FAIL = 8; // 启动网络读卡失败
    public static final int NFC_NET_INVALIDNETCMD = 9;// 无效的网络命令
    public static final int NFC_NET_UNKNOWN_ERROR = 10;// 未知错误
    public static final int READ_IDCARD_SUCCESS = 11; //读取身份信息成功
    public static final int READ_PHOTO_SUCCESS = 12; //读取照片成功
    public static final int PLASE_INIT_SERVER = 13; //请先初始化服务器
    public static final int NOT_FOUND_DEVICE = 14; //未找到读卡设备
    public static final int REFRESH_LOCATION = 15; //gps信息改变
    public static final int READ_PHOTO_ERROR = 16; //读取照片出错
    private Location location = null;

    private static final String ACTION_USB_PERMISSION = "com.lz.nfc.USB_PERMISSION";
    private AndroidDriver androidDriver;
    private LocationHandler locationHandler;
    private Context context;
    private String idx = null;
    //定义进度条
    private ProgressDialog dia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lardlord_insert);
        context = this;

        dia = new ProgressDialog(this);
        dia.setTitle("信息");
        dia.setMessage("正在新增...");
        dia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dia.setCancelable(false);

        // 默认软键盘不弹出
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        androidDriver = new AndroidDriver(usbManager, context, ACTION_USB_PERMISSION);
        Intent intent = getIntent();
        idx = intent.getStringExtra("idx");
        if (idx==null) {
            Toast.makeText(this, "请重新扫描二维码", Toast.LENGTH_LONG).show();
            finish();
        }

        /**
         * otg
         */
        /*onredo = (Button) findViewById(R.id.scale);
        onredo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onredo.setEnabled(false);
                onredo.setFocusable(false);
                readIdCardBt.setEnabled(false);
                readIdCardBt.setFocusable(false);
//                if(communityDoorplate.getType()==1){
                    lardLord = null;
//                }else {
//                    tenant = null;
//                }
                nametext.setText("");
                sextext.setText("");
                mingzutext.setText("");
                birthdaytext.setText("");
                addresstext.setText("");
                numbertext.setText("");
                qianfatext.setText("");
                starttext.setText("");
//                dncodetext.setText("");
                idimg.setImageBitmap(null);
                Readingtext.setText("      正在读卡，请稍候...");
                Readingtext.setVisibility(View.VISIBLE);
                int cfd = androidDriver.GetFD();
                ReadHandler threadHandler = new ReadHandler(mHandler, context, cfd);
                threadHandler.postData();
            }
        });*/


        readIdCardBt = (ImageButton) findViewById(R.id.readIdCard);
        readIdCardBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readIdCardBt.setEnabled(false);
                readIdCardBt.setFocusable(false);
//                onredo.setEnabled(false);
//                onredo.setFocusable(false);
//                if(communityDoorplate.getType()==1){
                lardLord = null;
//                }else {
//                    tenant = null;
//                }
                nametext.setText("");
                sextext.setText("");
                mingzutext.setText("");
                birthdaytext.setText("");
                addresstext.setText("");
                numbertext.setText("");
                qianfatext.setText("");
                starttext.setText("");
//                dncodetext.setText("");
                idimg.setImageBitmap(null);
                Readingtext.setText("      正在读卡，请稍候...");
                Readingtext.setVisibility(View.VISIBLE);
                int cfd = androidDriver.GetFD();
                ReadHandler threadHandler = new ReadHandler(mHandler, context, cfd);
                threadHandler.postData();
            }
        });

        photoButton = (ImageButton) findViewById(R.id.photo);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(context, LardLordPhotoActivity.class);
                ii.putExtra("idx", idx);
                startActivity(ii);
            }
        });

        imageButton = (ImageButton) findViewById(R.id.next);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidUtils.isValidPhoneNumber(phonetext.getText().toString())) {
                    Toaster.show(context, R.string.please_insert_success_phone_number);
                    return;
                }

                if (TextUtils.isEmpty(xzztext.getText().toString())) {
                    Toaster.show(context, "请填写相关信息", Toast.LENGTH_SHORT);
                    return;
                }
                if (lardLord != null) {
                    lardLord.setPhone(phonetext.getText().toString());
                    lardLord.setXzz(xzztext.getText().toString());
                    lardLord.setOcr(0);
//                    if (statusToggle.isChecked())
//                        lardLord.setType(1);
//                    else
//                        lardLord.setType(0);
                    InsertInfoHandler insertInfoHandler = new InsertInfoHandler(context, mHandler);
                    insertInfoHandler.handler(lardLord,idx);
                    dia.show();
                } else {
                    Toaster.show(context, "请重新读取房东信息", Toast.LENGTH_SHORT);
                }
            }
        });

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
        Readingtext = (TextView) findViewById(R.id.Readingtext);
//        dncodetext = (TextView) findViewById(R.id.dncodetext);
//        dncode = (TextView) findViewById(R.id.dncode);
        phone = (TextView) findViewById(R.id.phone);
        phonetext = (EditText) findViewById(R.id.phonetext);
        xzz = (TextView) findViewById(R.id.xzz);
        xzztext = (EditText) findViewById(R.id.xzztext);

//        status = (TextView) findViewById(R.id.status);
//        statusToggle = (ToggleButton) findViewById(R.id.statusToggle);

        header_left_small = (ImageButton) findViewById(R.id.header_left_small);
        header_left_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                            Toaster.show(PersonInfoActivity.this,"header_left_small");
                finish();
            }
        });

        Button bt_sys = (Button) findViewById(R.id.sys);
        bt_sys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context, MainFragmentActivity.class));
            }
        });

        header_title = (TextView) findViewById(R.id.header_title);
        header_title.setText("添加户主");

        name.setText("姓名：");
        sex.setText("性别：");
        mingzu.setText("民族：");
        birthday.setText("出生年月：");
        address.setText("地址：");
        number.setText("身份证号码：");
        qianfa.setText("签发机关：");
        start.setText("有效时间：");
//        dncode.setText("DN码：");
        phone.setText("电话：");
        xzz.setText("现住址：");
//        status.setText("状态：");
        idimg = (ImageView) findViewById(R.id.idimg);
        Readingtext.setVisibility(View.GONE);
        Readingtext.setText("      正在读卡，请稍候...");
        Readingtext.setTextColor(Color.RED);

        InitHandler initHandler = new InitHandler(context);
        initHandler.handler();

        locationHandler = new LocationHandler(context, mHandler);
        boolean open = locationHandler.getGPSState();
        if (open) {
            locationHandler.registerListen();
        } else {
            locationHandler.toggleGPS();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_menu_admin:
                startActivity(new Intent(context, SetAdminActivity.class));
                return true;
            case R.id.action_menu_otg:
                startActivity(new Intent(context, SetOtgActivity.class));
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
    public void onDestroy() {
        if (androidDriver != null) {
            if (androidDriver.isConnected()) {
                androidDriver.CloseDevice();
            }
            androidDriver = null;
        }
        if (locationHandler != null) {
            locationHandler.unRegisterListen();
            locationHandler = null;
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        if (2 == androidDriver.ResumeUsbList()) {
            androidDriver.CloseDevice();
        }
        super.onResume();
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case READ_IDCARD_SUCCESS:

                    Readingtext.setText("      读卡成功");
                    Readingtext.setVisibility(View.GONE);
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    IdentityCard identityCard = (IdentityCard) msg.obj;
                    nametext.setText(identityCard.getName());
                    sextext.setText(identityCard.getSex());
                    mingzutext.setText(identityCard.getMz());
                    birthdaytext.setText(identityCard.getBirth());
                    addresstext.setText(identityCard.getAddress());
                    numbertext.setText(identityCard.getIdCard());
                    qianfatext.setText(identityCard.getSign());
                    starttext.setText(identityCard.getValidity());
//                    dncodetext.setText(identityCard.getDN());

                    if (lardLord == null) {
                        lardLord = new LardLord();
                    }
                    lardLord.setName(identityCard.getName());
                    lardLord.setSex(identityCard.getSex());
                    lardLord.setMz(identityCard.getMz());
                    lardLord.setBirth(identityCard.getBirth());
                    lardLord.setAddress(identityCard.getAddress());
                    lardLord.setIdCard(identityCard.getIdCard());
                    lardLord.setSign(identityCard.getSign());
                    lardLord.setValidity(identityCard.getValidity());
                    lardLord.setDN(identityCard.getDN());

                    if (location != null) {
                        lardLord.setLongitude(String.valueOf(location.getLongitude()));
                        lardLord.setLatitude(String.valueOf(location.getLatitude()));
                    } else {
                        Location l = locationHandler.getLocationByGps();
                        if (l != null) {
                            location = l;

                            lardLord.setLongitude(String.valueOf(location.getLongitude()));
                            lardLord.setLatitude(String.valueOf(location.getLatitude()));
                        }
                    }
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    Readingtext.setVisibility(View.GONE);
                    break;
                case READ_PHOTO_SUCCESS:
                    Readingtext.setText("      读照片成功");
                    Readingtext.setVisibility(View.GONE);
                    byte[] cardbmp = (byte[]) msg.obj;
                    Bitmap bm = BitmapFactory.decodeByteArray(cardbmp, 0, cardbmp.length);
                    idimg.setImageBitmap(bm);
                    if (lardLord == null)
                        lardLord = new LardLord();
                    lardLord.setBytes(cardbmp);
                    break;
                case NFC_NET_NETCONNECT_ERROR:
                    Readingtext.setText("      网络连接错误！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_NETRECV_ERROR:
                    Readingtext.setText("      网络接收错误！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_NETSEND_ERROR:
                    Readingtext.setText("      网络发送错误！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_NFCOPEN_ERROR:
                    Readingtext.setText("      NFC打开错误！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_NFCREADCARD_ERROR:
                    Readingtext.setText("      NFC读卡错误！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_AUTHENTICATION_FAIL:
                    Readingtext.setText("      认证失败！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_SEARCHCARD_FAIL:
                    Readingtext.setText("      NFC寻卡失败！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_STARTNETREADCARD_FAIL:
                    Readingtext.setText("      启动网络读卡失败！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_INVALIDNETCMD:
                    Readingtext.setText("      无效的网络命令！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NFC_NET_UNKNOWN_ERROR:
                    Readingtext.setText("      未知错误！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case PLASE_INIT_SERVER:
                    Readingtext.setText("      请初始化服务器配置！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    break;
                case NOT_FOUND_DEVICE:
                    Readingtext.setText("      未找到读卡设备！");
//                    onredo.setEnabled(true);
//                    onredo.setFocusable(true);
                    readIdCardBt.setEnabled(true);
                    readIdCardBt.setFocusable(true);
//                    onredo.setBackgroundResource(R.drawable.sfz_dq);
                    if (2 == androidDriver.ResumeUsbList()) {
                        androidDriver.CloseDevice();
                    }
                    break;
                case REFRESH_LOCATION:
                    Location l = (Location) msg.obj;
                    if (l != null) {
                        location = l;
                    }
                    break;
                case READ_PHOTO_ERROR:
//                    String m = (String) msg.obj;
//                    Toast.makeText(context, m, Toast.LENGTH_SHORT).show();
                    break;
                case InsertInfoHandler.RESULT_INSERT_SUCCESS:
                    dia.dismiss();
                    Toaster.show(context, (String) msg.obj);
                    lardLord = null;
                    finish();
                    break;
                case InsertInfoHandler.RESULT_INSERT_ERROR:
                    dia.dismiss();
                    Toaster.show(context, (String) msg.obj);
                    break;
            }
        }
    };

}
