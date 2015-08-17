package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;

/**
 * 提交用户信息
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitRegisterInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<CarsDto> registerInfo;

	public SubmitRegisterInfoHandler(Context context,
			PdaRequest<CarsDto> registerInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_REGISTER_INFO_ACTION;
		this.registerInfo = registerInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		String jsonString = new Gson().toJson(registerInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.SUBMIT_REGISTER_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_REGISTER_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_REGISTER_INFO_ERROR, errorCode);
	}
}
