package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.MemAccountDto;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.util.GsonUtils;

/**
 * 充值账号信息，json解析
 * @author zhazhaobao
 *
 */
public class RechargeAccountJsonParser {

	public static PdaResponse<MemAccountDto> parserRechargeAccountJson(String json) {
		PdaResponse<MemAccountDto> response = new PdaResponse<MemAccountDto>();;
		try {
			response = new PdaResponse<MemAccountDto>();
			Type type = new TypeToken<PdaResponse<MemAccountDto>>() {
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
