package com.mikeoertli.sample.mrs.common;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Verify functionality of the date/time parsing/handling utils in {@link DateTimeUtils}
 *
 * @since 0.0.1
 */
class DateTimeUtilsTest
{

    @Test
    void testDateTimeToString()
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2022, 2, 22, 2, 22, 22, 222000000, ZoneId.of("UTC"));
        String result = DateTimeUtils.dateTimeToString(zonedDateTime);
        Assertions.assertThat(result).isEqualTo("2022-02-22T02:22:22.222Z");

        ZonedDateTime zonedDateTime22h = ZonedDateTime.of(2022, 2, 22, 22, 22, 22, 222000000, ZoneId.of("UTC"));
        String result22h = DateTimeUtils.dateTimeToString(zonedDateTime22h);
        Assertions.assertThat(result22h).isEqualTo("2022-02-22T22:22:22.222Z");
    }

    @Test
    void testDateTimeToZonedString()
    {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(2022, 2, 22, 2, 22, 22, 222000000, ZoneId.of("-0700"));
        String result = DateTimeUtils.dateTimeToZonedString(zonedDateTime);
        Assertions.assertThat(result).isEqualTo("2022-02-22T02:22:22.222-0700");

        ZonedDateTime zonedDateTime22h = ZonedDateTime.of(2022, 2, 22, 22, 22, 22, 0, ZoneId.of("-0700"));
        String result22h = DateTimeUtils.dateTimeToZonedString(zonedDateTime22h);
        Assertions.assertThat(result22h).isEqualTo("2022-02-22T22:22:22.000-0700");
    }

    @Test
    void testZonedDateTimeUtcFromString()
    {
        String zonedDateTimeString = "2022-02-22T02:22:22.222Z";
        ZonedDateTime expected = ZonedDateTime.of(2022, 2, 22, 2, 22, 22, 222000000, ZoneId.of("UTC"));
        ZonedDateTime result = DateTimeUtils.zonedDateTimeUtcFromString(zonedDateTimeString);
        Assertions.assertThat(result).isEqualTo(expected);
    }

    @Test
    void testZonedDateTimeFromStringWithZoneOffset()
    {
        String zonedDateTimeString = "2022-02-22T02:22:22.222-0700";
        ZonedDateTime expected = ZonedDateTime.of(2022, 2, 22, 2, 22, 22, 222000000, ZoneId.of("-0700"));
        ZonedDateTime result = DateTimeUtils.zonedDateTimeFromStringWithZoneOffset(zonedDateTimeString);
        Assertions.assertThat(result).isEqualTo(expected);

        String zonedDateTimeString22h = "2022-02-22T22:22:22.222-0700";
        ZonedDateTime expected22h = ZonedDateTime.of(2022, 2, 22, 22, 22, 22, 222000000, ZoneId.of("-0700"));
        ZonedDateTime result22h = DateTimeUtils.zonedDateTimeFromStringWithZoneOffset(zonedDateTimeString22h);
        Assertions.assertThat(result22h).isEqualTo(expected22h);
    }


    @Test
    void testInstantFromUtcString()
    {
        String utcDateTimeString = "2022-02-22T02:22:22.222Z";
        Instant expected = ZonedDateTime.of(2022, 2, 22, 2, 22, 22, 222000000, ZoneId.of("UTC")).toInstant();
        Instant result = DateTimeUtils.instantFromUtcString(utcDateTimeString);
        Assertions.assertThat(result).isEqualTo(expected);

        String utcDateTimeString22h = "2022-02-22T22:22:22.222Z";
        Instant expected22h = ZonedDateTime.of(2022, 2, 22, 22, 22, 22, 222000000, ZoneId.of("UTC")).toInstant();
        Instant result22h = DateTimeUtils.instantFromUtcString(utcDateTimeString22h);
        Assertions.assertThat(result22h).isEqualTo(expected22h);
    }

    @Test
    void testInstantFromZonedString()
    {
        String zonedDateTimeString = "2022-02-22T02:22:22.222-0700";
        Instant expected = ZonedDateTime.of(2022, 2, 22, 2, 22, 22, 222000000, ZoneId.of("-0700")).toInstant();
        Instant result = DateTimeUtils.instantFromZonedString(zonedDateTimeString);
        Assertions.assertThat(result).isEqualTo(expected);

        String zonedDateTimeString22h = "2022-02-22T22:22:22.222-0700";
        Instant expected22h = ZonedDateTime.of(2022, 2, 22, 22, 22, 22, 222000000, ZoneId.of("-0700")).toInstant();
        Instant result22h = DateTimeUtils.instantFromZonedString(zonedDateTimeString22h);
        Assertions.assertThat(result22h).isEqualTo(expected22h);
    }

    @Test
    void testGetDurationFromNow()
    {
        String pastDateTimeString = "2020-02-22T02:22:22.222-0700";
        String futureDateTimeString = "3020-02-22T02:22:22.222-0700";

        Assertions.assertThat(DateTimeUtils.getDurationFromNow(pastDateTimeString).isNegative()).isTrue();
        Assertions.assertThat(DateTimeUtils.getDurationFromNow(futureDateTimeString).isNegative()).isFalse();
    }
}