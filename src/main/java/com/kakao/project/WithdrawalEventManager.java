package com.kakao.project;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class WithdrawalEventManager {
	private static WithdrawalEventManager instance = new WithdrawalEventManager();
	
	private ConcurrentHashMap<String, List<Withdrawal>> repository = null;
	
	private WithdrawalEventManager() {
		repository = new ConcurrentHashMap<String, List<Withdrawal>>();
	}
	
	public static WithdrawalEventManager getInstance() {
		if( instance == null ) {
			synchronized(WithdrawalEventManager.class) {
				if( instance == null ) {
					instance = new WithdrawalEventManager();
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
	
	public void putEvent(String pKey, Withdrawal pWithdrawal) {
		if( repository.get(pKey) == null ) {
			List<Withdrawal> tmp = new CopyOnWriteArrayList<Withdrawal>();
			repository.put(pKey, tmp);
		}
		repository.get(pKey).add(pWithdrawal);
	}
	
	public void removeEvent(String pKey) {
		repository.remove(pKey);
	}
	
	public List<Withdrawal> getEvent(String pKey) {
		return repository.get(pKey);
	}
}
