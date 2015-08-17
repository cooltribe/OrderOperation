package com.searun.orderoperation.activity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.adapter.NormalTextAdapter;
import com.searun.orderoperation.application.ApplicationPool;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.CheckVehicleStatusHandler;
import com.searun.orderoperation.datahandler.SignInHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SCM_AttachMentDto;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.entity.VehicleStatusDto;
import com.searun.orderoperation.jsonparser.CheckVehichleStatusJsonParser;
import com.searun.orderoperation.jsonparser.OrderSystemInfoJsonParser;
import com.searun.orderoperation.service.BDLocationService;
import com.searun.orderoperation.util.CommonUtils;
import com.searun.orderoperation.util.ToastUtil;

@SuppressLint("NewApi")
public class SignInActivity extends BaseActivity implements OnClickListener,
		OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	/**
	 * 车牌号
	 */
	private TextView signin_vehicle;

	/**
	 * 作业类型
	 */
	private CheckBox signin_type;

	private Context context;

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

	private VehicleStatusDto vehicleStatusDto;

	private SharedPreferences sPreferences;

	/**
	 * 调度单
	 */
	private TmDispatchDto tmDispatchDto;

	private Button signin_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_signin);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		vehicleStatusDto = new VehicleStatusDto();
		sPreferences = getSharedPreferences(ConstantPool.OPERATION_PREFERENCES,
				Context.MODE_PRIVATE);
		initView();
		Intent intent = new Intent(SignInActivity.this, BDLocationService.class);
		startService(intent);
