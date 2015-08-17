package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.AccountDto;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.util.GsonUtils;

/**
 * 充值账号信息，json解析
 * @author zhazhaobao
 *
 */
public class AccountInfoJsonParser {

	public static PdaResponse<List<AccountDto>> parserOrderOperationDataJson(
			String json) {
		PdaResponse<List<AccountDto>> response = new PdaResponse<List<AccountDto>>();
		try {
			response = new PdaResponse<List<AccountDto>>();
			Type type = new TypeToken<PdaResponse<List<AccountDto>>>() {
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
