package org.zkoss.handlers.jimp;

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
		// TODO use Jackson instead of this ugly concatenation
		String jimp = "{\"" + "contrast\" : " + contrast + ", " + "\"brightness\" : " + brightness + ","
				+ "\"color\" : \"" + color + "\"," + "\"sendToServer\" : " + sendToServer + "}";
		this.setClientDataAttribute("jimp", jimp);
	}

	public void setSendToServer(boolean sendToServer) {
		this.sendToServer = sendToServer;
		setJimp();
	}
}
