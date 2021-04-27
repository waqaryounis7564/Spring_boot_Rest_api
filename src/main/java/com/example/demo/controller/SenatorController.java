package com.example.demo.controller;

import com.example.demo.service.SenatorData;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class SenatorController {
    private SenatorData senatorData;

    @Autowired
    SenatorController(SenatorData senatorData) {
        this.senatorData = senatorData;
    }

    @GetMapping(path = "/sd", produces = MediaType.APPLICATION_JSON_VALUE)
    @Scheduled(fixedDelay = 1000)
    ResponseEntity<JSONObject> getStart() {
        JSONObject jsonObject = null;
        try {
            jsonObject = senatorData.scrapeData(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }
}
