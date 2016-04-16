package com.otg.community.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.otg.community.R;
import com.otg.community.activities.MainDoorplateActivity;
import com.otg.community.activities.MainRoomActivity;
import com.otg.community.utils.Toaster;
import com.zxing.activity.CaptureActivity;


public class QRCodeFragment extends Fragment {
    private static final int QRCODE_RESULT = 111;
    private ImageButton imageButtonReset;
    private String result = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //跳转到拍照界面扫描二维码
        Intent i = new Intent(getActivity(), CaptureActivity.class);
        startActivityForResult(i, QRCODE_RESULT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_qrcode, container, false);
        imageButtonReset = (ImageButton) v.findViewById(R.id.reset);
        imageButtonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(i, QRCODE_RESULT);
            }
        });
        return v;
    }

    /**
     * 拍照上传
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case QRCODE_RESULT:
                    result = data.getExtras().getString("result");
                    if (result.startsWith("CXSQRK")) {
                        String[] split = result.split("-");
                        if(split.length==6){
                            Intent i = new Intent(getActivity(), MainDoorplateActivity.class);
                            i.putExtra("idx", result);
                            startActivity(i);
                        }else if(split.length==7){
                            Intent i = new Intent(getActivity(), MainRoomActivity.class);
                            i.putExtra("idx", result);
                            startActivity(i);
                        }
                    } else {
                        Toaster.show(getActivity(), "读取二维码出错,请使用本应用生成二维码");
                    }
                    break;
                default:
                    break;
            }
            ;
        }
    }
}
