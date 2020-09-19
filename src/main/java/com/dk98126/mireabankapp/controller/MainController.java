package com.dk98126.mireabankapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class MainController {

    private final AtomicInteger counter = new AtomicInteger();

    @GetMapping("/main")
    public String mainPage(@RequestParam(value = "name", defaultValue = "Незнакомец") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("counter", counter.incrementAndGet());
        return "main";
    }

    @GetMapping("/registration")
    public String registrationPage(Model model) {
        return "registration";
    }

}
