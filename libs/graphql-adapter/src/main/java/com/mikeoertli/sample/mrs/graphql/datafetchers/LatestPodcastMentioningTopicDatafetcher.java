package com.mikeoertli.sample.mrs.graphql.datafetchers;

import com.mikeoertli.sample.mrs.graphql.model.DgsConstants;
import com.mikeoertli.sample.mrs.graphql.model.types.PodcastEpisode;
import com.mikeoertli.sample.mrs.mongo.IMediaRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;

@DgsComponent
public class LatestPodcastMentioningTopicDatafetcher
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IMediaRepository mediaRepo;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.LatestPodcastMentioningTopic)
    public PodcastEpisode getLatestPodcastMentioningTopic(DataFetchingEnvironment dataFetchingEnvironment)
    {
        return null;
    }
}