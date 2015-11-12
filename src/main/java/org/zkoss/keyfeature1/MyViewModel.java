package org.zkoss.keyfeature1;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.ToClientCommand;
import org.zkoss.json.JSONObject;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.ListModelList;

@ToClientCommand(MyViewModel.ADD_NEW_MESSAGE)
public class MyViewModel {
	public static final String ADD_NEW_MESSAGE = "addNewMessage";
	public static final String HANDLE_PANEL_BUTTON = "handlePanelButton";
	public static final String VIEW_DETAIL = "viewDetail";
	private ListModelList<StatBlock> myStats = new ListModelList<StatBlock>();
	private ListModelList<Message> myMessages = new ListModelList<Message>();
	private ListModelList<PanelButton> myPanelButtons = new ListModelList<PanelButton>();
	private String messageTextbox = "";
	private JSONObject scrollProperty = new JSONObject();

	@Init
	public void init() {
		myStats.add(new StatBlock("totalVisitorClick", "TOTAL VISITORS", "3,291,922", "fa-desktop", "green"));
		myStats.add(new StatBlock("bounceRateClick", "BOUNCE RATE", "20,44%", "fa-chain-broken ", "blue"));
		myStats.add(new StatBlock("uniqueVisitorClick", "UNIQUE VISITORS", "1,291,922", "fa-users", "purple"));
		myStats.add(new StatBlock("avgTimeClick", "AVG TIME ON SITE", "00:12:23", "fa-clock-o", "red"));
		
		myMessages.add(new Message(new User("John Doe","./img/user-5.jpg"),"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi id nunc non eros fermentum vestibulum ut id felis."));
		myMessages.add(new Message(new User("Terry Ng","./img/user-6.jpg"),"Sed in ante vel ipsum tristique euismod posuere eget nulla. Quisque ante sem, scelerisque iaculis interdum quis, eleifend id mi."));
		myMessages.add(new Message(new User("Fiona Log","./img/user-8.jpg"),"Pellentesque dictum in tortor ac blandit. Nulla rutrum eu leo vulputate ornare. Nulla a semper mi, ac lacinia sapien."));
		myMessages.add(new Message(new User("John Doe","./img/user-7.jpg"),"Morbi molestie lorem quis accumsan elementum. Morbi condimentum nisl iaculis, laoreet risus sed, porta neque."));
		
		myPanelButtons.add(new PanelButton("button1","fa-expand","btn-default",HANDLE_PANEL_BUTTON));
		myPanelButtons.add(new PanelButton("button2","fa-repeat","btn-success",HANDLE_PANEL_BUTTON));
		myPanelButtons.add(new PanelButton("button3","fa-minus","btn-warning",HANDLE_PANEL_BUTTON));
		myPanelButtons.add(new PanelButton("button4","fa-times","btn-danger",HANDLE_PANEL_BUTTON));

	}

	public ListModelList<Message> getMyMessages() {
		return myMessages;
	}

	public ListModelList<StatBlock> getMyStats() {
		return myStats;
	}
	
	public ListModelList<PanelButton> getMyPanelButtons() {
		return myPanelButtons;
	}
	
	public void setMessageTextbox(String messageTextbox) {
		this.messageTextbox = messageTextbox;
	}
	
	public String getMessageTextbox() {
		return messageTextbox;
	}

	public JSONObject getScrollProperty() {
		return scrollProperty;
	}
	
	@Command(VIEW_DETAIL)
	public void viewDetail(@BindingParam("statId") String statId) {
		Clients.showNotification(statId, null, null, null, 2000);
	}
	
	@Command(HANDLE_PANEL_BUTTON)
	public void handlePanelButton(@BindingParam("buttonId") String buttonId) {
		Clients.showNotification(buttonId, null, null, null, 2000);
	}
	
	@Command(ADD_NEW_MESSAGE)
	@NotifyChange("messageTextbox")
	public void addNewMessage() {
		if (messageTextbox != null && !"".equals(messageTextbox)) {
			myMessages.add(new Message(new User("John Doe", "./img/user-7.jpg"), messageTextbox));
			messageTextbox = "";
		}
	}

}
