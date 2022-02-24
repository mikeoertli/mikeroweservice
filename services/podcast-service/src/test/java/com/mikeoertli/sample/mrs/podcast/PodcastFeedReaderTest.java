package com.mikeoertli.sample.mrs.podcast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileUrlResource;
import reactor.core.publisher.Mono;

import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;

/**
 * Verify functionality of the podcast feed reader ({@link PodcastFeedReader})
 *
 * @since 0.0.1
 */
class PodcastFeedReaderTest
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

//    @Test
    void testPodcastFeedReader() throws MalformedURLException
    {
        IPodcastFeedItemHandler handler = message -> {
            logger.debug("Handling feed item. Headers = {}. Payload = {}.", message.getHeaders(), message.getPayload());
            return Mono.empty();
        };

        PodcastFeedReader podcastFeedReader = new PodcastFeedReader(handler);
        FileUrlResource fileUrlResource = new FileUrlResource("samplerss.xml");
        podcastFeedReader.setFeedResource(fileUrlResource);
//        podcastFeedReader.
    }
}