package com.example.activitygroup;

import android.os.Bundle;

import com.example.activitygroup.base.ActivityChild;

public class IndexActivity extends ActivityChild{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
	}
}
