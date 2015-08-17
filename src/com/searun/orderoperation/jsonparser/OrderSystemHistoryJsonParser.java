package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.TmDispatchDto;
import com.searun.orderoperation.util.GsonUtils;

public class OrderSystemHistoryJsonParser {

	public static PdaResponse<List<TmDispatchDto>> parserOrderSystemHistoryJson(
			String json) {
		PdaResponse<List<TmDispatchDto>> response = new PdaResponse<List<TmDispatchDto>>();
		try {
			response = new PdaResponse<List<TmDispatchDto>>();
			Type type = new TypeToken<PdaResponse<List<TmDispatchDto>>>() {
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
