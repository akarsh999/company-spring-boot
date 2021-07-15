package com.company.demo.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.company.demo.entity.EmpSalary;

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;

@Service
public class SalaryScheduler {
	
	private static final Logger log = LoggerFactory.getLogger(SalaryScheduler.class);
	
	
	@Autowired
	DataManager datamanager;
	@Autowired
	private KafkaTemplate<String, EmpSalary> kafkaTemplate;
	private static final String topic = "Example";
	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	
	@SchedulerLock(name = "scheduledTaskName", lockAtMostFor = "600m", lockAtLeastFor = "600m")
//	@Scheduled(cron = "${scheduler.cron}")
	@Scheduled(fixedDelayString = "${scheduler.FixedDelay}", initialDelayString = "${scheduler.initialDelay}")
	public void fetchDBjob() {
		List<EmpSalary>salarylist;
		
		Calendar cal = Calendar.getInstance();
		
		cal.set(Calendar.DATE, 1);
		
		String d=formatter.format(cal.getTime());
		while(true) {
		salarylist = this.datamanager.salaryToPay(5,d);
			
			if(!salarylist.isEmpty()) {
				log.info("initiating salaries payment");
				for(EmpSalary empsalary: salarylist) {
					log.debug(""+empsalary.getId());
					kafkaTemplate.send(topic, empsalary);
					log.debug("initiating salary for:"+empsalary.getId());
				}
				this.datamanager.addSalaryDate(salarylist);
				log.info("salary processed for the batch "+new Date().toString());
			}
			else {
				log.info("salary list completed");
				break;
			}
		}
		
	}

	
	
//	private static final String topic = "Example";
//	
//	@GetMapping("/{message}")
//	public String post(@PathVariable("message") String message) {
//		kafkaTemplate.send(topic, message);
//		System.out.println("message produced");
//		return "published "+message;
//	}
	
	
	
}
