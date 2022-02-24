package com.mikeoertli.sample.mrs.clientapi.api;

import com.mikeoertli.sample.mrs.clientapi.service.MikeRoweService;
import com.mikeoertli.sample.mrs.model.result.AResultWrapper;
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

/**
 * TODO
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

    @GetMapping("/popular/{year}")
    public ResponseEntity<AResultWrapper> getMostPopularTweetForYear(@RequestParam(value = "year") int year)
    {
        return service.getMostPopularTweetForYear(year).map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Year = " + year + " was not found."));
    }
}
