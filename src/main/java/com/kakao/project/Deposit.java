package com.kakao.project;

public class Deposit {
	private long mCreateTime;
	private int mClientNumber;
	private String mAccountNumber;
	private long mDepositMoney;
	
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
	
	public void setDepositMoney(long pDepositMoney) {
		this.mDepositMoney = pDepositMoney;
	}
	
	public long getDepositMoney() {
		return mDepositMoney;
	}
}
