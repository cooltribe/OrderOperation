package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SmsInfoDto;
import com.searun.orderoperation.util.GsonUtils;

public class SMSJsonParser {

	public static PdaResponse<SmsInfoDto> parserSMSJson(String json) {
		PdaResponse<SmsInfoDto> response = new PdaResponse<SmsInfoDto>();
		try {
			response = new PdaResponse<SmsInfoDto>();
			Type type = new TypeToken<PdaResponse<SmsInfoDto>>() {
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
