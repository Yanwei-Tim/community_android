package com.otg.community.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import com.otg.community.R;
import com.otg.community.domain.LardLord;
import com.otg.community.handler.InsertInfoHandler;
import com.otg.community.utils.ImageThumbnail;
import com.otg.community.utils.Toaster;
import com.otg.community.utils.ValidUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class LardLordPhotoActivity extends Activity {
    private static final int REQUEST_CODE = 2;
    private ImageView img;
    private ImageButton imageButton;
    private ImageButton imageButtonReset;
    private Context context;
    private static final String jpgfile = "temp.jpg";
    private ImageButton header_left_small;
    private TextView header_title;
    private TextView phone;
    private EditText phonetext;
    private TextView xzz;
    private EditText xzztext;
    private String idx = null;
    //定义进度条
    private ProgressDialog dia;
    private LardLord tenant = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lardlord_photo);
        this.context = this;
        dia = new ProgressDialog(this);
        dia.setTitle("信息");
        dia.setMessage("正在新增...");
        dia.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dia.setCancelable(false);

        Intent intent = getIntent();
        idx = intent.getStringExtra("idx");
        if (idx == null) {
            Toast.makeText(this, "请重新扫描二维码", Toast.LENGTH_LONG).show();
            finish();
        }
        img = (ImageView) findViewById(R.id.img);
        imageButtonReset = (ImageButton) findViewById(R.id.reset);
        imageButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File("/sdcard/Image/");
                file.mkdirs();// 创建文件夹
                String fileName = "/sdcard/Image/" + jpgfile;
                File f = new File(fileName);
                if (f.exists()) {
                    f.delete();
                }
                Uri imageUri = Uri.fromFile(f);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(cameraIntent, REQUEST_CODE);
            }
        });

        header_left_small = (ImageButton) findViewById(R.id.header_left_small);
        header_left_small.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        header_title = (TextView) findViewById(R.id.header_title);
        header_title.setText("户主证件");

        phone = (TextView) findViewById(R.id.phone);
        phonetext = (EditText) findViewById(R.id.phonetext);
        xzz = (TextView) findViewById(R.id.xzz);
        xzztext = (EditText) findViewById(R.id.xzztext);

        phone.setText("电话：");
        xzz.setText("现住址：");

        imageButton = (ImageButton) findViewById(R.id.success);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!ValidUtils.isValidPhoneNumber(phonetext.getText().toString())){
                    Toaster.show(context, R.string.please_insert_success_phone_number);
                    return;
                }
                if (TextUtils.isEmpty(xzztext.getText().toString())) {
                    Toaster.show(context, "请填写相关信息", Toast.LENGTH_SHORT);
                    return;
                }
                if (tenant.getCardPicBefore() != null) {
                    tenant.setOcr(1);
                    tenant.setXzz(xzztext.getText().toString());
                    tenant.setPhone(phonetext.getText().toString());

                    InsertInfoHandler insertInfoHandler = new InsertInfoHandler(context, mHandler);
                    insertInfoHandler.handler(tenant, idx);
                    dia.show();
                }else {
                    Toaster.show(context, "请先拍取身份证信息", Toast.LENGTH_SHORT);
                    return;
                }
            }
        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File("/sdcard/Image/");
        file.mkdirs();// 创建文件夹
        String fileName = "/sdcard/Image/" + jpgfile;
        File f = new File(fileName);
        if (f.exists()) {
            f.delete();
        }
        Uri imageUri = Uri.fromFile(f);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, REQUEST_CODE);
    }

    /**
     * 拍照上传
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE:
                    // 将保存在本地的图片取出并缩小后显示在界面上
                    String fileName = "/sdcard/Image/" + jpgfile;
                    File f = new File(fileName);
                    if (f.exists()) {
                        Bitmap camorabitmap = BitmapFactory.decodeFile(fileName);
                        if (null != camorabitmap) {
                            // 下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。
                            int scale = ImageThumbnail.reckonThumbnail(camorabitmap.getWidth(), camorabitmap.getHeight(), 500, 600);
                            Bitmap bitMap = ImageThumbnail.PicZoom(camorabitmap, camorabitmap.getWidth() / scale, camorabitmap.getHeight() / scale);
                            img.setImageBitmap(bitMap);
                            ByteArrayOutputStream out = new ByteArrayOutputStream();
                            bitMap.compress(Bitmap.CompressFormat.JPEG, 30, out);//40 是压缩率，表示压缩70%; 如果不压缩是100，表示压
                            try {
                                out.flush();
                                out.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            byte[] bytes = out.toByteArray();
                            tenant = null;
                            tenant = new LardLord();
                            tenant.setCardPicBefore(bytes);
                            // 由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
                            camorabitmap.recycle();
                            // 将处理过的图片显示在界面上，并保存到本地
                        }
                    }

                    break;
                default:
                    break;
            }
            ;
        }
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case InsertInfoHandler.RESULT_INSERT_SUCCESS:
                    dia.dismiss();
                    Toaster.show(context, (String) msg.obj);
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
