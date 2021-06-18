package com.wesfalc.planning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@SpringBootApplication
@RestController
public class Controller {

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/", method = {RequestMethod.POST, RequestMethod.GET})
    public String index(HttpServletRequest request) throws Exception {
        log.info("index page hit");

        return "-1";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/sampleCalc", method = {RequestMethod.POST, RequestMethod.GET})
    public String sampleCalc(HttpServletRequest request) throws Exception {
        log.info("Sample calcualtion.");

        return "Sample calculation.";
    }
}
