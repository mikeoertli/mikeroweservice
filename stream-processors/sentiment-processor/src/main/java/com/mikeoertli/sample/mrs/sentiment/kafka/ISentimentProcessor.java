package com.mikeoertli.sample.mrs.sentiment.kafka;

/**
 * Common sentiment processor interface
 *
 * @since 0.0.1
 */
public interface ISentimentProcessor
{
    int getSentimentScore(String message);
}
