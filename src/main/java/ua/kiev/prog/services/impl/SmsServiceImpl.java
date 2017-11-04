package ua.kiev.prog.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.kiev.prog.entities.sms.Sms;
import ua.kiev.prog.repositories.sms.SmsRepository;
import ua.kiev.prog.services.SmsService;

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private SmsRepository smsRepository;

    @Override
    public void addSms(Sms sms) {
        smsRepository.save(sms);
    }
}
