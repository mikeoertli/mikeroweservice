package com.mikeoertli.sample.mrs.transcript.service;

import com.mikeoertli.sample.mrs.model.generated.types.IMovie;
import com.mikeoertli.sample.mrs.model.generated.types.IPodcastEpisode;
import com.mikeoertli.sample.mrs.model.generated.types.ITelevisionEpisode;
import com.mikeoertli.sample.mrs.transcript.api.TranscriptWrapper;
import com.mikeoertli.sample.mrs.transcript.elastic.ITranscriptElasticRepository;
import com.mikeoertli.sample.mrs.transcript.util.SimulatorUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Optional;

/**
 * Fetches a transcript for various types of media.
 *
 * @since 0.0.1
 */
@Service
public class TranscriptDownloadService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final ITranscriptElasticRepository transcriptRepository;

    @Autowired
    public TranscriptDownloadService(ITranscriptElasticRepository transcriptRepository)
    {
        this.transcriptRepository = transcriptRepository;
    }

    public Optional<String> downloadPodcastTranscript(IPodcastEpisode podcastEpisode)
    {
        TranscriptWrapper randomTranscript = SimulatorUtilities.createTranscriptWrapperWithPodcastEpisodeNum(podcastEpisode.getEpisodeNumber());
        // TODO retrieve podcast MP3 and send to Descript or similar service to retrieve text transcript
        //      Possible resources: https://www.thepodcasthost.com/planning/podcast-transcription/

        transcriptRepository.save(randomTranscript);
        return Optional.of(randomTranscript.getTranscriptId());
    }

    public Optional<String> downloadTelevisionTranscript(ITelevisionEpisode televisionEpisode)
    {
        TranscriptWrapper randomTranscript = SimulatorUtilities.createTranscriptWrapperWithTitle(televisionEpisode.getEpisodeName());
        transcriptRepository.save(randomTranscript);
        return Optional.of(randomTranscript.getTranscriptId());
    }

    public Optional<String> downloadMovieTranscript(IMovie movie)
    {
        TranscriptWrapper randomTranscript = SimulatorUtilities.createTranscriptWrapperWithTitle(movie.getName());
        transcriptRepository.save(randomTranscript);
        return Optional.of(randomTranscript.getTranscriptId());
    }

}