//		Intent intent = new Intent();
//		intent.setAction("com.searun.orderoperation.service.BDLocationService");
//		intent.setPackage(getPackageName());
//		startService(intent);
		checkVehicleStatus();
	}

	

	
	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);
		maintitle_back_iv.setVisibility(View.VISIBLE);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.signin_hint);

		signin_vehicle = (TextView) findViewById(R.id.signin_vehicle);
		signin_vehicle.setText(sPreferences.getString("vehicleNum", ""));

		signin_type = (CheckBox) findViewById(R.id.signin_type);
		signin_type.setOnClickListener(this);

		signin_btn = (Button) findViewById(R.id.signin_btn);

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

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {
		case R.id.signin_btn:
			new Thread(new Runnable() {

				@Override
				public void run() {
					if (signin_btn.getText().toString().equalsIgnoreCase("确定")) {
						Intent intent = new Intent(SignInActivity.this,
								MainActivity.class);
						startActivity(intent);
						// CommonUtils.finishActivity(SignInActivity.this);
						CommonUtils.addActivity(SignInActivity.this);
					} else {
						doSignIn();
					}
				}
			}).start();
			break;

		default:
			break;
		}

	}

	private void checkVehicleStatus() {
		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<VehicleStatusDto> request = new PdaRequest<VehicleStatusDto>();
		vehicleStatusDto.setVehicle_num(signin_vehicle.getText().toString());
		vehicleStatusDto.setTask_status(1);
		request.setData(vehicleStatusDto);
		CheckVehicleStatusHandler dataHandler = new CheckVehicleStatusHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	/**
	 * 签到
	 */
	private void doSignIn() {
		if (TextUtils.isEmpty(signin_type.getText().toString())) {
			Message msg = myHandler.obtainMessage();
			msg.what = SHOW_TOAST;
			msg.obj = "请选择正确的作业类型";
			msg.sendToTarget();
			return;
		}

		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		vehicleStatusDto.setVehicle_num(signin_vehicle.getText().toString());
		vehicleStatusDto.setLat(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("latitude", "0"))));
		vehicleStatusDto.setLng(BigDecimal.valueOf(Double.valueOf(sPreferences
				.getString("longitude", "0"))));
		vehicleStatusDto
				.setLocation_desc(sPreferences.getString("address", ""));
		vehicleStatusDto.setMobile(sPreferences.getString("phone", ""));
		vehicleStatusDto.setTask_status(1);
		vehicleStatusDto.setTask_type(CommonUtils.getWorkType(signin_type
				.getText().toString()));
		PdaRequest<VehicleStatusDto> request = new PdaRequest<VehicleStatusDto>();
		request.setData(vehicleStatusDto);
		SignInHandler handler = new SignInHandler(context, request);
		handler.setOnDataReceiveListener(this);
		handler.startNetWork();
	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			CommonUtils.finishActivity(this);
			break;
		case R.id.signin_type:
			doSignInType();
			break;

		default:
			break;
		}
	}

	private void doSignInType() {
		final List<String> mDataList = new ArrayList<String>();
		TypedArray typedArray = getResources().obtainTypedArray(
				R.array.signin_type);
		int size = typedArray.length();
		for (int i = 0; i < size; i++) {
			mDataList.add(typedArray.getString(i).toString());
		}
		typedArray.recycle();
		ListView listview = new ListView(this);
		NormalTextAdapter adapter = new NormalTextAdapter(mDataList, context);
		listview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		// listview.setAdapter(new SearchPopWindowAdapter(mDataList, context));
		listview.setAdapter(adapter);
		// 新建一个popwindow，并显示里面的内容
		final PopupWindow popupWindow = makePopupWindow(context, listview);
		// int[] xy = new int[2];
		// GoodsSourceList_CarType.getLocationOnScreen(xy);
		// popupWindow.showAtLocation(GoodsSourceList_CarType, Gravity.RIGHT
		// | Gravity.TOP, xy[0] / 2,
		// xy[1] + GoodsSourceList_CarType.getWidth());
		// popwindow与按钮之间的相对位置
		popupWindow.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				signin_type.setChecked(false);
			}
		});
		popupWindow.showAsDropDown(signin_type, 2, 5);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String typeString = mDataList.get((int) id).toString();
				signin_type.setText(typeString);
				popupWindow.dismiss();
			}
		});
	}

	// 创建一个包含自定义view的PopupWindow
	@SuppressWarnings("deprecation")
	private PopupWindow makePopupWindow(Context context, ListView listview) {
		PopupWindow window;
		window = new PopupWindow(context);
		LinearLayout linearLayout = new LinearLayout(this);
		linearLayout.addView(listview);
		linearLayout.setOrientation(LinearLayout.VERTICAL);

		window.setContentView(linearLayout); // 选择布局方式
		// 设置popwindow的背景图片
		window.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.app_pref_bg));
		// 设置popwindow的高和宽
		window.setWidth(signin_type.getWidth());
		window.setHeight(ApplicationPool.screenHeight >> 1);
		// 设置PopupWindow外部区域是否可触摸
		window.setFocusable(true); // 设置PopupWindow可获得焦点
		window.setTouchable(true); // 设置PopupWindow可触摸
		window.setOutsideTouchable(false); // 设置非PopupWindow区域可触摸
		return window;
	}
	
	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.SIGN_IN_OK:
			doSignInSuccess(data);
			break;
		case NetWork.CHECK_STATUS_OK:
			doCheckStatusSuccess(data);
			break;
		case NetWork.CHECK_STATUS_ERROR:
		case NetWork.SIGN_IN_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;
		default:
			break;
		}
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
			System.err.println("111111111111111111111"+ response.isSuccess());
			if (response.isSuccess()) {
				if (null != response.getData()) {
					// 已经签到
					ToastUtil.show(context, "已经签到");
					signin_btn.setText("确定");
					Editor editor = sPreferences.edit();
					editor.putInt("vehicleType", response.getData()
							.getTask_type());
					editor.commit();
				} else {
					// 签到
					return;
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
				ToastUtil.show(context, "签到失败,请重新签到");
				return;
			}
			if (response.isSuccess()) {
//				tmDispatchDto = response.getData();
//				if (tmDispatchDto.getAttatchMentDtos() == null) {
//					List<SCM_AttachMentDto> list = new ArrayList<SCM_AttachMentDto>();
//					tmDispatchDto.setAttatchMentDtos(list);
//				}
//				Editor editor = sPreferences.edit();
//				editor.putLong("dispatchID", tmDispatchDto.getDispatchId());
//				editor.putInt("vehicleStatus", CommonUtils
//						.getWorkType(signin_type.getText().toString()));
//				editor.commit();
//				ApplicationPool.setDispatch(null == tmDispatchDto
//						.getDispatchId() ? -1 : tmDispatchDto.getDispatchId());
				Intent intent = new Intent(SignInActivity.this,
						MainActivity.class);
//				intent.putExtra("tmDispatchDto", tmDispatchDto);
				startActivity(intent);
				CommonUtils.finishActivity(SignInActivity.this);
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
}
