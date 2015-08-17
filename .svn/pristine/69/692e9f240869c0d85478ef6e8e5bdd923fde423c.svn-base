package com.searun.orderoperation.jsonparser;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.SmsInfoDto;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 获取短信
 * 
 * @author zhazhaobao
 * 
 */
public class SendSMSHanlder extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<SmsInfoDto> smsDto;

	public SendSMSHanlder(Context context, PdaRequest<SmsInfoDto> smsDto) {
		this.mContext = context;
		this.server_url = NetWork.SEND_SMS_ACTION;
		this.smsDto = smsDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		smsDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(smsDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SEND_SMS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SEND_SMS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SEND_SMS_ERROR, errorCode);
	}
}
