package com.kakao.project;

import java.util.List;
import java.util.Map;

public class WithdrawalRule implements Rule{
    private String name = "WithdrawalRule";

    public String getRuleName() { return name; }

    public boolean when(Map<String, String> pEvent) {
        long currentTime = Long.parseLong((pEvent.get("Time")));
        // long currentWithdrawal = pEvent.getWithdrawalMoney();
        String accountNo = pEvent.get("Account");
        
        CreateAccountEventManager newAccounts = CreateAccountEventManager.getInstance();
        CreateAccount newAccount = newAccounts.getEvent(accountNo);
        
        if ( newAccount != null ) {
            if ((currentTime - newAccount.getCreateTime()) <= (86400 * 7)) {
            	DepositEventManager deposits = DepositEventManager.getInstance();
                List<Deposit> depositList = deposits.getEvent(accountNo);
                long depositAmount = 0;

                boolean isSuspected = false;

                if ( depositList != null ) {
                    for (Deposit d : depositList) {
                        depositAmount += d.getDepositMoney();
                        if ((currentTime - d.getCreateTime()) <= 7200) {
                            if (d.getDepositMoney() >= 900000 && d.getDepositMoney() <= 1000000) {
                                isSuspected = true;
                            }
                        }
                    }
                }

                if (isSuspected) {
                	WithdrawalEventManager withdrawals = WithdrawalEventManager.getInstance();
                    List<Withdrawal> withdrawalList = withdrawals.getEvent(accountNo);
                    long withdrawalAmount = 0;

                    if (withdrawalList != null) {
                        for (Withdrawal w : withdrawalList) {
                            withdrawalAmount += w.getWithdrawalMoney();
                        }
                    }
                    
                    // withdrawalAmount += currentWithdrawal;
                    if ((depositAmount - withdrawalAmount) <= 10000) {
                        return true;
                    }
                }
            }
        }

        return false;
    }
    
    public void then() {
		SendEvent sendEvent = new SendEvent();
		sendEvent.sendEvent("Detected Rule Event.");
    }
}

