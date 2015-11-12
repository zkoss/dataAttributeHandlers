/** DataListViewModel.java.

	Purpose:
		
	Description:
		
	History:
		12:07:27 PM Jan 22, 2015, Created by jumperchen

Copyright (C) 2015 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.keyfeature2;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.bind.BindUtils;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.keyfeature2.catalog.Catalog;

public class CatalogViewModel {
	
	private List<TemplateInfo> availableTemplates = new ArrayList<TemplateInfo>();
	private TemplateInfo currentTemplate;
	private Catalog catalog;
	
	@Init
	public void init() {
		availableTemplates.add(newTemplateInfo("grid", "catalogGrid.zul", "z-icon-th-large"));
		availableTemplates.add(newTemplateInfo("list", "catalogList.zul", "z-icon-th-list"));
		availableTemplates.add(newTemplateInfo("tree", "catalogTree.zul", "z-icon-sitemap"));
		availableTemplates.add(newTemplateInfo("editable", "catalogEditable.zul", "z-icon-edit"));
		availableTemplates.add(newTemplateInfo("raw data", "catalogRaw.zul", "z-icon-file-text-o"));
		currentTemplate = availableTemplates.get(0); 
		catalog = new Catalog();
	}

	@Command("changeTemplate")
	@NotifyChange("currentTemplate")
	public void changeTemplate(@BindingParam("template") TemplateInfo template) {
		BindUtils.postNotifyChange(null, null, currentTemplate, "active");
		currentTemplate = template;
		BindUtils.postNotifyChange(null, null, currentTemplate, "active");
	}
	
	public TemplateInfo getCurrentTemplate() {
		return currentTemplate;
	}

	public List<TemplateInfo> getAvailableTemplates() {
		return availableTemplates;
	}

	public Catalog getCatalog() {
		return catalog;
	}
	
	private TemplateInfo newTemplateInfo(String name, String template, String icon) {
		return new TemplateInfo(name, templateLocation(template), icon) {
			@Override
			public boolean isActive() {
				return currentTemplate == this;
			}
		};
	}

	private String templateLocation(String name) {
		return ResourceLocation.TEMPLATE_LOCATION + "/" + name;
	}
}
