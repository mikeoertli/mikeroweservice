package com.mikeoertli.sample.mrs.imdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

/**
 * http://www.omdbapi.com
 *
 * @since 0.0.1
 */
@Service
public class OpenMovieDbService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String URL_ROOT = "http://www.omdbapi.com/?apikey=[KEY_GOES_HERE]&";
    private static final String KEY_PLACEHOLDER = "KEY_GOES_HERE";

    @Value("${omdb.api.key}")
    private String apiKey;



}
