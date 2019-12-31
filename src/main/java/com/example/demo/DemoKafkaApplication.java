package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import twitter4j.*;

@SpringBootApplication
public class DemoKafkaApplication {

	public static Logger log = LoggerFactory.getLogger(DemoKafkaApplication.class);

	@Autowired
	KafkaTemplate<Long, Status> kafkaTemplate;
	
	FilterQuery fq = new FilterQuery();
    
    //Keywords to filter the stream on. Go sixers!
    String keywords[] = {"sixers"};

	public static void main(String[] args) {
		SpringApplication.run(DemoKafkaApplication.class, args);
	}
	
    TwitterStream twitterStream = new TwitterStreamFactory().getInstance().addListener(new StatusListener()
    {

        @Override
        public void onStatus(Status status) {
        	kafkaTemplate.send("test", status.getId(), status);
        }

        @Override
        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
            System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
        }

        @Override
        public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
            System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
        }

        @Override
        public void onScrubGeo(long userId, long upToStatusId) {
            System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
        }

        @Override
        public void onStallWarning(StallWarning warning) {
            System.out.println("Got stall warning:" + warning);
        }

        @Override
        public void onException(Exception ex) {
            ex.printStackTrace();
        }
    }).filter(fq.track(keywords));
    
}
