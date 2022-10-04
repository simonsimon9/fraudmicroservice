package com.simon.notification.service;

import com.simon.clients.notification.NotificationRequest;
import com.simon.notification.model.Notification;
import com.simon.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
@Service
public class NotificationService {

    NotificationRepository notificationRepository;

    NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    public boolean send(NotificationRequest notificationRequest){
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerName()).sender("simon")
                        .message(notificationRequest.message())
                        .sentAt(LocalDateTime.now())
                        .build()
        );

        return true;
    }
}
