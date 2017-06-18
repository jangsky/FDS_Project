package com.kakao.project;

import java.util.Arrays;
import java.util.List;

public class KakaoApplication {
	
	public static void main( String[] args ) {
		// kafka log off
        ch.qos.logback.classic.Logger root = (ch.qos.logback.classic.Logger)
        		org.slf4j.LoggerFactory.getLogger((ch.qos.logback.classic.Logger.ROOT_LOGGER_NAME));
        root.setLevel(ch.qos.logback.classic.Level.OFF);
		
		String groupId = "consumer-tutorial-group";
        List<String> createAccountTopic = Arrays.asList("CreateAccount");
        List<String> transferTopic = Arrays.asList("Transfer");
        List<String> withdrawalTopic = Arrays.asList("Withdrawal");
        List<String> depositTopic = Arrays.asList("Deposit");
        
        WithdrawalConsumer withdrawalConsumer = new WithdrawalConsumer(groupId, withdrawalTopic);
        withdrawalConsumer.start();

        TransferConsumer transferConsumer = new TransferConsumer(groupId, transferTopic);
        transferConsumer.start();
        
        DepositConsumer depositConsumer = new DepositConsumer(groupId, depositTopic);
        depositConsumer.start();
        
        CreateAccountConsumer createAccountConsumer = new CreateAccountConsumer(groupId, createAccountTopic);
        createAccountConsumer.start();
	}
}
