package com.kakao.project;

import java.util.concurrent.ConcurrentHashMap;

public class TransferEventManager {
	private static TransferEventManager instance = new TransferEventManager();
	
	private ConcurrentHashMap<String, Transfer> repository = null;
	
	private TransferEventManager() {
		repository = new ConcurrentHashMap<String, Transfer>();
	}
	
	public static TransferEventManager getInstance() {
		if( instance == null ) {
			synchronized(TransferEventManager.class) {
				if( instance == null ) {
					instance = new TransferEventManager();
				}
			}
		}
		return instance;
	}
	
	public void putEvent(String pKey, Transfer pTransfer) {
		repository.put(pKey, pTransfer);
	}
	
	public void removeEvent(String pKey) {
		repository.remove(pKey);
	}
	
	public Transfer getEvent(String pKey) {
		return repository.get(pKey);
	}
}
