package com.kakao.project;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class DepositEventManager {
	private static DepositEventManager instance = new DepositEventManager();
	
	private ConcurrentHashMap<String, List<Deposit>> repository = null;
	
	private DepositEventManager() {
		repository = new ConcurrentHashMap<String, List<Deposit>>();
	}
	
	public static DepositEventManager getInstance() {
		if( instance == null ) {
			synchronized(DepositEventManager.class) {
				if( instance == null ) {
					instance = new DepositEventManager();
				}
			}
		}
		return instance;
	}
	
	public boolean checkEvent(String pKey) {
		boolean check = false;
		
		CreateAccountEventManager account = CreateAccountEventManager.getInstance();
		check = account.checkEvent(pKey);
	
		return check;
	}
	
	public void putEvent(String pKey, Deposit pDeposit) {
		if( repository.get(pKey) == null ) {
			List<Deposit> tmp = new CopyOnWriteArrayList<Deposit>();
			repository.put(pKey, tmp);
		}
			
		repository.get(pKey).add(pDeposit);
	}
	
	public void removeEvent(String pKey) {
		repository.remove(pKey);
	}
	
	public List<Deposit> getEvent(String pKey) {
		return repository.get(pKey);
	}
}
