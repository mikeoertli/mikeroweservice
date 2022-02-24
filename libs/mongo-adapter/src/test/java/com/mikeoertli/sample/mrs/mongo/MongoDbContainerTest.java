package com.mikeoertli.sample.mrs.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikeoertli.sample.mrs.model.result.MediaResult;
import com.mikeoertli.sample.mrs.mongo.testcontainers.MongoDbContainer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.lang.invoke.MethodHandles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verify mongo-backed GraphQL setup
 * Borrowed heavily (at least initially) from:
 * https://github.com/nirajsonawane/SpringBoot-mongodb-testcontainers/blob/master/src/test/java/com/example/mongodbwithtestcontainer/MongoDbContainerTest.java
 *
 * @since 0.0.1
 */
@SpringBootTest
// As we want to write Integration test we are using @SpringBootTest which tells Spring to load complete application context.
@AutoConfigureMockMvc
// configure auto-configuration of MockMvc. As we want to test controller->service->repository ->database
@ContextConfiguration(initializers = MongoDbContainerTest.MongoDbInitializer.class)
public class MongoDbContainerTest
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IMediaRepository mediaRepository;

    private static MongoDbContainer mongoDbContainer;

    @BeforeAll
    public static void startContainerAndPublicPortIsAvailable()
    {
        mongoDbContainer = new MongoDbContainer();
        mongoDbContainer.start();
    }

    @Test
    public void containerStartsAndPublicPortIsAvailable() throws Exception
    {

        MediaResult build = MediaResult.Builder.newBuilder().build();
        mockMvc.perform(post("/fruits") // fixme
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(build)))
                .andExpect(status().isCreated());

        Assertions.assertThat(mediaRepository.findAll().size()).isEqualTo(1);
    }

    public static class MongoDbInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>
    {
        @Override
        public void initialize(ConfigurableApplicationContext configurableApplicationContext)
        {
            logger.info("Overriding Spring Properties for mongodb!!");

            TestPropertyValues values = TestPropertyValues.of(
                    "spring.data.mongodb.host=" + mongoDbContainer.getContainerIpAddress(),
                    "spring.data.mongodb.port=" + mongoDbContainer.getPort()

            );
            values.applyTo(configurableApplicationContext);
        }
    }
}
