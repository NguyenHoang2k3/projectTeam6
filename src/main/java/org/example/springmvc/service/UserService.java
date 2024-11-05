package org.example.springmvc.service;

import org.example.springmvc.dto.request.CreateUser;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService  extends UserDetailsService {
    void save(CreateUser createUser, MultipartFile avatar) throws IOException;
}
