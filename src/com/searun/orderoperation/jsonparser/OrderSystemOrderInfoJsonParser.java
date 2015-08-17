package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SysOrderDto;
import com.searun.orderoperation.util.GsonUtils;

public class OrderSystemOrderInfoJsonParser {

	public static PdaResponse<List<SysOrderDto>> parserOrderSystemOrderInfoJson(String json) {
		PdaResponse<List<SysOrderDto>> response = new PdaResponse<List<SysOrderDto>>();
		try {
			response = new PdaResponse<List<SysOrderDto>>();
			Type type = new TypeToken<PdaResponse<List<SysOrderDto>>>() {
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
