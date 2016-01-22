package org.zkoss.handlers.enrico.ctracker;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.annotation.ToServerCommand;
import org.zkoss.json.JSONArray;
import org.zkoss.json.JSONObject;
import org.zkoss.json.parser.JSONParser;
import org.zkoss.json.parser.ParseException;
import org.zkoss.zk.ui.Executions;

/**
 * @author Enrico Tedeschini (etedeschini@gmail.com)
 * 
 */
 
//@ToServerCommand Specifies that the client-side will invoke this view model.  
@ToServerCommand({"zkddslick$onSelect", "zkctracker$onClickUser", "zkctracker$onSelectMessage", "zkctracker$onDeleteMessage", "zkctracker$onMoveMessage"})
public class CTrackerViewModel {

	private JSONObject	usersDataModel;
	private JSONObject	messagesDataModel;
	private JSONObject	user	= null;
	private String		msg		= null;
	
	/** Constructor */
	public CTrackerViewModel() {
		
		// Load JSON file with the list of the users
		
		usersDataModel = readJSONFile("examples/UsersList.json");
		
		// Create an empty list of chat messages
		
		// messagesDataModel = new JSONObject();
		
		// or load an example of chat from JSON file
		
		messagesDataModel = readJSONFile("examples/messagesList1.json");
	}
	
	/** Method to get the list of users for our custom drop down zkddslick */
	public JSONObject getUsers() {
		return usersDataModel;
	}	
	
	/** Method to get the list of messages for our Chat Tracker (zkctracker) */
	public JSONObject getMessages() {
		return messagesDataModel;
	}
	
	
	@NotifyChange({"enableAdd"})
	@Command
	public void messageTyping(@BindingParam("msg") String msg) {
		this.msg = msg;
	}
	
	/**
	 * Event from client side to notify when a user has been selected on custom drop down zkddslick. 
	 * @param id: zk component ID 
	 * @param index: user index selected 
	 */
	@NotifyChange({"enableAdd"})
	@Command("zkddslick$onSelect")
	public void onSelectUser(@BindingParam("id") String id, @BindingParam("index") Integer index) {
	   
		// get list of users
		JSONArray users = (JSONArray)usersDataModel.get("data");
	
		// get User by index 
		this.user = (JSONObject)users.get(index);
	
			// print to console for debugging
		System.out.println("DdSlick event - user with index=" + index + " has been selected");
	}
	
	/**
	 * Method to add a message on Chat Tracker (zkctracker)
	 * About data structure required by Chat Tracker 
	 * see /examples/messagesList1.json or /examples/messagesList2.json file
	 */
	@NotifyChange({"messages"})
	@Command
	public void addMessage() {
		
		// create new message
		JSONObject message = new JSONObject();
		message.put("id",	System.currentTimeMillis());// unique id
		message.put("time",	new Date().toString());		// never used on client or server side 
		message.put("from",	this.user.get("text"));		// user name
		message.put("text",	msg);						// text of message
		
		// get queue of messages
		JSONArray messages = (JSONArray)messagesDataModel.get("messages");
		
		if (messages == null) {
			messages = new JSONArray();
			messagesDataModel.put("messages", messages);
		}
		
		// add message in the queue
		messages.add(message);
		
		// get users
		JSONArray users = (JSONArray)messagesDataModel.get("users");
	
		if (users == null) {
			users = new JSONArray();
			messagesDataModel.put("users", users);
		}
		
		// add user if not already present
		JSONObject user = new JSONObject();
		user.put("id",		this.user.get("text"));  	// user id
		user.put("image",	this.user.get("imageSrc"));	// user icon
		
		if (users.indexOf(user) == -1)
			users.add(user);
		
		// scroll to view the message added (optional)
		messagesDataModel.put("selectedIndex", messages.size()-1);
		
		System.out.println("Messages:" + messagesDataModel.toJSONString());
	}
	
	/** 
	 * Called to check the conditions satisfied to enable the "Add" button  
	 */
	public boolean isEnableAdd() {
		if (user == null)
			return false;
		if (msg == null || msg.length() == 0)
			return false;
		return true;
	}
	
	/** 
	 * Called to clean up/reset the Chat Tracker (zkctracker)
	 */
	@NotifyChange({"messages"})
	@Command
	public void clearAllMessages() {
		
		messagesDataModel.clear();
	}
	
	/** 
	 * Event from client side to notify when a user has been selected on Chat Tracker (zkctracker) 
	 * 
	 * @param id: zk component ID 
	 * @param name: user name
	 */
	@Command("zkctracker$onClickUser")
	public void onClickUser(@BindingParam("id") String id, @BindingParam("name") String name) {
	   
		System.out.println("Chat Tracker event - user with name=" + name + " has been selected");
	}
	
