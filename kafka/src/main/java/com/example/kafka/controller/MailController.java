package com.example.kafka.controller;

import com.example.kafka.dto.MailReceivedDto;
import com.example.kafka.messaging.MailReceivedMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/kafka")
@RequiredArgsConstructor
public class MailController {

    @Value("${kafka.topic.mailReceived}")
    private String mailReceivedTopic;

    private final KafkaOperations<String, MailReceivedMessage> kafkaOperations;

    @PostMapping("/sendMail")
    public void receiveMail(@RequestBody MailReceivedDto dto) {
        kafkaOperations.send(mailReceivedTopic, toMessage(dto));
    }

    private static MailReceivedMessage toMessage(MailReceivedDto dto) {
        return MailReceivedMessage.builder()
                .subject(dto.getSubject())
                .consumerEmail(dto.getConsumerEmail())
                .content(dto.getContent())
                .transactionId(UUID.randomUUID().toString())
                .build();
    }
}
