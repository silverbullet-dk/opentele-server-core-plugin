package org.opentele.server.core.model.types


enum QuestionType {
	START('S'), END('E'),QUESTION('Q'), CHOICE('C'), INFORMATION('I')
	
	private final String value
	
	QuestionType(String value) {
		this.value = value
	}

	String value() {
		value
	}
}
