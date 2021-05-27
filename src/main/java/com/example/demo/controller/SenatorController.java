package com.example.demo.controller;

import com.example.demo.service.SbbUsa;
import com.example.demo.service.SenatorData;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value="user", method = RequestMethod.GET)
    public @ResponseBody String  getItem(@RequestParam("url") String itemid){
        return itemid;
    }

    @GetMapping(value = "/test/{fromPreviuosYear}/{toPreviuosYear}")
    public ResponseEntity<Integer> getResult(@PathVariable int id){
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
