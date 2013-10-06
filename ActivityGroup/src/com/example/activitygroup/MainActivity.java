package com.example.activitygroup;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.activitygroup.base.ActivityGroup;

import android.net.wifi.WifiConfiguration.GroupCipher;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;


public class MainActivity extends ActivityGroup implements OnCheckedChangeListener{
	
	private RadioGroup radioGroup;
	/** 导航栏的*/
	private ArrayList<String> navList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		radioGroup.setOnCheckedChangeListener(this);
		navList = new ArrayList<String>();
		System.out.println("----------------------------");
		setIndex(IndexActivity.class);
		setNav(new String[]{"IndexActivity", "SecondActivity", "ThirdActivity", "FourActivity"});
	}


	@Override
	protected ViewGroup getContainer() {
		// TODO Auto-generated method stub
		return (ViewGroup) findViewById(R.id.continer);
	}


	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.btn_1:
			setActivityChildView(getActivityName(IndexActivity.class), new Intent(this, IndexActivity.class));
			break;
		case R.id.btn_2:
			setActivityChildView(getActivityName(SecondActivity.class), new Intent(this, SecondActivity.class));
			break;
		case R.id.btn_3:
			setActivityChildView(getActivityName(ThirdActivity.class), new Intent(this, ThirdActivity.class));
			break;
		case R.id.btn_4:
			setActivityChildView(getActivityName(FourActivity.class), new Intent(this, FourActivity.class));
			break;
		}
	}

}
