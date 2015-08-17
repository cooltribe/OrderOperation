package com.searun.orderoperation.activity;

import org.json.JSONException;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.application.ApplicationPool;
import com.searun.orderoperation.application.ConstantPool;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.MuInputEditText;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.SubmitRegisterPasswordHandler;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.UserInfo;
import com.searun.orderoperation.jsonparser.LoginJsonParser;
import com.searun.orderoperation.provider.DBOperate;
import com.searun.orderoperation.util.CommonUtils;
import com.searun.orderoperation.util.ToastUtil;

/**
 * 注册密码
 * 
 * @author zhazhaobao
 * 
 */
@SuppressLint("HandlerLeak")
public class RegisterPasswordActivity extends BaseActivity implements
		OnClickListener, OnDataReceiveListener {

	/**
	 * 返回按钮
	 */
	private ImageView maintitle_back_iv;

	/**
	 * 标题title
	 */
	private TextView defaulttitle_title_tv;

	private Context context;

	/**
	 * 输入密码
	 */
	private MuInputEditText rUserPWEdt;

	/**
	 * 确认密码
	 */
	private MuInputEditText againUserPWEdt;

	/**
	 * 提交，确认
	 */
	private Button rSubmitBtn;

	private CarsDto registerInfo;

	private final int SUBMIT_PASSWORD_SUCCESS = 200;

	private SharedPreferences sPreferences;

	private DBOperate dbOperate;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_register_password);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		registerInfo = (CarsDto) getIntent().getSerializableExtra(
				"registerInfo");
		sPreferences = getSharedPreferences(ConstantPool.OPERATION_PREFERENCES,
				Context.MODE_PRIVATE);
		dbOperate = DBOperate.getInstance(context);
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.register_hint);

		rUserPWEdt = (MuInputEditText) findViewById(R.id.rUserPWEdt);
		// rUserPWEdt.addTextChangedListener(textWatcherListener);

		againUserPWEdt = (MuInputEditText) findViewById(R.id.againUserPWEdt);
		// againUserPWEdt.addTextChangedListener(textWatcherListener);

		rSubmitBtn = (Button) findViewById(R.id.rSubmitBtn);
	}

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SUBMIT_PASSWORD_SUCCESS:
				// Intent imIntent = new Intent(RegisterPasswordActivity.this,
				// LoginIMServerService.class);
				// startService(imIntent);
				Intent intent = new Intent(RegisterPasswordActivity.this,
						SignInActivity.class);
				CommonUtils.addActivity(RegisterPasswordActivity.this);
				// 登录成功，保存登录信息
				startActivity(intent);
				CommonUtils.finishAllActivity();
				break;
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
				ToastUtil.show(context, msg.obj.toString());
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
		case R.id.rSubmitBtn:
			// doSubmitPassword();
			CommonUtils.closeKeyboard(context, rUserPWEdt);
			CommonUtils.closeKeyboard(context, againUserPWEdt);
			doSubmitPassword(rUserPWEdt.getText().toString(), againUserPWEdt
					.getText().toString());
			break;

		default:
			break;
		}
	}

	/**
	 * 提交密码
	 */
	// private void doSubmitPassword() {
	//
	// if (!CommonUtils.isPasswordTypeCorrect(rUserPWEdt.getText().toString()))
	// {
	// rUserPWEdt.showPopWindow(RegisterPasswordActivity.this,
	// getResources().getString(R.string.psw_number_format));
	// return;
	// }
	//
	// if (CommonUtils.isPasswordCorrect(rUserPWEdt.getText().toString(),
	// againUserPWEdt.getText().toString())) {
	//
	// SubmitRegisterPasswordHandler datahHandler = new
	// SubmitRegisterPasswordHandler(
	// context, registerInfo);
	// datahHandler.setOnDataReceiveListener(this);
	// datahHandler.startNetWork();
	// } else {
	// againUserPWEdt.showPopWindow(RegisterPasswordActivity.this,
	// getResources().getString(R.string.entered_psw_differ));
	// }
	//
	// }

	/**
	 * 提交密码
	 * 
	 * @param pass1
	 * @param pass2
	 */
	private void doSubmitPassword(String pass1, String pass2) {

		Filter f1 = new PasswordFilter1();

		Filter f2 = new PasswordFilter2();

		f1.setNext(f2);

		String result = f1.doFilter(pass1, pass2);
		if (result.equalsIgnoreCase("成功")) {
			myHandler.sendEmptyMessage(SHOW_PROGRESS);
			PdaRequest<CarsDto> request = new PdaRequest<CarsDto>();
			// registerInfo.setPassword(MD5Util.getMD5String(
			// registerInfo.getVehicleNum() + pass1).toLowerCase());
			registerInfo.setPassword(pass1);
			request.setData(registerInfo);
			SubmitRegisterPasswordHandler datahHandler = new SubmitRegisterPasswordHandler(
					context, request);
			datahHandler.setOnDataReceiveListener(this);
			datahHandler.startNetWork();
		} else if (result.equalsIgnoreCase("两次密码输入不一致")) {
			againUserPWEdt.showPopWindow(RegisterPasswordActivity.this,
					getResources().getString(R.string.entered_psw_differ));
		} else if (result.equalsIgnoreCase("密码长度必须大于8")) {
			rUserPWEdt.showPopWindow(RegisterPasswordActivity.this,
					getResources().getString(R.string.psw_number_format));
		}

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!RegisterPasswordActivity.this.isFinishing()) {
				finish();
			}
			break;

		default:
			break;
		}
	}

	private TextWatcher textWatcherListener = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void afterTextChanged(Editable arg0) {
			if (rUserPWEdt.length() > 0 && againUserPWEdt.length() > 0) {
				registerInfo.setPassword(rUserPWEdt.getText().toString());
				rSubmitBtn.setEnabled(true);
				rSubmitBtn
						.setBackgroundResource(R.drawable.confirm_back_button_select);
			} else {
				rSubmitBtn.setEnabled(false);
				rSubmitBtn
						.setBackgroundResource(R.drawable.submint_btn_unfocaus);
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.SUBMIT_REGISTER_PASSWORD_OK:
			doSubmitAuthcodeSuccess(data);
			break;
		case NetWork.SUBMIT_REGISTER_PASSWORD_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));
			break;

		default:
			break;
		}
	}

	/**
	 * 执行提交密码
	 */
	private void doSubmitAuthcodeSuccess(Object data) {
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
				editor.putString("password", rUserPWEdt.getText().toString());
				editor.putString("userName", result.getVehicleNum());
				editor.putString("phone", result.getMobile());
				editor.commit();
				ApplicationPool.setPassword(rUserPWEdt.getText().toString());
				ApplicationPool.setUUID(result.getVehicleNum());
				ApplicationPool.setUserName(result.getVehicleNum());
				UserInfo userInfo = new UserInfo();
				userInfo.setUuId(result.getVehicleNum());
				userInfo.setUSER_NAME(result.getVehicleNum());
				userInfo.setPASSWORD(rUserPWEdt.getText().toString());
				userInfo.setIsLogin("Y");
				dbOperate.updateUserInfo(userInfo);

				myHandler.sendEmptyMessage(SUBMIT_PASSWORD_SUCCESS);
			} else {// 登录失败
				String result = response.getMessage();
				Message msg = myHandler.obtainMessage();
				msg.what = SHOW_TOAST;
				msg.obj = result;
				myHandler.sendMessage(msg);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

abstract class Filter {

	Filter next = null;

	public Filter getNext() {

		return next;

	}

	public void setNext(Filter next) {

		this.next = next;

	}

	public String doFilter(String pass1, String pass2) {

		if (next == null) {
			return "成功";
		} else
			return next.doFilter(pass1, pass2);

	}

}

class PasswordFilter2 extends Filter {

	public String doFilter(String pass1, String pass2) {

		if (!(pass1.equals(pass2))) {

			return "两次密码输入不一致";
		} else
			return super.doFilter(pass1, pass2);

	}

}

class PasswordFilter1 extends Filter {

	public String doFilter(String pass1, String pass2) {

		if (pass1.length() < 8) {

			return "密码长度必须大于8";
		} else
			return super.doFilter(pass1, pass2);

	}

}
