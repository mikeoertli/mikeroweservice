package com.mikeoertli.sample.mrs.transcript.kafka;

import com.mikeoertli.sample.mrs.model.Constants;
import com.mikeoertli.sample.mrs.model.generated.types.ContentType;
import com.mikeoertli.sample.mrs.model.generated.types.ICompany;
import com.mikeoertli.sample.mrs.model.generated.types.IMovie;
import com.mikeoertli.sample.mrs.model.generated.types.IPerson;
import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITelevisionEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITopic;
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
public class TranscriptAnalyzerProcessor
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${kafka.topic.transcript}")
    private String transcriptTopic;

    @Value("${kafka.topic.person}")
    private String personTopic;

    @Value("${kafka.topic.company}")
    private String companyTopic;

    @Value("${kafka.topic.subject}")
    private String subjectTopic;

    private static final Serde<String> STRING_SERDE = Serdes.String();


    @Autowired
    void transcriptRetrievalPipeline(StreamsBuilder streamsBuilder)
    {
        KStream<String, String> transcriptTopicStream = streamsBuilder.stream(transcriptTopic, Consumed.with(STRING_SERDE, STRING_SERDE));

        transcriptTopicStream
                .mapValues(this::extractPersonData)
                .filter((key, value) -> value.isPresent())
                .to(personTopic);

        transcriptTopicStream
                .mapValues(this::extractCompanyData)
                .filter((key, value) -> value.isPresent())
                .to(companyTopic);

        transcriptTopicStream
                .mapValues(this::extractSubjectData)
                .filter((key, value) -> value.isPresent())
                .to(subjectTopic);
    }

    private Optional<IPerson> extractPersonData(String key, String value)
    {
        return Optional.empty();
    }

    private Optional<ICompany> extractCompanyData(String key, String value)
    {
        return Optional.empty();
    }

    private Optional<ITopic> extractSubjectData(String key, String value)
    {
        return Optional.empty();
    }
}
