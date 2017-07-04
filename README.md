# FDS_Project

# Create Topic
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic CreateAccount
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Transfer
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Deposit
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic Withdrawal
bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic ReceiveEvent

# Receive Event
bin/kafka-console-consumer.sh --zookeeper localhost:2181 --topic ReceiveEvent --from-beginning

# Consumer Input Record
# CreateAccount
2017-06-19 18:55:55.0,2,620-218246-807

# Deposit
2017-06-19 18:55:55.0,2,620-218246-807,100

# Withdrawal
2017-06-19 18:55:55.0,2,620-218246-807,500

# Transfer
2017-06-19 18:55:55.0,2,620-218246-807,100,kakaobank,jang,100

# Build
mvn clean package assembly:single

# Execute
java -jar project-0.0.1-SNAPSHOT-jar-with-dependencies.jar
