package com.mikeoertli.sample.mrs.sentiment.kafka;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Mock "processor" that "provides" sentiment scores for a stream of messages.
 *
 * @since 0.0.1
 */
public class MockSentimentProcessor implements ISentimentProcessor
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${kafka.topic.sentiment}")
    private String sentimentTopic;

    @Value("${kafka.topic.social}")
    private String socialMediaTopic;

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    void buildPipeline(StreamsBuilder streamsBuilder)
    {
        KStream<String, String> messageStream = streamsBuilder.stream(socialMediaTopic, Consumed.with(STRING_SERDE, STRING_SERDE));

        messageStream
                .mapValues(this::getSentimentScore)
                .to(sentimentTopic);
    }

    @Override
    public int getSentimentScore(String message)
    {
        int score = ThreadLocalRandom.current().nextInt(0, 100);
        logger.trace("Sentiment score for message: {} is {}", message, score);
        return score;
    }
}