	/** 
	 * Event from client side to notify when a message has been selected on Chat Tracker (zkctracker)
	 * 
	 * @param id: zk component ID 
	 * @param messageId: unique message id
	 */
	@Command("zkctracker$onSelectMessage")
	public void onSelectedMessage(@BindingParam("id") String id, @BindingParam("messageId") String messageId) {
	   
		System.out.println("Chat Tracker event - message with messageId=" + messageId + " has been selected");
	}
	
	/** 
	 * Event from client side to notify when a message has been deleted on Chat Tracker (zkctracker)
	 * Useful to synch the data model
	 * 
	 * @param id: zk component ID 
	 * @param messageId: unique message id
	 */
	@Command("zkctracker$onDeleteMessage")
	public void onDeleteMessage(@BindingParam("id") String id, @BindingParam("messageId") String messageId) {
	   
		// find message in the data model by unique message id
		JSONArray messages = (JSONArray)messagesDataModel.get("messages");
		
		for (Object obj: messages) {
		
			JSONObject message = (JSONObject)obj;
			String strId = message.get("id").toString();
			if (strId.equals(messageId)) {
				// delete message in the queue
				messages.remove(obj);
				break;
			}
		}
		
		System.out.println("Chat Tracker event - message with messageId=" + messageId + " has been deleted");
	}
	
	/** 
	 * Event from client side to notify when a message has been moved on Chat Tracker (zkctracker)
	 * Useful to synch the data model
	 * 
	 * @param id: zk component ID 
	 * @param messageId: unique message id
	 * @param from: user owner of this message
	 * @param y: new y message coordinate  
	 * 
	 */
	@Command("zkctracker$onMoveMessage")
	public void onMoveMessage(@BindingParam("id") String id, @BindingParam("messageId") String messageId, @BindingParam("from") String from, @BindingParam("y") Integer y) {
	   
		// Find message in the data model by unique message id
		JSONArray messages = (JSONArray)messagesDataModel.get("messages");
		
		for (Object obj: messages) {
		
			JSONObject message = (JSONObject)obj;
			String strId = message.get("id").toString();
			if (strId.equals(messageId)) {
			
				// update message position
				message.put("from", from);	// new x coordinate is given by user owner x position on the screen
				message.put("y", y);		// new y coordinate
				break;
			}
		}
		
		System.out.println("Chat Tracker event - message with messageId=" + messageId + " has been moved at y=" + y);
	}
	
	/**
	 * Called to load from file an example of chat
	 */
	@NotifyChange({"messages"})
	@Command
	public void example1() {
		
		messagesDataModel = readJSONFile("examples/messagesList1.json");
		
		// I added this line to force the Chat Tracker reloading. 
		messagesDataModel.put("date", new Date().toString());
	}
	
	/**
	 * Called to load from file another example of chat
	 */
	@NotifyChange({"messages"})
	@Command
	public void example2() {
		
		messagesDataModel = readJSONFile("examples/messagesList2.json");
	
		// I added this line to force the Chat Tracker reloading. 
		messagesDataModel.put("date", new Date().toString());
	}	
	
	/**
	 * Read JSON resource file with JRE 1.6, JRE 1.7 or JDK 1.8
	 * Simple version compliant with all JRE 1.6, JRE 1.7 or JDK 1.8
	 * to avoid issues.
	 */
	private JSONObject readJSONFile(String resourceFileName) {
		
		BufferedReader br = null;
		try {
			// get resource file handle
			String pathRootResources = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
			File file = new File(pathRootResources, resourceFileName);
			
			if (!file.exists()) {
				
				// In case of running this Demo with all other Data Handler Demo
				// Projects we have a different web application root path. 
				// For this reason if the file has not been found we try to search this file 
				// under "ctracker" sub folder.
	
				file = new File(pathRootResources + "/ctracker/", resourceFileName);
			}
			
			// read file line by line
			br = new BufferedReader(new FileReader(file));
		    	StringBuilder  sb = new StringBuilder();
		    	String line = br.readLine();
	
		    	while (line != null) {
		        	sb.append(line);
		        	line = br.readLine();
		    	}
		    	String everything = sb.toString();
		    	br.close();
		    	br = null;
		    
		    	// parse text file to get JSON Object
		    	Object obj = new JSONParser().parse(everything);
		    	return (JSONObject)obj;
		    
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (RuntimeException e) {
			e.printStackTrace();
		} 
		finally {
			try {
				if (br != null) 
				    br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return new JSONObject();
	}
}
