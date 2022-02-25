package com.mikeoertli.sample.mrs.graphql;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;

/**
 * GraphQL provider bean
 *
 * @since 0.0.1
 */
@SuppressWarnings("UnstableApiUsage")
@Component
public class GraphQlProvider
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    public static final String GRAPHQL_SCHEMA_FILE_RESOURCE = "schema.graphqls";

    private GraphQL graphQl;

    @Autowired
    GraphQlDataFetchers graphQLDataFetchers;

    @Bean
    public GraphQL graphQl()
    {
        return graphQl;
    }

    @PostConstruct
    public void init() throws IOException
    {
        URL url = Resources.getResource(GRAPHQL_SCHEMA_FILE_RESOURCE);
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        graphQl = GraphQL.newGraphQL(graphQLSchema).build();
        logger.debug("Initialized the GraphQL instance from schema ({})", GRAPHQL_SCHEMA_FILE_RESOURCE);
    }

    private GraphQLSchema buildSchema(String sdl)
    {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring runtimeWiring = buildWiring();
        SchemaGenerator schemaGenerator = new SchemaGenerator();
        logger.trace("Initializing the executable schema from parsed SDL resource...");
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring);
    }

    private RuntimeWiring buildWiring()
    {
        return RuntimeWiring.newRuntimeWiring()
//                .type(newTypeWiring("Query").dataFetcher("bookById", graphQLDataFetchers.getBookByIdDataFetcher()))
//                .type(newTypeWiring("Book").dataFetcher("author", graphQLDataFetchers.getAuthorDataFetcher()))
                .build();
    }
}
