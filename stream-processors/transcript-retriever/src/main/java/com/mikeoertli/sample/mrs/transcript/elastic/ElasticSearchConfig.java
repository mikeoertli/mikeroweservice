package com.mikeoertli.sample.mrs.transcript.elastic;

import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.lang.NonNull;

import java.lang.invoke.MethodHandles;

/**
 * Configuration for basic ElasticSearch integration
 *
 * @since 0.0.1
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.mikeoertli.sample.mrs.transcript.elastic")
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Value("${spring.data.elasticsearch.cluster-nodes}")
    private String clusterNodesList;

    @Bean
    public ElasticsearchOperations elasticsearchTemplate()
    {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

    @Override
    @Bean
    @NonNull
    public RestHighLevelClient elasticsearchClient()
    {
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(clusterNodesList)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
