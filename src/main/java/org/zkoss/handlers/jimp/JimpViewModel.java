package org.zkoss.handlers.jimp;

/**
 * @author Mathieu Piette
 */
public class JimpViewModel {

	private int contrast = 1;
	private int brightness = 3;

	public int getContrast() {
		return contrast;
	}

	public void setContrast(int contrast) {
		this.contrast = contrast;
	}

	public int getBrightness() {
		return brightness;
	}

	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}

}
