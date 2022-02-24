package com.mikeoertli.sample.mrs.transcript.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * Wrapper for a transcript result.
 *
 * @since 0.0.1
 */
@Document(indexName = "transcript")
public class TranscriptWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Id
    private String transcriptId;
    private String timestamp;
    private String mediaId;

    @Field(type = FieldType.Nested, includeInParent = true)
    private Map<String, Object> metadata;
    private String transcriptBody;

    public TranscriptWrapper()
    {
    }

    public TranscriptWrapper(String transcriptId, String timestamp, String mediaId, Map<String, Object> metadata, String transcriptBody)
    {
        this.transcriptId = transcriptId;
        this.timestamp = timestamp;
        this.mediaId = mediaId;
        this.metadata = metadata;
        this.transcriptBody = transcriptBody;
    }

    public String getTranscriptId()
    {
        return transcriptId;
    }

    public void setTranscriptId(String transcriptId)
    {
        this.transcriptId = transcriptId;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp(String timestamp)
    {
        this.timestamp = timestamp;
    }

    public String getMediaId()
    {
        return mediaId;
    }

    public void setMediaId(String mediaId)
    {
        this.mediaId = mediaId;
    }

    public Map<String, Object> getMetadata()
    {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata)
    {
        this.metadata = metadata;
    }

    public String getTranscriptBody()
    {
        return transcriptBody;
    }

    public void setTranscriptBody(String transcriptBody)
    {
        this.transcriptBody = transcriptBody;
    }

    public static final class Builder
    {
        private String transcriptId;
        private String timestamp;
        private String mediaId;
        private Map<String, Object> metadata;
        private String transcriptBody;

        private Builder()
        {
        }

        public static Builder newBuilder()
        {
            return new Builder();
        }

        public Builder withTranscriptId(String transcriptId)
        {
            this.transcriptId = transcriptId;
            return this;
        }

        public Builder withTimestamp(String timestamp)
        {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withMediaId(String mediaId)
        {
            this.mediaId = mediaId;
            return this;
        }

        public Builder withMetadata(Map<String, Object> metadata)
        {
            this.metadata = metadata;
            return this;
        }

        public Builder withTranscriptBody(String transcriptBody)
        {
            this.transcriptBody = transcriptBody;
            return this;
        }

        public TranscriptWrapper build()
        {
            TranscriptWrapper transcriptWrapper = new TranscriptWrapper();
            transcriptWrapper.setTranscriptId(transcriptId);
            transcriptWrapper.setTimestamp(timestamp);
            transcriptWrapper.setMediaId(mediaId);
            transcriptWrapper.setMetadata(metadata);
            transcriptWrapper.setTranscriptBody(transcriptBody);
            return transcriptWrapper;
        }
    }
}
