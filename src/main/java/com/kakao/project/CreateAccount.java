package com.kakao.project;

public class CreateAccount {
	private long mCreateTime;
	private int mClientNumber;
	private String mAccountNumber;
	
	public void setCreateTime(long pCreateTime) {
		this.mCreateTime = pCreateTime;
	}
	
	public long getCreateTime() {
		return mCreateTime;
	}
	
	public void setClientNumber(int pClientNumber) {
		this.mClientNumber = pClientNumber;
	}
	
	public int getClientNumber() {
		return mClientNumber;
	}
	
	public void setAccountNumber(String pAccountNumber) {
		this.mAccountNumber = pAccountNumber;
	}
	
	public String getAccountNumber() {
		return mAccountNumber;
	}
}
