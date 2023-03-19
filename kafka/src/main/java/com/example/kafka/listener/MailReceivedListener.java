package com.example.kafka.listener;

import com.example.kafka.messaging.MailReceivedMessage;
import com.example.kafka.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailReceivedListener {
    private final MailService mailService;

    @KafkaListener(topics = "${kafka.topic.mailReceived}")
    public void mailReceived(MailReceivedMessage message) {
        mailService.processMailReceived(message);
    }
}
