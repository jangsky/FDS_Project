package com.kakao.project;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class WithdrawalConsumer extends Thread {
	private final KafkaConsumer<String, String> consumer;
	private final List<String> topics;

	public WithdrawalConsumer(String groupId, List<String> topics) {

		this.topics = topics;
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("group.id", groupId);
		props.put("key.deserializer", StringDeserializer.class.getName());
		props.put("value.deserializer", StringDeserializer.class.getName());
		props.put("auto.offset.reset", "latest");
		this.consumer = new KafkaConsumer<String, String>(props);
	}

	public void run() {
		try {
			consumer.subscribe(topics);
			RuleEngine ruleEngine = new RuleEngine();
			WithdrawalRule withdrawalRule = new WithdrawalRule();
			ruleEngine.load(withdrawalRule);
			
			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : records) {
					// record value : partition , offset , value
					// value parsing
					String[] wSplit = record.value().split(",");
					Withdrawal withdrawal = new Withdrawal();
					Timestamp wTemp = Timestamp.valueOf(wSplit[0]);
					withdrawal.setCreateTime(wTemp.getTime());
					withdrawal.setClientNumber(Integer.parseInt(wSplit[1]));
					withdrawal.setAccountNumber(wSplit[2]);
					withdrawal.setWithdrawalMoney(Long.parseLong(wSplit[3]));

					WithdrawalEventManager withdrawalEvent = WithdrawalEventManager.getInstance();
					if (!withdrawalEvent.checkEvent(wSplit[2])) {
						withdrawalEvent.putEvent(wSplit[2], withdrawal);
						
						Map<String, String> config = new HashMap<String, String>();
						config.put("Time", String.valueOf(withdrawal.getCreateTime()));
						config.put("Account", withdrawal.getAccountNumber());
						ruleEngine.execute("WithdrawalRule", config);
						Map<String, Withdrawal> wData = new HashMap<String, Withdrawal>();
						wData.put(wSplit[2], withdrawal);

						System.out.println("----------------Withdrawal---------------");
						for (String key : wData.keySet()) {
							System.out.println("Event Time : " + wData.get(key).getCreateTime());
							System.out.println("Client Number : " + wData.get(key).getClientNumber());
							System.out.println("Account Number : " + wData.get(key).getAccountNumber());
							System.out.println("Withdrawal Money : " + wData.get(key).getWithdrawalMoney());
						}

					} else {
						SendEvent sendEvent = new SendEvent();
						sendEvent.sendEvent(wSplit[2]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}

	public void shutdown() {
		consumer.wakeup();
	}
}
