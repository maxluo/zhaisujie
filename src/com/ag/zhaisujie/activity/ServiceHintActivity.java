package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ag.zhaisujie.R;
import com.ag.zhaisujie.model.Hint;

/**
 * 信息提示界面
 * 
 * @author max.Luo
 * @email max_null@sina.com
 * 
 */
public class ServiceHintActivity extends BaseActivity {
	private Button backBtn;

	private TextView hintInfo;
	private ImageView hintPic;

	private Hint hintFlag = Hint.NO_SERVICE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_hint);

		Bundle bundle = this.getIntent().getExtras();
		if(bundle != null) {
			hintFlag = (Hint)bundle.getSerializable("hintFlag");
			if(hintFlag == null) {
				hintFlag = Hint.NO_SERVICE;
			}
		}
		
		
		TextView title = (TextView) findViewById(R.id.title);
		title.setText(R.string.no_service_title);
		backBtn = (Button) findViewById(R.id.title_btn_back);
		backBtn.setVisibility(View.VISIBLE);
		backBtn.setOnClickListener(listener);
		
		hintInfo = (TextView) findViewById(R.id.hint_info);
		hintPic = (ImageView) findViewById(R.id.hint_pic);
		// 根据枚举内容显示不同界面提示信息
		switch (hintFlag) {
		case NO_SERVICE:
			hintPic.setImageResource(R.drawable.no_service);
			hintInfo.setText(getString(R.string.no_service_hint));
			break;
		default:
			break;
		}
	}

	OnClickListener listener = new OnClickListener() {
		public void onClick(View v) {
			Button btn = (Button) v;
			switch (btn.getId()) {
			case R.id.title_btn_back:
				ServiceHintActivity.this.finish();
				break;
			}
		}
	};
}
