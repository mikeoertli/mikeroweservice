package com.mikeoertli.sample.mrs.twitter.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * Configure the topics used for the Kafka integration in this module
 * See: https://docs.spring.io/spring-kafka/docs/2.8.x/reference/html/
 *
 * @since 0.0.1
 */
@Configuration
public class TwitterKafkaTopicConfig
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value(value = "${kafka.topic.social.twitter}")
    private String twitterStatusTopicName;

    @Bean
    public KafkaAdmin createKafkaAdmin()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic getTwitterStatusTopic()
    {
        return new NewTopic(twitterStatusTopicName, 1, (short) 1);
    }
}
