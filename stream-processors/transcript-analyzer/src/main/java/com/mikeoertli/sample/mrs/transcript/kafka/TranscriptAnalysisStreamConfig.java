package com.mikeoertli.sample.mrs.transcript.kafka;

import com.mikeoertli.sample.mrs.kafka.stream.AKafkaStreamConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

/**
 * Stream configuration for subscribing to the transcript topic, extracting data about people, companies, and subjects,
 * and publishing those on their respective topics.
 *
 * @since 0.0.1
 */
@Component
public class TranscriptAnalysisStreamConfig extends AKafkaStreamConfig
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value(value = "${kafka.application.id}")
    private String applicationId;

    @Value("${kafka.bootstrap.servers}")
    private String bootstrapServers;

    @Value("${kafka.client.id.transcript.analyzer}")
    private String clientId;

    @Override
    protected String getApplicationId()
    {
        return applicationId;
    }

    @Override
    protected String getBootstrapServers()
    {
        return bootstrapServers;
    }

    @Override
    protected String getClientId()
    {
        return clientId;
    }
}
