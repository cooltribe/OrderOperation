package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PaymentDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 提交支付，进行支付
 * 
 * @author zhazhaobao
 * 
 */
public class SubmitPaymentHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<PaymentDto> paymentDto;

	public SubmitPaymentHandler(Context context,
			PdaRequest<PaymentDto> paymentDto) {
		this.mContext = context;
		this.server_url = NetWork.SUBMIT_PAYMENT_ACTION;
		this.paymentDto = paymentDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		paymentDto.setUuId(CommonUtils.getUUID(mContext));
		paymentDto.setMemberType(CommonUtils.getMemberType(mContext));
		paymentDto.setOriginApp("ANDROID");
		String jsonString = new Gson().toJson(paymentDto);
		httpAction.addBodyParam("jsonString", jsonString);

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.SUBMIT_PAYMENT_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.SUBMIT_PAYMENT_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.SUBMIT_PAYMENT_ERROR, errorCode);
	}
}
