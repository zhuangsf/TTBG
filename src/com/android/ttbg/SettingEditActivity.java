package com.android.ttbg;


import java.io.File;

import com.android.ttbg.util.FileUtil;
import com.android.ttbg.util.OperatingSP;
import com.android.ttbg.util.Utils;
import com.android.ttbg.view.SelectPicPopupWindow;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingEditActivity extends Activity {
	
    private ImageView  title_back;
    private LinearLayout mainLayout;

	private static final int REQUESTCODE_PICK = 0; // 相册选图标记
	private static final int REQUESTCODE_TAKE = 1; // 相机拍照标记
	private static final int REQUESTCODE_CUTTING = 2; // 图片裁切标记
	private SelectPicPopupWindow menuWindow; // 自定义的头像编辑弹出框
	private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 头像文件名称
	private static final String IMAGE_FILE_NAME_CROP = "avatarImage_crop.jpg";// 头像文件名称
	private String urlpath; // 图片本地路径
	
	private ImageView userHead;
	private TextView tv_user_name;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting_edit);
		
		
		 title_back = (ImageView)findViewById(R.id.title_back);
		 title_back.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
		        	finish();
		        }  
		  }); 
		 
		 
		 
		 mainLayout = (LinearLayout) findViewById(R.id.main_layout);
		 View item_edit_icon = (View)findViewById(R.id.item_edit_icon);
		 item_edit_icon.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					menuWindow = new SelectPicPopupWindow(SettingEditActivity.this, itemsOnClick);
					menuWindow.showAtLocation(mainLayout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
		        }  
		  }); 
		 //用户头像
		 userHead = (ImageView) findViewById(R.id.iv_setting_item_icon);
		 
		 
		 View item_setting_edit = (View)findViewById(R.id.item_edit_name);
		 item_setting_edit.setOnClickListener(new View.OnClickListener() {  
		        public void onClick(View v) {  
					Intent intent = new Intent(SettingEditActivity.this, SettingNameActivity.class);
					startActivity(intent);
		        }  
		  }); 
		  //昵称的值
		 tv_user_name = (TextView)findViewById(R.id.tv_setting_item_tips);
		 updateUserName();
	}
	
	@Override
    public void onResume() {
        super.onResume();
        
        updateUserName();
    }
	private void updateUserName()
	{
		if(tv_user_name != null)
		{
			String user_name = OperatingSP.getUserName(SettingEditActivity.this);
			tv_user_name.setText(user_name);
		}
	}
	private void updateUserHead(Drawable drawable)
	{
		if(userHead != null)
		{
			userHead.setImageDrawable(drawable);
		}
	}
	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUESTCODE_PICK:// 直接从相册获取
			try {
				startPhotoZoom(data.getData());
			} catch (NullPointerException e) {
				e.printStackTrace();// 用户点击取消操作
			}
			break;
		case REQUESTCODE_TAKE:// 调用相机拍照
			startPhotoZoom(getTakePicSaveUri());
			break;
		case REQUESTCODE_CUTTING:// 取得裁剪后的图片
			try {
				setPicToView(data);
			} catch (Exception e) {
				e.printStackTrace();// 用户点击取消操作
			}
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 裁剪图片方法实现
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		intent.putExtra("scale", true);// 去黑边
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 300);
		// the return data true may waste logs of mem
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, getCropPicSaveUri());
		startActivityForResult(intent, REQUESTCODE_CUTTING);
	}

	/**
	 * 保存裁剪之后的图片数据
	 * 
	 * @param picdata
	 */
	private void setPicToView(Intent picdata) {
		Bundle extras = picdata.getExtras();
		Context context = SettingEditActivity.this;
		if (extras != null) {
			// 取得SDCard图片路径做显示
			// Bitmap photo = extras.getParcelable("data");
			Bitmap photo = getBitmapFromUri(getCropPicSaveUri(), context);
			Drawable drawable = new BitmapDrawable(null, photo);
			urlpath = FileUtil.saveFile(context, Utils.getInternelStoragePath(SettingEditActivity.this), IMAGE_FILE_NAME, photo);
			OperatingSP.saveUserImage(context,urlpath);
			// 更新头像
			updateUserHead(drawable);
			
			
			//上传到服务器的代码,暂时还未处理
/*			try {
				// send to server
				new Thread(new Runnable() {
					@Override
					public void run() {
						SharedPreferences p = Utils.getSharedPpreference(getActivity());
						final String phone = p.getString(Utils.SHARE_PREFERENCE_CUP_PHONE, "");
						final String accountid = p.getString(Utils.SHARE_PREFERENCE_CUP_ACCOUNTID, "");

						// http://121.199.75.79:8280//user/updateProfile.do
						if (!TextUtils.isEmpty(urlpath)) {
//							Utils.httpPostFile(Utils.URL_PATH + "/user/updateProfile.do", urlpath, mHandler, accountid, phone);
						}
					}
				}).start();
			} catch (Exception ee) {
				Utils.Log("httpPut error:" + ee);
				ee.printStackTrace();
			}*/

		}
	}

	public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
		try {
			// 读取uri所在的图片
			Bitmap bitmap = BitmapFactory.decodeStream(mContext.getContentResolver().openInputStream(uri));
			bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	// save and edit pic uri can not be same or it will 0byte
	private Uri getTakePicSaveUri() {
		String filePath = Utils.getInternelStoragePath(SettingEditActivity.this);
		Utils.Log("getTakePicSaveUri filePath = " + filePath);
		File file = new File(filePath);
		Utils.Log("getTakePicSaveUri file = " + file);
		
		if(file != null)
		{
			Utils.Log("getTakePicSaveUri file.exists() = " + file.exists());
		}
		
		if (file != null && !file.exists()) {
			boolean createSuccess = file.mkdirs();
			Utils.Log("getTakePicSaveUri createSuccess  = " + createSuccess);
		}

		return Uri.fromFile(new File(filePath, IMAGE_FILE_NAME));
	}
	private Uri getCropPicSaveUri() {

		String filePath = Utils.getInternelStoragePath(SettingEditActivity.this);
		File file = new File(filePath);
		if (!file.exists()) {
			file.mkdirs();
		}

		return Uri.fromFile(new File(filePath, IMAGE_FILE_NAME_CROP));
	}
	// 为弹出窗口实现监听类
	private OnClickListener itemsOnClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			menuWindow.dismiss();
			switch (v.getId()) {
			// 拍照
			case R.id.takePhotoBtn:
				Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				// 下面这句指定调用相机拍照后的照片存储的路径
				takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, getTakePicSaveUri());
				startActivityForResult(takeIntent, REQUESTCODE_TAKE);
				
				
			//	getTakePicSaveUri();
				break;
			// 相册选择图片
			case R.id.pickPhotoBtn:
				Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
				// 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
				pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
				startActivityForResult(pickIntent, REQUESTCODE_PICK);
				break;
			default:
				break;
			}
		}
	};
	
	

}