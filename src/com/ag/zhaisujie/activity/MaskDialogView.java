package com.ag.zhaisujie.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.LinearLayout;

import com.ag.zhaisujie.R;
import com.ag.zhaisujie.utils.CustomGifView;

/**
 * MaskDialog.java
 * 
 * @author max.Luo
 * @email max_null@sina.com 2013-6-11
 */
public class MaskDialogView {
	private Dialog dialog;
	private String title = "≤È—Ø÷–,«Î…‘∫Ú...";

	private CustomGifView progressImg;
	private boolean isShow = false;

	public MaskDialogView(Activity mActivity) {
		dialog = new Dialog(mActivity, R.style.mask_dialog);
		LinearLayout popView = (LinearLayout) LayoutInflater.from(mActivity)
				.inflate(R.layout.mask_dialog_view, null);

		progressImg = (CustomGifView) popView.findViewById(R.id.mask_process);
		progressImg.setResource(R.drawable.load_small_blue);

		dialog.setContentView(popView, new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dialog.setFeatureDrawableAlpha(Window.FEATURE_OPTIONS_PANEL, 0);

		dialog.setCancelable(false);
	}

	public void show() {
		if (!isShow) {
			dialog.show();
			isShow = true;
		}

	}

	public void hide() {
		if (isShow) {
			dialog.dismiss();
			isShow = false;
		}
	}

	public boolean isShow() {
		return isShow;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public CustomGifView getProgressImg() {
		return progressImg;
	}

	public void setProgressImg(CustomGifView progressImg) {
		this.progressImg = progressImg;
	}
}
