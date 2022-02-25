package com.mikeoertli.sample.mrs.graphql.datafetchers;

import com.mikeoertli.sample.mrs.graphql.model.types.Transcript;
import com.mikeoertli.sample.mrs.mongo.IMediaRepository;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;

/**
 * Data fetching component for the defined {@code Query} in the {@code .graphqls} schema file.
 * The foundation of this file was auto-generated, but we were not able to continue using the auto-generated
 * parts as-is, so they were copied and modified.
 * <p>
 * Furthermore, the auto-generated code used the {@link DgsData} annotation rather than {@link DgsQuery}, so
 * instead of something like this:
 * <pre>
 *     @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.TelevisionEpisode)
 *     public TelevisionEpisode getTelevisionEpisode(DataFetchingEnvironment dataFetchingEnvironment)
 * </pre>
 * we can have something like this (note that by making the method name == the field name, we can omit that annotation param):
 * <pre>
 *     @DgsQuery
 *     public TelevisionEpisode televisionEpisode(@InputArgument("showName") String showName, @InputArgument("seasonNumber") int seasonNumber, @InputArgument("episodeNumber") int episodeNumber)
 * </pre>
 * <p>
 * This is the integration point for resolving the given query response using a service that is of no concern
 * to GraphQL.
 *
 * @since 0.0.1
 */
@DgsComponent
public class PodcastTranscriptByEpisodeNumberDatafetcher
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IMediaRepository mediaRepo;

    @DgsQuery
    public Transcript podcastTranscriptByEpisodeNumber(@InputArgument("episodeNumber") int episodeNumber)
    {
        return null;
    }
}
