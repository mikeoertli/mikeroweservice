package com.mikeoertli.sample.mrs.twitter;

import com.mikeoertli.sample.mrs.twitter.kafka.TwitterKafkaPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.lang.invoke.MethodHandles;

/**
 * https://twitter.com/mikeroweworks
 *
 * @since 0.0.1
 */
@Service
public class TwitterMonitorService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value(value = "${oauth.access.token}")
    private String oauthToken;

    @Value(value = "${oauth.access.token.secret}")
    private String oauthTokenSecret;

    @Value(value = "${mike.row.twitter.user.id}")
    private long mikeRoweId;

    @Value(value = "${mike.row.twitter.user.handle}")
    private String mikeRoweHandle;

    //    private Twitter twitterApi;
//    private User mikeRoweUser;
    private TwitterStream twitterStream;

    public void initialize(TwitterKafkaPublisher publisher)
    {
        twitterStream = TwitterStreamFactory.getSingleton();
        twitterStream.user(mikeRoweHandle).onStatus(publisher::forwardTwitterStatusToKafka);
//        TwitterFactory twitterFactory = new TwitterFactory();
//        twitterApi = twitterFactory.getInstance(new AccessToken(oauthToken, oauthTokenSecret));
//        try
//        {
//            mikeRoweUser = twitterApi.showUser(mikeRoweId);
//            // TODO
//        } catch (TwitterException e)
//        {
//            logger.error("Error while retrieving Mike Rowe user", e);
//        }
    }

    @PreDestroy
    public void shutdown()
    {
        logger.trace("Twitter service is shutting down...");
        if (twitterStream != null)
        {
            twitterStream.shutdown();
        }
    }
}
