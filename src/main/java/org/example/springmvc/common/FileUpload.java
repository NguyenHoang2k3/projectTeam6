package org.example.springmvc.common;

import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class FileUpload {
    private final String DIR = "upload/";

    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();
        File dest = new File(DIR + fileName);
        if(!dest.getParentFile().exists()){
            dest.getParentFile().mkdirs();
        }

        Files.write(dest.toPath(), file.getBytes());
        return DIR + fileName;

    }
}
