package com.ag.zhaisujie;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 *    App.java
 *     <p>
 *     系统信息
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class App {
	public static final String SHAREDPREFERENCES_NAME = "zhaisujie_pref";
	public static final String USER_NAME = "userSession";
	private User user;
	private Context context;
	private static App app; 
	public static final String FAIL="fail";
	public static final String SUCCES="succes";
	
	public App(Context context){
		this.context=context;
		init();
	}
	
	public static App getInstance(Context context){
		if(app==null){
			app =new App(context);
		}
		return app;
	}
	public static App getInstance(){
		return app;
	}
	private void init(){
		try{
			SharedPreferences preferences = this.context.getSharedPreferences(SHAREDPREFERENCES_NAME,this.context.MODE_PRIVATE);
			// 取得相应值，如果没有该值，
			String userJson=preferences.getString(USER_NAME,"");
			if(userJson==null||userJson.length()==0)
				return;
			JSONTokener jsonParser = new JSONTokener(userJson);
			JSONObject job= (JSONObject)jsonParser.nextValue();
			user =new User();
			user.setUid(job.getInt("uid"));
			user.setUserName(job.getString("username"));
			
		}catch(Exception ex){
			user=null;
		}
	}
	
	public void writeSesson(){
		if(user!=null){
			try{
				JSONObject userJob = new JSONObject(); 
				userJob.put("uid", user.getUid());
				userJob.put("userName", user.getUserName());
				
				SharedPreferences preferences = this.context.getSharedPreferences(SHAREDPREFERENCES_NAME,this.context.MODE_PRIVATE);
				Editor editor = preferences.edit();
	    		// 存入数据
	    		editor.putString(USER_NAME, userJob.toString());
	    		
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		writeSesson();
	}
	
	public Context getContext() {
		return context;
	}
	public void setContext(Context context) {
		this.context = context;
		init();
	}

	
}
