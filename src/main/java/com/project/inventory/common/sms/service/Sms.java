package com.project.inventory.common.sms.service;

public interface Sms {
    public Boolean sendSms(String number, String message);
}
