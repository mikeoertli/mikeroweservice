package com.mikeoertli.sample.mrs.mongo.testcontainers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.testcontainers.containers.GenericContainer;

import java.lang.invoke.MethodHandles;

/**
 * Test container for unit testing a Mongo-backed GraphQL setup
 * https://nirajsonawane.github.io/2019/12/25/Testcontainers-With-Spring-Boot-For-Integration-Testing/
 *
 * @since 0.0.1
 */
public class MongoDbContainer extends GenericContainer<MongoDbContainer>
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final int MONGODB_PORT = 27017;
    public static final String DEFAULT_IMAGE_AND_TAG = "mongo:3.2.4";

    public MongoDbContainer()
    {
        this(DEFAULT_IMAGE_AND_TAG);
    }

    public MongoDbContainer(@NonNull String image)
    {
        super(image);
        addExposedPort(MONGODB_PORT);
    }

    @NonNull
    public Integer getPort()
    {
        return getMappedPort(MONGODB_PORT);
    }
}
