package com.searun.orderoperation.datahandler;

import java.util.List;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 上传回单-订单中心
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitOrderPhotoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<TmDispatchDto> tmDispatchDto;

	public SubmitOrderPhotoHandler(Context context,
			PdaRequest<TmDispatchDto> tmDispatchDto) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_ORDER_PHOTO_ACTION;
		this.tmDispatchDto = tmDispatchDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		tmDispatchDto.setUuId(CommonUtils.getUUID(mContext));
		tmDispatchDto.setMemberType(CommonUtils.getMemberType(mContext));
		tmDispatchDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(tmDispatchDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SUBMIT_ORDER_PHOTO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_ORDER_PHOTO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_ORDER_PHOTO_ERROR, errorCode);
	}
}
