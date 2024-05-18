package com.example.bookingmovieticket.service;

import com.example.bookingmovieticket.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Verification";
        String senderName = "HN Cinema - Service";
        String mailContent = "<p> Hi, "+ user.getName()+ ", </p>"+
                "<p>Thank you for registering with us. " +
                "Please, follow the link below to complete your registration.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("hienjang910@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }

    public void sendPasswordRetrievalEmail(User user, String url) throws MessagingException, UnsupportedEncodingException {
        String subject = "Email Password Retrieval";
        String senderName = "HN Cinema - Service";
        String mailContent = "<p> Hi, "+ user.getName()+ ", </p>"+
                "<p>STUPID, Muahahahaha! </p>"+
                "<p>We're sad that you forgot your password, but it's okay hehe. " +
                "Please, click to the link below to reset your password.</p>"+
                "<a href=\"" +url+ "\">Click to reset your password</a>"+
                "<p> Thank you for forgetting password, <br> HN Cinema.</p>";
        MimeMessage message = mailSender.createMimeMessage();
        var messageHelper = new MimeMessageHelper(message);
        messageHelper.setFrom("hienjang910@gmail.com", senderName);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);
        mailSender.send(message);
    }
}
