package com.searun.orderoperation.activity;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.ImageAlertDlialog;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.customview.SelectPicPopupWindow;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.GetAttachmentsHandler;
import com.searun.orderoperation.datahandler.SubmitOrderPhotoHandler;
import com.searun.orderoperation.entity.ImageDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SCM_AttachMentDto;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.entity.VehicleStatusDto;
import com.searun.orderoperation.jsonparser.AttachmentsJsonParser;
import com.searun.orderoperation.jsonparser.ResultCodeJsonParser;
import com.searun.orderoperation.util.CommonUtils;
import com.zzb.pubcontrols.controls.RemoteImageView;
import com.zzb.pubcontrols.utils.ToastUtil;

/**
 * 上传订单操作
 * 
 * @author zhazhaobao
 * 
 */
public class UploadOrderActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 订单号
	 */
	private TextView order_number;

	/**
	 * 照片layout
	 */
	private LinearLayout photo_layout;

	private String filePath;
	private String photoURL;
	private Bitmap resultBitmap;

	private final int REQUEST_CODE_PHOTOALBUM = 500;
	private final int REQUEST_CODE_PHOTO = 501;
	private final int REQUEST_CODE_PHOTOOK = 502;
	private final int REQUEST_CODE_PICK = 503;

	private Context context;

	/**
	 * 调度单
	 */
	private TmDispatchDto tmDispatchDto;

	// private VehicleStatusDto vehicleStatusDto;

	/**
	 * 显示进度条
	 */
	private final int SHOW_PROGRESS = 2000;
	/**
	 * 关闭进度条
	 */
	private final int CLOSE_PROGRESS = 2001;

	private final int SHOW_TOAST = 2002;

	private ProgressAlertDialog progressDialog;

	private SharedPreferences sPreferences;

	private Boolean isCheckPhoto;

	private LinearLayout operation_layout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activitiy_upload_order);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.OPERATION_PREFERENCES,
				Context.MODE_PRIVATE);
		tmDispatchDto = (TmDispatchDto) getIntent().getSerializableExtra(
				"tmDispatchDto");
		isCheckPhoto = (Boolean) getIntent().getBooleanExtra("isCheckPhoto",
				false);
		initView();
		if(isCheckPhoto){
			doGetAttachments();
		}
		CommonUtils.addActivity(this);
	}
	/**
	 * 获取照片信息
	 */
	private void doGetAttachments() {
		PdaRequest<SCM_AttachMentDto> request = new PdaRequest<SCM_AttachMentDto>();
		SCM_AttachMentDto data = new SCM_AttachMentDto();
		data.setPbillid(tmDispatchDto.getDispatchId());
		data.setTblName("Dispatch_Order");
		request.setData(data);
		GetAttachmentsHandler dataHandler = new GetAttachmentsHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.ordersystem_title_hint);

		order_number = (TextView) findViewById(R.id.order_number);
		order_number.setText(String.format(
				getResources().getString(R.string.dispatch_number_hint),
				tmDispatchDto.getDh_dispatch_no()));
		photo_layout = (LinearLayout) findViewById(R.id.photo_layout);

		operation_layout = (LinearLayout) findViewById(R.id.operation_layout);
		operation_layout.setVisibility(isCheckPhoto ? View.GONE : View.VISIBLE);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.photograph_btn:
			doPhotoGraph();
			break;
		case R.id.upload_order_btn:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doEnsureUpload();
				}
			}).start();
			break;

		default:
			break;
		}
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_PROGRESS:
				showProgress();
				break;
			case CLOSE_PROGRESS:
				dismissProgress();
				break;
			case SHOW_TOAST:
				ToastUtil.show(context, msg.obj.toString());
				break;

			default:
				break;
			}
		};
	};

	private void showProgress() {
		if (progressDialog == null) {
			progressDialog = new ProgressAlertDialog(this);
		} else {
			progressDialog.show();
		}
	}

	private void dismissProgress() {
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}

	private void doEnsureUpload() {
		if (null == tmDispatchDto.getAttatchMentDtos()
				|| tmDispatchDto.getAttatchMentDtos().size() == 0) {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = "回单为空,请上传回单后确定上传";
			msg.sendToTarget();
			return;
		}
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<TmDispatchDto> request = new PdaRequest<TmDispatchDto>();
		VehicleStatusDto vehicleStatusDto = new VehicleStatusDto();
		vehicleStatusDto.setVehicle_num(sPreferences
				.getString("vehicleNum", ""));
		vehicleStatusDto.setTask_status(5);
		vehicleStatusDto.setLat(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("latitude", "0"))));
		vehicleStatusDto.setLng(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("longitude", "0"))));
		vehicleStatusDto
				.setLocation_desc(sPreferences.getString("address", ""));
		vehicleStatusDto.setTask_type(sPreferences.getInt("vehicleType", 0));
		vehicleStatusDto.setMobile(sPreferences.getString("phone", ""));
		tmDispatchDto.setVehicleStatusDto(vehicleStatusDto);
		request.setData(tmDispatchDto);
		SubmitOrderPhotoHandler dataHandler = new SubmitOrderPhotoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private void doPhotoGraph() {
		showOptionDialog(REQUEST_CODE_PHOTOALBUM, REQUEST_CODE_PHOTO);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;

		default:
			break;
		}
	}

	private void showOptionDialog(final int photoCode, final int cameraCode) {
		final SelectPicPopupWindow dialog = new SelectPicPopupWindow(this);
		dialog.setFirstButtonContent(getResources().getString(
				R.string.take_photo_hint));
		dialog.setFirstButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// CommonUtils.selectCameraPhone(cameraCode, resultPath,
				// AddNewDriverActivity.this);
				takePhoto(cameraCode);
				dialog.dismiss();
			}
		});
		dialog.setSecendButtonContent(getResources().getString(
				R.string.get_system_photo_hint));
		dialog.setSecendButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				selectSystemPhone(photoCode);
				dialog.dismiss();
			}
		});
		dialog.setThirdButtonContent(getResources().getString(R.string.cancel));
		dialog.setThirdButtonOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		// 显示窗口
		dialog.showAtLocation(this.findViewById(R.id.main), Gravity.BOTTOM
				| Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
	}

	/**
	 * 
	 * 裁剪图片方法实现
	 * 
	 * 
	 * 
	 * @param uri
	 */
	public void startPhotoZoom(Uri uri, int photoook) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);

		photoURL = ConstantPool.DEFAULT_ICON_PATH + System.currentTimeMillis()
				+ ".jpg";
		File tempFile = new File(photoURL);
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			startActivityForResult(intent, photoook);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cropPhoto(String filePath, int pick) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(Uri.parse(filePath), "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 5);
		intent.putExtra("aspectY", 3);
		intent.putExtra("outputX", 480);
		intent.putExtra("outputY", 480);
		intent.putExtra("scale", true);

		// File tempFile = new File(ConstantPool.DEFAULT_ICON_PATH
		// + "image_diy_resultphoto.jpg");
		photoURL = ConstantPool.DEFAULT_ICON_PATH + System.currentTimeMillis()
				+ ".jpg";
		File tempFile = new File(photoURL);
		intent.putExtra("output", Uri.fromFile(tempFile));
		intent.putExtra("outputFormat", "JPEG");// 返回格式
		try {
			startActivityForResult(intent, pick);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void takePhoto(int photo) {
		Intent intent = new Intent();
		intent.setAction("android.media.action.IMAGE_CAPTURE");
		Bundle bundle = new Bundle();

		String path = ConstantPool.DEFAULT_ICON_PATH;
		if (path != null) {
			filePath = "file://" + path + "image_diy_takephoto.jpg";
			Log.v("filePath", filePath);
			Uri uri = Uri.parse(filePath);
			bundle.putParcelable(MediaStore.EXTRA_OUTPUT, uri);
			intent.putExtras(bundle);
			try {
				startActivityForResult(intent, photo);
			} catch (Exception e) {
				ToastUtil.show(
						this,
						getResources().getString(
								R.string.msg_send_nophoto_prompt));
			}
		}
	}

	private void selectSystemPhone(final int photoCode) {

		if (!CommonUtils.CheckExternStorage(this)) {
			ToastUtil.show(this,
					getResources().getString(R.string.msg_no_sdcard));
			return;
		}
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
				"image/*");
		startActivityForResult(intent, photoCode);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case REQUEST_CODE_PHOTOALBUM:
			if (data != null) {
				startPhotoZoom(data.getData(), REQUEST_CODE_PHOTOOK);
			}
			break;
		case REQUEST_CODE_PHOTO:
			filePath = "file://" + ConstantPool.DEFAULT_ICON_PATH
					+ "image_diy_takephoto.jpg";
			if (filePath != null) {
				cropPhoto(filePath, REQUEST_CODE_PICK);
			}
			break;
		case REQUEST_CODE_PHOTOOK:
			// resultBitmap = BitmapFactory
			// .decodeFile(ConstantPool.DEFAULT_ICON_PATH
			// + "image_diy_resultphoto_temp.jpg");
			resultBitmap = BitmapFactory.decodeFile(photoURL);
			addPhotoIntoLayout(context, photoURL, resultBitmap);
			break;
		case REQUEST_CODE_PICK:
			// resultBitmap = BitmapFactory
			// .decodeFile(ConstantPool.DEFAULT_ICON_PATH
			// + "image_diy_resultphoto.jpg");
			// addPhotoIntoLayout(context, resultBitmap);
			resultBitmap = BitmapFactory.decodeFile(photoURL);
			addPhotoIntoLayout(context, photoURL, resultBitmap);
			break;

		default:
			break;
		}
	}

	private void addPhotoIntoLayout(Context context, final String photoURL,
			Bitmap bitmap) {
		if (TextUtils.isEmpty(photoURL))
			return;
		View view = LayoutInflater.from(context).inflate(
				R.layout.upload_photo_layout, null);
		RemoteImageView image = (RemoteImageView) view
				.findViewById(R.id.upload_photo_img);
		ImageView delete = (ImageView) view
				.findViewById(R.id.upload_photo_delete);
		image.draw(photoURL, ConstantPool.DEFAULT_ICON_PATH, false, false);
		image.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 显示回单
				doShowPeceipt(photoURL);
			}
		});
		delete.setTag(photoURL);
		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				deletePhotoIntoLayout(v.getTag().toString());
			}
		});
		ImageDto uploadDto = new ImageDto();
		uploadDto.setImageSuffix("PNG");
		uploadDto.setFile(CommonUtils.getBitmapByByte(bitmap));
		uploadDto.setHeaderImgURL(photoURL);
		SCM_AttachMentDto photoList = new SCM_AttachMentDto();
		photoList.setPath(uploadDto);
		photo_layout.addView(view);
		tmDispatchDto.setAttatchMentDtos(new ArrayList<SCM_AttachMentDto>());
		tmDispatchDto.getAttatchMentDtos().add(photoList);
	}

	/**
	 * 删除回单
	 * 
	 * @param photoURL2
	 */
	protected void deletePhotoIntoLayout(String photoURL2) {
		int size = photo_layout.getChildCount();
		for (int i = 0; i < size; i++) {
			if (tmDispatchDto.getAttatchMentDtos().get(i).getPath()
					.getHeaderImgURL().equalsIgnoreCase(photoURL2)) {
				photo_layout.removeViewAt(i);
				tmDispatchDto.getAttatchMentDtos().remove(i);
			}
		}
	}

	/**
	 * 显示回单
	 */
	protected void doShowPeceipt(String imagePath) {
		ImageAlertDlialog dialog = new ImageAlertDlialog(this, imagePath, "");
		dialog.show();
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.SUBMIT_ORDER_PHOTO_OK:
			doSubmitOrderPhotoSuccess(data);
			break;
		case NetWork.GET_ATTACHMENTS_OK:
			doGetAttachmentsSuccess(data);
			break;
		case NetWork.GET_ATTACHMENTS_ERROR:
		case NetWork.GET_ORDER_SYSTEM_INFO_ERROR:
		case NetWork.GET_ORDER_SYSTEM_ORDER_INFO_ERROR:
		case NetWork.SUBMIT_ORDER_PHOTO_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		default:
			break;
		}
	}
	
	/**
	 * 获取订单图片成功
	 * 
	 * @param data
	 */
	private void doGetAttachmentsSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String result = "";
		Message msg = myHandler.obtainMessage();
		msg.what = SHOW_TOAST;
		try {
			PdaResponse<List<SCM_AttachMentDto>> response = AttachmentsJsonParser
					.parserAttachmentsJson(dataString);
			if (null == response) {
				ToastUtil.show(context, "获取图片信息失败，请重新获取");
				return;
			}
			if (response.isSuccess()) {
				List<SCM_AttachMentDto> resultData = response.getData();
				for (final SCM_AttachMentDto dto : resultData) {
					View view = LayoutInflater.from(context).inflate(
							R.layout.upload_photo_layout, null);
					RemoteImageView image = (RemoteImageView) view
							.findViewById(R.id.upload_photo_img);
					ImageView delete = (ImageView) view
							.findViewById(R.id.upload_photo_delete);
					delete.setVisibility(View.GONE);
					image.draw(dto.getPath().getHeaderImgURL(),
							ConstantPool.DEFAULT_ICON_PATH, false, false);
					image.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// 显示回单
							doShowPeceipt(dto.getPath().getHeaderImgURL());
						}
					});
					delete.setTag(dto.getPath().getHeaderImgURL());
					delete.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							deletePhotoIntoLayout(v.getTag().toString());
						}
					});
					photo_layout.addView(view);
					tmDispatchDto.getAttatchMentDtos().add(dto);
				}
			} else {
				result = response.getMessage();
				msg.obj = result;
				msg.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = getResources().getString(R.string.network_error_hint);
			msg.obj = result;
			msg.sendToTarget();
		}
	}

	/**
	 * 上传回单成功
	 * 
	 * @param data
	 */
	private void doSubmitOrderPhotoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			String result = response.getMessage();
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			if (response.isSuccess()) {
				msg.obj = "回单已上传保存";
				Intent intent = new Intent(UploadOrderActivity.this,
						MainActivity.class);
				startActivity(intent);
				CommonUtils.finishAllActivity();
			} else {
				msg.obj = result;
			}
			msg.sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
