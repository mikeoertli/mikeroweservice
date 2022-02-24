package com.mikeoertli.sample.mrs.transcript.service;

import com.mikeoertli.sample.mrs.transcript.api.TranscriptWrapper;
import com.mikeoertli.sample.mrs.transcript.elastic.ITranscriptElasticRepository;
import com.mikeoertli.sample.mrs.transcript.util.MetadataConstants;
import com.mikeoertli.sample.mrs.transcript.util.SimulatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

import static com.mikeoertli.sample.mrs.transcript.util.MetadataConstants.TRANSCRIPT_BODY_KEY;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

/**
 * Fetches a transcript for various types of media.
 *
 * @since 0.0.1
 */
@Service
public class TranscriptRetrievalService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private ITranscriptElasticRepository transcriptRepository;

    @Autowired
    private ElasticsearchOperations searchTemplate;


    public Optional<TranscriptWrapper> getTranscript(String transcriptId)
    {
        return transcriptRepository.findById(transcriptId);
    }

    public Optional<TranscriptWrapper> fetchTranscriptByTitle(String mediaTitle)
    {
        logger.debug("Fetching transcript for requested title: {}", mediaTitle);
        TranscriptWrapper retrievedTranscript = SimulatorUtilities.createTranscriptWrapperWithTitle(mediaTitle);

        TranscriptWrapper saved = transcriptRepository.save(retrievedTranscript);
        return Optional.of(saved);
    }

    public Optional<TranscriptWrapper> getTranscriptLocalOnly(String transcriptId)
    {
        return transcriptRepository.findById(transcriptId);
    }

    public Page<TranscriptWrapper> getTranscriptForMedia(String mediaId)
    {
        return transcriptRepository.findTranscriptForMediaId(mediaId, Pageable.ofSize(20));
    }

    public Optional<TranscriptWrapper> getTranscriptForPodcast(int episodeNum)
    {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery(MetadataConstants.EPISODE_NUMBER, episodeNum))
                .build();

        SearchHits<TranscriptWrapper> searchResults = searchTemplate.search(searchQuery, TranscriptWrapper.class);
        if (searchResults.getMaxScore() < 0.5f)
        {
            TranscriptWrapper wrapper = SimulatorUtilities.createTranscriptWrapperWithPodcastEpisodeNum(episodeNum);
            TranscriptWrapper saved = transcriptRepository.save(wrapper);
            return Optional.of(saved);
        } else
        {
            SearchHit<TranscriptWrapper> searchHit = searchResults.getSearchHit(0);

            return Optional.of(searchHit.getContent());
        }
    }

    public SearchHits<TranscriptWrapper> getTranscriptsWithKeywords(String keywordsCsv)
    {
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery(TRANSCRIPT_BODY_KEY, keywordsCsv))
                .build();

        return searchTemplate.search(searchQuery, TranscriptWrapper.class);
    }
}
