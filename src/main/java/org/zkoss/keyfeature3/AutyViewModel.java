package org.zkoss.keyfeature3;

import java.util.Arrays;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zul.ListModelList;

public class AutyViewModel {

	private ListModelList<Question> questions;
	private static final String CHECKBOX = "checkbox";
	private static final String RADIO = "radio";

	@Init
	public void init() {
		questions = new ListModelList<Question>();
		questions.add(newQuestion("Q1: Which java versions do you currenlty use ?", CHECKBOX, null, "120px",
				new Option("We use JDK 6", "We don't use JDK 6"), new Option("We use JDK 7", "We don't use JDK 7"),
				new Option("We use JDK 8", "We don't use JDK 8")));
		questions.add(newQuestion("Q2: When do you plan to upgrade to JDK 8 ?", RADIO, null, "100px",
				new Option("we already did"), new Option("next month"), new Option("next year"), new Option("never")));
		questions.add(newQuestion("Q3: Do you like the new features in JDK8 ?", CHECKBOX, "labelauty terms-icon", "100px",
				new Option("I like it", "Don't like it")));
	}

	private Question newQuestion(String statement, String type, String className, String minWidth, Option... options) {
		for (Option option : options) {
			if (className != null)
				option.setClass(className);

			if (minWidth != null)
				option.setMinimumWidth(minWidth);
		}
		return new Question(statement, Arrays.asList(options), type);
	}

	public ListModelList<Question> getQuestions() {
		return questions;
	}

}
