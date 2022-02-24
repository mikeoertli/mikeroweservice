<h1 align="center">Mike's Mike Rowe Service Microservice</h1>

<p align="center">
<img width="200" alt="Mike Rowe (source: thepodcastplayground.com)" src="https://thepodcastplayground.com/wp-content/uploads/2021/12/mike-rown-backstage-with-gentry-thomas-podcast-playground.jpg"/>
</p>

<h3 align="center">In order to learn more about microservices, I figured... <br>What better way than with a <em>Mike Rowe Service</em>? <br><br>🙊😜</h3>

# Overview
A demo/sample project using Spring Boot, Kafka, REST, SQL, NoSQL, GraphQL, Gradle multimodule builds, etc., 
in a microservice architecture.

**This is not affiliated with Mike Rowe, Dirty Jobs, 'The Way I Heard It', or any other Mike-Rowe-based entity/product in ANY way.** 

## 🚨 Setting Expectations 🚨

1. 🤓 This repo and its contents exist solely for the purpose of 🔬🧠 learning/playing with a few technologies and concepts (in no particular order): 
   1. [microservices](https://microservices.io)
   2. [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
   3. [REST](https://en.wikipedia.org/wiki/Representational_state_transfer)
   4. [SQL](https://en.wikipedia.org/wiki/SQL) 
   5. [NoSQL](https://en.wikipedia.org/wiki/NoSQL)
   6. [GraphQL](https://www.graphql-java.com)
   7. [Elasticsearch](https://www.elastic.co)
   8. [Kafka (Streams)](https://kafka.apache.org/documentation/streams/)
   9. [a monorepo](https://en.wikipedia.org/wiki/Monorepo)
   10. [gradle multimodule builds](https://reflectoring.io/spring-boot-gradle-multi-module/), and more.
2. 🚧 This is ***nowhere near complete*** – not even in the "do an end-to-end 'hello world' test" sense.
3. 🐣 There is no plan to fully implement each of the modules/services. 
4. 🥸 Data will be canned and mocked in most cases.
5. 🐳 For now, this uses `docker-compose` 
   1. There is a tentative future plan to deploy this with K8s since I'd like to get more hands-on with that too, but no ETA on that.
6. 🚨 This was created to learn about these technologies/concepts. ⚠️ **DO NOT consider this as a reference for how to do anything the "right" way!** ⚠️
7. 🙈 Apologies in advance for the lack of tests and javadocs, I plan to add both. The plan was to get an outline with stubbed modules in place first.
8. 👋 Please don't hesitate to reach out with suggestions etc.

Essentially, having lacked any production microservice experience prior to this repo's inception, 
I wanted a "playground" in which to explore/learn a bunch of concepts and technologies.

## 🛠👷🏻🧱 Architecture

This is my "thinking out loud" diagram for what this could look like.

There are several notes embedded in the diagram which describe nuances, considerations, or theoretical "what if" ideas.

![Microservice Architecture Diagram](doc/architecture-summary.png)

## 🕓 Timestamps

All timestamps are either [unix epoch millis](https://en.wikipedia.org/wiki/Unix_time)
([converter](https://www.epochconverter.com)) (as stated by variable/field name) or
[ISO-8601 (ex: 2012-04-23T18:25:43.511Z)](http://en.wikipedia.org/wiki/ISO_8601).

## 📂 Directory Structure

```
├── README.md
├── build.gradle
├── client-api
├── doc
│   └── architecture-summary.png
├── docker
│   └── mapped-volumes
├── docker-compose.yml
├── libs
│   ├── db-adapter
│   ├── graphql-adapter
│   ├── kafka-adapter
│   ├── model
│   └── mongo-adapter
├── services
│   ├── imdb-service
│   ├── notification-service
│   ├── podcast-service
│   ├── transcript-service
│   └── twitter-service
├── settings.gradle
└── stream-processors
    └── sentiment-processor

```

# 🧑‍💻 Client API 🧑‍💻

There is a client API that is the "main" REST API for accessing this Mike Rowe "Service" backend.

The exact endpoints are TBD for now, but some ideas include:
* Get the link to the latest podcast that mentions `<topic>`.
* Retrieve the last tweet from Mike that has more than `<#>` likes.
* Get the highest rated episode of *Dirty Jobs* since `<date>`.


# 📚 Libraries 📚

## 📀 Data Model

This library contains POJO structures that serve as a common data model between services.

```groovy
api project(":model")
```

The original intent was to use JSON schema to define the beans and generate the POJOs as part of the
build, however that is not included in the initial pass. 

The (tentative) plan was to use [jsonschema2dataclass/js2d-gradle](https://github.com/jsonschema2dataclass/js2d-gradle).

## 🧩 MongoDB Adapter

**This includes the `Data Model` library.**

```groovy
api project(":mongo-adapter")
```

An API wrapper around a MongoDB to make it easier for services to use MongoDB without implementing a database
instance. This would also make it much easier to replace Mongo with an alternative solution at some point.


## 🦦 Kafka Adapter 

**This includes the `Data Model` library.**

Services can include this with:
```groovy
api project(":kafka-adapter")
```

This is a library that makes it exceedingly easy to integrate with Kafka or Kafka Streams as a publisher or 
receiver of Kafka data.

### Kafka Topics

Kafka topics are defined centrally in `libs/kafka-adapter/src/main/resources/application.properties`. For example: 

```properties
#
# Topics
#
kafka.topic.notification=notification
kafka.topic.sentiment=sentiment
kafka.topic.podcast=podcast
kafka.topic.video=video
kafka.topic.news=news
kafka.topic.social=social
kafka.topic.social.twitter=twitter
kafka.topic.social.facebook=facebook
```


## 📈 GraphQL Adapter

**This includes the `Data Model` and `MongoDB Adapter` libraries.**

```groovy
api project(":graphql-adapter")
```

There are numerous resources for [GraphQL with Java](https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/) 
and Spring Boot. It seems as though I approach this at an inflection point between pre-official-Spring-Boot-GraphQL and post. 
Right now, it requires a pre-release version of Spring Boot to use. At the time of writing, I have opted for the "old" approach.

TODO: Integrate this to use the data model's POJOs: https://github.com/graphql-java-kickstart/graphql-java-tools

The official [GraphQL Schema resources](https://graphql.org/learn/schema) are really useful.

The GraphQL Schema is defined in: `libs/graphql-adapter/src/main/resources/schema.graphqls`. This schema defines the
data structures and query API for the GraphQL functionality.

For example:

```graphql
type Query {
  latestPodcastMentioningTopic(topic: String!): PodcastEpisode
  mostPopularPodcastTopics(numMostPopular: Int): TopicList
  podcastTranscriptByEpisodeNumber(episodeNumber: Int!): Transcript
  podcastByEpisodeNumber(episodeNumber: Int!): PodcastEpisode
  televisionTranscript(showName: String!, seriesNumber: Int!, episodeNumber: Int!): Transcript
  televisionEpisode(showName: String!, seriesNumber: Int!, episodeNumber: Int!): TelevisionEpisode
  mostPopularTelevisionEpisode(showName: String!, seriesNumber: Int): TelevisionEpisode
  mostPopularTweetSince(numDays: Int): SocialMediaPost
  mostRecentTweetWithNumLikes(numLikes: Int): SocialMediaPost
  mostPopularSocialMediaPostSince(numDays: Int): SocialMediaPost
  mostPopularMovies(numMovies: Int): [Movie!]
}
```


# Modules and Services

These are services which process incoming data and requests in various ways in order to produce results, filter data, generate transcripts, and more.

## 🎬 Movies and TV Service (IMDB Service)

Retrieves data about TV and movies. 

This was called the "IMDB" service, but is likely going to use more "open" 
alternatives like [The Movie Database (TMDB)](https://developers.themoviedb.org) or 
[Open Movie Database (OMDB)](http://www.omdbapi.com).

## 📧 Notification Service

Based on the current rules/configuration, publish notifications for certain events.

For example, send a push notification or SMS each time a podcast episode is posted or each time any 
of Mike Rowe's content mentions the state of Colorado.

Right now, this is a placeholder. Eventually this could just be the service interface for several services behind
a curtain, things like [Twilio](https://twilio.com) (SMS), [Email](https://www.baeldung.com/spring-email), 
[IFTTT](https://ifttt.com), [SimplePush](https://simplepush.io), [AWS SNS](https://aws.amazon.com/sns/), and others.


## 🎧 Podcast Service

Retrieves episodes of Mike Rowe's podcast [The Way I Heard It](https://mikerowe.com/podcast/).

Uses RSS processing with the [Feed Adapter Spring Integration](https://docs.spring.io/spring-integration/reference/html/feed.html) 
(which uses [ROME](https://rometools.github.io/rome/) under the hood).

Feeds media to the `Transcript Service` where a transcript does not yet exist in the cache.

Brainstorm: extract topics, people, etc. and queue up OSINT "jobs" on each.


## 💬 Transcript Service

Retrieves existing transcripts or fetches them using a service like [Descript](https://www.descript.com). 
Note that right now, this just generates mock data in lieu of actually integrating with [Descript](https://www.descript.com) 
or similar.

Uses ElasticSearch to store transcripts.

### 📝 Development Note

To start a simple ElasticSearch container (stand-alone, independent of this project):
```shell
docker run -d --name elasticsearch -p 9200:9200 -e "discovery.type=single-node" elasticsearch:7.6.2
```

## 🐥 Twitter Service

This ia a service which leverages the [Twitter4J](https://twitter4j.org/en/index.html) library to 
monitor [Mike Rowe's Twitter account](https://twitter.com/mikeroweworks) as well as topics related 
to Mike Rowe. Data is ingested and published to a Kafka topic.

Monitored topics can be configured in the properties file for this service.


# 🦦💻 Kafka Stream Processors

Stream processors use [Kafka Streams](https://kafka.apache.org/documentation/streams/) to process data.

## 👍👎 Sentiment Analysis Service

Placeholder/future.

The general idea is creating a Kafka Stream processor that takes content that mentions Mike Rowe and generate
a sentiment score.


# Key Items on TODO List

The "TODO" list here is endless, but a few focal points include:
* Create the Kafka adapter and remove redundant code from various modules.
* Figure out how to share `properties` files, right now each module has `application.properties` of its own.
  * Lots of resources for this one, including:
    * [Spring Boot Docs - Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.external-config)
    * [StackOverflow](https://stackoverflow.com/questions/35663679/spring-boot-inherit-application-properties-from-dependency)
    * [Baeldung - Properties with Spring and Spring Boot](https://www.baeldung.com/properties-with-spring)
* Testcontainers setup ([example](https://nirajsonawane.github.io/2019/12/25/Testcontainers-With-Spring-Boot-For-Integration-Testing/))
* Use Secrets to capture things like the Twitter API tokens.

