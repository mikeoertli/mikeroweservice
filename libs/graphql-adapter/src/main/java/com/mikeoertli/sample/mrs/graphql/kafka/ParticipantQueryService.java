package com.mikeoertli.sample.mrs.graphql.kafka;

import com.mikeoertli.sample.mrs.model.generated.types.IPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service used to query participants/people
 *
 * @since 0.0.1
 */
@Service
public class ParticipantQueryService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    // FIXME temp hack
    private final List<IPerson> people = new ArrayList<>();

    public Optional<IPerson> findPerson(@NonNull String personId)
    {
        return people.stream()
                .filter(person -> person.getId().equals(personId))
                .findFirst();
    }

    public List<IPerson> findPeople(@NonNull Collection<String> ids)
    {
        return people.stream()
                .filter(person -> ids.contains(person.getId()))
                .collect(Collectors.toList());
    }
}
