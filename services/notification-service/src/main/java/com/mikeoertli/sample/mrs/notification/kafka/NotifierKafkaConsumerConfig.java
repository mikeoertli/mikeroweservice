package com.mikeoertli.sample.mrs.notification.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * Configuration for Kafka message consumer factory logic.
 * <p>
 * The {@link EnableKafka} annotation is required on the configuration class to enable detection of
 * {@link org.springframework.kafka.annotation.KafkaListener} annotation on spring-managed beans
 *
 * @since 0.0.1
 */
@EnableKafka
@PropertySource("classpath:kafka-application.properties")
@Configuration
public class NotifierKafkaConsumerConfig
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value(value = "${kafka.client.id.notification}")
    private String clientId;

    @Value(value = "${kafka.group.id}")
    private String groupId;

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> createNotificationKafkaListenerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(createStringConsumerFactory());
        return factory;
    }

    public ConsumerFactory<String, String> createStringConsumerFactory()
    {
        Map<String, Object> props = getConsumerConfigMap();

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    private Map<String, Object> getConsumerConfigMap()
    {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        return props;
    }

    public ConsumerFactory<String, String> createNotificationConsumerFactory()
    {
        Map<String, Object> props = getConsumerConfigMap();

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new StringDeserializer());
    }
}
