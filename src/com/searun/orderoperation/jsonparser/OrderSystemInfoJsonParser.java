package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.util.GsonUtils;

public class OrderSystemInfoJsonParser {

	public static PdaResponse<TmDispatchDto> parserOrderSystemInfoJson(
			String json) {
		PdaResponse<TmDispatchDto> response = new PdaResponse<TmDispatchDto>();
		try {
			response = new PdaResponse<TmDispatchDto>();
			Type type = new TypeToken<PdaResponse<TmDispatchDto>>() {
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
