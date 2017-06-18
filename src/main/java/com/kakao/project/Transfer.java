package com.kakao.project;

public class Transfer {
	private long mCreateTime;
	private int mClientNumber;
	
	private String mSendAccount;
	private long mBeforMoney;
	private String mSendBank;
	private String mSendAccountName;
	private long mSendMoney;
	
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
	
	public void setSendAccount(String pSendAccount) {
		this.mSendAccount= pSendAccount;
	}
	
	public String getSendAccount() {
		return mSendAccount;
	}
	
	public void setSendMoney(long pTransferMoney) {
		this.mSendMoney = pTransferMoney;
	}
	
	public long getSendMoney() {
		return mSendMoney;
	}

	public void setBeforMoney(long pBeforMoney) {
		this.mBeforMoney = pBeforMoney;
	}
	
	public long getBeforMoney() {
		return mBeforMoney;
	}
	
	public void setSendBank(String pSendBank) {
		this.mSendBank = pSendBank;
	}
	
	public String getSendBank() {
		return mSendBank;
	}
	
	public void setSendAccountName(String pSendAccountName) {
		this.mSendAccountName = pSendAccountName;
	}
	
	public String getSendAccountName() {
		return mSendAccountName;
	}
}
