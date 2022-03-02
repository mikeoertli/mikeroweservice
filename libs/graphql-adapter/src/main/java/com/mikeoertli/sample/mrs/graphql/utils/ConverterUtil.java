package com.mikeoertli.sample.mrs.graphql.utils;

import com.mikeoertli.sample.mrs.graphql.generated.types.ContentType;
import com.mikeoertli.sample.mrs.graphql.generated.types.IMetadataItem;
import com.mikeoertli.sample.mrs.graphql.generated.types.ITopic;
import com.mikeoertli.sample.mrs.graphql.generated.types.MetadataItem;
import com.mikeoertli.sample.mrs.graphql.generated.types.Topic;
import com.mikeoertli.sample.mrs.graphql.generated.types.Transcript;
import com.mikeoertli.sample.mrs.graphql.kafka.ParticipantQueryService;
import com.mikeoertli.sample.mrs.transcript.api.TranscriptWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Converter utility methods between the data model module and the GraphQL schema-defined data model.
 * FIXME: why have both!? Just use a single data model! :facepalm:
 *
 * @since 0.0.1
 */
public class ConverterUtil
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static Transcript convertTranscriptWrapper(TranscriptWrapper wrapper, ContentType contentType, ParticipantQueryService participantQueryService)
    {
        return Transcript.newBuilder()
                .contentType(contentType)
                .id(wrapper.getTranscriptId())
                .description(wrapper.getDescription())
                .metadata(convertMetadata(wrapper.getMetadata()))
                .sourceMediaId(wrapper.getMediaId())
                .participants(participantQueryService.findPeople(wrapper.getParticipantIds()))
                .topicList(createTopicList(wrapper.getTopics()))
                .timestamp(wrapper.getTimestamp())
                .build();
    }

    private static List<IMetadataItem> convertMetadata(Map<String, Object> source)
    {
        return source.entrySet().stream().map(entry -> convertMetadataItem(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private static IMetadataItem convertMetadataItem(String key, Object value)
    {
        MetadataItem.Builder builder = MetadataItem.newBuilder()
                .id(key);
        if (value != null)
        {
            builder
                    .value(value.toString())
                    .dataType(value.getClass().getName());
        }
        return builder.build();
    }

    public static ITopic createTopic(String topicName)
    {
        return Topic.newBuilder()
                .topic(topicName)
                .id(UUID.randomUUID().toString())
                .build();
    }

    public static List<ITopic> createTopicList(Collection<String> topicNames)
    {
        return topicNames.stream().map(ConverterUtil::createTopic).collect(Collectors.toList());
    }
}
