package com.example.activitygroup.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

public abstract class ActivityGroup extends Activity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//加载Activity的View容器，容器应该是ViewGroup的子�?
    private ViewGroup container;
	private static final String STATES_KEY = "android:states";
    static final String PARENT_NON_CONFIG_INSTANCE_KEY = "android:parent_non_config_instance";

    /**
     * This field should be made private, so it is hidden from the SDK.
     * {@hide}
     */
    protected LocalActivityManager mLocalActivityManager;
    
    protected String showingActivityName = null;
    
    /** 导航栏的activityName*/
	private String[] nav;
	/** 首页activityName*/
	private Class<?> index;
	
    
    public ActivityGroup() {
        this(true);
    }
    
    public ActivityGroup(boolean singleActivityMode) {
        mLocalActivityManager = new LocalActivityManager(this, singleActivityMode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle states = savedInstanceState != null
                ? (Bundle) savedInstanceState.getBundle(STATES_KEY) : null;
        mLocalActivityManager.dispatchCreate(states);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Bundle state = mLocalActivityManager.saveInstanceState();
        if (state != null) {
            outState.putBundle(STATES_KEY, state);
        }
    }
    
    /**
     * 加载Activity的View容器的id并不是固定的，将命名规则交给�?���?
     * �?��者可以在布局文件中自定义其id，�?过重写这个方法获得这个View容器的对�?
     * @return
     */
    abstract protected ViewGroup getContainer();
    

	public void setActivityChildView(String activityName, Intent intent){
    	if(null == mLocalActivityManager){
    		mLocalActivityManager = getLocalActivityManager();
        }
    	
    	if(null == container){
             container = getContainer();
        }
    	
    	ActivityChild currentActivity = (ActivityChild) mLocalActivityManager.getActivity(showingActivityName);
//    	System.out.println("showingActivityName="+showingActivityName);
    	ActivityChild nextActivity = (ActivityChild) mLocalActivityManager.getActivity(activityName);
    	if(null != currentActivity && null != nextActivity && !showingActivityName.equals(nextActivity)){
    		currentActivity.onPause();
    	}
    	
    	container.removeAllViews();
    	
        if (null == nextActivity) {
        	intent.putExtra("parent", this);
        	mLocalActivityManager.startActivity(activityName, intent);
        }else if(nextActivity.canReCreate()){
        	nextActivity.finish();
        	intent.putExtra("parent", this);
        	mLocalActivityManager.startActivity(activityName, intent);
        }else{
        	nextActivity.onResume();
        }

        //加载Activity
        container.addView(
        		mLocalActivityManager.getActivity(activityName)
                        .getWindow().getDecorView(),
                new LayoutParams(LayoutParams.FILL_PARENT,
                        LayoutParams.FILL_PARENT));
        showingActivityName = activityName;
    }
	
	/**
	 * 设置导航栏的activityname
	 * @param nav
	 */
	public void setNav(String[] nav){
		this.nav = nav;
	}
	
	/**
	 * 获取导航栏的activityName
	 * @return
	 */
	public String[] getNav(){
		return nav;
	}
	
	/**
	 * 设置首页activityName
	 * @param activityName
	 */
	public void setIndex(Class<?> activity){
		index = activity;
	}
	
	/**
	 * 获取首页activityName
	 * @return
	 */
	public Class<?> getIndex(){
		return index;
	}
	

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(isFinishing());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLocalActivityManager.dispatchStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocalActivityManager.dispatchDestroy(isFinishing());
    }

    public final LocalActivityManager getLocalActivityManager() {
        return mLocalActivityManager;
    }
    
    public ActivityChild getCurrentActivity(){
    	return (ActivityChild) mLocalActivityManager.getActivity(showingActivityName);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	ActivityChild activity = getCurrentActivity();
    	if(null != activity){
    		activity.onActivityResult(requestCode, resultCode, data);
    	}
    	
    	super.onActivityResult(requestCode, resultCode, data);
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	ActivityChild activity = getCurrentActivity();
    	if(null != activity){
    		return activity.onKeyDown(keyCode, event);
    	}
    	return super.onKeyDown(keyCode, event);
    }
    
    public String getActivityName(Class<?> cls){
    	return cls.getSimpleName();
    }
    
    /**
	 * 销毁子activity
	 * @param activity
	 */
	public void finishChild(Activity activity){
		if(activity == null){
			return;
		}
		mLocalActivityManager.destroyActivity(activity.getClass().getSimpleName(), true);
	}
}
