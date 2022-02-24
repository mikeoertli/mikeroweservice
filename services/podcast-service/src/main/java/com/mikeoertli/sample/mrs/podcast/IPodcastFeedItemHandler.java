package com.mikeoertli.sample.mrs.podcast;

import org.springframework.messaging.Message;
import reactor.core.publisher.Mono;

/**
 * Simple interface to capture handling of a single feed item
 *
 * @since 0.0.1
 */
public interface IPodcastFeedItemHandler
{
    Mono<Void> handleFeedItem(Message<?> message);
}
