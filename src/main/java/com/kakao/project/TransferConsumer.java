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

public class TransferConsumer extends Thread {
	private final KafkaConsumer<String, String> consumer;
	private final List<String> topics;

	public TransferConsumer(String groupId, List<String> topics) {

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
					Map<String, Object> data = new HashMap<String, Object>();
					data.put("partition", record.partition());
					data.put("offset", record.offset());
					data.put("value", record.value());

					// record value : partition , offset , value
					// value parsing
					Map<String, Transfer> wData = new HashMap<String, Transfer>();
					String[] wSplit = record.value().split(",");
					Transfer transfer = new Transfer();
					Timestamp wTemp = Timestamp.valueOf(wSplit[0]);
					transfer.setCreateTime(wTemp.getTime());
					transfer.setClientNumber(Integer.parseInt(wSplit[1]));
					transfer.setSendAccount(wSplit[2]);
					transfer.setBeforMoney(Long.parseLong(wSplit[3]));
					transfer.setSendBank(wSplit[4]);
					transfer.setSendAccountName(wSplit[5]);
					transfer.setSendMoney(Long.parseLong(wSplit[6]));
					
					String keyConcat = wSplit[0].concat(wSplit[1]);
					
					TransferEventManager transferEvent = TransferEventManager.getInstance();
					transferEvent.putEvent(keyConcat, transfer);
					
					wData.put(keyConcat, transfer);
					System.out.println("--------------Transfer-------------");
					for ( String key : wData.keySet() ) {
						System.out.println("Event Time : " + wData.get(key).getCreateTime());
						System.out.println("Client Number : " +  wData.get(key).getClientNumber());
						System.out.println("Send Account Number : " +  wData.get(key).getSendAccount());
						System.out.println("Send Account Name : " +  wData.get(key).getSendAccountName());
						System.out.println("Send Bank : " +  wData.get(key).getSendBank());
						System.out.println("Send Money : " +  wData.get(key).getSendMoney());
					}
				}
			}
		} catch ( Exception e ) {
			e.printStackTrace();
		} finally {
			consumer.close();
		}
	}

	public void shutdown() {
		consumer.wakeup();
	}
}
