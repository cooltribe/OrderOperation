package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.OrderDto;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.util.GsonUtils;

public class OrderDetialInfoJsonParser {

	public static PdaResponse<OrderDto> parserOrderDetailInfoJson(String json) {
		PdaResponse<OrderDto> response = new PdaResponse<OrderDto>();
		try {
			Type type = new TypeToken<PdaResponse<OrderDto>>() {
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
