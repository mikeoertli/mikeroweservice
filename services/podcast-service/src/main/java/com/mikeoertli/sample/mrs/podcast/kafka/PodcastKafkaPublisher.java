package com.mikeoertli.sample.mrs.podcast.kafka;

import com.mikeoertli.sample.mrs.podcast.IPodcastFeedItemHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.CompletableFuture;

/**
 * Simple publisher of Kafka messages
 *
 * @since 0.0.1
 */
@Component
public class PodcastKafkaPublisher implements IPodcastFeedItemHandler
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private KafkaTemplate<String, Message<?>> podcastKafkaTemplate;

    @Value(value = "${kafka.podcast.topic}")
    private String kafkaPodcastTopic;

    @Override
    public Mono<Void> handleFeedItem(Message<?> message)
    {
        logger.trace("Attempting to publish message to Kafka [topic = {}]. Message = {}.", kafkaPodcastTopic, message);
        ListenableFuture<SendResult<String, Message<?>>> future = podcastKafkaTemplate.send(kafkaPodcastTopic, message);

        CompletableFuture<Void> kafkaFuture = new CompletableFuture<>();

        future.addCallback(new ListenableFutureCallback<>()
        {
            @Override
            public void onFailure(Throwable ex)
            {
                logger.error("Unable to send message=[{}]", message, ex);
                kafkaFuture.completeExceptionally(ex);
            }

            @Override
            public void onSuccess(SendResult<String, Message<?>> result)
            {
                logger.debug("Sent message=[{}] with offset=[{}]", message, result.getRecordMetadata().offset());
                kafkaFuture.complete(null);
            }
        });
        return Mono.fromFuture(kafkaFuture);
    }
}
