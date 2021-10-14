package com.project.inventory.mail.service;

public interface MailService {

    void sendEmail(String to, String subject, String text);

    Boolean validateEmail(String email);
}
