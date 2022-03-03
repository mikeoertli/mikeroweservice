package com.mikeoertli.sample.mrs.graphql;

import com.mikeoertli.sample.mrs.model.generated.types.ContentType;
import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITopic;
import com.mikeoertli.sample.mrs.model.generated.types.PodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.Topic;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Misc utility methods for tests
 *
 * @since 0.0.1
 */
public class TestUtilities
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static IPodcastEpisode createPodcastEpisode(int episodeNumber, @Nullable String name, @Nullable String... givenTopics)
    {
        List<ITopic> topics;
        if (givenTopics == null)
        {
            topics = new ArrayList<>();
            topics.add(Mockito.mock(ITopic.class));
        } else
        {
            topics = Arrays.stream(givenTopics).map(topic -> Topic.newBuilder().topic(topic).build()).collect(Collectors.toList());
        }

        if (!StringUtils.hasLength(name))
        {
            name = "episode name";
        }

        return PodcastEpisode.newBuilder()
                .episodeNumber(episodeNumber)
                .episodeName(name)
                .contentType(ContentType.PODCAST)
                .id(UUID.randomUUID().toString())
                .mediaUrl("https://example.com/podcast/" + episodeNumber + ".mp3")
                .rssFeedUrl("https://example.com/podcast.rss")
                .releaseTimestamp("2022-02-22'T'22:22:22.222Z") // yyyy-MM-dd'T'HH:mm:ss.SSSZ
                .topicList(topics)
                .build();
    }
}
