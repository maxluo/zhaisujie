package com.ag.zhaisujie.activity;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.ag.zhaisujie.App;
import com.ag.zhaisujie.HttpUtil;
import com.ag.zhaisujie.R;
import com.ag.zhaisujie.ToastUtil;
import com.ag.zhaisujie.ValidUtil;
import com.ag.zhaisujie.model.User;
/**
 * 
 *    LoginActivity.java
 *     <p>
 *     登录页面
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class LoginActivity extends Activity {
	private TimeCount time;
	private Button backBtn;
	private EditText phoneTxt;
	private EditText codeTxt;
	private Button getCodeBtn;
	private Button loginBtn;
	private CheckBox agreeCbx;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		TextView title = (TextView)findViewById(R.id.title);
		title.setText(R.string.login_detail_title);
		backBtn=(Button)findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		phoneTxt=(EditText)findViewById(R.id.login_txt_phone);
		codeTxt=(EditText)findViewById(R.id.login_txt_pwd);
		getCodeBtn=(Button)findViewById(R.id.login_btn_get_code);
		loginBtn=(Button)findViewById(R.id.login_btn_login);
		agreeCbx=(CheckBox)findViewById(R.id.login_cbx_agree);
		
		getCodeBtn.setOnClickListener(listener);
		loginBtn.setOnClickListener(listener);
	}
	
	//获得验证码
	private void getCode(){
		String phone=phoneTxt.getText().toString().trim();
		if("".equals(phoneTxt)){
			ToastUtil.show(this, "请输入手机号码！");
			phoneTxt.requestFocus();
			return;
		}else if(!ValidUtil.isMobileNO(phone)){
			ToastUtil.show(this, "手机号码非法！");
			phoneTxt.requestFocus();
			return;
		}
		
		String rtn=HttpUtil.getCode(phone);
		if(App.FAIL.equals(rtn)){
			ToastUtil.show(this, "获取验证码失败！");
		}else{
			time = new TimeCount(60000, 1000);//构造CountDownTimer对象
			time.start();
			ToastUtil.show(this,"注意查收短信消息！");
		}
		
	}
	private void login(){
		String phone=phoneTxt.getText().toString().trim();
		String pwd=codeTxt.getText().toString().trim();
		if("".equals(phone)){
			ToastUtil.show(this, "请输入手机号码！");
			phoneTxt.requestFocus();
			return;
		}else if(!ValidUtil.isMobileNO(phone)){
			ToastUtil.show(this, "手机号码非法！");
			phoneTxt.requestFocus();
			return;
		}else if("".equals(pwd)){
			ToastUtil.show(this, "请输入验证码！");
			phoneTxt.requestFocus();
			return;
		}else if(!agreeCbx.isChecked()){
			ToastUtil.show(this, "请同意软件服务协议！");
			agreeCbx.requestFocus();
			return;
		}
		String rtn=HttpUtil.login(phone, pwd);
		if(App.FAIL.equals(rtn)){
			ToastUtil.show(this, "登陆失败！");
		}else{
			try{
				User user=new User();
				JSONTokener jsonParser = new JSONTokener(rtn);
				JSONObject job= (JSONObject)jsonParser.nextValue();
				user.setUid(job.getInt("uid"));
				user.setUserName(job.getString("username"));
				if(App.getInstance()!=null){
					App.getInstance().setUser(user);
					MainActivity.initButton();
					this.finish();
				}
			}catch(Exception ex){
				ex.printStackTrace();
				ToastUtil.show(this, "登陆异常！");
			}
		}
	}
	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				//Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				//LoginActivity.this.startActivity(intent);
				LoginActivity.this.finish();
				break;
			case R.id.login_btn_get_code:	
				getCode();
				break;
			case R.id.login_btn_login:	
				login();
				break;
			}
		}
	};
	/* 定义一个倒计时的内部类 */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			getCodeBtn.setText("获取验证码");
			getCodeBtn.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			getCodeBtn.setClickable(false);
			getCodeBtn.setText("获取中"+millisUntilFinished / 1000 );
		}
	}
}
