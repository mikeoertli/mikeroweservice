package com.mikeoertli.sample.mrs.podcast.kafka;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * Configure the topics used for the Kafka integration
 * See: https://docs.spring.io/spring-kafka/docs/2.8.x/reference/html/
 *
 * @since 0.0.1
 */
@EnableKafka
@PropertySource("classpath:kafka-application.properties")
@Configuration
public class PodcastKafkaTopicConfig
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value(value = "${kafka.podcast.topic}")
    private String podcastTopicName;

    @Bean
    public KafkaAdmin createKafkaAdmin()
    {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic getPodcastTopic()
    {
        return new NewTopic(podcastTopicName, 1, (short) 1);
    }
}
