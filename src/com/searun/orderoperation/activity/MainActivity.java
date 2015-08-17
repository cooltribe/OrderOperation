package com.searun.orderoperation.activity;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.adapter.OrderSystemOrderInfoAdapter;
import com.searun.orderoperation.application.ApplicationPool;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.ImageAlertDlialog;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.customview.SelectPicPopupWindow;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.GetAttachmentsHandler;
import com.searun.orderoperation.datahandler.GetOrderSystemInfoHandler;
import com.searun.orderoperation.datahandler.GetOrderSystemOrderInfoHandler;
import com.searun.orderoperation.datahandler.GetVehicleStatusHandler;
import com.searun.orderoperation.datahandler.SignInHandler;
import com.searun.orderoperation.datahandler.SubmitOrderPhoto2Handler;
import com.searun.orderoperation.entity.ImageDto;
import com.searun.orderoperation.entity.PdaPagination;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SCM_AttachMentDto;
import com.searun.orderoperation.entity.SysOrderDto;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.entity.VehicleStatusDto;
import com.searun.orderoperation.jsonparser.AttachmentsJsonParser;
import com.searun.orderoperation.jsonparser.CheckVehichleStatusJsonParser;
import com.searun.orderoperation.jsonparser.OrderSystemInfoJsonParser;
import com.searun.orderoperation.jsonparser.OrderSystemOrderInfoJsonParser;
import com.searun.orderoperation.jsonparser.ResultCodeJsonParser;
import com.searun.orderoperation.map.TailafterMapActivity;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase;
import com.searun.orderoperation.pullrefreshview.PullToRefreshBase.OnRefreshListener;
import com.searun.orderoperation.pullrefreshview.PullToRefreshListView;
import com.searun.orderoperation.util.CommonUtils;
import com.zzb.pubcontrols.controls.RemoteImageView;
import com.zzb.pubcontrols.utils.ToastUtil;

