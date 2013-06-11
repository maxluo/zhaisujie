package com.ag.zhaisujie.activity;

import org.json.JSONObject;
import org.json.JSONTokener;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
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
import com.ag.zhaisujie.model.Order;
import com.ag.zhaisujie.model.User;
/**
 * 
 *    LoginActivity.java
 *     <p>
 *     ��¼ҳ��
 *     Copyright: Copyright(c) 2013 
 *     @author Gavin_Feng
 *     </p>
 * 
 */
public class LoginActivity extends BaseActivity {
	private TimeCount time;
	private Button backBtn;
	private EditText phoneTxt;
	private EditText codeTxt;
	private Button getCodeBtn;
	private Button loginBtn;
	private CheckBox agreeCbx;
	private String phone="";
	private String pwd="";
	private static final int GET_CODE = 0;
    private static final int LOGIN = 1;
    private MaskDialogView maskDialog;
	
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
		// ��������
		if (maskDialog == null) {
			maskDialog = new MaskDialogView(LoginActivity.this);
		}
	}
	
	Handler handler =  new Handler() {

		@Override
		public void handleMessage(Message msg) {
			
			 switch (msg.what) {  
	           case GET_CODE:  
		       		String rtn=HttpUtil.getCode(phone);
		    		if(App.FAIL.equals(rtn)){
		    			ToastUtil.show(LoginActivity.this, "��ȡ��֤��ʧ�ܣ�");
		    		}else{
		    			time = new TimeCount(60000, 1000);//����CountDownTimer����
		    			time.start();
		    			ToastUtil.show(LoginActivity.this,"ע����ն�����Ϣ��");
		    		}
		    		maskDialog.hide();
	               break; 
	           case LOGIN:
	        	    String rtn1=HttpUtil.login(phone, pwd);
		       		if(App.FAIL.equals(rtn1)){
		       			ToastUtil.show(LoginActivity.this, "��½ʧ�ܣ�");
		       		}else{
		       			try{
		       				User user=new User();
		       				JSONTokener jsonParser = new JSONTokener(rtn1);
		       				JSONObject job= (JSONObject)jsonParser.nextValue();
		       				user.setUid(job.getInt("uid"));
		       				user.setUserName(job.getString("username"));
		       				if(App.getInstance()!=null){
		       					App.getInstance().setUser(user);
		       					MainActivity.initButton();
		       					dispatch();
		       				}
		       			}catch(Exception ex){
		       				ex.printStackTrace();
		       				ToastUtil.show(LoginActivity.this, "��½�쳣��");
		       			}
		       		}
		       		maskDialog.hide();
	        	   break;
			 }
		}

	};
	
	//�����֤��
	private void getCode(){
		maskDialog.show();
		phone=phoneTxt.getText().toString().trim();
		if("".equals(phoneTxt)){
			ToastUtil.show(this, "�������ֻ����룡");
			phoneTxt.requestFocus();
			maskDialog.hide();
			return;
		}else if(!ValidUtil.isMobileNO(phone)){
			ToastUtil.show(this, "�ֻ�����Ƿ���");
			phoneTxt.requestFocus();
			maskDialog.hide();
			return;
		}
		Message msg = new Message();
		msg.what=GET_CODE;
		handler.sendMessage(msg);
	}
	
	private void login(){
		maskDialog.show();
		phone=phoneTxt.getText().toString().trim();
		pwd=codeTxt.getText().toString().trim();
		if("".equals(phone)){
			ToastUtil.show(this, "�������ֻ����룡");
			phoneTxt.requestFocus();
			maskDialog.hide();
			return;
		}else if(!ValidUtil.isMobileNO(phone)){
			ToastUtil.show(this, "�ֻ�����Ƿ���");
			phoneTxt.requestFocus();
			maskDialog.hide();
			return;
		}else if("".equals(pwd)){
			ToastUtil.show(this, "��������֤�룡");
			phoneTxt.requestFocus();
			maskDialog.hide();
			return;
		}else if(!agreeCbx.isChecked()){
			ToastUtil.show(this, "��ͬ���������Э�飡");
			agreeCbx.requestFocus();
			maskDialog.hide();
			return;
		}
		Message msg = new Message();
		msg.what=LOGIN;
		handler.sendMessage(msg);
		
	}
	
	private void dispatch(){
		
		//���õ�ַ
		if(getIntent().getSerializableExtra("Order")!=null){
			Order order=(Order)getIntent().getSerializableExtra("Order");
			Intent intent = new Intent(LoginActivity.this, OrderFrstActivity.class);
			Bundle bundle = new Bundle();  
			bundle.putSerializable("Order", order);
			intent.putExtras(bundle);//���ݵ�ַ����һҳ��
			LoginActivity.this.startActivity(intent);
		}
		
		this.finish();
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
	/* ����һ������ʱ���ڲ��� */
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// ��������Ϊ��ʱ��,�ͼ�ʱ��ʱ����
		}

		@Override
		public void onFinish() {// ��ʱ���ʱ����
			getCodeBtn.setText("��ȡ��֤��");
			getCodeBtn.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// ��ʱ������ʾ
			getCodeBtn.setClickable(false);
			getCodeBtn.setText("��ȡ��"+millisUntilFinished / 1000 );
		}
	}
}
