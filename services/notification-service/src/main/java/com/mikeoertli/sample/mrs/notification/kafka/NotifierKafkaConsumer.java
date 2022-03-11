package com.mikeoertli.sample.mrs.notification.kafka;

import com.mikeoertli.sample.mrs.notification.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

/**
 * Simple Kafka topic listener
 *
 * @since 0.0.1
 */
@Component
public class NotifierKafkaConsumer
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private int notificationCount;

    @Autowired
    private NotificationService notificationService;

    @Value(value = "${kafka.group.id}")
    private String groupId;

    @Value(value = "${kafka.topic.notification}")
    private String notificationTopic;

    @KafkaListener(topics = "${kafka.topic.notification}", groupId = "${kafka.group.id}", containerFactory = "createNotificationKafkaListenerContainerFactory")
    public void listenToNotificationTopic(@Payload String payload)
    {
        notificationCount++;
        logger.debug("[Kafka groupId={} | notificationCount = {} ] Kafka payload (type: {}): \n\t{}",
                groupId, notificationCount, String.class.getSimpleName(), payload);

        // TODO figure out what we want to do here, this is a placeholder
        notificationService.onNotifiableEvent(payload);
    }
}
