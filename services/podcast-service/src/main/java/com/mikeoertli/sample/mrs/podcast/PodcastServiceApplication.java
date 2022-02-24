package com.mikeoertli.sample.mrs.podcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class PodcastServiceApplication
{
    public static void main(String[] args)
    {
        ConfigurableApplicationContext context = SpringApplication.run(PodcastServiceApplication.class, args);
        PodcastFeedReader feedReader = context.getBean(PodcastFeedReader.class);
        feedReader.run();
    }
}
