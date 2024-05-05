package amcn.amcn.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.UnsupportedEncodingException;

public interface MailService {
    MimeMessage createMessage(String to)  throws MessagingException, UnsupportedEncodingException, MessagingException;
    String createKey();
    String sendSimpleMessage(String to) throws Exception;
}
