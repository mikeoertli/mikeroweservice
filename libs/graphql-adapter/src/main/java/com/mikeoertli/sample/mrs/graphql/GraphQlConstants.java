package com.mikeoertli.sample.mrs.graphql;

/**
 * Constants for things like Keys in GraphQL
 *
 * @since 0.0.1
 */
public class GraphQlConstants
{
    private GraphQlConstants()
    {
        // prevent instantiation
    }

    public static final String ID_KEY = "id";

    public static final String BOOK_NAME_KEY = "name";
    public static final String BOOK_PAGE_COUNT_KEY = "pageCount";
    public static final String BOOK_AUTHOR_ID_KEY = "authorId";

    public static final String AUTHOR_FIRST_NAME_KEY = "firstName";
    public static final String AUTHOR_LAST_NAME_KEY = "lastName";
}
