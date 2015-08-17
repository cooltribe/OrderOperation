package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.LocationDto;
import com.searun.orderoperation.entity.MemberDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 提交个人位置信息
 * 
 * @author Administrator
 * 
 */
public class SubmitPersonalPositionHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<LocationDto> positionInfo;

	public SubmitPersonalPositionHandler(Context context,
			PdaRequest<LocationDto> positionInfo) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_PERSONAL_POSITION_ACTION;
		this.positionInfo = positionInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		positionInfo.setUuId(CommonUtils.getUUID(mContext));
		positionInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(positionInfo);
		httpAction.addBodyParam("jsonString", jsonString);
		startNetwork(httpAction); 
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		// String result = new String(receiveBody);
		sendResult(NetWork.SUBMIT_PERSONAL_POSITION_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_PERSONAL_POSITION_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_PERSONAL_POSITION_ERROR, errorCode);
	}
}
