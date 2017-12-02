package com.dwd.controller;

import com.dwd.model.build.Build;
import com.dwd.persistence.BuildRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/builds")
public class BuildController {

    private static final Logger logger = Logger.getLogger(BuildController.class);

    @Autowired
    private BuildRepository buildRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody String json) {
        try {
            Build build = objectMapper.readValue(json, Build.class);
            buildRepository.save(build);
            logger.info("Saved build " + build.getBuildName() + " to database");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
