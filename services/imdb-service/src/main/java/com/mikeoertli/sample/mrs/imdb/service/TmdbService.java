package com.mikeoertli.sample.mrs.imdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.invoke.MethodHandles;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * https://developers.themoviedb.org/
 * <p>
 * Sample queries:
 * <ul>
 *     <li>Search for people named "Mike Rowe"</li>
 *     <ul>
 *         <li>
 *           https://api.themoviedb.org/3/search/person?api_key=702825a515c11f4ceb349ee346e3bb62&language=en-US&query=Mike%20Rowe&page=1&include_adult=false
 *       </li>
 *     </ul>
 *     <li>Get the person with TMDB ID == 1215558 (Mike Rowe's ID)</li>
 *     <ul>
 *         <li>
 *             https://api.themoviedb.org/3/person/1215558?api_key=702825a515c11f4ceb349ee346e3bb62&language=en-US
 *         </li>
 *     </ul>
 *     <li>Get TV shows named "Dirty Jobs"</li>
 *     <ul>
 *         <li>
 *    https://api.themoviedb.org/3/search/tv?api_key=702825a515c11f4ceb349ee346e3bb62&language=en-US&query=Dirty%20Jobs&include_adult=false
 *         </li>
 *     </ul>
 * </ul>
 *
 * @since 0.0.1
 */
@Service
public class TmdbService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String BASE_URL = "https://api.themoviedb.org";
    public static final String API_VERSION = "3";
    public static final String SEARCH_PERSON_SUFFIX = "search/person";
    public static final String TV_CREDITS = "tv_credits";
    public static final String PERSON_SUFFIX = "person";
    public static final String API_KEY_PREFIX = "?api_key=";
    public static final String ENGLISH_LANG = "&language=en-US";
    public static final String QUERY_PREFIX = "&query=";
    public static final String MIKE_ROWE_NAME = "Mike Rowe";
    public static final String OMIT_ADULT_RESULTS_SUFFIX = "&include_adult=false";
    public static final String MIKE_ROWE_TMDB_ID = "1215558";
    public static final String MIKE_ROWE_IMDB_ID = "nm1192046";

    @Value("${tmdb.api.v3.key}")
    private String apiV3Key;

    private WebClient client;

    private void initializeClient()
    {
        client = WebClient.builder()
                .baseUrl(BASE_URL)
//                .defaultCookie("cookieKey", "cookieValue")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .defaultUriVariables(Collections.singletonMap("url", "http://localhost:8080"))
                .build();
    }

    public String getRawMikeRoweSearchResults()
    {
        WebClient.ResponseSpec result = client.get()
                .uri(getMikeRowePersonSearchQueryUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        String rawJson = result.bodyToMono(String.class).block();
        logger.debug("getRawMikeRoweSearchResults - Raw result: \n{}", rawJson);
        return rawJson;
    }

    public String getRawMikeRowePerson()
    {
        WebClient.ResponseSpec result = client.get()
                .uri(getMikeRowePersonUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        String rawJson = result.bodyToMono(String.class).block();
        logger.debug("getRawMikeRowePerson - Raw result: \n{}", rawJson);
        return rawJson;
    }

    public String getMikeRoweTvCredits()
    {
        // https://api.themoviedb.org/3/person/1215558/tv_credits?api_key=702825a515c11f4ceb349ee346e3bb62&language=en-US

        // TODO - loop through results to get all pages
        WebClient.ResponseSpec result = client.get()
                .uri(getMikeRoweTvCreditsUrl())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve();

        String rawJson = result.bodyToMono(String.class).block();
        logger.debug("getMikeRoweTvCredits - Raw result: \n{}", rawJson);
        return rawJson;
    }

    private String getMikeRowePersonSearchQueryUrl()
    {
        String encodedMikeRoweName = URLEncoder.encode(MIKE_ROWE_NAME, StandardCharsets.UTF_8);
        return "/" + API_VERSION + "/" + SEARCH_PERSON_SUFFIX + API_KEY_PREFIX + apiV3Key + ENGLISH_LANG + QUERY_PREFIX + encodedMikeRoweName + OMIT_ADULT_RESULTS_SUFFIX;
    }

    private String getMikeRowePersonUrl()
    {
        // https://api.themoviedb.org/3/person/1215558?api_key=702825a515c11f4ceb349ee346e3bb62&language=en-US
        return "/" + API_VERSION + "/" + PERSON_SUFFIX + "/" + MIKE_ROWE_TMDB_ID + API_KEY_PREFIX + apiV3Key + ENGLISH_LANG;
    }

    private String getMikeRoweTvCreditsUrl()
    {
        // https://api.themoviedb.org/3/person/1215558/tv_credits?api_key=702825a515c11f4ceb349ee346e3bb62&language=en-US
        return "/" + API_VERSION + "/" + PERSON_SUFFIX + "/" + MIKE_ROWE_TMDB_ID + "/" + TV_CREDITS + API_KEY_PREFIX + apiV3Key + ENGLISH_LANG;
    }
}
