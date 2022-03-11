package com.mikeoertli.sample.mrs.transcript.kafka;

import com.mikeoertli.sample.mrs.model.Constants;
import com.mikeoertli.sample.mrs.model.generated.types.ContentType;
import com.mikeoertli.sample.mrs.model.generated.types.IMovie;
import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITelevisionEpisode;
import com.mikeoertli.sample.mrs.transcript.api.TranscriptWrapper;
import com.mikeoertli.sample.mrs.transcript.elastic.ITranscriptElasticRepository;
import com.mikeoertli.sample.mrs.transcript.service.TranscriptDownloadService;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

/**
 * Kafka stream processor that listens to the "media" topic, downloads transcripts, then publishes information about
 * said transcripts on the "transcript" topic.
 *
 * @since 0.0.1
 */
@Component
public class TranscriptRetrievalProcessor
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${kafka.topic.transcript}")
    private String transcriptTopic;

    @Value("${kafka.topic.media}")
    private String mediaTopic;

    private final TranscriptDownloadService downloadService;
    private final ITranscriptElasticRepository transcriptRepository;

    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    public TranscriptRetrievalProcessor(TranscriptDownloadService downloadService, ITranscriptElasticRepository transcriptRepository)
    {
        this.downloadService = downloadService;
        this.transcriptRepository = transcriptRepository;
    }

    @Autowired
    void transcriptRetrievalPipeline(StreamsBuilder streamsBuilder)
    {
        KStream<String, String> mediaTopicStream = streamsBuilder.stream(mediaTopic, Consumed.with(STRING_SERDE, STRING_SERDE));

        mediaTopicStream
                .mapValues(this::downloadTranscript)
                .filter((key, value) -> value.isPresent())
                .to(transcriptTopic);
    }

    private Optional<TranscriptWrapper> downloadTranscript(String key, String value)
    {
        if (isMediaTypeMatch(key, value, ContentType.PODCAST))
        {
            return downloadPodcastTranscript(key, value);
        } else if (isMediaTypeMatch(key, value, ContentType.TELEVISION))
        {
            return downloadTelevisionTranscript(key, value);
        } else if (isMediaTypeMatch(key, value, ContentType.MOVIE))
        {
            return downloadMovieTranscript(key, value);
        }
        return Optional.empty();
    }

    private Optional<TranscriptWrapper> downloadPodcastTranscript(String key, String value)
    {
        IPodcastEpisode podcastEpisode = asPodcastEpisode(key, value);
        Optional<String> transcriptIdMatch = downloadService.downloadPodcastTranscript(podcastEpisode);
        return transcriptIdMatch.flatMap(transcriptRepository::findById);
    }

    private Optional<TranscriptWrapper> downloadTelevisionTranscript(String key, String value)
    {
        ITelevisionEpisode televisionEpisode = asTelevisionEpisode(key, value);
        Optional<String> transcriptIdMatch = downloadService.downloadTelevisionTranscript(televisionEpisode);
        return transcriptIdMatch.flatMap(transcriptRepository::findById);
    }

    private Optional<TranscriptWrapper> downloadMovieTranscript(String key, String value)
    {
        IMovie movie = asMovie(key, value);
        Optional<String> transcriptIdMatch = downloadService.downloadMovieTranscript(movie);
        return transcriptIdMatch.flatMap(transcriptRepository::findById);
    }

    private IPodcastEpisode asPodcastEpisode(String key, String value)
    {
        // FIXME
        return null;
    }

    private ITelevisionEpisode asTelevisionEpisode(String key, String value)
    {
        // FIXME
        return null;
    }

    private IMovie asMovie(String key, String value)
    {
        // FIXME
        return null;
    }

    private boolean isMediaTypeMatch(String key, String value, ContentType contentType)
    {
        // fixme - figure out the right way to actually match
        if (ContentType.PODCAST == contentType && value.contains(Constants.PODCAST_NAME))
        {
            return true;
        } else if (ContentType.TELEVISION == contentType && value.contains(Constants.DIRTY_JOBS_SERIES_NAME))
        {
            return true;
        } else
        {
            return ContentType.MOVIE == contentType && value.contains(Constants.MOVIE_TYPE);
        }
    }
}
