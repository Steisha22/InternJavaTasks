package com.example.kafka.service;

public interface EmailService {
    void sendSimpleEmail(String consumerEmail, String subject, String content);
}
