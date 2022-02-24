# Mike's Mike Rowe Service Microservice

![Mike Rowe (source: thepodcastplayground.com)](https://thepodcastplayground.com/wp-content/uploads/2021/12/mike-rown-backstage-with-gentry-thomas-podcast-playground.jpg)

## ⚠️ In order to learn more about microservices, I figured, what better way than with a *Mike Rowe Service*? ⚠️

A demo/sample project using Spring Boot, Kafka, REST, SQL, NoSQL, GraphQL, Gradle multimodule builds, etc., 
in a microservice architecture.

## 🚨 Setting Expectations 🚨

1. 🤓 This repo and its contents exist solely for the purpose of 🔬🧠 learning/playing with a few technologies and concepts (in no particular order): 
   1. [microservices](https://microservices.io)
   2. [Spring Boot](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
   3. [REST](https://en.wikipedia.org/wiki/Representational_state_transfer)
   4. [SQL](https://en.wikipedia.org/wiki/SQL) 
   5. [NoSQL](https://en.wikipedia.org/wiki/NoSQL)
   6. [GraphQL](https://www.graphql-java.com)
   7. [Kafka (Streams)](https://kafka.apache.org/documentation/streams/)
   8. [a monorepo](https://en.wikipedia.org/wiki/Monorepo)
   9. [gradle multimodule builds](https://reflectoring.io/spring-boot-gradle-multi-module/), and more.
2. 🚧 This is nowhere near complete – even in the "do an end-to-end 'hello world' test" sense.
3. 🐣 There is no plan to fully implement each of the modules/services. 
4. 🥸 Data will be canned and mocked in most cases.
5. 🐳 For now, this uses `docker-compose` with a tentative future plan to deploy with K8s as another learning "playground."
6. 🚨 This was created to learn about these technologies/concepts. ⚠️ **DO NOT consider this as a reference for how to do anything!** ⚠️ 
7. 👋 Please don't hesitate to reach out with suggestions etc.

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

The original intent was to use JSON schema to define the beans and generate the POJOs as part of the
build, however that is not included in the initial pass. 

The (tentative) plan was to use [jsonschema2dataclass/js2d-gradle](https://github.com/jsonschema2dataclass/js2d-gradle).

## 🧩 MongoDB Adapter

**This includes the `Data Model` library.**

An API wrapper around a MongoDB to make it easier for services to use MongoDB without implementing a database
instance. This would also make it much easier to replace Mongo with an alternative solution at some point.


## 🦦 Kafka Adapter 

**This includes the `Data Model` library.**

This is a library that makes it exceedingly easy to integrate with Kafka or Kafka Streams as a publisher or receiver of Kafka data.


## 📈 GraphQL Adapter

**This includes the `Data Model` and `MongoDB Adapter` libraries.**

There are numerous resources for [GraphQL with Java](https://www.graphql-java.com/tutorials/getting-started-with-spring-boot/) 
and Spring Boot. It seems as though I approach this at an inflection point between pre-official-Spring-Boot-GraphQL and post. 
Right now, it requires a pre-release version of Spring Boot to use. At the time of writing, I have opted for the "old" approach.

TODO: Integrate this to use the data model's POJOs: https://github.com/graphql-java-kickstart/graphql-java-tools


# Modules and Services

## 🎬 Movies and TV Service (IMDB Service)

Retrieves data about TV and movies. This was called the "IMDB" service, but is likely going to use more "open" 
alternatives like [The Movie Database (TMDB)](https://developers.themoviedb.org) or 
[Open Movie Database (OMDB)](http://www.omdbapi.com).

## ⚠️ Notification Service

Based on the current rules/configuration, publish notifications for certain events.

For example, send a push notification or SMS each time a podcast episode is posted or each time any 
of Mike Rowe's content mentions the state of Colorado.


## 🎧 Podcast Service

Retrieves episodes of Mike Rowe's podcast [The Way I Heard It](https://mikerowe.com/podcast/).

Feeds media to the `Transcript Service` where a transcript does not yet exist in the cache.

Brainstorm: extract topics, people, etc. and queue up OSINT "jobs" on each.


## 💬 Transcript Service

Retrieves existing transcripts or fetches them using a service like [Descript](https://www.descript.com). 
Note that right now, this just generates mock data in lieu of actually integrating with [Descript](https://www.descript.com) 
or similar.

Uses ElasticSearch to store transcripts.

### 📝 Development Note

To start a simple ElasticSearch container:
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


