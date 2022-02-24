package com.mikeoertli.sample.mrs.twitter;

import com.mikeoertli.sample.mrs.twitter.kafka.TwitterKafkaPublisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TwitterMonitorApplication
{

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(TwitterMonitorApplication.class, args);

		TwitterMonitorService twitterMonitorService = context.getBean(TwitterMonitorService.class);
		TwitterKafkaPublisher publisher = context.getBean(TwitterKafkaPublisher.class);

		twitterMonitorService.initialize(publisher);
	}

}
