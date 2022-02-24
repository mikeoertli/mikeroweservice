package com.mikeoertli.sample.mrs.clientapi.service;

import com.mikeoertli.sample.mrs.model.result.AResultWrapper;
import com.mikeoertli.sample.mrs.model.result.MediaResult;
import com.mikeoertli.sample.mrs.model.result.SocialResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

/**
 * FIXME THIS IS ALL A REALLY GROSS HACK AND PLACEHOLDER
 *
 * @since 0.0.1
 */
@Service
public class MikeRoweService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public Optional<AResultWrapper> getMostPopularTweetForYear(int year)
    {
        // fixme
        return Optional.of(new SocialResult());
    }

    public Optional<AResultWrapper> getMostPopularTweets(int numMostPopularTweets)
    {
        // fixme
        return Optional.of(new SocialResult());
    }

    public Optional<AResultWrapper> getMostPopularEpisodeForYear(int year)
    {
        // fixme
        return Optional.of(new MediaResult());
    }

    public Optional<AResultWrapper> getMostPopularEpisodes(int numMostPopularEpisodes)
    {
        // fixme
        return Optional.of(new MediaResult());
    }
}
