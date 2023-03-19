package com.example.kafka;

import com.example.kafka.service.EmailService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

@SpringBootTest
@RequiredArgsConstructor
class MailApplicationTests {

	@Value("${spring.mail.username}")
	private String senderEmail;

	@MockBean
	JavaMailSender javaMailSender;

	@Autowired
	EmailService emailService;

	private static final String EMAIL = "anastasiia.novoselova@nure.ua";
	private static final String CONTENT = "Some contents.";
	private static final String SUBJECT = "Some subject";

	@Test
	void testSendMail() throws MessagingException {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(senderEmail);
		simpleMailMessage.setTo(EMAIL);
		simpleMailMessage.setSubject(SUBJECT);
		simpleMailMessage.setText(CONTENT);

		// Act
		javaMailSender.send(simpleMailMessage);

		// Assert
		Mockito.verify(javaMailSender, Mockito.times(1)).send(simpleMailMessage);
	}

	@Test
	void testSendEmailWithError() {
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
		simpleMailMessage.setFrom(senderEmail);
		simpleMailMessage.setTo(EMAIL);
		simpleMailMessage.setSubject(SUBJECT);
		simpleMailMessage.setText(CONTENT);

		// Mock behavior to throw exception
		doThrow(new TestMailException("Test Exception")).when(javaMailSender).send(simpleMailMessage);

		// Act and Assert
		assertThatThrownBy(() -> javaMailSender.send(simpleMailMessage))
				.isInstanceOf(MailException.class)
				.hasMessageContaining("Test Exception");
	}

	public class TestMailException extends MailException {

		public TestMailException(String message) {
			super(message);
		}
	}
}
