package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 获取订单系统作业信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetOrderSystemOrderInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<TmDispatchDto> orderDto;

	public GetOrderSystemOrderInfoHandler(Context context,
			PdaRequest<TmDispatchDto> orderDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_ORDER_SYSTEM_ORDER_INFO_ACTION;
		this.orderDto = orderDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		orderDto.setUuId(CommonUtils.getUUID(mContext));
		orderDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(orderDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_ORDER_SYSTEM_ORDER_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_ORDER_SYSTEM_ORDER_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_ORDER_SYSTEM_ORDER_INFO_ERROR, errorCode);
	}
}
