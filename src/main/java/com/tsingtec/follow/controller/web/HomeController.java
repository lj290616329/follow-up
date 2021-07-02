package com.tsingtec.follow.controller.web;


import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Api(tags = "视图", description = "负责返回视图")
@Controller
@RequestMapping("/home")
public class HomeController {

    @GetMapping("/{str}")
    public String home(@PathVariable("str") String str) {
        return "index/" + str;
    }

    @GetMapping("/{str}/{str1}/{str2}")
    public String str(@PathVariable("str") String str, @PathVariable("str1") String str1, @PathVariable("str2") String str2) {
        return "page/" + str + "/" + str1 + "/" + str2;
    }
}
