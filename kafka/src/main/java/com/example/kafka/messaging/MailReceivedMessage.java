package com.example.kafka.messaging;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class MailReceivedMessage {
    private String transactionId;

    private String subject;

    private String consumerEmail;

    private String content;
}
