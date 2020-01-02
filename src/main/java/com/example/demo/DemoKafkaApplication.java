package com.example.demo;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import twitter4j.*;

@SpringBootApplication
public class DemoKafkaApplication {

	public static Logger log = LoggerFactory.getLogger(DemoKafkaApplication.class);

	@Autowired
	KafkaTemplate<Long, TwitterStatusPojo> kafkaTemplate;

	@Value("${com.example.demo.filterword}")
	public String filterword;

	public static void main(String[] args) {
		SpringApplication.run(DemoKafkaApplication.class, args);
	}

	@PostConstruct
	public void producer() {
		@SuppressWarnings("unused")
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener() {

			@Override
			public void onStatus(Status status) {
				if (status.getLang().equalsIgnoreCase("en")) {
					TwitterStatusPojo demostatus = new TwitterStatusPojo(status);
					log.debug(demostatus.toString());
					kafkaTemplate.send("test", demostatus.getId(), demostatus);
				}
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				log.debug("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}

			@Override
			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				log.debug("Got track limitation notice:" + numberOfLimitedStatuses);
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {
				log.debug("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			}

			@Override
			public void onStallWarning(StallWarning warning) {
				log.debug("Got stall warning:" + warning);
			}

			@Override
			public void onException(Exception ex) {
				ex.printStackTrace();
			}
		}).filter(new FilterQuery().track(filterword));
	}

	@KafkaListener(topics = "test", groupId = "group_id")
	public void consumer(TwitterStatusPojo demostatus) {
		log.debug("Consuming from test topic! "+demostatus.toString());
	}

}
