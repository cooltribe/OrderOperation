package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 找回密码，提交密码
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitRetrivevPasswordHandlerHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<CarsDto> accountInfo;

	public SubmitRetrivevPasswordHandlerHandler(Context context,
			PdaRequest<CarsDto> accountInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_RETRIVEV_PASSWORD_ACTION;
		this.accountInfo = accountInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		accountInfo.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(accountInfo));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SUBMIT_RETRIVEV_PASSWORD_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_RETRIVEV_PASSWORD_OK, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_RETRIVEV_PASSWORD_OK, errorCode);
	}
}
