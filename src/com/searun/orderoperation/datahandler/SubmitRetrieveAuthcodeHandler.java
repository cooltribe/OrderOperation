package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.MemberDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.RegisterInfo;
import com.searun.orderoperation.net.http.HttpAction;

/**
 * 找回密码。提交用户名，验证码
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitRetrieveAuthcodeHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<CarsDto> registerInfo;

	public SubmitRetrieveAuthcodeHandler(Context context,
			PdaRequest<CarsDto> registerInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_REGISTER_AUTHCODE_ACTION;
		this.registerInfo = registerInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		registerInfo.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(registerInfo));
		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.SUBMIT_REGISTER_AUTHCODE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_REGISTER_AUTHCODE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_REGISTER_AUTHCODE_ERROR, errorCode);
	}
}
