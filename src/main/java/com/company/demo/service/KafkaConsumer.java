 package com.company.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.company.demo.entity.EmpSalary;

@Service
public class KafkaConsumer {
	@Autowired
	DataManager datamanager;

	
	private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);

	@KafkaListener(topics = "Example", groupId = "group_json", containerFactory = "kafkaListenerContainerFactory")
	public void consumeJson(List<EmpSalary> list) {
		for(EmpSalary epsl:list) {
			try {
				this.datamanager.kafkaSalary(epsl);
				log.info("salary paid");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("salary initiated but paying failed",e);
			}
		}
	}
}
