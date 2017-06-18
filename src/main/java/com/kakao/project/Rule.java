package com.kakao.project;

import java.util.Map;

public interface Rule {
	public String getRuleName();
	public boolean when(Map<String,String> event);
	public void then();
}
