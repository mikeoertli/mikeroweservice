package com.mikeoertli.sample.mrs.mongo;

import com.mikeoertli.sample.mrs.model.generated.types.SocialResult;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * MongoDB interface for {@link SocialResult} data
 *
 * @since 0.0.1
 */
public interface ISocialResultsRepository extends MongoRepository<SocialResult, String>
{

}
