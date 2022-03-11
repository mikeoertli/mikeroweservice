package com.mikeoertli.sample.mrs.transcript.service;

import com.mikeoertli.sample.mrs.model.generated.types.ICompany;
import com.mikeoertli.sample.mrs.model.generated.types.IPerson;
import com.mikeoertli.sample.mrs.model.generated.types.ITopic;
import com.mikeoertli.sample.mrs.transcript.api.TranscriptWrapper;
import com.mikeoertli.sample.mrs.transcript.elastic.ITranscriptElasticRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Fetches a transcript for various types of media.
 *
 * @since 0.0.1
 */
@Service
public class TranscriptAnalysisService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ITranscriptElasticRepository transcriptRepository;
    private final ElasticsearchOperations searchTemplate;

    @Autowired
    public TranscriptAnalysisService(ITranscriptElasticRepository transcriptRepository, ElasticsearchOperations searchTemplate)
    {
        this.transcriptRepository = transcriptRepository;
        this.searchTemplate = searchTemplate;
    }

    public List<IPerson> identifyPeopleForTranscript(String transcriptId)
    {
        Optional<TranscriptWrapper> transcriptMatch = transcriptRepository.findById(transcriptId);
        // FIXME
        return Collections.emptyList();
    }

    public List<ICompany> identifyCompaniesForTranscript(String transcriptId)
    {
        Optional<TranscriptWrapper> transcriptMatch = transcriptRepository.findById(transcriptId);
        // FIXME
        return Collections.emptyList();
    }

    public List<ITopic> identifySubjectsForTranscript(String transcriptId)
    {
        Optional<TranscriptWrapper> transcriptMatch = transcriptRepository.findById(transcriptId);
        // FIXME
        return Collections.emptyList();
    }
}
