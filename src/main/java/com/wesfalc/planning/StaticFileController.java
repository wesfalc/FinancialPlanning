package com.wesfalc.planning;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StaticFileController
{

    @GetMapping("/")
    public String index(HttpServletRequest request) throws Exception {
        return "mainpage";
    }
}