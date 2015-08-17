package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.OrderDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 获取订单系统作业信息历史信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetOrderSystemHistoryHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<String> orderDto;

	public GetOrderSystemHistoryHandler(Context context,
			PdaRequest<String> orderDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_ORDER_SYSTEM_HISTORY_ACTION;
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
		sendResult(NetWork.GET_ORDER_SYSTEM_HISTORY_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_ORDER_SYSTEM_HISTORY_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_ORDER_SYSTEM_HISTORY_ERROR, errorCode);
	}
}
