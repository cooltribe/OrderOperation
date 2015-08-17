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
 * 订单，重新报价，修改价格
 * 
 * @author zhazhaobao
 * 
 */
public class EditOrderPriceHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<OrderDto> orderDto;

	public EditOrderPriceHandler(Context context, PdaRequest<OrderDto> orderDto) {
		this.mContext = context;
		this.server_url = NetWork.EDIT_ORDER_PRICE_ACTION;
		this.orderDto = orderDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		orderDto.setUuId(CommonUtils.getUUID(mContext));
		orderDto.setMemberType(CommonUtils.getMemberType(mContext));
		orderDto.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(orderDto);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.EDIT_ORDER_PRICE_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.EDIT_ORDER_PRICE_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.EDIT_ORDER_PRICE_ERROR, errorCode);
	}
}
