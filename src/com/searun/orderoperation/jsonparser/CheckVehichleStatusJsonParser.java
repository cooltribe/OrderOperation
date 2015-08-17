package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.entity.VehicleStatusDto;
import com.searun.orderoperation.util.GsonUtils;

public class CheckVehichleStatusJsonParser {

	public static PdaResponse<VehicleStatusDto> parserVehicleStatusInfoJson(
			String json) {
		PdaResponse<VehicleStatusDto> response = new PdaResponse<VehicleStatusDto>();
		try {
			response = new PdaResponse<VehicleStatusDto>();
			Type type = new TypeToken<PdaResponse<VehicleStatusDto>>() {
			}.getType();
			response = GsonUtils.createCommonBuilder().create()
					.fromJson(json, type);
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
			return response;
		}
		return response;
	}

}
