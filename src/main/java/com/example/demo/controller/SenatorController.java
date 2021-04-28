package com.example.demo.controller;

import com.example.demo.service.SenatorData;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;

@RestController
public class SenatorController {
    private SenatorData senatorData;
    private static final Logger logger = LoggerFactory.getLogger(SenatorController.class);

    @Autowired
    SenatorController(SenatorData senatorData) {
        this.senatorData = senatorData;
    }

    @GetMapping(path = "/sd", produces = MediaType.APPLICATION_JSON_VALUE)
    @Scheduled(fixedDelay = 900000)
    ResponseEntity<JSONObject> getStart() {
        System.out.println("Senator API starts");
        JSONObject jsonObject = null;
        try {
            jsonObject = senatorData.scrapeData(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(java.time.LocalTime.now());
        logger.error(String.valueOf(java.time.LocalTime.now()));
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
