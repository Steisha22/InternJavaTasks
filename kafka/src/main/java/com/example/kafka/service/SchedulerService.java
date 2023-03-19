package com.example.kafka.service;

import com.example.kafka.data.MailData;
import com.example.kafka.data.MailStatus;
import com.example.kafka.repository.MailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    @Autowired
    private final EmailService emailService;

    private final MailService mailService;

    private final MailRepository mailRepository;

    @Scheduled(fixedDelay = 300000)
    public void sendMail() throws IOException {
        List<MailData> mails = mailService.getErrorMails();
        for (MailData mail: mails) {
            try {
                emailService.sendSimpleEmail(mail.getConsumerEmail(), mail.getSubject(), mail.getContent());
            } catch (MailException mailException) {
                mail.setStatus(MailStatus.ERROR);
                mail.setErrorMessage("Class: " + mailException.getClass() + "; errorMessage: " + mailException.getMessage());
                mailRepository.createOrUpdateDocument(mail);
                return;
            }
            mail.setStatus(MailStatus.SENT);
            mailRepository.createOrUpdateDocument(mail);
        }

    }
}
