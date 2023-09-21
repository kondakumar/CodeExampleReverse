package com.code.codeexamplereverse.controller;

import com.code.codeexamplereverse.api.MessageApi;
import com.code.codeexamplereverse.model.Message;
import com.code.codeexamplereverse.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReverseController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageApi messageApi;


    @GetMapping("msg/reverse")
    public Message reverseMessage(){
        Message msg=messageApi.getMessage();
        return messageService.getMessage(msg);
    }
}
