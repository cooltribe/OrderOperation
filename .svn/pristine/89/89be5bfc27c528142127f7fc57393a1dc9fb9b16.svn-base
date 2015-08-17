package com.searun.orderoperation.activity;

import org.json.JSONException;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.application.ApplicationPool;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.LoginHandler;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.UserInfo;
import com.searun.orderoperation.jsonparser.LoginJsonParser;
import com.searun.orderoperation.provider.DBOperate;
import com.searun.orderoperation.util.CommonUtils;

public class WelcomeActivity extends BaseActivity implements
		OnDataReceiveListener {

	/**
	 * 欢迎界面图片
	 */
	private ImageView welcome_img_iv;

	/**
	 * 用户信息
	 */
	private UserInfo mUserInfo;
	private DBOperate dbOperate;
	private Context context;
	private SharedPreferences sPreferences;
	/**
	 * 登录成功
	 */
	private final int LOGIN_CODE_SUCCESS = 100;
	private final int LOGIN_CODE_FAILED = 101;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_welcome);
		context = getApplicationContext();
		sPreferences = getSharedPreferences(ConstantPool.OPERATION_PREFERENCES,
				Context.MODE_PRIVATE);
		dbOperate = DBOperate.getInstance(context);
		initView();

		myHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				startActivity(intent);
				CommonUtils.finishActivity(WelcomeActivity.this);
			}
		}, 3000);
	}

	@Override
	public void onClickListener(View view) {
		switch (view.getId()) {

		default:
			break;
		}
	}

	@Override
	public void initView() {
		welcome_img_iv = (ImageView) findViewById(R.id.welcome_img_iv);
	}

	private Handler myHandler = new Handler() {

		public void handleMessage(Message msg) {
			switch (msg.what) {
			case LOGIN_CODE_SUCCESS:
				login();
				break;
			case LOGIN_CODE_FAILED:
				doLoginFailed();
				break;
			}
		}
	};

	private void login() {
		// Intent imIntent = new Intent(WelcomeActivity.this,
		// LoginIMServerService.class);
		// startService(imIntent);
		Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 提交登录信息，自动登录
	 */
	private void submitLoginInfo() {
		mUserInfo = dbOperate.getUesrInfoByUUID(CommonUtils.getUUID(context));
		if (null == mUserInfo || mUserInfo.getIsLogin().equalsIgnoreCase("N")) {
			myHandler.sendEmptyMessage(LOGIN_CODE_FAILED);
			return;
		}

		CarsDto loginInfo = new CarsDto();
		loginInfo.setVehicleNum(mUserInfo.getUSER_NAME());
		loginInfo.setPassword(mUserInfo.getPASSWORD());
		PdaRequest<CarsDto> request = new PdaRequest<CarsDto>();
		request.setData(loginInfo);
		LoginHandler loginHandler = new LoginHandler(context, request);
		loginHandler.setOnDataReceiveListener(this);
		loginHandler.startNetWork();

	}

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		switch (resultCode) {
		case NetWork.LOGIN_OK:
			doLoginSuccess(data);
			break;
		case NetWork.LOGIN_ERROR:
			myHandler.sendEmptyMessage(LOGIN_CODE_FAILED);
			break;

		default:
			break;
		}
	}

	/**
	 * 登录失败
	 */
	private void doLoginFailed() {
		Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 登录成功
	 */
	private void doLoginSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PdaResponse<CarsDto> response = LoginJsonParser
					.parserLoginJson(dataString);
			if (response.isSuccess()) {// 登录成功
				CarsDto result = response.getData();
				// 保存登录信息
				Editor editor = sPreferences.edit();
				editor.putString("userName", result.getVehicleNum());
				editor.commit();
				ApplicationPool.setUserName(result.getVehicleNum());
				ApplicationPool.setUUID(result.getVehicleNum());
				UserInfo userInfo = new UserInfo();
				userInfo.setUuId(result.getVehicleNum());
				userInfo.setUSER_NAME(result.getVehicleNum());
				userInfo.setPASSWORD(CommonUtils.getPassword(context));
				userInfo.setIsLogin("Y");
				dbOperate.updateUserInfo(userInfo);
				myHandler.sendEmptyMessage(LOGIN_CODE_SUCCESS);
			} else {// 登录失败
				String result = response.getMessage();
				Message msg = myHandler.obtainMessage();
				msg.what = LOGIN_CODE_FAILED;
				msg.obj = result;
				myHandler.sendMessage(msg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
