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

public class CreateAccountConsumer extends Thread {
	private final KafkaConsumer<String, String> consumer;
	private final List<String> topics;

	public CreateAccountConsumer(String groupId, List<String> topics) {

		this.topics = topics;
		Properties props = new Properties();
		// props.put("bootstrap.servers",
		// "ec2-13-59-77-249.us-east-2.cumpute.amazonaws.com:9092");
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

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : records) {
					// record value : partition , offset , value
					// value parsing
					Map<String, CreateAccount> wData = new HashMap<String, CreateAccount>();

					String[] wSplit = record.value().split(",");

					CreateAccount createAccount = new CreateAccount();
					Timestamp wTemp = Timestamp.valueOf(wSplit[0]);
					createAccount.setCreateTime(wTemp.getTime());
					createAccount.setClientNumber(Integer.parseInt(wSplit[1]));
					createAccount.setAccountNumber(wSplit[2]);

					CreateAccountEventManager createAccountEvent = CreateAccountEventManager.getInstance();
					if (createAccountEvent.checkEvent(wSplit[2])) {
						createAccountEvent.putEvent(wSplit[2], createAccount);
						wData.put(wSplit[2], createAccount);

						System.out.println("--------------CreateAccount-------------");
						for (String key : wData.keySet()) {
							System.out.println("Account Create Time : " + wData.get(key).getCreateTime());
							System.out.println("Client Number : " + wData.get(key).getClientNumber());
							System.out.println("Account Number : " + wData.get(key).getAccountNumber());
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
