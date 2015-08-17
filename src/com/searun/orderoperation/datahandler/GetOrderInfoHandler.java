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
 * 获取用户订单信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetOrderInfoHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<OrderDto> orderInfo;

	public GetOrderInfoHandler(Context context, PdaRequest<OrderDto> orderInfo) {
		this.mContext = context;
		this.server_url = NetWork.GET_ORDER_INFO_ACTION;
		this.orderInfo = orderInfo;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		orderInfo.setUuId(CommonUtils.getUUID(mContext));
		orderInfo.setMemberType(CommonUtils.getMemberType(mContext));
		orderInfo.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(orderInfo);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_ORDER_INFO_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_ORDER_INFO_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_ORDER_INFO_ERROR, errorCode);
	}
}
