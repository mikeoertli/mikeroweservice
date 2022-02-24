package com.mikeoertli.sample.mrs.model.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * Base class for result metadata wrappers. This is likely a temporary solution since it would be far more
 * ideal to use schema-generated data models.
 *
 * @since 0.0.1
 */
@Document
public abstract class AResultWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Unique identifier for this result
     */
    @Id
    protected String resultId;

    /**
     * URL of the content. This might be a twitter status URL, a podcast episode URL, or something else.
     * At some point, we will probably need a scheme for "local" URLs to point to content type not strictly
     * found via the world wide web.
     */
    protected String resultUrl;

    /**
     * Unique identifier regarding the source of the material. This might be a production company, a website,
     * or a twitter handle.
     */
    protected String sourceId;

    /**
     * Human-readable title of the result content
     */
    protected String title;

    /**
     * Human-readable description of the result content type
     */
    protected String description;

    /**
     * ISO-8601
     * http://en.wikipedia.org/wiki/ISO_8601
     * Ex: 2012-04-23T18:25:43.511Z
     */
    protected String dateTime;

    /**
     * https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types
     */
    protected String mimeType;

    /**
     * Map containing metadata
     */
    protected Map<String, String> metadata;

    public AResultWrapper()
    {
    }

    public String getResultId()
    {
        return resultId;
    }

    public void setResultId(String resultId)
    {
        this.resultId = resultId;
    }

    public String getResultUrl()
    {
        return resultUrl;
    }

    public void setResultUrl(String resultUrl)
    {
        this.resultUrl = resultUrl;
    }

    public String getSourceId()
    {
        return sourceId;
    }

    public void setSourceId(String sourceId)
    {
        this.sourceId = sourceId;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getMimeType()
    {
        return mimeType;
    }

    public void setMimeType(String mimeType)
    {
        this.mimeType = mimeType;
    }

    public Map<String, String> getMetadata()
    {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata)
    {
        this.metadata = metadata;
    }
}
