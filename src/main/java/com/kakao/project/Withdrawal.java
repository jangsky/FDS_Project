package com.kakao.project;

public class Withdrawal {
	private long mCreateTime;
	private int mClientNumber;
	private String mAccountNumber;
	private long mWithdrawalMoney;
	
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
	
	public void setWithdrawalMoney(long pWithdrawalMoney) {
		this.mWithdrawalMoney= pWithdrawalMoney;
	}
	
	public long getWithdrawalMoney() {
		return mWithdrawalMoney;
	}
}
