package com.otg.community.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.otg.community.R;
import com.otg.community.fragments.MeFragment;
import com.otg.community.fragments.QRCodeFragment;

import java.lang.reflect.Method;

public class MainFragmentActivity extends FragmentActivity implements OnClickListener
{
	private LinearLayout mTabQrCode;
	private LinearLayout mTabMe;

	private ImageButton mImgQrCode;
	private ImageButton mImgMe;

	private Fragment mFragmentQrCode;
	private Fragment mFragmentMe;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fragment_main);
		initView();
		initEvent();
		setSelect(0);
	}

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
				startActivity(new Intent(MainFragmentActivity.this, SetAdminActivity.class));
				return true;
			case R.id.action_menu_otg:
				startActivity(new Intent(MainFragmentActivity.this, SetOtgActivity.class));
				return true;
		}
		return false;
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

	private void initEvent()
	{
		mTabQrCode.setOnClickListener(this);
		mTabMe.setOnClickListener(this);
	}

	private void initView()
	{
		mTabQrCode = (LinearLayout) findViewById(R.id.id_tab_weixin);
		mTabMe = (LinearLayout) findViewById(R.id.id_tab_frd);

		mImgQrCode = (ImageButton) findViewById(R.id.id_tab_weixin_img);
		mImgMe = (ImageButton) findViewById(R.id.id_tab_frd_img);
	}




	private void setSelect(int i){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		hideFragment(transaction);
		// 把图片设置为亮的
		// 设置内容区域
		switch (i)
		{
			case 0:
				if (mFragmentQrCode == null)
				{
					mFragmentQrCode = new QRCodeFragment();
					transaction.add(R.id.id_content, mFragmentQrCode);
				} else
				{
					transaction.show(mFragmentQrCode);
				}
				mImgQrCode.setImageResource(R.drawable.tab_settings_pressed);



				break;
			case 1:
				if (mFragmentMe == null)
				{
					mFragmentMe = new MeFragment();
					transaction.add(R.id.id_content, mFragmentMe);
				} else
				{
					transaction.show(mFragmentMe);

				}
				mImgMe.setImageResource(R.drawable.tab_settings_pressed);
				break;
			default:
				break;
		}

		transaction.commit();
	}

	private void hideFragment(FragmentTransaction transaction)
	{
		if (mFragmentQrCode != null)
		{
			transaction.hide(mFragmentQrCode);
		}
		if (mFragmentMe != null)
		{
			transaction.hide(mFragmentMe);
		}
	}

	@Override
	public void onClick(View v)
	{
		resetImgs();
		switch (v.getId())
		{
			case R.id.id_tab_weixin:
				setSelect(0);
				break;
			case R.id.id_tab_frd:
				setSelect(1);
				break;
			default:
				break;
		}
	}

	/**
	 * 切换图片至暗色
	 */
	private void resetImgs()
	{
		mImgQrCode.setImageResource(R.drawable.tab_settings_normal);
		mImgMe.setImageResource(R.drawable.tab_settings_normal);
	}

}
