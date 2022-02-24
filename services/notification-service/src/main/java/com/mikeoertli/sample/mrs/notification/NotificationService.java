package com.mikeoertli.sample.mrs.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

/**
 * TODO
 *
 * @since 0.0.1
 */
@Service
public class NotificationService
{
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public void onNotifiableEvent(String payload)
    {
        // TODO
        logger.trace("onNotifiableEvent -- placeholder to push notification with payload: {}", payload);
    }

}
