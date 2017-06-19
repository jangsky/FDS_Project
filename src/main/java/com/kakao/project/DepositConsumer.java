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

public class DepositConsumer extends Thread {
	private final KafkaConsumer<String, String> consumer;
	private final List<String> topics;

	public DepositConsumer(String groupId, List<String> topics) {
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

			while (true) {
				ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : records) {
					// record value : partition , offset , value
					// value parsing
					Map<String, Deposit> wData = new HashMap<String, Deposit>();

					String[] wSplit = record.value().split(",");

					Deposit deposit = new Deposit();
					Timestamp wTemp = Timestamp.valueOf(wSplit[0]);
					deposit.setCreateTime(wTemp.getTime());
					deposit.setClientNumber(Integer.parseInt(wSplit[1]));
					deposit.setAccountNumber(wSplit[2]);
					deposit.setDepositMoney(Long.parseLong(wSplit[3]));

					DepositEventManager depositEvent = DepositEventManager.getInstance();
					if (!depositEvent.checkEvent(wSplit[2])) {
						depositEvent.putEvent(wSplit[2], deposit);

						wData.put(wSplit[2], deposit);

						System.out.println("---------------Deposit----------------");
						for (String key : wData.keySet()) {
							System.out.println("Event Time : " + wData.get(key).getCreateTime());
							System.out.println("Client Number : " + wData.get(key).getClientNumber());
							System.out.println("Account Number : " + wData.get(key).getAccountNumber());
							System.out.println("Deposit Money : " + wData.get(key).getDepositMoney());
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
