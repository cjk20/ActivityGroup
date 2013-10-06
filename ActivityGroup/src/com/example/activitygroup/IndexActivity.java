package com.example.activitygroup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.activitygroup.base.ActivityChild;

public class IndexActivity extends ActivityChild implements OnClickListener{
	
	private TextView fiveBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		fiveBtn = (TextView) findViewById(R.id.FiveBtn);
		fiveBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.FiveBtn){
			startActivityAtParent("FiveActivity", new Intent(parent, FiveActivity.class));
		}
	}
}
