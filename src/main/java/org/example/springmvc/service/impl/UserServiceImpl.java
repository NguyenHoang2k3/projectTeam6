package org.example.springmvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.springmvc.common.FileUpload;
import org.example.springmvc.dto.request.CreateUser;
import org.example.springmvc.entity.User;
import org.example.springmvc.repository.UserRepository;
import org.example.springmvc.service.UserSecurity;
import org.example.springmvc.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl  implements UserService {

    private final UserRepository userRepository;
    private final FileUpload fileUpload;

    public void save(CreateUser createUser, MultipartFile avatar) throws IOException {

        String path = fileUpload.uploadFile(avatar);

        User user = new User();
        BeanUtils.copyProperties(createUser, user);
        user.setAvatar(path);
        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return new UserSecurity(user);
    }
}
