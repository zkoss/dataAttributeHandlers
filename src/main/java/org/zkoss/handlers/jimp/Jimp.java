package org.zkoss.handlers.jimp;

import org.zkoss.json.JSONObject;
import org.zkoss.zul.Image;

/**
 * @author Mathieu Piette
 */
public class Jimp extends Image {
	private static final long serialVersionUID = -6256500698118155465L;

	private int brightness;
	private String color;
	private int contrast;
	private boolean sendToServer;

	public int getBrightness() {
		return brightness;
	}
	public String getColor() {
		return color;
	}

	public int getContrast() {
		return contrast;
	}

	public boolean isSendToServer() {
		return sendToServer;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
		setJimp();
	}

	public void setColor(String color) {
		this.color = color;
		setJimp();
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
		setJimp();
	}

	public void setJimp() {
		JSONObject json = new JSONObject();
		
		json.put("contrast", contrast);
		json.put("brightness", brightness);
		json.put("color", color);
		json.put("sendToServer", sendToServer);

		this.setClientDataAttribute("jimp", json.toJSONString());
	}

	public void setSendToServer(boolean sendToServer) {
		this.sendToServer = sendToServer;
		setJimp();
	}
}
