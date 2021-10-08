package com.project.inventory.mail.service.impl;

import com.project.inventory.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail( String to, String subject, String text ) {
      try {
          SimpleMailMessage msg = new SimpleMailMessage();

          msg.setTo( to );
          msg.setSubject( subject );
          msg.setText(text);

          javaMailSender.send( msg );
      }catch( MailSendException e ){
          throw e;
      }
    }
}
