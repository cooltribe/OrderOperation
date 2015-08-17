package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import org.json.JSONException;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.CarsDto;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.util.GsonUtils;

public class LoginJsonParser {

	/**
	 * 解析json数据，得到具体的实体类
	 * 
	 * @param json
	 * @return
	 * @throws JSONException
	 */
	public static PdaResponse<CarsDto> parserLoginJson(String json)
			throws JSONException {
		PdaResponse<CarsDto> response = new PdaResponse<CarsDto>();;
		try {
			Type type = new TypeToken<PdaResponse<CarsDto>>() {
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
