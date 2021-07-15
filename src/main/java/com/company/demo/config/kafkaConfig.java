package com.company.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.company.demo.entity.EmpSalary;

@EnableKafka
@Configuration
public class kafkaConfig {

		@Bean
		public ProducerFactory<String, EmpSalary> producerFactory() {
			Map<String, Object> configuration = new HashMap<>();
			configuration.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
			configuration.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
			configuration.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
			return new DefaultKafkaProducerFactory<String, EmpSalary>(configuration);
		}
		
		@Bean  
		public KafkaTemplate<String, EmpSalary> kafkaTemplate(){
			return new KafkaTemplate<>(producerFactory());
		}
		
		//Might not work make diff config file
		
		@Bean
		public ConsumerFactory<String, EmpSalary> consumerFactory() {
			Map<String, Object> config = new HashMap<>();
			
			config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
			config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_json");
			config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
			config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
			
			return new DefaultKafkaConsumerFactory<String,EmpSalary>(config, new StringDeserializer(),new JsonDeserializer<>(EmpSalary.class));
		}
		 
		@Bean
		public ConcurrentKafkaListenerContainerFactory<String, EmpSalary> kafkaListenerContainerFactory(){
			ConcurrentKafkaListenerContainerFactory<String, EmpSalary> factory = new ConcurrentKafkaListenerContainerFactory<>();
			factory.setConsumerFactory(consumerFactory());
			factory.setBatchListener(true);
			return factory;
		}
}
