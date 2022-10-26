package com.sgenlecroyant.twitter.kafka.broker.twitter.producer;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import com.github.javafaker.Bool;

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
		configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, String.valueOf(1024 * 64));
		configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
		configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, Integer.toString(5));
		configProps.put(ProducerConfig.ACKS_CONFIG, String.valueOf("all"));
		configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, String.valueOf(Boolean.TRUE));
		configProps.put(ProducerConfig.LINGER_MS_CONFIG, String.valueOf(30 * 1000));
		return configProps;
	}

}
