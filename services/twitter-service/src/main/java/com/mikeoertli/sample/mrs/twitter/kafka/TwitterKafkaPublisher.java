package com.mikeoertli.sample.mrs.twitter.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import twitter4j.Status;

import java.lang.invoke.MethodHandles;

/**
 * Simple publisher of Kafka messages
 *
 * @since 0.0.1
 */
@Component
public class TwitterKafkaPublisher
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private KafkaTemplate<String, Status> statusKafkaTemplate;

    @Value(value = "${kafka.topic.social}")
    private String kafkaSocialMediaTopic;

    public void forwardTwitterStatusToKafka(Status status)
    {
        ListenableFuture<SendResult<String, Status>> future = statusKafkaTemplate.send(kafkaSocialMediaTopic, status);

        future.addCallback(new ListenableFutureCallback<>()
        {
            @Override
            public void onFailure(Throwable throwable)
            {
                logger.error("Unable to send message=[{}]", status, throwable);
            }

            @Override
            public void onSuccess(SendResult<String, Status> result)
            {
                logger.debug("Sent message=[{}] with offset=[{}]", status, result.getRecordMetadata().offset());
            }
        });
    }
}
