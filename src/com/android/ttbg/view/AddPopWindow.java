package com.android.ttbg.view;

import com.android.ttbg.R;
import com.android.ttbg.util.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class AddPopWindow extends PopupWindow {
	private View conentView;

	public AddPopWindow(final Activity context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conentView = inflater.inflate(R.layout.add_popup_dialog, null);
		int h = context.getWindowManager().getDefaultDisplay().getHeight();
		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		this.setContentView(conentView);
		this.setWidth(w / 2 + 50);
		this.setHeight(LayoutParams.WRAP_CONTENT);
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// ˢ��״̬
		this.update();
		ColorDrawable dw = new ColorDrawable(0000000000);
		this.setBackgroundDrawable(dw);
		// mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog)
		this.setAnimationStyle(R.style.AnimationPreview);
		LinearLayout menu_history = (LinearLayout) conentView
				.findViewById(R.id.menu_history);
		LinearLayout menu_share = (LinearLayout) conentView
				.findViewById(R.id.menu_share);
		LinearLayout menu_help = (LinearLayout) conentView
				.findViewById(R.id.menu_help);
		
		menu_help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AddPopWindow.this.dismiss();
			}
		});
		menu_history.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AddPopWindow.this.dismiss();
			}
		});

		menu_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AddPopWindow.this.dismiss();
			}
		});
		
		if(Utils.WTF){
			menu_history.setVisibility(View.GONE);
		}
	}

	/**
	 * ��ʾpopupWindow
	 * 
	 * @param parent
	 */
	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 18);
		} else {
			this.dismiss();
		}
	}
}
