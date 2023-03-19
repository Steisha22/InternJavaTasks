package com.example.kafka.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class MailReceivedDto {
    private String subject;

    private String consumerEmail;

    private String content;
}
