package com.SweetDreams.sweetDreams.Services;

import com.SweetDreams.sweetDreams.Models.Email;
import org.springframework.stereotype.Service;

@Service
public interface EmailSenderService {
    public void sendSimpleMessage(Email email);

    public void sendEmail(Email email) throws Exception;
}
