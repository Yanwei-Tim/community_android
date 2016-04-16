package com.otg.community.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.otg.community.R;
import com.otg.community.activities.LoginActivity;
import com.otg.community.activities.ResetPwdActivity;
import com.otg.community.activities.UserInfoActivity;

public class MeFragment extends Fragment implements View.OnClickListener
{
	private Button exitLogin;
	RelativeLayout layoutUserInfo ;
	RelativeLayout layoutPwdReset ;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
	{
		View v = inflater.inflate(R.layout.tab_me, container,false);

		layoutUserInfo = (RelativeLayout)v.findViewById(R.id.fragment_my_personal_layout);
		layoutUserInfo.setClickable(true);
		layoutUserInfo.setOnClickListener(this);

		layoutPwdReset = (RelativeLayout)v.findViewById(R.id.fragment_my_password_layout);
		layoutPwdReset.setClickable(true);
		layoutPwdReset.setOnClickListener(this);

		exitLogin = (Button)v.findViewById(R.id.fragment_my_signout_button);
		exitLogin.setOnClickListener(this);
		return v;
	}


	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.fragment_my_personal_layout:
//				Toast.makeText(getActivity(),"personInfo",Toast.LENGTH_LONG).show();
				startActivity(new Intent(getActivity(), UserInfoActivity.class));
				break;
			case R.id.fragment_my_password_layout:
//				Toast.makeText(getActivity(),"passwordInfo",Toast.LENGTH_LONG).show();
				startActivity(new Intent(getActivity(), ResetPwdActivity.class));
				break;
			case R.id.fragment_my_signout_button:
//				Toast.makeText(getActivity(),"exitLogin",Toast.LENGTH_LONG).show();
				startActivity(new Intent(getActivity(), LoginActivity.class));
				SharedPreferences sharedPreferences = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
				sharedPreferences.edit().clear().commit();
				getActivity().finish();
				break;
			default:
				break;
		}
	}
}
