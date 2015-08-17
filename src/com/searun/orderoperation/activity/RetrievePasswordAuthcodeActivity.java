package com.searun.orderoperation.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.searun.orderoperation.R;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.customview.MuInputEditText;
import com.searun.orderoperation.customview.ProgressAlertDialog;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.datacenter.OnDataReceiveListener;
import com.searun.orderoperation.datahandler.SubmitRetrieveAuthcodeHandler;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SmsInfoDto;
import com.searun.orderoperation.jsonparser.ResultCodeJsonParser;
import com.searun.orderoperation.jsonparser.SendSMSHanlder;
import com.searun.orderoperation.util.CommonUtils;
import com.searun.orderoperation.util.ToastUtil;

/**
 * 找回密码，输入验证码
 * 
 * @author zhazhaobao
 * 
 */
public class RetrievePasswordAuthcodeActivity extends BaseActivity implements
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
	 * 输入用户名
	 */
	private MuInputEditText retrieve_username_edt;

	/**
	 * 验证码
	 */
	private MuInputEditText retrieve_authcode_edt;

	/**
	 * 获取验证码
	 */
	private Button retrieve_get_authcode_button;

	/**
	 * 下一步
	 */
	private Button retrieve_next_button;

	/**
	 * 重新获取验证码计时器
	 */
	private TimeCount timeCount;

	private CarsDto retrieveInfo = new CarsDto();

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
		setContentView(R.layout.activity_retrieve_password_authcode);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.deflaut_titlebar); // titlebar为自己标题栏的布局
		context = getApplicationContext();
		CommonUtils.addActivity(this);
		initView();
	}

	@Override
	public void initView() {
		maintitle_back_iv = (ImageView) findViewById(R.id.maintitle_back_iv);
		maintitle_back_iv.setOnClickListener(this);

		defaulttitle_title_tv = (TextView) findViewById(R.id.defaulttitle_title_tv);
		defaulttitle_title_tv.setText(R.string.get_password_hint);

		retrieve_username_edt = (MuInputEditText) findViewById(R.id.retrieve_username_edt);
		// retrieve_username_edt.addTextChangedListener(textWatcherListener);
		retrieve_authcode_edt = (MuInputEditText) findViewById(R.id.retrieve_authcode_edt);
		// retrieve_authcode_edt.addTextChangedListener(textWatcherListener);
		retrieve_get_authcode_button = (Button) findViewById(R.id.retrieve_get_authcode_button);

		retrieve_next_button = (Button) findViewById(R.id.retrieve_next_button);
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
		case R.id.callBtn:
			CommonUtils.makeingCalls(context,
					getResources().getString(R.string.Service_phone));
			break;
		case R.id.retrieve_get_authcode_button:
			doGetAuthcode();
			break;
		case R.id.retrieve_next_button:
			doGotoSetPassword();
			break;

		default:
			break;
		}
	}

	/**
	 * 进入设置密码
	 */
	private void doGotoSetPassword() {

		if (retrieve_username_edt.getText().length() == 0) {
			retrieve_username_edt.showPopWindow(
					RetrievePasswordAuthcodeActivity.this, "请输入正确的用户名");
			return;
		}

		if (!isCorrectAuthcode(retrieve_authcode_edt.getText().toString())) {
			retrieve_authcode_edt.showPopWindow(
					RetrievePasswordAuthcodeActivity.this, "请输入正确的验证码");
			return;
		}

		myHandler.sendEmptyMessage(SHOW_PROGRESS);
		PdaRequest<CarsDto> request = new PdaRequest<CarsDto>();
		retrieveInfo.setMobile(retrieve_username_edt.getText().toString());
		retrieveInfo.setVerifyCode(retrieve_authcode_edt.getText().toString());
		request.setData(retrieveInfo);
		SubmitRetrieveAuthcodeHandler dataHandler = new SubmitRetrieveAuthcodeHandler(
				context, request);
		dataHandler.setOnDataReceiveListener(this);
		dataHandler.startNetWork();
	}

	private boolean isCorrectAuthcode(String authcode) {
		if (authcode.length() < 6)
			return false;
		return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.maintitle_back_iv:
			if (!RetrievePasswordAuthcodeActivity.this.isFinishing()) {
				finish();
			}
			break;

		default:
			break;
		}
	}

	/**
	 * 获取验证码
	 */
	private void doGetAuthcode() {
		if (TextUtils.isEmpty(retrieve_username_edt.getText().toString())) {
			retrieve_username_edt.showPopWindow(
					RetrievePasswordAuthcodeActivity.this, "请输入正确的手机号");
			return;
		}
		timeCount = new TimeCount(180000L, 1000L);
		timeCount.start();
		getAuthcodeSMS();
	}

	/**
	 * 获取验证码短信
	 */
	private void getAuthcodeSMS() {
		PdaRequest<SmsInfoDto> requset = new PdaRequest<SmsInfoDto>();
		SmsInfoDto smsDto = new SmsInfoDto();
		smsDto.setMobile(retrieve_username_edt.getText().toString());
		requset.setData(smsDto);
		SendSMSHanlder dataHanlder = new SendSMSHanlder(context, requset);
		dataHanlder.setOnDataReceiveListener(this);
		dataHanlder.startNetWork();
	}

	class TimeCount extends CountDownTimer {

		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			retrieve_get_authcode_button.setEnabled(true);
			retrieve_get_authcode_button.setText(getResources().getString(
					R.string.register_get_authcode_btntext));
		}

		@Override
		public void onTick(long currentTime) {
			retrieve_get_authcode_button.setEnabled(false);
			retrieve_get_authcode_button.setText(String.format(getResources()
					.getString(R.string.get_authcode_again_hint),
					currentTime / 1000L));
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
			if (retrieve_username_edt.length() > 0
					&& retrieve_authcode_edt.length() > 0) {
				retrieve_next_button.setEnabled(true);
				retrieve_next_button
						.setBackgroundResource(R.drawable.confirm_back_button_select);
				retrieveInfo.setMobile(retrieve_username_edt.getText()
						.toString());
				retrieveInfo.setVerifyCode(retrieve_authcode_edt.getText()
						.toString());
			} else {
				retrieve_next_button.setEnabled(false);
				retrieve_next_button
						.setBackgroundResource(R.drawable.submint_btn_unfocaus);
			}
		}
	};

	@Override
	public void onDataReceive(DataHandler dataHandler, int resultCode,
			Object data, int type) {
		myHandler.sendEmptyMessage(CLOSE_PROGRESS);
		switch (resultCode) {
		case NetWork.SUBMIT_REGISTER_AUTHCODE_OK:
			doSubmitSuccess(data);
			break;
		case NetWork.SUBMIT_REGISTER_AUTHCODE_ERROR:
			ToastUtil.show(context,
					getResources().getString(R.string.network_error_hint));

			break;
		default:
			break;
		}
	}

	private void doSubmitSuccess(Object data) {
		String dataString = null;
		try {
			dataString = new String((byte[]) data, "UTF-8");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			PdaResponse<String> response = ResultCodeJsonParser
					.parserResultCodeJson(dataString);
			if (null != response && response.isSuccess()) {
				Intent intent = new Intent(
						RetrievePasswordAuthcodeActivity.this,
						RetrievePasswordSettingActivity.class);
				intent.putExtra("carsDto", retrieveInfo);
				startActivity(intent);
			} else {// 登录失败
				Message msg = myHandler.obtainMessage();
				String result = "";
				try {
					result = response.getMessage();
				} catch (Exception e) {
					e.printStackTrace();
					result = getResources().getString(
							R.string.network_error_hint);
				}
				msg.what = SHOW_TOAST;
				msg.obj = result;
				msg.sendToTarget();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
