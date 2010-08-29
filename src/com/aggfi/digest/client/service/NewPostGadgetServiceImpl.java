package com.aggfi.digest.client.service;

import java.util.Date;
import com.vegalabs.general.client.request.RequestService;
import com.aggfi.digest.client.constants.ConstantsImpl;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.inject.Inject;

public class NewPostGadgetServiceImpl implements NewPostGadgetService {
	
	private RequestService requestService;
	private String url;
	
	@Inject
	public NewPostGadgetServiceImpl(RequestService requestService, ConstantsImpl constants){
		this.requestService = requestService;
		url = "/admin/jsonrpc" + "?cachebust=" + new Date().getTime();
	}



	@Override
	public void createNewPost(String projectId,String userId, String title, AsyncCallback<JSONValue> asyncCallback)
			throws RequestException {
		com.google.gwt.json.client.JSONObject paramsJson = new JSONObject();
		com.google.gwt.json.client.JSONObject postDataJson = new JSONObject();
		
		paramsJson.put("projectId", new JSONString(projectId));
		paramsJson.put("userId", new JSONString(userId));
		paramsJson.put("title", new JSONString(title));
		postDataJson.put("params", paramsJson);
		postDataJson.put("method", new JSONString("CREATE_NEW_POST"));
		
		JavaScriptObject params = postDataJson.getJavaScriptObject();
		requestService.makeRequest(url,asyncCallback,params);
	}
}
