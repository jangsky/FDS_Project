package com.kakao.project;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RuleEngine {
	private List<Rule> ruleList;
	
	public RuleEngine() {
		ruleList = new ArrayList<Rule>();
	}

	public void load(Rule rule) {
		ruleList.add(rule);
	}
	
	public void execute(String ruleName, Map<String, String> event) {
		for ( Rule rule : ruleList ) {
			if ( rule.getRuleName().equals(ruleName) ) {
				if ( rule.when(event) ) {
					rule.then();
				}
			}
		}
	}
	
	public void execute(Map<String, String> event) {
		for ( Rule rule : ruleList ) {
			if ( rule.when(event) ) {
				rule.then();
			}
		}
	}
}
