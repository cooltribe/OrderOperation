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
 * 修改订单状态
 * 
 * @author zhazhaobao
 * 
 */
public class ChangeOrderStatusHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<OrderDto> orderInfo;

	public ChangeOrderStatusHandler(Context context, PdaRequest<OrderDto> orderInfo) {
		this.mContext = context;
		this.server_url = NetWork.CHANGE_ORDER_STATUS_ACTION;
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
		sendResult(NetWork.CHANGE_ORDER_STATUS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.CHANGE_ORDER_STATUS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.CHANGE_ORDER_STATUS_ERROR, errorCode);
	}
}
