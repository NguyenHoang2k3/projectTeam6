package org.example.springmvc.controller.api;

import lombok.RequiredArgsConstructor;
import org.example.springmvc.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class UserApiController {

    private final PasswordEncoder encoder;

    public void createUser(){
        encoder.encode("1234");
    }

    @GetMapping("/user")
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

}
