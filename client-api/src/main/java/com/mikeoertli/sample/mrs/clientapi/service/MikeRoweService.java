package com.mikeoertli.sample.mrs.clientapi.service;

import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITelevisionEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.PodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.SocialMediaPost;
import com.mikeoertli.sample.mrs.model.generated.types.SocialResult;
import com.mikeoertli.sample.mrs.model.generated.types.TelevisionEpisode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.List;
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

    public Optional<SocialResult> getMostPopularTweetForYear(int year)
    {
        // fixme
        return Optional.of(SocialMediaPost.newBuilder().build());
    }

    public List<SocialResult> getMostPopularTweets(int numMostPopularTweets)
    {
        // fixme
        return List.of(SocialMediaPost.newBuilder().build(), SocialMediaPost.newBuilder().build(), SocialMediaPost.newBuilder().build());
    }

    public Optional<ITelevisionEpisode> getMostPopularTvEpisodeForYear(int year)
    {
        // fixme
        return Optional.of(TelevisionEpisode.newBuilder().build());
    }

    public Optional<IPodcastEpisode> getMostPopularPodcastEpisodeForYear(int year)
    {
        // fixme
        return Optional.of(PodcastEpisode.newBuilder().build());
    }

    public List<ITelevisionEpisode> getMostPopularTvEpisodes(int numMostPopularEpisodes)
    {
        // fixme
        return List.of(TelevisionEpisode.newBuilder().build(), TelevisionEpisode.newBuilder().build());
    }

    public List<IPodcastEpisode> getMostPopularPodcastEpisodes(int numMostPopularEpisodes)
    {
        // fixme
        return List.of(PodcastEpisode.newBuilder().build(), PodcastEpisode.newBuilder().build(), PodcastEpisode.newBuilder().build());
    }
}
