package com.example.kafka.service;

import com.example.kafka.data.MailData;
import com.example.kafka.messaging.MailReceivedMessage;

import java.io.IOException;
import java.util.List;

public interface MailService {
    void processMailReceived(MailReceivedMessage message);

    List<MailData> getErrorMails() throws IOException;
}
