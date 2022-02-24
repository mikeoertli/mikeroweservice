package com.mikeoertli.sample.mrs.podcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.feed.dsl.Feed;
import org.springframework.integration.handler.ReactiveMessageHandlerAdapter;
import org.springframework.integration.metadata.MetadataStore;
import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

/**
 * Create an RSS feed reader and publish incoming items to Kafka
 *
 * @since 0.0.1
 */
@Component
public class PodcastFeedReader
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${podcast.data.dir}")
    private String dataRootDir;

    @Value("${podcast.rss.feed.path}")
    private String feedResourcePath;


    private final IPodcastFeedItemHandler feedItemHandler;

    private Resource feedResource;

    @Autowired
    public PodcastFeedReader(IPodcastFeedItemHandler feedItemHandler)
    {
        this.feedItemHandler = feedItemHandler;
    }

    @Bean
    public MetadataStore metadataStore()
    {
        File rootDirectory = new File(dataRootDir);
        PropertiesPersistingMetadataStore metadataStore = new PropertiesPersistingMetadataStore();
        metadataStore.setBaseDirectory(rootDirectory.getAbsolutePath());
        return metadataStore;
    }

    @Bean
    public IntegrationFlow feedFlow() throws MalformedURLException
    {
        // Using "channel" here as the podcast metadata tag that contains the list of feed items
        // https://support.google.com/podcast-publishers/answer/9889544?hl=en
        // The validity of this logic is entirely unconfirmed
        return IntegrationFlows
                .from(Feed.inboundAdapter(getFeedResource(), "channel").metadataStore(metadataStore()),
                        sourcePollingEndpoint -> sourcePollingEndpoint.poller(pollerFactory -> pollerFactory.fixedDelay(1L, TimeUnit.MINUTES)))
                .handle(getReactiveKafkaHandler())
                .get();
    }

    private MessageHandler getReactiveKafkaHandler()
    {
        return new ReactiveMessageHandlerAdapter(message -> feedItemHandler.handleFeedItem(message));
    }

    public void setFeedResource(Resource feedResource)
    {
        logger.trace("Overriding feed resource (which was {} [{}]) with new feed resource ({} [{}])",
                this.feedResource == null ? "NULL" : this.feedResource,
                this.feedResource == null ? "NULL" : this.feedResource.getClass().getSimpleName(),
                feedResource, feedResource.getClass().getSimpleName());
        this.feedResource = feedResource;
    }

    private Resource getFeedResource() throws MalformedURLException
    {
        if (feedResource == null)
        {
            try
            {
                feedResource = new UrlResource(feedResourcePath);
            } catch (MalformedURLException e)
            {
                logger.error("Failed to find podcast feed URL resource at provided URL: {}", feedResourcePath, e);
                throw e;
            }
        }

        return feedResource;
    }

    public void run()
    {
        logger.debug("Starting the RSS feed poller...");
    }
}
