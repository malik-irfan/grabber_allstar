package com.im.ai.grabber.controllers;

import com.im.ai.grabber.services.TestChatService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/grabber/api/v1")
public class CompanyController {

    /*private final ALMCompanyAssistService almCompanyAssistService;*/
    private final TestChatService testChatService;

    public CompanyController(TestChatService testChatService) {
        this.testChatService = testChatService;
    }

   /* public CompanyController(ALMCompanyAssistService almCompanyAssistService, TestChatService testChatService) {
        this.almCompanyAssistService = almCompanyAssistService;
        this.testChatService = testChatService;
    }*/

   /* @PostMapping(value = "/chat")
    public String companyEmployeeDetail(@RequestBody String message){
        System.out.println(message);
        return almCompanyAssistService.custExpEmpLeaves(message);
    }*/

    @PostMapping(value = "/testchat")
    public String testchat(@RequestBody String message){
        System.out.println(message);
        return testChatService.chatwithme(message);
    }

    @PostMapping(value = "/springchat")
    public String spChat(@RequestBody String message){
        System.out.println(message);
        return testChatService.springChat(message);
    }
}
