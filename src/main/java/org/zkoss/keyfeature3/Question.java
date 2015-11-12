package org.zkoss.keyfeature3;

import java.util.List;

public class Question {
	private String statement;
	private String type;
	private List<Option> options;

	public Question(String statement, List<Option> options, String type) {
		this.statement = statement;
		this.options = options;
		this.type = type;
	}

	public String getStatement() {
		return statement;
	}

	public String getType() {
		return type;
	}

	public List<Option> getOptions() {
		return options;
	}
}
