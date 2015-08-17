package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.MemberDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;

public class LoginHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<CarsDto> loginInfo;

	public LoginHandler(Context context, PdaRequest<CarsDto> loginInfo) {
		this.mContext = context;
		this.server_url = NetWork.lOGIN_ACTION;
		this.loginInfo = loginInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		String jsonString = new Gson().toJson(loginInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.LOGIN_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.LOGIN_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.LOGIN_ERROR, errorCode);
	}
}
