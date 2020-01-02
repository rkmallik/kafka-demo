# kafka-demo
demo of spring boot + kafka on Pivotal Web Services and Confluent Cloud

# pre-reqs
- create an account on confluent cloud https://confluent.cloud/
- create an account on pivotal web services https://run.pivotal.io/
- create a developer account on twitter and create an app https://developer.twitter.com/

Currently, this demo simply filters all tweets containing the word: "sixers" and publishes them to a confluent cloud topic "test"

# Enter your details
- application.properties
	- com.example.demo.filterword - add your own filterword for the demo. default is sixers. Go Sixers! (FYI, this also picks up a bunch Sydney Sixers cricket team tweets ;-)
	- spring.kafka.properties.bootstrap.servers - from confluent cloud cluster dashboard
	- spring.kafka.properties.sasl.jaas.config=org.apache.kafka.common.security.plain.PlainLoginModule required username="XXX" password="XXX"; - from API access tab on the cluster dashboard
- twitter4j.properties - all from twitter developer app dashbard ("Keys and tokens" tab)
	- oauth.consumerKey
	- oauth.consumerSecret
	- oauth.accessToken
	- oauth.accessTokenSecret
- manifest.yml
	- name: your app name on pws