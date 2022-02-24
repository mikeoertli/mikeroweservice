package com.mikeoertli.sample.mrs.notification.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.lang.invoke.MethodHandles;

/**
 * Simple publisher of Kafka messages
 *
 * @since 0.0.1
 */
@Component
public class NotifierKafkaPublisher
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private KafkaTemplate<String, String> simpleKafkaTemplate;

    @Value(value = "${kafka.topic.notification}")
    private String notificationTopic;

    public void sendTwitterStatusMessage(String message)
    {
        ListenableFuture<SendResult<String, String>> future = simpleKafkaTemplate.send(notificationTopic, message);

        future.addCallback(new ListenableFutureCallback<>()
        {
            @Override
            public void onFailure(Throwable ex)
            {
                logger.error("Unable to send message=[{}]", message, ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result)
            {
                logger.debug("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
            }
        });
    }
}
