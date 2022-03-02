package com.mikeoertli.sample.mrs.graphql.kafka;

import com.mikeoertli.sample.mrs.graphql.generated.types.IPodcastEpisode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service used to query Kafka
 *
 * @since 0.0.1
 */
@Service
public class KafkaQueryService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

//    private final ParticipantQueryService participantQueryService;

    // FIXME TEMP HACK
    public final List<IPodcastEpisode> podcasts = new ArrayList<>();

//    @Autowired
//    public KafkaQueryService(ParticipantQueryService participantQueryService)
//    {
//        this.participantQueryService = participantQueryService;
//    }

    public Optional<IPodcastEpisode> getPodcastByEpisode(int episodeNumber)
    {
        if (podcasts.size() > episodeNumber)
        {
            return Optional.of(podcasts.get(episodeNumber));
        } else
        {
            return Optional.empty();
        }
    }
}
