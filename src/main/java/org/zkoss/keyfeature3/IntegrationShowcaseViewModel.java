package org.zkoss.keyfeature3;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.zkoss.zk.ui.WebApps;
import org.zkoss.zsoup.helper.StringUtil;

public class IntegrationShowcaseViewModel {

	private boolean like = true;
	private boolean zk = true;
	private String percentage = "33%";
	private String chartist_percentage = "55%";
	private String syntax = "var i = 0;\n\nfor (var i = 0; i < 10; i++) {\n\tconsole.log('Hello');\n}";
	private String markdown = "#Attention \n The team made some adjustments in the **style.css** file. \n Please remember to remove ~~italic~~, and changethe style to: \n \t font-style: normal; \n\n For more information, please read the [style guide](https://example.com).";

	public boolean isLike() {
		return like;
	}

	public void setLike(boolean like) {
		this.like = like;
	}

	public boolean isZk() {
		return zk;
	}

	public void setZk(boolean zk) {
		this.zk = zk;
	}

	public String getPercentage() {
		return percentage;
	}

	public String getSyntax() {
		return syntax;
	}

	public void setSyntax(String syntax) {
		this.syntax = syntax;
	}

	public String getMarkdown() {
		return markdown;
	}

	public void setMarkdown(String markdown) {
		this.markdown = markdown;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getChartist_percentage() {
		return chartist_percentage;
	}

	public void setChartist_percentage(String chartist_percentage) {
		this.chartist_percentage = chartist_percentage;
	}

	public String loadSource(String path) throws IOException {
		InputStream inputStream = WebApps.getCurrent().getResourceAsStream(path);
		String rawSource = IOUtils.toString(inputStream);
		String[] lines = rawSource.split("[\r\n]{1,2}");
		List<String> codes = new ArrayList<String>();
		for (String line : lines) {
			if (notContainedSpecificString(line)) {
				codes.add(line);
			}
		}
		return StringUtil.join(codes, "\r\n");
	}

	private boolean notContainedSpecificString(String line) {
		return !line.contains("<zk") && !line.contains("/zk") && !line.contains("vlayout");
	}
}
