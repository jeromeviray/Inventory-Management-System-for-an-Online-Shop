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
   public static final String ACCOUNT_SID = "ACf756c0b3173a674655ac1ceb14f4a355";
   public static final String AUTH_TOKEN = "d0b3bdc8b824ec0a859b45a5b4fad540";
   public static final String MESSAGE_SID = "MGa41583b291b3a3fe612f0eacd6860f35";

   Logger logger = LoggerFactory.getLogger( SmsImpl.class);

   public SmsImpl() {
      Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
   }

   @Override
   public Boolean sendSms(String number, String textMessage) {
      Message message = Message.creator(
              new PhoneNumber(number),
              MESSAGE_SID,
              textMessage
      ).create();
      logger.info(message.getSid());
      return true;
   }
}
