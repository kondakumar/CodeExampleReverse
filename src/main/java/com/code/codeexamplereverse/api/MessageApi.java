package com.code.codeexamplereverse.api;

import com.code.codeexamplereverse.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MessageApi {

    @Autowired
    private RestTemplate restTemplate;

    public Message getMessage(){
        Message  message=restTemplate.getForObject("http://localhost:8080/codetest",Message.class);
        return  message;
    }
}
