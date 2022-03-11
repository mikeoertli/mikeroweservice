package com.mikeoertli.sample.mrs.graphql.kafka;

import com.mikeoertli.sample.mrs.model.generated.types.EpisodicMedia;
import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITranscript;
import com.mikeoertli.sample.mrs.model.generated.types.TranscriptResult;
import org.jetbrains.annotations.NotNull;
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
    public final List<ITranscript> transcripts = new ArrayList<>();

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

    public Optional<ITranscript> getTranscriptForPodcastEpisode(int episodeNumber)
    {
        Optional<IPodcastEpisode> podcastByEpisode = getPodcastByEpisode(episodeNumber);
        return podcastByEpisode.map(EpisodicMedia::getTranscriptId)
                .flatMap(this::getTranscript);
    }

    @NotNull
    private Optional<ITranscript> getTranscript(String transcriptId)
    {
        return transcripts.stream().filter(t -> t.getId().equals(transcriptId)).findFirst();
    }
}
