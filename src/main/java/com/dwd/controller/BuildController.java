package com.dwd.controller;

import com.dwd.model.build.Build;
import com.dwd.persistence.BuildRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/builds")
public class BuildController {

    private static final Logger logger = Logger.getLogger(BuildController.class);

    @Autowired
    private BuildRepository buildRepository;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public void save(@RequestBody Build build) {
        buildRepository.save(build);
        logger.info("Saved build " + build.getBuildName() + " to database");
    }

    @RequestMapping(value = "/load", method = RequestMethod.GET)
    public ResponseEntity<Build> load(@RequestParam("name") String name) {
        Build build = buildRepository.findByBuildName(name);
        if (build == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(build, HttpStatus.OK);
        }
    }
}
