package com.sgenlecroyant.twitter.kafka.broker;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class TwitterKafkaConsumer {

	private static final String KEY_DESERIALIZER = StringDeserializer.class.getName();
	private static final String VALUE_DESERIALIZER = StringDeserializer.class.getName();
	private Consumer<String, String> kafkaConsumer;

	public Consumer<String, String> createTwitterKafkaConsumer() {
		this.kafkaConsumer = new KafkaConsumer<>(this.consumerConfigProps());
		return this.kafkaConsumer;
	}

	private Map<String, Object> consumerConfigProps() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, KEY_DESERIALIZER);
		configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, VALUE_DESERIALIZER);
//		configProps.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka-twitter-elasticsearch-1");
		return configProps;
	}

}
