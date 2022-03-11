package com.mikeoertli.sample.mrs.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;

/**
 * Utilities related to simulated data
 *
 * @since 0.0.1
 */
public class Constants
{
    public static final String EPISODE_NUMBER = "episode_num";
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String AUTHOR_KEY = "author";
    public static final String TITLE_KEY = "title";
    public static final String PUBLISHER_KEY = "publisher";
    public static final String MEDIA_TYPE_KEY = "media_type";
    public static final String PODCAST_TYPE = "podcast";
    public static final String TV_EPISODE_TYPE = "tv_episode";
    public static final String MOVIE_TYPE = "movie";
    public static final String URL_KEY = "url";
    public static final String PUBLICATION_DATE_KEY = "publication_date";
    public static final String TRANSCRIPT_BODY_KEY = "transcript_body";
    public static final List<String> MEDIA_TYPES = Arrays.asList(PODCAST_TYPE, TV_EPISODE_TYPE, MOVIE_TYPE);

    public static final String PODCAST_NAME = "The Way I Heard It";
    public static final String DIRTY_JOBS_SERIES_NAME = "Dirty Jobs";

    private Constants()
    {
        // prevent instantiation
    }
}
