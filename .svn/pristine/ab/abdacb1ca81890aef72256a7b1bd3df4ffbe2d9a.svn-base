package com.searun.orderoperation.jsonparser;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.searun.orderoperation.entity.PdaResponse;
import com.searun.orderoperation.entity.SCM_AttachMentDto;
import com.searun.orderoperation.util.GsonUtils;

/**
 * 订单图片信息，json解析
 * 
 * @author zhazhaobao
 * 
 */
public class AttachmentsJsonParser {

	public static PdaResponse<List<SCM_AttachMentDto>> parserAttachmentsJson(
			String json) {
		PdaResponse<List<SCM_AttachMentDto>> response = new PdaResponse<List<SCM_AttachMentDto>>();
		try {
			response = new PdaResponse<List<SCM_AttachMentDto>>();
			Type type = new TypeToken<PdaResponse<List<SCM_AttachMentDto>>>() {
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
