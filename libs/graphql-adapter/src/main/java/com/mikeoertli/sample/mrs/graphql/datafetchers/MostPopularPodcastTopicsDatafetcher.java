package com.mikeoertli.sample.mrs.graphql.datafetchers;

import com.mikeoertli.sample.mrs.graphql.model.DgsConstants;
import com.mikeoertli.sample.mrs.graphql.model.types.TopicList;
import com.mikeoertli.sample.mrs.mongo.IMediaRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import graphql.schema.DataFetchingEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;

@DgsComponent
public class MostPopularPodcastTopicsDatafetcher
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final int DEFAULT_NUM_TOPICS = 10;

    @Autowired
    private IMediaRepository mediaRepo;

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.MostPopularPodcastTopics)
    public TopicList getMostPopularPodcastTopics(DataFetchingEnvironment dataFetchingEnvironment)
    {
        return null;
    }
}
