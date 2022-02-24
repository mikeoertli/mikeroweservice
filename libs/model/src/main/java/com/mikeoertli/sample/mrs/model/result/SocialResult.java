package com.mikeoertli.sample.mrs.model.result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Set;

/**
 * Social Media related result
 *
 * @since 0.0.1
 */
public class SocialResult extends AResultWrapper
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String socialMediaPlatform;
    private int numLikes;
    private int numShares;
    private Set<String> taggedUsers;
    private Set<String> hashtags;
}
