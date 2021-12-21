package com.tp.serviceley.server.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@AllArgsConstructor
public class MailContentBuilder {

    //Constructor based dependency injection
    private final TemplateEngine templateEngine;

    public String build(String message, String url, String btnName){
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("url", url);
        context.setVariable("buttonName", btnName);
        return templateEngine.process("mailTemplate", context);
    }
}