package com.example.kafka.service;

import com.example.kafka.controller.MailController;
import com.example.kafka.data.MailData;
import com.example.kafka.data.MailStatus;
import com.example.kafka.messaging.MailReceivedMessage;
import com.example.kafka.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService{

    private final MailRepository mailRepository;

    @Autowired
    EmailService emailService;

    private static final Logger LOG = LoggerFactory.getLogger(MailController.class);

    @Override
    public void processMailReceived(MailReceivedMessage message) {
        MailData mail = new MailData();
        if (!mail.hasTransactionId(message.getTransactionId())) {
            mail.setSubject(message.getSubject());
            mail.setConsumerEmail(message.getConsumerEmail());
            mail.setContent(message.getContent());
            mail.setStatus(MailStatus.NEW);
            mail.addTransactionId(message.getTransactionId());
            String id = saveOrUpdateEmail(false, mail);
            mail.setId(id);
            try {
                emailService.sendSimpleEmail(mail.getConsumerEmail(), mail.getSubject(), mail.getContent());
            } catch (MailException mailException) {
                mail.setStatus(MailStatus.ERROR);
                mail.setErrorMessage("Class: " + mailException.getClass() + "; errorMessage: " + mailException.getMessage());
                saveOrUpdateEmail(true, mail);
                return;
            }
            mail.setStatus(MailStatus.SENT);
            saveOrUpdateEmail(true, mail);
        }
    }

    @Override
    public List<MailData> getErrorMails() {
        return mailRepository.searchErrorMails();
    }

    private String saveOrUpdateEmail(boolean isSaved, MailData mail) {
        if (isSaved == false) {
            String id = "";
            try {
                id = mailRepository.createOrUpdateDocument(mail);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return id;
        }
        else {
            try {
                mailRepository.createOrUpdateDocument(mail);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }
}
