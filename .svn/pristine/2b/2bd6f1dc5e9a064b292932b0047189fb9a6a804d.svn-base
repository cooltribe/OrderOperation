package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 获取结算 计算规则
 * 
 * @author zhazhaobao
 * 
 */
public class GetCalculateInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> calculateInfo;

	public GetCalculateInfoHandler(Context context,
			PdaRequest<String> calculateInfo) {
		this.mContext = context;
		this.server_url = NetWork.GET_CALCULATE_INFO_ACTION;
		this.calculateInfo = calculateInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		calculateInfo.setUuId(CommonUtils.getUUID(mContext));
		calculateInfo.setMemberType(CommonUtils.getMemberType(mContext));
		calculateInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(calculateInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_CALCULATE_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_CALCULATE_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_CALCULATE_INFO_ERROR, errorCode);
	}
}
