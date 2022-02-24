package com.mikeoertli.sample.mrs.model.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;

/**
 * Wrapper for media result data
 *
 * @since 0.0.1
 */
public class MediaResult extends AResultWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * The name of the media series
     */
    private String seriesName;

    /**
     * "Master" episode number - i.e. the episode number of "all time".
     * If a non-episodic type of media, then this shall be -1
     */
    private int masterEpisodeNumber = -1;

    /**
     * Season episode number if this media is a "seasonal" type of media, otherwise it will == {@link #masterEpisodeNumber}
     */
    private int seasonEpisodeNumber = masterEpisodeNumber;

    /**
     * Season number if this media is a "seasonal" type of media, otherwise it shall be -1
     */
    private int seasonNumber = -1;

    /**
     * Duration of the content in milliseconds if it has duration, otherwise {@code 0L}
     */
    private long durationMillis = 0L;

    /**
     * Map containing actors (shows/movies) and/or guests (podcasts)
     * Key: Actor Name
     * Value: Role description (ex: character name, or "guest", or "host" for things like podcasts)
     */
    protected Map<String, String> people = new HashMap<>();

    public MediaResult()
    {
    }

    public MediaResult(String seriesName, int masterEpisodeNumber, int seasonEpisodeNumber, int seasonNumber, long durationMillis, Map<String, String> people)
    {
        this.seriesName = seriesName;
        this.masterEpisodeNumber = masterEpisodeNumber;
        this.seasonEpisodeNumber = seasonEpisodeNumber;
        this.seasonNumber = seasonNumber;
        this.durationMillis = durationMillis;
        this.people = people;
    }

    public String getSeriesName()
    {
        return seriesName;
    }

    public void setSeriesName(String seriesName)
    {
        this.seriesName = seriesName;
    }

    public int getMasterEpisodeNumber()
    {
        return masterEpisodeNumber;
    }

    public void setMasterEpisodeNumber(int masterEpisodeNumber)
    {
        this.masterEpisodeNumber = masterEpisodeNumber;
    }

    public int getSeasonEpisodeNumber()
    {
        return seasonEpisodeNumber;
    }

    public void setSeasonEpisodeNumber(int seasonEpisodeNumber)
    {
        this.seasonEpisodeNumber = seasonEpisodeNumber;
    }

    public int getSeasonNumber()
    {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber)
    {
        this.seasonNumber = seasonNumber;
    }

    public long getDurationMillis()
    {
        return durationMillis;
    }

    public void setDurationMillis(long durationMillis)
    {
        this.durationMillis = durationMillis;
    }

    public Map<String, String> getPeople()
    {
        return people;
    }

    public void setPeople(Map<String, String> people)
    {
        this.people = people;
    }

    public static final class Builder
    {
        private Map<String, String> people = new HashMap<>();
        private String resultId;
        private String resultUrl;
        private String sourceId;
        private String title;
        private String description;
        private String dateTime;
        private String mimeType;
        private Map<String, String> metadata;
        private String seriesName;
        private int masterEpisodeNumber = -1;
        private int seasonEpisodeNumber = masterEpisodeNumber;
        private int seasonNumber = -1;
        private long durationMillis = 0L;

        private Builder()
        {
        }

        public static Builder newBuilder()
        {
            return new Builder();
        }

        public Builder withSeriesName(String seriesName)
        {
            this.seriesName = seriesName;
            return this;
        }

        public Builder withMasterEpisodeNumber(int masterEpisodeNumber)
        {
            this.masterEpisodeNumber = masterEpisodeNumber;
            return this;
        }

        public Builder withSeasonEpisodeNumber(int seasonEpisodeNumber)
        {
            this.seasonEpisodeNumber = seasonEpisodeNumber;
            return this;
        }

        public Builder withSeasonNumber(int seasonNumber)
        {
            this.seasonNumber = seasonNumber;
            return this;
        }

        public Builder withDurationMillis(long durationMillis)
        {
            this.durationMillis = durationMillis;
            return this;
        }

        public Builder withPeople(Map<String, String> people)
        {
            this.people = people;
            return this;
        }

        public Builder withResultId(String resultId)
        {
            this.resultId = resultId;
            return this;
        }

        public Builder withResultUrl(String resultUrl)
        {
            this.resultUrl = resultUrl;
            return this;
        }

        public Builder withSourceId(String sourceId)
        {
            this.sourceId = sourceId;
            return this;
        }

        public Builder withTitle(String title)
        {
            this.title = title;
            return this;
        }

        public Builder withDescription(String description)
        {
            this.description = description;
            return this;
        }

        public Builder withDateTime(String dateTime)
        {
            this.dateTime = dateTime;
            return this;
        }

        public Builder withMimeType(String mimeType)
        {
            this.mimeType = mimeType;
            return this;
        }

        public Builder withMetadata(Map<String, String> metadata)
        {
            this.metadata = metadata;
            return this;
        }

        public MediaResult build()
        {
            MediaResult mediaResult = new MediaResult();
            mediaResult.setSeriesName(seriesName);
            mediaResult.setMasterEpisodeNumber(masterEpisodeNumber);
            mediaResult.setSeasonEpisodeNumber(seasonEpisodeNumber);
            mediaResult.setSeasonNumber(seasonNumber);
            mediaResult.setDurationMillis(durationMillis);
            mediaResult.setPeople(people);
            mediaResult.setResultId(resultId);
            mediaResult.setResultUrl(resultUrl);
            mediaResult.setSourceId(sourceId);
            mediaResult.setTitle(title);
            mediaResult.setDescription(description);
            mediaResult.setDateTime(dateTime);
            mediaResult.setMimeType(mimeType);
            mediaResult.setMetadata(metadata);
            return mediaResult;
        }
    }
}
