package org.example.springmvc.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.springmvc.dto.request.CreateUser;
import org.example.springmvc.entity.User;
import org.example.springmvc.repository.UserRepository;
import org.example.springmvc.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public String getForm(Model model) {
        CreateUser createUser = new CreateUser();
        model.addAttribute("user", createUser);
        return "user/add";
    }

    @PostMapping
    public String save(@Validated @ModelAttribute("user") CreateUser createUser,
                       BindingResult bindingResult,
                       @RequestPart("avatar") MultipartFile avatar) throws IOException {

        if(userRepository.existsByEmail(createUser.getEmail())) {
            bindingResult.
                    addError(new FieldError("user",
                            "email",
                            "Email is already in use!"));
        }
        if(userRepository.existsByUsername(createUser.getUsername())) {
            bindingResult.addError(
                    new FieldError("user",
                            "username",
                            "Username is already in use!")
            );
        }

        if(bindingResult.hasErrors()) {
            return "user/add";
        }

        userService.save(createUser, avatar);

        return "redirect:/user/view";
    }

    @GetMapping("/view")
    public String view( @RequestParam(value = "index", defaultValue = "1") Integer index,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        Model model) {

        Pageable pageable = PageRequest.of(index-1,size);

        Page<User> pageUser = userRepository.findAll(pageable);
        List<User> users = pageUser.getContent();

        model.addAttribute("users", users);
        model.addAttribute("currentPage", index);
        model.addAttribute("size", size);
        model.addAttribute("totalPages", pageUser.getTotalPages());

        return "user/view";
    }


    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new EntityNotFoundException("Not found entity with id: " + id);
        });
        CreateUser createUser = new CreateUser();
        BeanUtils.copyProperties(user, createUser);
        model.addAttribute("user", createUser);
        return "user/update";
    }

    @PostMapping("/update")
    public String update (@Validated @ModelAttribute("user") CreateUser createUser, BindingResult bindingResult, Model model) {

        if(userRepository.existsByEmail(createUser.getEmail())) {
            bindingResult.
                    addError(new FieldError("user",
                            "email",
                            "Email is already in use!"));
        }
        if(userRepository.existsByUsername(createUser.getUsername())) {
            bindingResult.addError(
                    new FieldError("user",
                            "username",
                            "Username is already in use!")
            );
        }

        if(bindingResult.hasErrors()) {
            return "user/update";
        }
        User user = new User();
        BeanUtils.copyProperties(createUser, user);
        userRepository.save(user);
        return "redirect:/user/view";
    }

    @GetMapping("/search")
    public String search( @RequestParam("search") String search,
                          Model model) {

        List<User> users =  userRepository.findByAllField("%"+search+"%");
        model.addAttribute("users", users);
        return "/user/view";
    }


}
