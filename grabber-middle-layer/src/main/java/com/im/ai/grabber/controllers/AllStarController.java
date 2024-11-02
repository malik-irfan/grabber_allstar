package com.im.ai.grabber.controllers;

import com.im.ai.grabber.services.AllStarService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/grabber/api/v1")
public class AllStarController {

    private final AllStarService allStarService;

    public AllStarController(AllStarService allStarService) {
        this.allStarService = allStarService;
    }

    @PostMapping(value = "/services")
    public String spChat(@RequestBody String message){
        System.out.println(message);
        return allStarService.getServicesDetail(message);
    }
}
