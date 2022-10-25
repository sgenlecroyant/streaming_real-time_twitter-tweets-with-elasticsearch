package com.sgenlecroyant.twitter.kafka.broker.twitter.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

public class TwitterKafkaProducer {

	private String BOOTSTRAP_SERVER = "localhost:9092";
	private String KEY_SERIALIZER = StringSerializer.class.getName();
	private String VALUE_SERIALIZER = StringSerializer.class.getName();

	public Producer<String, String> createProducer() {
		return new KafkaProducer<String, String>(this.getProducerConfigProps());
	}

	private Map<String, Object> getProducerConfigProps() {
		Map<String, Object> configProps = new HashMap<>();
		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, KEY_SERIALIZER);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, VALUE_SERIALIZER);
		return configProps;
	}

}
