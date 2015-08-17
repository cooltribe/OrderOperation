package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 添加新车辆
 * 
 * @author zhazhaobao
 * 
 */
public class CheckUpdateHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> updateInfo;

	public CheckUpdateHandler(Context context, PdaRequest<String> updateInfo) {
		this.mContext = context;
		this.server_url = NetWork.CHECK_UPDATE_ACTION;
		this.updateInfo = updateInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
//		updateInfo.setUuId(CommonUtils.getUUID(mContext));
//		updateInfo.setMemberType(CommonUtils.getMemberType(mContext));
		updateInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(updateInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.CHECK_UPDATE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.CHECK_UPDATE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.CHECK_UPDATE_ERROR, errorCode);
	}
}
