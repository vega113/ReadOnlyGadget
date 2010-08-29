package com.aggfi.digest.client.service;


import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface NewPostGadgetService {

	void createNewPost(String projectId, String userId, String title,
			AsyncCallback<JSONValue> asyncCallback) throws RequestException;
	
}
