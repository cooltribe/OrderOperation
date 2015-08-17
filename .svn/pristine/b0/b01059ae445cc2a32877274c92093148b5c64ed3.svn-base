package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.OrderDto;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.util.GsonUtils;

public class OrderInfoJsonParser {

	public static PdaResponse<List<OrderDto>> parserOrderInfoJson(String json) {
		PdaResponse<List<OrderDto>> response = new PdaResponse<List<OrderDto>>();
		try {
			Type type = new TypeToken<PdaResponse<List<OrderDto>>>() {
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
