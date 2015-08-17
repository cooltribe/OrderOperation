package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.SCM_AttachMentDto;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 获取订单图片信息
 * 
 * @author zhazhaobao
 * 
 */
public class GetAttachmentsHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<SCM_AttachMentDto> attachMentDto;

	public GetAttachmentsHandler(Context context,
			PdaRequest<SCM_AttachMentDto> attachMentDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_ATTACHMENTS_ACTION;
		this.attachMentDto = attachMentDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		attachMentDto.setUuId(CommonUtils.getUUID(mContext));
		attachMentDto.setMemberType(CommonUtils.getMemberType(mContext));
		attachMentDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString", new Gson().toJson(attachMentDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_ATTACHMENTS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_ATTACHMENTS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_ATTACHMENTS_ERROR, errorCode);
	}
}
