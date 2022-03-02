package com.mikeoertli.sample.mrs.clientapi.api;

import com.mikeoertli.sample.mrs.clientapi.service.MikeRoweService;
import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITelevisionEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.SocialResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * Primary backend API for the client to use for this demo/sample project.
 *
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/query")
public class MikeRoweController
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private MikeRoweService service;

    @GetMapping("/popular/tweet/year/{year}")
    public ResponseEntity<SocialResult> getMostPopularTweetForYear(@RequestParam(value = "year") int year)
    {
        return service.getMostPopularTweetForYear(year).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Year = " + year + " was not found."));
    }

    @GetMapping("/popular/tweet/total/{year}")
    public ResponseEntity<List<SocialResult>> getMostPopularTweets(@RequestParam(value = "numResults") int numResults)
    {
        List<SocialResult> results = service.getMostPopularTweets(numResults);

        if (results.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No popular tweets found.");
        } else
        {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    @GetMapping("/popular/podcast/year/{year}")
    public ResponseEntity<IPodcastEpisode> getMostPopularPodcastEpisodeForYear(@RequestParam(value = "year") int year)
    {
        return service.getMostPopularPodcastEpisodeForYear(year).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Year = " + year + " was not found."));
    }

    @GetMapping("/popular/podcast/total/{numResults}")
    public ResponseEntity<List<IPodcastEpisode>> getMostPopularPodcastEpisodes(@RequestParam(value = "numResults") int numResults)
    {
        List<IPodcastEpisode> results = service.getMostPopularPodcastEpisodes(numResults);
        if (results.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No popular podcast episodes found.");
        } else
        {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }

    @GetMapping("/popular/tv/year/{year}")
    public ResponseEntity<ITelevisionEpisode> getMostPopularTvEpisodeForYear(@RequestParam(value = "year") int year)
    {
        return service.getMostPopularTvEpisodeForYear(year).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Year = " + year + " was not found."));
    }

    @GetMapping("/popular/tv/total/{numResults}")
    public ResponseEntity<List<ITelevisionEpisode>> getMostPopularTvEpisodes(@RequestParam(value = "numResults") int numResults)
    {
        List<ITelevisionEpisode> results = service.getMostPopularTvEpisodes(numResults);
        if (results.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No popular TV episodes found.");
        } else
        {
            return new ResponseEntity<>(results, HttpStatus.OK);
        }
    }
}
