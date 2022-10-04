package com.simon.notification.service;

import com.simon.notification.model.Notification;
import com.simon.notification.repository.NotificationRepository;

import java.time.LocalDateTime;

public class NotificationService {

    NotificationRepository notificationRepository;

    NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    public boolean sendNotification(Integer id, String message){
        notificationRepository.save(
                Notification.builder()
                        .message(message)
                        .id(id)
                        .build()
        );

        return true;
    }
}
