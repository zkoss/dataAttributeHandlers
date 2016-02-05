package org.zkoss.handlers.ArborJSTree.viewmodel;

public class ArborJSNodeUtil {

	private int currentId = 0;
	
	/** Provides incremental ids to avoid node name duplicates  */
	public String getNewId(){
		return Integer.toString(++currentId);
	}
	
}
