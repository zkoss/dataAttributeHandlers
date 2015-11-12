package org.zkoss.keyfeature2;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.NotifyChange;

public class EditCatalogViewModel {

	private String message;
	
	@Command
	@NotifyChange("message")
	public void save() {
		message = "Catalog updated ...";
	}

	public String getMessage() {
		return message;
	}
}
