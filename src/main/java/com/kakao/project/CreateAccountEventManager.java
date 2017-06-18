package com.kakao.project;

import java.util.concurrent.ConcurrentHashMap;

public class CreateAccountEventManager {
	private static CreateAccountEventManager instance = new CreateAccountEventManager();
	
	private ConcurrentHashMap<String, CreateAccount> repository = null;
	
	private CreateAccountEventManager() {
		repository = new ConcurrentHashMap<String, CreateAccount>();
	}
	
	public static CreateAccountEventManager getInstance() {
		if( instance == null ) {
			synchronized(CreateAccountEventManager.class) {
				if( instance == null ) {
					instance = new CreateAccountEventManager();
				}
			}
		}
		return instance;
	}

	public boolean checkEvent(String pKey) {
		boolean check = true;
	
		if(repository.containsKey(pKey)){
			check = false;
		}
		return check;
	}
	
	public void putEvent(String pKey, CreateAccount pCreateAccount) {
		repository.put(pKey, pCreateAccount);
	}
	
	public void removeEvent(String pKey) {
		repository.remove(pKey);
	}
	
	public CreateAccount getEvent(String pKey) {
		return repository.get(pKey);
	}
}
