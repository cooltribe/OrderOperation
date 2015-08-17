package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.PdaVersionInfoDto;
import com.searun.orderoperation.util.GsonUtils;

public class CheckUpdateJsonParser {

	public static PdaResponse<PdaVersionInfoDto> parserCheckUpdateJson(String json) {
		PdaResponse<PdaVersionInfoDto> response = new PdaResponse<PdaVersionInfoDto>();
		try {
			response = new PdaResponse<PdaVersionInfoDto>();
			Type type = new TypeToken<PdaResponse<PdaVersionInfoDto>>() {
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
