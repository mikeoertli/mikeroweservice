package com.mikeoertli.sample.mrs.mongo;

import com.mikeoertli.sample.mrs.model.result.MediaResult;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB interface for {@link MediaResult} data
 *
 * @since 0.0.1
 */
public interface IMediaRepository extends MongoRepository<MediaResult, String>
{

}
