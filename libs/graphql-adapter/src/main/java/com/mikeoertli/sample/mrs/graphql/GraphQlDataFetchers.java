package com.mikeoertli.sample.mrs.graphql;

import com.google.common.collect.ImmutableMap;
import com.mikeoertli.sample.mrs.mongo.IMediaRepository;
import com.mikeoertli.sample.mrs.mongo.ISocialResultsRepository;
import graphql.schema.DataFetcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Source: https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/
 *
 * @since 0.0.1
 */
@Component
public class GraphQlDataFetchers
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private IMediaRepository mediaRepo;

    @Autowired
    private ISocialResultsRepository socialMediaRepo;

//    public DataFetcher<Map<String, String>> getBookByIdDataFetcher() {
//        return dataFetchingEnvironment -> {
//            String bookId = dataFetchingEnvironment.getArgument(GraphQlConstants.ID_KEY);
//            return SAMPLE_DATA_BOOKS
//                    .stream()
//                    .filter(book -> book.get(GraphQlConstants.ID_KEY).equals(bookId))
//                    .findFirst()
//                    .orElse(null);
//        };
//    }
//
//    public DataFetcher<Map<String, String>> getAuthorDataFetcher() {
//        return dataFetchingEnvironment -> {
//            Map<String,String> book = dataFetchingEnvironment.getSource();
//            String authorId = book.get(GraphQlConstants.BOOK_AUTHOR_ID_KEY);
//            return SAMPLE_DATA_AUTHORS
//                    .stream()
//                    .filter(author -> author.get(GraphQlConstants.ID_KEY).equals(authorId))
//                    .findFirst()
//                    .orElse(null);
//        };
//    }

    // From the example...
    // Let's assume for a second we have a mismatch and the book Map has a key totalPages instead of pageCount.
    // This would result in a null value for pageCount for every book, because the PropertyDataFetcher can't fetch the
    // right value. In order to fix that you would have to register a new DataFetcher for Book.pageCount
    // which looks like this:
//    public DataFetcher<String> getPageCountDataFetcher() {
//        return dataFetchingEnvironment -> {
//            Map<String,String> book = dataFetchingEnvironment.getSource();
//            return book.get("totalPages");
//        };
//    }
}
