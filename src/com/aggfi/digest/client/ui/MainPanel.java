package com.aggfi.digest.client.ui;

import com.aggfi.digest.client.constants.ConstantsImpl;
import com.aggfi.digest.client.constants.MessagesImpl;
import com.aggfi.digest.client.resources.GlobalResources;
import com.aggfi.digest.client.service.NewPostGadgetService;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.vegalabs.general.client.utils.VegaUtils;

public class MainPanel extends Composite {

	private static MainPanelUiBinder uiBinder = GWT
			.create(MainPanelUiBinder.class);

	interface MainPanelUiBinder extends UiBinder<Widget, MainPanel> {
	}
	
	@UiField
	Button createNewPostBtn;
	@UiField
	TextBox newPostTtlTextBox;
	@UiField
	Label aboutNewPostLbl;
	@UiField
	Label helloUserLbl;
	@UiField
	DisclosurePanel dsAboutPnl;

	private NewPostGadgetService service;
	private MessagesImpl messages;
	private ConstantsImpl constants;
	private GlobalResources resources;
	private VegaUtils utils;

	
	@Inject
	public MainPanel(final MessagesImpl messages, final ConstantsImpl constants, final GlobalResources resources, final NewPostGadgetService service, final VegaUtils utils) {
		initWidget(uiBinder.createAndBindUi(this));
		resources.globalCSS().ensureInjected();
		this.service = service;
		this.messages = messages;
		this.constants = constants;
		this.resources = resources;
		this.utils = utils;
//		dsAboutPnl.setHeader()
		try{
			setForumInfoIntoGadget(messages, utils);
		}catch(Exception e){
			Log.debug(e.getMessage());
		}
		Timer timer = new Timer() {
			
			@Override
			public void run() {
				try{
					setForumInfoIntoGadget(messages, utils);
				}catch(Exception e){
					Log.debug(e.getMessage());
				}
			}
		};
		timer.schedule(1000);
		utils.reportPageview("/newpost/");
	}
	
	@UiHandler("createNewPostBtn")
	void createNewPost(ClickEvent event){
		String userId = utils.retrUserId();
		String projectId = utils.retrFromState("projectId");
		String title = newPostTtlTextBox.getText();
		utils.showStaticMessage(messages.showSentRequestMsg(utils.retrFromState("projectName")));
		try {
			service.createNewPost(projectId, userId, title, new AsyncCallback<JSONValue>() {
				
				@Override
				public void onSuccess(JSONValue result) {
					Log.debug(result.toString());
					utils.dismissStaticMessage();
					utils.showSuccessMessage(constants.successStr(), 3);
				}
				
				@Override
				public void onFailure(Throwable caught) {
					utils.dismissStaticMessage();
					utils.alert(caught.getMessage());
				}
			});
		} catch (RequestException e) {
			Log.error("error in createNewPost", e);
		}
		utils.reportEvent("/newpost/", "createNewPost", projectId, 1);
	}

	public void setForumInfoIntoGadget(final MessagesImpl messages,
			final VegaUtils utils) {
		aboutNewPostLbl.setText(messages.aboutUseNewPostGadgetMsg(utils.retrFromState("projectName")));
		helloUserLbl.setText(messages.helloMsg(utils.retrUserName()));
	}
}