@SuppressLint("NewApi")
public class MainActivity extends BaseActivity implements OnClickListener,
		OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private ImageView maintitle_comfirm_iv;

	/**
	 * 订单 refreshview
	 */
	private PullToRefreshListView ordersystem_content_layout;
	// private ListView ordersystem_content_layout;
	private ListView ordersystem_content_listview;

	/**
	 * 调度单号
	 */
	private TextView dispatchNum;

	/**
	 * 车牌
	 */
	private TextView vehicle_number;

	/**
	 * 车型
	 */
	private TextView vehicle_type;

	/**
	 * 总件数
	 */
	private TextView total_package;

	/**
	 * 总体积
	 */
	private TextView total_volume;

	/**
	 * 总净重
	 */
	private TextView total_weight;

	/**
	 * 主驾驶
	 */
	private TextView main_driver;

	/**
	 * 主驾驶手机号码
	 */
	private TextView main_driver_phone;

	/**
	 * 副驾驶
	 */
	private TextView copilot_name;

	/**
	 * 副驾驶手机号码
	 */
	private TextView copilot_phone;

	/**
	 * 需求每页元素个数
	 */
	private final int pageSize = 5;
	/**
	 * 页数
	 */
	private int pageNum = 0;

	/**
	 * 尾页
	 */
	private int pageEnd = pageSize;

	/**
	 * 一共多少页
	 */
	private int totalPage = 0;

	/**
	 * 是否获取更多数据
	 */
	private boolean isGetMoreData = false;

	/**
	 * APP 初次进入由于数据是直接获得，所以添加此FLAG，获取更多的时候执行判断
	 */
	private boolean isFirstRefreshData = false;

	private Context context;

	private List<SysOrderDto> mDataList;

	private OrderSystemOrderInfoAdapter mAdapter;

	/**
	 * 调度单
	 */
	private TmDispatchDto tmDispatchDto = new TmDispatchDto();

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

	private final int REQUEST_CODE_PHOTOALBUM = 500;
	private final int REQUEST_CODE_PHOTO = 501;
	private final int REQUEST_CODE_PHOTOOK = 502;
	private final int REQUEST_CODE_PICK = 503;

	private String filePath;
	private String photoURL;
	private Bitmap resultBitmap;

	/**
	 * 管理回单布局
	 */
	private LinearLayout ordersystem_photo_layout;

	/**
	 * 2次退出记录时间
	 */
	private long mKeyTime;

	private SharedPreferences sPreferences;

	/**
	 * 警告layout
	 */
	private RelativeLayout warning_layout;

	private Button upload_btn, ensure_upload_btn, upload_btn2, grid_btn,
			arrival_btn, check_photo_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.OPERATION_PREFERENCES,
				Context.MODE_PRIVATE);
		// tmDispatchDto = (TmDispatchDto) getIntent().getSerializableExtra(
		// "tmDispatchDto");
		initView();
		initAdapter();
		// getOrderSystemInfo();
		// checkVehicleStatus();
		getOrderSystemInfo();
		// showView(tmDispatchDto);
		CommonUtils.finishAllActivity();
		CommonUtils.addActivity(this);
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);
		maintitle_back_iv.setVisibility(View.GONE);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.ordersystem_title_hint);

		maintitle_comfirm_iv = (ImageView) findViewById(R.id.maintitle_comfirm_iv);
		maintitle_comfirm_iv.setVisibility(View.VISIBLE);
		maintitle_comfirm_iv.setOnClickListener(this);

		dispatchNum = (TextView) findViewById(R.id.dispatchNum);
		vehicle_number = (TextView) findViewById(R.id.vehicle_number);
		vehicle_type = (TextView) findViewById(R.id.vehicle_type);
		total_package = (TextView) findViewById(R.id.total_package);
		total_volume = (TextView) findViewById(R.id.total_volume);
		total_weight = (TextView) findViewById(R.id.total_weight);
		main_driver = (TextView) findViewById(R.id.main_driver_name);
		main_driver_phone = (TextView) findViewById(R.id.main_driver_phone);
		copilot_name = (TextView) findViewById(R.id.copilot_name);
		copilot_phone = (TextView) findViewById(R.id.copilot_phone);

		ordersystem_photo_layout = (LinearLayout) findViewById(R.id.ordersystem_photo_layout);
		warning_layout = (RelativeLayout) findViewById(R.id.warning_layout);

		// upload_btn,ensure_upload_btn,upload_btn2,grid_btn;
		upload_btn = (Button) findViewById(R.id.upload_btn);
		ensure_upload_btn = (Button) findViewById(R.id.ensure_upload_btn);
		upload_btn2 = (Button) findViewById(R.id.upload_btn2);
		grid_btn = (Button) findViewById(R.id.grid_btn);
		arrival_btn = (Button) findViewById(R.id.arrival_btn);
		check_photo_btn = (Button) findViewById(R.id.check_photo_btn);
	}

	private void initAdapter() {
		ordersystem_content_layout = (PullToRefreshListView) findViewById(R.id.ordersystem_content_layout);
		// ordersystem_content_layout = (ListView)
		// findViewById(R.id.ordersystem_content_layout);
		ordersystem_content_listview = ordersystem_content_layout
				.getRefreshableView();
		// mDataList = new ArrayList<SysOrderDto>();
		// mAdapter = new OrderSystemOrderInfoAdapter(context, mDataList);
		ordersystem_content_listview.setAdapter(mAdapter);
		// ordersystem_content_layout.setAdapter(mAdapter);
		ordersystem_content_layout
				.setOnRefreshListener(new OnRefreshListener() {

					@Override
					public void onRefresh(int scrollState) {
						if (scrollState == PullToRefreshBase.STATE_OF_HEADER) {
							pageNum = 0;
							isGetMoreData = false;
							isFirstRefreshData = false;
							pageEnd = pageSize;
							getOrderSystemOrderInfo(pageNum, pageEnd,
									tmDispatchDto);
						} else if (scrollState == PullToRefreshBase.STATE_OF_FOOTER) {
							if (totalPage == 0) {
								isFirstRefreshData = true;
								getOrderSystemOrderInfo(pageNum, pageEnd,
										tmDispatchDto);
							} else if (pageNum < totalPage) {
								isGetMoreData = true;
								isFirstRefreshData = false;
								pageNum = pageNum + pageSize;
								pageEnd = pageNum + pageSize;
								getOrderSystemOrderInfo(pageNum, pageEnd,
										tmDispatchDto);
							} else {
								ToastUtil.show(context, "没有更多数据");
								ordersystem_content_layout.onRefreshComplete();
							}
						}
					}
				});
	}

	private void checkVehicleStatus() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<VehicleStatusDto> request = new PdaRequest<VehicleStatusDto>();
		VehicleStatusDto vehicleStatusDto = new VehicleStatusDto();
		vehicleStatusDto.setVehicle_num(sPreferences
				.getString("vehicleNum", ""));
		// vehicleStatusDto.setTask_status(1);
		vehicleStatusDto.setDispatch_id(tmDispatchDto.getDispatchId());
		request.setData(vehicleStatusDto);
		GetVehicleStatusHandler dataHandler = new GetVehicleStatusHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取作业信息
	 * 
	 */
	protected void getOrderSystemInfo() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<String> request = new PdaRequest<String>();
		request.setData("");
		GetOrderSystemInfoHandler dataHandler = new GetOrderSystemInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 获取订单信息
	 * 
	 * @param pageNum
	 */
	protected void getOrderSystemOrderInfo(int pageNum, int pageEnd,
			TmDispatchDto tmDispatchDto) {
		PdaRequest<TmDispatchDto> request = new PdaRequest<TmDispatchDto>();
		PdaPagination pagination = new PdaPagination();
		pagination.setStartPos(pageNum);
		pagination.setAmount(pageSize);
		pagination.setEndPos(pageEnd);
		request.setData(tmDispatchDto);
		request.setPagination(pagination);
		GetOrderSystemOrderInfoHandler dataHandler = new GetOrderSystemOrderInfoHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.upload_btn:
			showOptionDialog(REQUEST_CODE_PHOTOALBUM, REQUEST_CODE_PHOTO);
			break;
		case R.id.tailafter_btn:
			doTailafterBtn();
			break;
		case R.id.ensure_upload_btn:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doEnsureUpload();
				}
			}).start();
			break;
		case R.id.upload_btn2:
			doUploadOrder();
			break;
		case R.id.grid_btn:
			new Thread(new Runnable() {

				@Override
				public void run() {
					doGrid();
				}
			}).start();
			break;
		case R.id.arrival_btn:
			Intent intent = new Intent(this,
					ArrivalGoodsOperationActivity.class);
			intent.putExtra("tmDispatchDto", tmDispatchDto);
			startActivity(intent);
			break;
		case R.id.check_photo_btn:
			Intent photoIntent = new Intent(this, UploadOrderActivity.class);
			photoIntent.putExtra("tmDispatchDto", tmDispatchDto);
			photoIntent.putExtra("isCheckPhoto", true);
			startActivity(photoIntent);
			break;

		default:
			break;
		}

	}

	/**
	 * 发车
	 */
	private void doGrid() {

		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		VehicleStatusDto vehicleStatusDto = new VehicleStatusDto();
		vehicleStatusDto.setVehicle_num(sPreferences.getString("vehicleNum",
				"0"));
		vehicleStatusDto.setLat(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("latitude", "0"))));
		vehicleStatusDto.setLng(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("longitude", "0"))));
		vehicleStatusDto
				.setLocation_desc(sPreferences.getString("address", ""));
		vehicleStatusDto.setMobile(sPreferences.getString("phone", ""));
		vehicleStatusDto.setTask_type(sPreferences.getInt("vehicleType", 0));
		vehicleStatusDto.setTask_status(3);
		PdaRequest<VehicleStatusDto> request = new PdaRequest<VehicleStatusDto>();
		request.setData(vehicleStatusDto);
		SignInHandler handler = new SignInHandler(context, request);
		handler.setOnDataReceiveListener(this);
		handler.startNetWork();

	}

	/**
	 * 上传回单
	 */
	private void doUploadOrder() {
		Intent intent = new Intent(this, UploadOrderActivity.class);
		intent.putExtra("tmDispatchDto", tmDispatchDto);
		startActivity(intent);
	}

	private void doTailafterBtn() {

		Intent intent = new Intent(this, TailafterMapActivity.class);
		startActivity(intent);

		// Intent intent = new Intent(this, BDLocationService.class);
		// startService(intent);
	}

	private void doEnsureUpload() {
		if (null == tmDispatchDto.getAttatchMentDtos()
				|| tmDispatchDto.getAttatchMentDtos().size() == 0) {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = "订单为空,请上传订单后确定上传";
			msg.sendToTarget();
			return;
		}
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<TmDispatchDto> request = new PdaRequest<TmDispatchDto>();
		VehicleStatusDto vehicleStatusDto = new VehicleStatusDto();
		vehicleStatusDto.setVehicle_num(sPreferences.getString("vehicleNum",
				"0"));
		vehicleStatusDto.setLat(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("latitude", "0"))));
		vehicleStatusDto.setLng(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("longitude", "0"))));
		vehicleStatusDto
				.setLocation_desc(sPreferences.getString("address", ""));
		vehicleStatusDto.setMobile(sPreferences.getString("phone", ""));
		vehicleStatusDto.setTask_type(sPreferences.getInt("vehicleType", 0));
		vehicleStatusDto.setTask_status(2);
		tmDispatchDto.setVehicleStatusDto(vehicleStatusDto);
		request.setData(tmDispatchDto);
		SubmitOrderPhoto2Handler dataHandler = new SubmitOrderPhoto2Handler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
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
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			doSecendBack();
			break;
		case R.id.maintitle_comfirm_iv:
			Intent intent = new Intent(this, HistoryOrderSystemActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	/**
	 * 2次退出
	 */
	private void doSecendBack() {
		if (MainActivity.this.isFinishing()) {
			return;
		}
		long currentTime = System.currentTimeMillis();
		if (currentTime - mKeyTime > 2000) {
			mKeyTime = currentTime;
			ToastUtil.show(context, R.string.Secend_Back_hint);
		} else {
			CommonUtils.finishAllActivity();
		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			doSecendBack();
			return false;
		}
		return super.dispatchKeyEvent(event);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ordersystem_content_layout.onRefreshComplete();
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

	private void showView(TmDispatchDto tmDispatchDto) {
		if (null == tmDispatchDto)
			return;
		warning_layout.setFocusable(true);
		warning_layout.setVisibility(View.GONE);
		String unknow = getResources().getString(R.string.unknow);
		dispatchNum.setText(String
				.format(context.getResources().getString(
						R.string.dispatch_number_hint), TextUtils
						.isEmpty(tmDispatchDto.getDh_dispatch_no()) ? unknow
						: tmDispatchDto.getDh_dispatch_no()));
		vehicle_number.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_truck_license_no()) ? unknow : tmDispatchDto
				.getDh_truck_license_no());
		vehicle_type.setText(TextUtils.isEmpty(tmDispatchDto.getCx()) ? unknow
				: tmDispatchDto.getCx());
		total_package.setText(String.format(
				getResources().getString(R.string.goods_number_piece_hint),
				tmDispatchDto.getLoh_total_packs()));
		total_volume.setText(String.format(
				getResources().getString(R.string.stere_hint),
				null == tmDispatchDto.getLoh_total_cubage() ? unknow
						: tmDispatchDto.getLoh_total_cubage().toString()));
		total_weight
				.setText(String
						.format(getResources().getString(R.string.kg_hint),
								null == tmDispatchDto
										.getLoh_total_gross_weight() ? unknow
										: tmDispatchDto
												.getLoh_total_gross_weight()
												.toString()));
		main_driver.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_primary_driver()) ? unknow : tmDispatchDto
				.getDh_primary_driver());
		main_driver_phone.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_primary_tel()) ? unknow : tmDispatchDto
				.getDh_primary_tel());
		copilot_name.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_secondary_driver()) ? "无" : tmDispatchDto
				.getDh_secondary_driver());
		copilot_phone.setText(TextUtils.isEmpty(tmDispatchDto
				.getDh_secondary_tel()) ? "无" : tmDispatchDto
				.getDh_secondary_tel());
		mDataList = tmDispatchDto.getSysOrderDtos();
		mAdapter = new OrderSystemOrderInfoAdapter(context, mDataList);
		pageNum = mDataList.size();
		pageEnd = pageNum + pageSize;
		ordersystem_content_listview.setAdapter(mAdapter);
		// ordersystem_content_layout.setAdapter(mAdapter);
	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.GET_ORDER_SYSTEM_INFO_OK:
			doGetOrderSystemOrderInfoSuccess(data);
			break;
		case NetWork.GET_ORDER_SYSTEM_ORDER_INFO_OK:
			if (!isFirstRefreshData || !isGetMoreData) {
				doGetOrderSystemInfoSuccess(data);
			} else {
				doGetMoreOrderSystemInfoSuccess(data);
			}
			break;
		case NetWork.SUBMIT_ORDER_PHOTO2_OK:
			doSubmitOrderPhotoSuccess(data);
			break;
		case NetWork.SIGN_IN_OK:
			doSignInSuccess(data);
			break;
		case NetWork.GET_STATUS_OK:
			doCheckStatusSuccess(data);
			break;
		case NetWork.GET_ATTACHMENTS_OK:
			doGetAttachmentsSuccess(data);
			break;
		case NetWork.GET_ATTACHMENTS_ERROR:
		case NetWork.GET_STATUS_ERROR:
		case NetWork.GET_ORDER_SYSTEM_INFO_ERROR:
		case NetWork.GET_ORDER_SYSTEM_ORDER_INFO_ERROR:
		case NetWork.SUBMIT_ORDER_PHOTO2_ERROR:
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
					ordersystem_photo_layout.addView(view);
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
	 * 获取照片信息
	 */
	private void doGetAttachments() {
		PdaRequest<SCM_AttachMentDto> request = new PdaRequest<SCM_AttachMentDto>();
		SCM_AttachMentDto data = new SCM_AttachMentDto();
		data.setPbillid(tmDispatchDto.getDispatchId());
		data.setTblName("Dispatch");
		request.setData(data);
		GetAttachmentsHandler dataHandler = new GetAttachmentsHandler(context,
				request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private void doCheckStatusSuccess(Object data) {
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
			PdaResponse<VehicleStatusDto> response = CheckVehichleStatusJsonParser
					.parserVehicleStatusInfoJson(dataString);
			if (null == response) {
				return;
			}
			if (response.isSuccess()) {
				VehicleStatusDto vehicleStatusDto = response.getData();
				if (null != vehicleStatusDto) {
					switch (vehicleStatusDto.getTask_status()) {
					case 1:// 签到
						upload_btn.setVisibility(View.VISIBLE);
						ensure_upload_btn.setVisibility(View.VISIBLE);
						upload_btn2.setVisibility(View.GONE);
						grid_btn.setVisibility(View.GONE);
						arrival_btn.setVisibility(View.GONE);
						check_photo_btn.setVisibility(View.GONE);
						break;
					case 2:// 上传订单
						upload_btn.setVisibility(View.GONE);
						ensure_upload_btn.setVisibility(View.GONE);
						upload_btn2.setVisibility(View.GONE);
						grid_btn.setVisibility(View.VISIBLE);
						arrival_btn.setVisibility(View.GONE);
						check_photo_btn.setVisibility(View.GONE);
						doGetAttachments();
						break;
					case 3:// 发车
						upload_btn.setVisibility(View.GONE);
						ensure_upload_btn.setVisibility(View.GONE);
						upload_btn2.setVisibility(View.GONE);
						grid_btn.setVisibility(View.GONE);
						arrival_btn.setVisibility(View.VISIBLE);
						check_photo_btn.setVisibility(View.GONE);
						doGetAttachments();
						break;
					case 4:// 到货
						upload_btn.setVisibility(View.GONE);
						ensure_upload_btn.setVisibility(View.GONE);
						upload_btn2.setVisibility(View.VISIBLE);
						grid_btn.setVisibility(View.GONE);
						arrival_btn.setVisibility(View.GONE);
						check_photo_btn.setVisibility(View.GONE);
						doGetAttachments();
						break;
					case 5:// 上传回单
						upload_btn.setVisibility(View.GONE);
						ensure_upload_btn.setVisibility(View.GONE);
						upload_btn2.setVisibility(View.GONE);
						grid_btn.setVisibility(View.GONE);
						arrival_btn.setVisibility(View.GONE);
						check_photo_btn.setVisibility(View.VISIBLE);
						doGetAttachments();
						break;
					case 6:// 完成
						upload_btn.setVisibility(View.GONE);
						ensure_upload_btn.setVisibility(View.GONE);
						upload_btn2.setVisibility(View.GONE);
						grid_btn.setVisibility(View.GONE);
						arrival_btn.setVisibility(View.GONE);
						check_photo_btn.setVisibility(View.VISIBLE);
						doGetAttachments();
						break;

					default:
						break;
					}
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

	private void doSignInSuccess(Object data) {
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
			PdaResponse<TmDispatchDto> response = OrderSystemInfoJsonParser
					.parserOrderSystemInfoJson(dataString);
			if (null == response) {
				ToastUtil.show(context, "发车失败,请重新发车");
				return;
			}
			if (response.isSuccess()) {
				Intent intent = new Intent(this, TailafterMapActivity.class);
				intent.putExtra("tmDispatchDto", tmDispatchDto);
				startActivity(intent);
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
				msg.obj = "订单已上传保存";
				checkVehicleStatus();
			} else {
				msg.obj = result;
			}
			msg.sendToTarget();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取订单信息成功
	 * 
	 * @param data
	 */
	private void doGetOrderSystemOrderInfoSuccess(Object data) {
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
			PdaResponse<TmDispatchDto> response = OrderSystemInfoJsonParser
					.parserOrderSystemInfoJson(dataString);
			if (null == response) {
				ToastUtil.show(context, "获取作业信息失败，请重新获取");
				return;
			}
			if (response.isSuccess()) {
				tmDispatchDto = response.getData();
				if (tmDispatchDto.getAttatchMentDtos() == null) {
					List<SCM_AttachMentDto> list = new ArrayList<SCM_AttachMentDto>();
					tmDispatchDto.setAttatchMentDtos(list);
				}
				Editor editor = sPreferences.edit();
				editor.putLong("dispatchID", tmDispatchDto.getDispatchId());
				editor.commit();
				ApplicationPool.setDispatch(null == tmDispatchDto
						.getDispatchId() ? -1 : tmDispatchDto.getDispatchId());
				showView(tmDispatchDto);
				checkVehicleStatus();
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

	private void doGetMoreOrderSystemInfoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<SysOrderDto>> response = OrderSystemOrderInfoJsonParser
					.parserOrderSystemOrderInfoJson(dataString);
			if (response.isSuccess()) {
				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取订单信息失败，请重新获取");
					return;
				}
				totalPage = new Long(response.getTotal()).intValue();
				for (SysOrderDto sysOrderDto : response.getData()) {
					mDataList.add(sysOrderDto);
				}
				mAdapter.notifyDataSetChanged();
				ordersystem_content_layout.onRefreshComplete();

			} else {
				String result = response.getMessage();
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = result;
				msg.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取作业信息成功
	 * 
	 * @param data
	 */
	private void doGetOrderSystemInfoSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			PdaResponse<List<SysOrderDto>> response = OrderSystemOrderInfoJsonParser
					.parserOrderSystemOrderInfoJson(dataString);
			if (response.isSuccess()) {
				// mDataList = response.getData();
				// myHandler.sendEmptyMessage(GET_ORDER_INFO_SUCCESS);

				if (null == response || null == response.getData()) {
					ToastUtil.show(context, "获取订单信息失败，请重新获取");
					return;
				}
				if (!isGetMoreData && !isFirstRefreshData) {
					mDataList.clear();
				}
				for (SysOrderDto sysOrderDto : response.getData()) {
					mDataList.add(sysOrderDto);
				}
				totalPage = new Long(response.getTotal()).intValue();
				mAdapter = new OrderSystemOrderInfoAdapter(context, mDataList);
				ordersystem_content_listview.setAdapter(mAdapter);
				ordersystem_content_layout.onRefreshComplete();
				// ordersystem_content_layout.setAdapter(mAdapter);

			} else {
				String result = response.getMessage();
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = result;
				msg.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// stopService(new Intent(MainActivity.this, BDLocationService.class));
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
		ordersystem_photo_layout.addView(view);
		tmDispatchDto.getAttatchMentDtos().add(photoList);
	}

	/**
	 * 删除回单
	 * 
	 * @param photoURL2
	 */
	protected void deletePhotoIntoLayout(String photoURL2) {
		int size = ordersystem_photo_layout.getChildCount();
		for (int i = 0; i < size; i++) {
			if (tmDispatchDto.getAttatchMentDtos().get(i).getPath()
					.getHeaderImgURL().equalsIgnoreCase(photoURL2)) {
				ordersystem_photo_layout.removeViewAt(i);
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
}
