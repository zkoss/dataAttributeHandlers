package org.zkoss.keyfeature2;

public class TemplateInfo {
	private String name;
	private String uri;
	private String icon;
	
	public TemplateInfo(String name, String uri, String icon) {
		this.name = name;
		this.uri = uri;
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public String getUri() {
		return uri;
	}

	public String getIcon() {
		return icon;
	}
	
	public boolean isActive() {
		return false;
	}
}