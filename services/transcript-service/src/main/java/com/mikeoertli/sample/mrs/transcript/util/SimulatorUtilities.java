package com.mikeoertli.sample.mrs.transcript.util;

import com.mikeoertli.sample.mrs.transcript.api.TranscriptWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.AUTHOR_KEY;
import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.MEDIA_TYPES;
import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.MEDIA_TYPE_KEY;
import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.PUBLICATION_DATE_KEY;
import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.PUBLISHER_KEY;
import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.TITLE_KEY;
import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.URL_KEY;

/**
 * Utilities related to simulated data
 *
 * @since 0.0.1
 */
public class SimulatorUtilities
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private SimulatorUtilities()
    {
        // prevent instantiation
    }

    public static String generateTranscriptBody()
    {
        StringBuilder builder = new StringBuilder();
        for (int loop = 0; loop < 250; loop++)
        {
            int length = ThreadLocalRandom.current().nextInt(3, 100);
            builder.append("Person ").append(ThreadLocalRandom.current().nextInt(1, 5)).append(": ");
            for (int sentenceIndex = 0; sentenceIndex < length; sentenceIndex++)
            {
                builder.append(UUID.randomUUID()).append(" ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }

    public static TranscriptWrapper createTranscriptWrapperWithId(String transcriptId)
    {
        Map<String, Object> metadata = createRandomMetadata(null);

        return TranscriptWrapper.Builder.newBuilder()
                .withTranscriptId(transcriptId)
                .withMediaId(UUID.randomUUID().toString())
                .withTranscriptBody(SimulatorUtilities.generateTranscriptBody())
                .withTimestamp(Instant.now().toString())
                .withMetadata(metadata)
                .build();
    }

    public static Map<String, Object> createRandomMetadata(String title)
    {
        Map<String, Object> metadata = new HashMap<>();
        metadata.put(AUTHOR_KEY, "Somebody Smart");
        metadata.put(TITLE_KEY, title == null ? "A Title" : title);
        metadata.put(PUBLISHER_KEY, "A Publisher");
        metadata.put(MEDIA_TYPE_KEY, MEDIA_TYPES.get(ThreadLocalRandom.current().nextInt(0, MEDIA_TYPES.size())));
        metadata.put(URL_KEY, "http://example.com/publication-" + UUID.randomUUID());
        metadata.put(PUBLICATION_DATE_KEY, Instant.now().minus(Duration.ofDays(ThreadLocalRandom.current().nextLong(10L, 10000L))).toString());
        return metadata;
    }

    public static TranscriptWrapper createTranscriptWrapperWithTitle(String requestedTitle)
    {
        Map<String, Object> metadata = createRandomMetadata(requestedTitle);

        return TranscriptWrapper.Builder.newBuilder()
                .withTranscriptId(UUID.randomUUID().toString())
                .withMediaId(UUID.randomUUID().toString())
                .withTranscriptBody(generateTranscriptBody())
                .withTimestamp(Instant.now().toString())
                .withMetadata(metadata)
                .build();
    }

    public static TranscriptWrapper createTranscriptWrapperWithPodcastEpisodeNum(int episodeNum)
    {
        Map<String, Object> metadata = createRandomMetadata(null);
        metadata.put(MetadataConstants.EPISODE_NUMBER, episodeNum);
        metadata.put(MEDIA_TYPE_KEY, MetadataConstants.PODCAST_TYPE);

        return TranscriptWrapper.Builder.newBuilder()
                .withTranscriptId(UUID.randomUUID().toString())
                .withMediaId(UUID.randomUUID().toString())
                .withTranscriptBody(generateTranscriptBody())
                .withTimestamp(Instant.now().toString())
                .withMetadata(metadata)
                .build();
    }
}
