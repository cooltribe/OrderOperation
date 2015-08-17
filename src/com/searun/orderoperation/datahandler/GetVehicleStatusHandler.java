package com.searun.orderoperation.datahandler;

import android.content.Context;

import com.google.gson.Gson;
import com.searun.orderoperation.application.NetWork;
import com.searun.orderoperation.datacenter.DataHandler;
import com.searun.orderoperation.entity.OrderDto;
import com.searun.orderoperation.entity.PdaRequest;
import com.searun.orderoperation.entity.VehicleStatusDto;
import com.searun.orderoperation.net.http.HttpAction;
import com.searun.orderoperation.util.CommonUtils;

/**
 * 获取操作单状态
 * 
 * @author zhazhaobao
 * 
 */
public class GetVehicleStatusHandler extends DataHandler {
	private Context mContext;
	private String server_url;
	private PdaRequest<VehicleStatusDto> vehicleStatusDto;

	public GetVehicleStatusHandler(Context context,
			PdaRequest<VehicleStatusDto> vehicleStatusDto) {
		this.mContext = context;
		this.server_url = NetWork.GET_STATUS_ACTION;
		this.vehicleStatusDto = vehicleStatusDto;
	}

	public void startNetWork() {
		HttpAction httpAction = new HttpAction(HttpAction.REQUEST_TYPE_POST);
		httpAction.setUri(server_url);
		vehicleStatusDto.setUuId(CommonUtils.getUUID(mContext));
		vehicleStatusDto.setOriginApp("ANDROID");
		httpAction.addBodyParam("jsonString",
				new Gson().toJson(vehicleStatusDto));

		startNetwork(httpAction);
	}

	@Override
	protected void onNetReceiveOk(byte[] receiveBody) {
		sendResult(NetWork.GET_STATUS_OK, receiveBody);
	}

	@Override
	protected void onNetReceiveError(int errorCode) {
		sendResult(NetWork.GET_STATUS_ERROR, errorCode);
	}

	@Override
	protected void onNetReceiveTimeout(int errorCode) {
		sendResult(NetWork.GET_STATUS_ERROR, errorCode);
	}
}
