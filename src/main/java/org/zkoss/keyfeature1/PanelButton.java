package org.zkoss.keyfeature1;

public class PanelButton {

	private String btnID;
	private String btnIcon;
	private String btnClass;
	private String btnCommand;

	public PanelButton(String btnID, String btnIcon, String btnClass, String btnCommand) {
		this.btnID = btnID;
		this.btnIcon = btnIcon;
		this.btnClass = btnClass;
		this.btnCommand = btnCommand;
	}
	public String getBtnID() {
		return btnID;
	}
	public String getBtnIcon() {
		return btnIcon;
	}
	public String getBtnClass() {
		return btnClass;
	}
	public String getBtnCommand() {
		return btnCommand;
	}
	
}
