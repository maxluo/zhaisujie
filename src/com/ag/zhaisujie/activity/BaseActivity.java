package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
/**
 * 
 *    BaseActivity.java
 *     <p>
 *     基本页面
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */

public class BaseActivity extends Activity {
	protected BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            finish();  
        }  
    };  
      
    @Override  
    public void onResume() {  
        super.onResume();  
        // 在当前的activity中注册广播  
        IntentFilter filter = new IntentFilter();  
        filter.addAction("ExitApp");  
        this.registerReceiver(this.broadcastReceiver, filter);  
    }  
      
    @Override  
    protected void onDestroy() {  
        // TODO Auto-generated method stub  
        super.onDestroy();  
        this.unregisterReceiver(this.broadcastReceiver);    
    }  
}
