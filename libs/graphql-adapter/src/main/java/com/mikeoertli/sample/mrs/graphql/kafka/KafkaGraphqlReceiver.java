package com.mikeoertli.sample.mrs.graphql.kafka;

import com.mikeoertli.sample.mrs.model.result.MediaResult;
import com.mikeoertli.sample.mrs.model.result.SocialResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

import java.lang.invoke.MethodHandles;

/**
 * Listener to all relevant Kafka topics that can be queried by GraphQL
 *
 * @since 0.0.1
 */
@KafkaListener(id = "${kafka.client.id.graphql}", groupId = "${kafka.group.id}")
public class KafkaGraphqlReceiver
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value(value = "${kafka.group.id}")
    private String groupId;


    @KafkaHandler
    public void listen(@Payload String payload)
    {

        logger.debug("[Kafka groupId={} ] Kafka payload (type: {}): \n\t{}", groupId, String.class.getSimpleName(), payload);

        // TODO figure out what we want to do here, this is a placeholder
    }

    @KafkaHandler
    public void listen(MediaResult mediaResult)
    {

    }

    @KafkaHandler
    public void listen(SocialResult socialResult)
    {

    }
}
