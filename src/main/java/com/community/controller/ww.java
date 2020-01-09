package com.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ww {
    @GetMapping("/ww")
    public String ww(){
        System.out.println(
                "---------------------"
        );
        return "redirect:community/templates/html.html";
    }
}
