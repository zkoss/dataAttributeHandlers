package org.zkoss.handlers.jimp;

/**
 * @author Mathieu Piette
 */
public class JimpViewModel {

	private int brightness = 3;
	private String color = "#B000B5"; // It's funny because boobs
	private int contrast = 1;

	public int getBrightness() {
		return brightness;
	}

	public String getColor() {
		return color;
	}

	public int getContrast() {
		return contrast;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

}
