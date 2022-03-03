package com.mikeoertli.sample.mrs.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * Utility methods used to handle/parse/construct date/time information.
 * Additional useful information can be found here: https://www.baeldung.com/java-datetimeformatter
 * And here: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/time/format/DateTimeFormatter.html
 *
 * @since 0.0.1
 */
public class DateTimeUtils
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Date/time format for a UTC timestamp, using the ISO_8601 format of yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * (ex: 2022-02-22T02:22:22.222Z)
     */
    public static final DateTimeFormatter DATE_FORMAT_UTC = DateTimeFormatter.ISO_INSTANT;

    /**
     * Date/time format using the ISO_8601 format of yyyy-MM-dd'T'HH:mm:ss.SSSZ
     * (ex: 2022-02-22T02:22:22.222Z)
     */
    public static final DateTimeFormatter DATE_FORMAT_ZONE_OFFSET = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

    /**
     * Convert the given {@link TemporalAccessor} (ex: {@link ZonedDateTime} or {@link Instant}) into a string in
     * the ISO_8601 format (ex: 2022-02-22T02:22:22.222Z).
     *
     * @param dateTime the date/time representation to be converted to a string
     * @return the string representation of the given date/time in the ISO_8601 format (ex: 2022-02-22T02:22:22.222Z)
     */
    public static String dateTimeToString(@NonNull TemporalAccessor dateTime)
    {
        return DATE_FORMAT_UTC.format(dateTime);
    }

    /**
     * Convert the given {@link TemporalAccessor} (ex: {@link ZonedDateTime} or {@link Instant}) into a string in
     * a format similar to ISO_8601, but with zone offset info relative to UTC. (ex: 2022-02-22T02:22:22.222-0700).
     *
     * @param dateTime the date/time representation to be converted to a string
     * @return the string representation of the given date/time in the ISO_8601-esque (+ zone offset) format (ex: 2022-02-22T02:22:22.222-0700)
     */
    public static String dateTimeToZonedString(@NonNull TemporalAccessor dateTime)
    {
        return DATE_FORMAT_ZONE_OFFSET.format(dateTime);
    }

    /**
     * Parse the given date/time string as an {@link ZonedDateTime}
     *
     * @param dateTime the date/time string in ISO_8601 format (ex: 2022-02-22T02:22:22.222Z)
     * @return the {@link ZonedDateTime} that represents the exact date/time of the given string
     */
    public static ZonedDateTime zonedDateTimeUtcFromString(@NonNull String dateTime)
    {
        TemporalAccessor parsed = DATE_FORMAT_UTC.parse(dateTime);
        Instant instant = Instant.from(parsed);
        return ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
    }

    /**
     * Parse the given date/time string as an {@link ZonedDateTime}
     *
     * @param dateTime the date/time string in a zone-affset-aware-ISO_8601 format (ex: 2022-02-22T02:22:22.222-0700)
     * @return the {@link ZonedDateTime} that represents the exact date/time of the given string
     */
    public static ZonedDateTime zonedDateTimeFromStringWithZoneOffset(@NonNull String dateTime)
    {
        return ZonedDateTime.from(DATE_FORMAT_ZONE_OFFSET.parse(dateTime));
    }

    /**
     * Parse the given date/time string as an {@link Instant}
     *
     * @param dateTime the date/time string in ISO_8601 format (ex: 2022-02-22T02:22:22.222Z)
     * @return the {@link Instant} that represents the exact date/time of the given string
     */
    public static Instant instantFromUtcString(@NonNull String dateTime)
    {
        if (dateTime.endsWith("Z"))
        {
            return Instant.from(DATE_FORMAT_UTC.parse(dateTime));
        } else
        {
            logger.debug("The given date/time string is not in the right format, rerouting request to instantFromZonedString");
            return instantFromZonedString(dateTime);
        }
    }

    /**
     * Parse the given date/time string as an {@link Instant}
     *
     * @param dateTime the date/time string in zone-affset-aware-ISO_8601 format (ex: 2022-02-22T02:22:22.222-0700)
     * @return the {@link Instant} that represents the exact date/time of the given string
     */
    public static Instant instantFromZonedString(@NonNull String dateTime)
    {
        if (dateTime.endsWith("Z"))
        {
            logger.debug("The given date/time string is not in the right format, rerouting request to instantFromUtcString");
            return instantFromUtcString(dateTime);
        } else
        {
            ZonedDateTime zonedDateTime = ZonedDateTime.from(DATE_FORMAT_ZONE_OFFSET.parse(dateTime));
            return zonedDateTime.toInstant();
        }
    }

    /**
     * Get the {@link Duration} between now and the provided date/time string (which is parsed to an {@link Instant}
     * using {@link #instantFromUtcString(String)}). If the given date/time is in the future, the duration will be
     * positive, otherwise it will be negative.
     *
     * @param dateTime the date/time to compare to {@link Instant#now()}, should be in the ISO-8601 format (ex: 2022-02-22T02:22:22.222Z)
     * @return the {@link Duration} between the provided date/time and now, positive if the given time is in the future,
     * negative if it is in the past.
     */
    public static Duration getDurationFromNow(@NonNull String dateTime)
    {
        Instant given = instantFromUtcString(dateTime);
        Instant now = Instant.now();
        return Duration.between(now, given);
    }
}
