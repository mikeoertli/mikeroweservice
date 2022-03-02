package com.mikeoertli.sample.mrs.transcript.api;

import com.mikeoertli.sample.mrs.transcript.service.TranscriptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.invoke.MethodHandles;

/**
 * REST API endpoint for transcript queries
 *
 * @since 0.0.1
 */
@RestController
@RequestMapping("/api/transcript/query")
public class TranscriptQueryController
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private TranscriptService service;

    @GetMapping("/podcast/{episodeNum}")
    public ResponseEntity<TranscriptWrapper> getTranscriptForPodcast(@RequestParam(value = "episodeNum") int episodeNum)
    {
        return service.getTranscriptForPodcast(episodeNum).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Episode #" + episodeNum + " was not found."));
    }

    @GetMapping("/topics/{keywords}")
    public ResponseEntity<SearchHits<TranscriptWrapper>> findPodcastsWithKeywords(@RequestParam(value = "keywords") String keywords)
    {
        SearchHits<TranscriptWrapper> searchHits = service.getTranscriptsWithKeywords(keywords);
        if (searchHits.isEmpty())
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Keywords = " + keywords + " was not found.");
        } else
        {
            return ResponseEntity.ok(searchHits);
        }
    }
}
