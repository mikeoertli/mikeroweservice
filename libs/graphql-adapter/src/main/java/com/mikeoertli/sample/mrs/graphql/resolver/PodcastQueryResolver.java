package com.mikeoertli.sample.mrs.graphql.resolver;

import com.mikeoertli.sample.mrs.graphql.model.PodcastEpisode;
import com.mikeoertli.sample.mrs.graphql.model.TopicList;
import com.mikeoertli.sample.mrs.graphql.model.Transcript;
import com.mikeoertli.sample.mrs.mongo.IMediaRepository;
import com.mikeoertli.sample.mrs.mongo.ISocialResultsRepository;
import graphql.kickstart.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

/**
 * GraphQL data resolver for podcasts
 *
 * @since 0.0.1
 */
@Component
public class PodcastQueryResolver implements GraphQLQueryResolver
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final int DEFAULT_NUM_TOPICS = 10;

    @Autowired
    private IMediaRepository mediaRepo;

    @Autowired
    private ISocialResultsRepository socialMediaRepo;

    public PodcastEpisode latestPodcastMentioningTopic(String topic)
    {
        // FIXME
        return new PodcastEpisode();
    }

    public TopicList mostPopularPodcastTopics(Integer numMostPopular)
    {
        int numTopics;
        if (numMostPopular == null || numMostPopular == 0)
        {
            numTopics = DEFAULT_NUM_TOPICS;
        } else
        {
            numTopics = numMostPopular;
        }

        // FIXME
        return new TopicList();
    }

    public Transcript podcastTranscriptByEpisodeNumber(int episodeNumber)
    {
        // FIXME
        return new Transcript();
    }

    public PodcastEpisode podcastByEpisodeNumber(int episodeNumber)
    {
        // FIXME
        return new PodcastEpisode();
    }
}
