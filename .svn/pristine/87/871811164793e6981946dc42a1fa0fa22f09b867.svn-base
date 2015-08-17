package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.util.GsonUtils;

public class ResultCodeJsonParser {

	public static PdaResponse<String> parserResultCodeJson(String json) {
		PdaResponse<String> response = new PdaResponse<String>();
		try {
			Type type = new TypeToken<PdaResponse<String>>() {
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
