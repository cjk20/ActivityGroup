package com.example.activitygroup.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class ActivityChild extends Activity{
	
	/** 是否需要重新创建*/
	private boolean reCreate;
	protected ActivityGroup parent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(getIntent() != null){
			parent = (ActivityGroup) getIntent().getSerializableExtra("parent");
		}
	}
	
	/**
	 * 在当前ActivityGroup中启动activity
	 * @param activityName
	 * @param intent
	 */
	protected void startActivityAtParent(String activityName, Intent intent){
		if(parent != null){
			parent.setActivityChildView(activityName, intent);
		}
	}
	
	protected void startActivityAtParentForResult(Intent intent, int requestCode){
		if(parent != null){
			parent.startActivityFromChild(this, intent, requestCode);
		}
	}
	
	public String getActivityName(){
		return getClass().getSimpleName();
	}
	
	@Override
	public void finish() {
		System.out.println(getActivityName()+"--ActivityChild---finish");
		super.finish();
		parent.finishChild(this);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		System.out.println(getActivityName()+"--ActivityChild---onStart");
		super.onStart();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		System.out.println(getActivityName()+"--ActivityChild---onResume");
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		System.out.println(getActivityName()+"--ActivityChild---onPause");
		super.onPause();
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		System.out.println(getActivityName()+"--ActivityChild---onStop");
		super.onStop();
	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		System.out.println(getActivityName()+"--ActivityChild---onDestroy");
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		System.out.println(getActivityName()+"--ActivityChild---onKeyDown");
		
		if(parent != null){
			String[] nav = parent.getNav();
			if(nav != null){
				for(int i=0;i<nav.length;i++){
					if(getClass().getSimpleName().equals(nav[i])){
						startActivityAtParent(parent.getIndex().getSimpleName(), new Intent(parent, parent.getIndex()));
						return false;
					}
				}
			}
			
			for(int i=0;i<parent.childs.size();i++){
				if(getClass().getSimpleName().equals(parent.childs.get(i))){
//					startActivityAtParent(parent.childs.get(i-1), new Intent(parent, ));
					finish();
					return false;
				}
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * 设置是否每次都重新调用onCreat()方法
	 * @param reCreate
	 */
	protected void setCanReCreate(boolean reCreate){
		this.reCreate = reCreate;
	}
	
	/**
	 * 是否每次都重新调用onCreat()方法
	 * @return
	 */
	public boolean canReCreate(){
		return reCreate;
	}

}
