package org.example.springmvc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateUser {

    private Long id;

    @NotBlank(message = "Username is required!")
    private String username;

    @Length(min = 8, max = 12, message = "Password have length from {min} to {max}")
    private String password;

    @Pattern(regexp = "[a-zA-Z0-9@.]{5,30}", message = "Email invalid format!")
    private String email;

    @Pattern(regexp = "\\d{10}", message = "Phone invalid!")
    private String phone;
    private String address;
    private String role;

    private MultipartFile avatar;
}
