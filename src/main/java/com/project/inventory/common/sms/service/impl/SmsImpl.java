package com.project.inventory.common.sms.service.impl;

import com.project.inventory.common.sms.service.Sms;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;


@Service(value = "smsImpl")
public class SmsImpl implements Sms {
   public static final String ACCOUNT_SID = "ACXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
   public static final String AUTH_TOKEN = "your_auth_token";
   public static final String FROM = "FROM";

   Logger logger = LoggerFactory.getLogger( SmsImpl.class);

   public SmsImpl() {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
   }

   @Override
   public Boolean sendSms(String number, String textMessage) {
      Message message = Message.creator(new PhoneNumber(number),
              new PhoneNumber(FROM),
              textMessage).create();
      logger.info(message.getSid());
      return true;
   }
}
