package com.code.codeexamplereverse.service;

import com.code.codeexamplereverse.model.Message;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public Message getMessage(Message message) {
        String msg=message.getMessage();
        StringBuilder m =new StringBuilder(msg);
        StringBuilder reverse=m.reverse();
        message.setMessage(reverse.toString());
        return message;
    }

}
