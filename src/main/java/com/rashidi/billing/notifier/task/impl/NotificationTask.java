package com.rashidi.billing.notifier.task.impl;

import com.rashidi.billing.notifier.service.impl.NotificationGeneratorServiceImpl;
import com.rashidi.billing.notifier.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;

/**
 * Scheduled task and in specified interval it runs and calls generate() method.
 *
 * @author Mina Rashidi
 */
@Component
public class NotificationTask implements Task {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificationTask.class);

    private NotificationGeneratorServiceImpl notificationGeneratorService;

    //    @Scheduled(cron = "*/5 * * * * *" )
    @Scheduled(fixedRate = 5000)
    public void execute() {
        LOGGER.info("The time is now {}", Instant.now());
        notificationGeneratorService.generate();
    }

    @Autowired
    public void setNotificationGeneratorService(NotificationGeneratorServiceImpl notificationGeneratorService) {
        this.notificationGeneratorService = notificationGeneratorService;
    }
}
