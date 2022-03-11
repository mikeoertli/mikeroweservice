package com.mikeoertli.sample.mrs.transcript.elastic;

import com.mikeoertli.sample.mrs.transcript.api.TranscriptWrapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * ElasticSearch repository for transcript storage and retrieval
 *
 * @since 0.0.1
 */
public interface ITranscriptElasticRepository extends ElasticsearchRepository<TranscriptWrapper, String>
{
    Page<TranscriptWrapper> findTranscriptByTopic(String topic, Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"media.id\": \"?0\"}}]}}")
    Page<TranscriptWrapper> findTranscriptForMediaId(String mediaId, Pageable pageable);
}
