package com.mikeoertli.sample.mrs.kafka.stream;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * Basic Kafka stream configuration base class that builds a config for stream processors. The default
 * data types for key and value are {@link String}, but can be configured with anything via
 * {@link #getDefaultPropertiesMap(String, String)}
 * <p>
 * Any class that extends this must be annotated with:
 * <pre>
 *     &#64;Configuration
 *     &#64;EnableKafka
 *     &#64;EnableKafkaStreams
 * </pre>
 * <p>
 * Extending classes will also need to implement the stream configuration method annotated with {@link Bean}.
 * <pre>
 *     &#64;Value(value = "${kafka.application.id}")
 *     private String applicationId;
 *
 *     &#64;Value("${kafka.bootstrap.servers}")
 *     private String bootstrapServers;
 *
 *     &#64;Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
 *     KafkaStreamsConfiguration getKafkaStreamsConfig()
 *     {
 *         Map<String, Object> props = getDefaultPropertiesMap();
 *
 *         return new KafkaStreamsConfiguration(props);
 *     }
 * </pre>
 *
 * @since 0.0.1
 */
public abstract class AKafkaStreamConfig
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    KafkaStreamsConfiguration getKafkaStreamsConfig()
    {
        Map<String, Object> props = getDefaultPropertiesMap();

        return new KafkaStreamsConfiguration(props);
    }

    /**
     * Retrieve the default {@link Map} (used to create a {@link KafkaStreamsConfiguration}) using
     * {@link String} as the data type for key and value.
     * <p>
     * To configure different data types, use {@link #getDefaultPropertiesMap(String, String)}
     *
     * @return the config map that contains properties like the client ID, bootstrap server info, and serde class
     * for both the key and value.
     */
    @NotNull
    protected Map<String, Object> getDefaultPropertiesMap()
    {
        String stringSerdeClassName = Serdes.String().getClass().getName();
        return getDefaultPropertiesMap(stringSerdeClassName, stringSerdeClassName);
    }

    /**
     * Retrieve the default {@link Map} (used to create a {@link KafkaStreamsConfiguration}) using
     * the provided types as the data type for key and value.
     * <p>
     * To configure the default ({@link String}) key/value data types, use: {@link #getDefaultPropertiesMap()}
     *
     * @param serdeClassConfigKey   the serde class type for the KEYS (this will be the value in the properties map
     *                              for the {@link StreamsConfig#DEFAULT_KEY_SERDE_CLASS_CONFIG} key
     * @param serdeClassConfigValue the serde class type for the VALUES (this will be the value in the properties map
     *                              for the {@link StreamsConfig#DEFAULT_VALUE_SERDE_CLASS_CONFIG} key
     * @return the config map that contains properties like the client ID, bootstrap server info, and serde class
     * for both the key and value using the provided serde data types for key and value..
     */
    @NotNull
    protected Map<String, Object> getDefaultPropertiesMap(String serdeClassConfigKey, String serdeClassConfigValue)
    {
        Map<String, Object> props = new HashMap<>();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, getApplicationId());
        props.put(StreamsConfig.CLIENT_ID_CONFIG, getClientId());
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, getBootstrapServers());
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, serdeClassConfigKey);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, serdeClassConfigValue);
        return props;
    }

    /**
     * @return the application ID used to populate the configuration properties map, this is the value that corresponds to
     * the {@link StreamsConfig#APPLICATION_ID_CONFIG} in the
     * {@link #getDefaultPropertiesMap(String, String)} method.
     */
    protected abstract String getApplicationId();

    /**
     * @return the bootstrap servers (in an {@code host:port,host:port} format) used to populate the configuration
     * properties map, this is the value that corresponds to the {@link StreamsConfig#BOOTSTRAP_SERVERS_CONFIG} in the
     * {@link #getDefaultPropertiesMap(String, String)} method.
     */
    protected abstract String getBootstrapServers();

    /**
     * @return the client ID used to populate the configuration properties map, this is the value that corresponds to
     * the {@link StreamsConfig#CLIENT_ID_CONFIG} in the
     * {@link #getDefaultPropertiesMap(String, String)} method.
     */
    protected abstract String getClientId();

    // other config
}
