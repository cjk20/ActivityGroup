package com.example.activitygroup.base;

import java.io.Serializable;



public class ParentHelper implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ActivityGroup parent;
	
	public ParentHelper(ActivityGroup parent){
		this.parent = parent;
	}
	
}
