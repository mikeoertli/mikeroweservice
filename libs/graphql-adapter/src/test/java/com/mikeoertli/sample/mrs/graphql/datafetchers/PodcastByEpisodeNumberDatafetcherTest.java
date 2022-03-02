package com.mikeoertli.sample.mrs.graphql.datafetchers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mikeoertli.sample.mrs.graphql.TestUtilities;
import com.mikeoertli.sample.mrs.model.generated.types.ContentType;
import com.mikeoertli.sample.mrs.model.generated.types.IPerson;
import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITopic;
import com.mikeoertli.sample.mrs.model.generated.types.PodcastEpisode;
import com.mikeoertli.sample.mrs.graphql.kafka.KafkaGraphqlReceiver;
import com.mikeoertli.sample.mrs.graphql.kafka.KafkaQueryService;
import com.mikeoertli.sample.mrs.graphql.utils.ConverterUtil;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Verify functionality of the {@link PodcastByEpisodeNumberDatafetcher}
 *
 * @since 0.0.1
 */
class PodcastByEpisodeNumberDatafetcherTest
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    void podcastByEpisodeNumber() throws JsonProcessingException
    {
        KafkaQueryService queryService = Mockito.mock(KafkaQueryService.class);
        PodcastByEpisodeNumberDatafetcher fetcher = new PodcastByEpisodeNumberDatafetcher(queryService);
        int episodeNumber = 1;
        IPodcastEpisode episode = TestUtilities.createPodcastEpisode(1, null, "test.topic");
        Mockito.when(queryService.getPodcastByEpisode(episodeNumber)).thenReturn(Optional.of(episode));

        // This will not work if any of the fields are mocked
        String prettyString = ConverterUtil.toPrettyJson(episode);

        logger.debug("Verifying fetcher for episode #{}. Full body: \n{}", episodeNumber, prettyString);

        // this isn't doing a ton, but essentially verifies that the query service return value is just passed through
        Assertions.assertThat(fetcher.podcastByEpisodeNumber(episodeNumber)).isEqualTo(episode);
    }
}